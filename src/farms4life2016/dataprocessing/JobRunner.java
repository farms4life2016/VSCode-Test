package farms4life2016.dataprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBException;

import farms4life2016.database.DatabaseIO;
import farms4life2016.fileio.DataPorterConfig;
import farms4life2016.fileio.FileIO;
import farms4life2016.fileio.PorterConfig;

public class JobRunner {

    private Job runThisJob;

    public JobRunner(Job j) {
        runThisJob = j;
    }


    public void run() {

        boolean successful = true;

        try { //match job type with respective method
            if (runThisJob.getType() == 'I') {
                successful = importFromFile();
            } else {
                successful = exportToFile();
            }

        //the entire job fails if the config file cannot be read in
        } catch (JAXBException e) { //TODO more errors
            successful = false;
            Controller.LOGGER4J.error("The XML file for Job #" + runThisJob.getId() + " is missing or written using the incorrect format: " + e.getMessage());
            Controller.mainMenu.errorBar.increaseErrorCount("The XML file for Job #" + runThisJob.getId() + " is missing or written using the incorrect format.", runThisJob.getId());
        }

        if (successful) { //also show successes
            Controller.mainMenu.errorBar.showSuccess(runThisJob.getId());
        }

    }

    /**
     * 
     * @return true means that the job was run successfully without any file IO problems
     */
    private boolean exportToFile() throws JAXBException { 

        DataPorterConfig dpc = getConfigFromXML();
        List<PorterConfig> portConfig = dpc.getPorterConfigs();
        boolean successful = true;

        //loop through all subjobs in the config file
        for (PorterConfig pc : portConfig) {

            //get export file's path
            String exportPath = dpc.getWorkingFolder() + pc.getRemotePath() + pc.getFilename();
            File target = new File(exportPath);

            try {
                
                //read data from db and convert to 2D str array
                List<String[]> dbData = DatabaseIO.readData(pc.getDbSproc(), runThisJob.getClient());
                String[][] data = new String[dbData.size()][pc.getColumns().size()];
                for (int i = 0; i < data.length; i++) {
                    data[i] = dbData.get(i);
                }

                //delete the file if it already exists to prevent overriding issues
                if (target.exists()) {
                    Files.delete(target.toPath());
                } //do we even need this if the fileio methods default to overwriting existing files????

                //write txt
                if (FileIO.getFileExt(pc.getFilename()).equals("txt")) {
                    FileIO.writeTableToTxt(target, data, pc.getDelimiter());
                //write excel
                } else if (FileIO.getFileExt(pc.getFilename()).equals("xlsx")) {
                    FileIO.writeGrid(exportPath, data);
                }

                //no need for timestamp or renaming of files if successful

            //each individual sub-job should handle errors so the next sub-jobs can still run to completion 
            } catch (SQLException | IOException e) { 

                successful = false;

                //handle the failure to read db or write to a file
                if (e instanceof SQLException) {
                    Controller.LOGGER4J.error("Failed to read from database while trying to create " + target.getName() + " for Job #" + runThisJob.getId() + ": " +  e.getMessage());
                    Controller.mainMenu.errorBar.increaseErrorCount("Failed to read from database while trying to create " + target.getName() + " for Job #" + runThisJob.getId() + ".", runThisJob.getId());
               
                } else if (e instanceof IOException) {
                    Controller.LOGGER4J.error("Failed to access and overwrite " + target.getName() + " for Job #" + runThisJob.getId() + ": " + e.getMessage());
                    Controller.mainMenu.errorBar.increaseErrorCount("Failed to access and overwrite " + target.getName() + " for Job #" + runThisJob.getId() + ".", runThisJob.getId());

                }

                //move the partially constructed file to the failure folder.
                if (target.exists()) {
                    String destination = target.getParent() + "\\Failure\\" + //insert date before the dot in the extension
                    target.getName().substring(0, target.getName().lastIndexOf('.')) + '-'//assume file has extension
                    + Job.SHORT_DATE_FORMATER.format(Calendar.getInstance().getTime()) + target.getName().substring(target.getName().lastIndexOf('.'));

                    try {
                        FileIO.moveFiles(target.getPath(), destination); //this will throw io exception :(
                    } catch (IOException ex) {
                        Controller.LOGGER4J.error("Failed to move " + target.getName() + "to the failure folder for Job #" + runThisJob.getId() + ": " + ex.getMessage());
                        Controller.mainMenu.errorBar.increaseErrorCount("Failed to move " + target.getName() + "to the failure folder for Job #" + runThisJob.getId() + ".", runThisJob.getId());

                    }
                    
                }

            } //note that there might be other exceptions such as the XML file not reading properly. 
            //these are not handled in this catch block

        } // end for

        return successful;

    } //end export

    private boolean importFromFile() throws JAXBException { 

        DataPorterConfig dpc = getConfigFromXML(); //read XML
        List<PorterConfig> portConfig = dpc.getPorterConfigs();
        boolean successful = true;

        //loop through all subjobs in the config file
        for (PorterConfig pc : portConfig) {
            
            List<File> files = new ArrayList<>();

            //handle *.txt or *.xlsx 
            if (pc.getFilename().charAt(0) == '*') {

                //get all files in the folder
                File folder = new File(dpc.getWorkingFolder() + pc.getRemotePath());
                File[] folderContents = folder.listFiles();

                for (File f : folderContents) {
                    //only add files that have the same extension as *.txt or *.xlsx
                    if (FileIO.getFileExt(f).equals(FileIO.getFileExt(pc.getFilename()))) {
                        files.add(f);
                    }
                }

            } else {
                //only a one file specified
                files.add(new File(dpc.getWorkingFolder() + pc.getRemotePath() + pc.getFilename()));
            }

            //loop through all the sub-job files
            for (File f : files) {

                try {

                    int loadingID = DatabaseIO.generateLoadingId(); //same for all data in a file
                    String[][] data = null;
                    
                    //read txt files
                    if (FileIO.getFileExt(f).equals("txt")) {

                        List<String> input = FileIO.readAllTxt(f.getPath());

                        //append loading id and other ids to the start of the string in the list 
                        for (int i = 0; i < input.size(); i++) { //then I don't have to swap columns lol
                            String appendToStart = Integer.toString(loadingID) + pc.getDelimiter() + (i+1) + pc.getDelimiter();
                            input.set(i, appendToStart + input.get(i));                             //db starts from 1 not 0
                        }

                        //convert to string array
                        data = new String[input.size()][];
                        for (int j = 0; j < data.length; j++) { //data gets split based on delimiter
                            data[j] = input.get(j).split(pc.getDelimiter());
                        }

                    //reading excel files
                    } else if (FileIO.getFileExt(f).equals("xlsx")) {

                        String[][] temp = FileIO.readGrid(f.getPath(), pc.getColumns().size(), -1);
                        data = new String[temp.length][temp[0].length+2];

                        //add loading id and row num; requires the 2D array to expand
                        for (int i = 0; i < data.length; i++) {
                            for (int j = 0; j < data[0].length; j++) {
                                if (j == 0) data[i][j] = Integer.toString(loadingID);
                                else if (j == 1) data[i][j] = Integer.toString(j+1); //db starts from 1 not 0
                                else data[i][j] = temp[i][j-2];
                            }
                        }
                        
                    }

                    //write to database
                    DatabaseIO.WriteData(pc.getDbTable(), data);

                    //move to sucess folder TODO  !!CURRENTLY COPYING!!! 
                    String destination = f.getParent() + "\\Success\\" +  //insert date before the dot in the extension
                        f.getName().substring(0, f.getName().lastIndexOf('.')) + '-'//assume file has extension
                        + Job.SHORT_DATE_FORMATER.format(Calendar.getInstance().getTime()) + f.getName().substring(f.getName().lastIndexOf('.'));

                    FileIO.copyFiles(f.getPath(), destination);

                } catch (SQLException | IOException e) { //handle errors regarding the current file

                    successful = false;

                    //handle the failure to read db or write to a file
                    if (e instanceof SQLException) {
                        Controller.LOGGER4J.error("Failed to write to database after reading " + f.getName() + " for Job #" + runThisJob.getId() + ": " +  e.getMessage());
                        Controller.mainMenu.errorBar.increaseErrorCount("Failed to write to database after reading " + f.getName() + " for Job #" + runThisJob.getId() + ".", runThisJob.getId());
                    } else if (e instanceof IOException) {
                        Controller.LOGGER4J.error("Failed to read " + f.getName() + " for Job #" + runThisJob.getId() + ": " + e.getMessage());
                        Controller.mainMenu.errorBar.increaseErrorCount("Failed to read " + f.getName() + " for Job #" + runThisJob.getId() + ".", runThisJob.getId());

                    }

                    String destination = f.getParent() + "\\Failure\\" + //insert date before the dot in the extension
                        f.getName().substring(0, f.getName().lastIndexOf('.')) + '-' //assume file has extension
                        + Job.SHORT_DATE_FORMATER.format(Calendar.getInstance().getTime()) + f.getName().substring(f.getName().lastIndexOf('.'));

                    //move to failure folder TODO  !!CURRENTLY COPYING!!! 
                    try {
                        FileIO.copyFiles(f.getPath(), destination); //this will throw io exception :(
                    } catch (IOException ex) {
                        Controller.LOGGER4J.error("Failed to move " + f.getName() + "to the failure folder for Job #" + runThisJob.getId() + ": " + ex.getMessage());
                        Controller.mainMenu.errorBar.increaseErrorCount("Failed to move " + f.getName() + "to the failure folder for Job #" + runThisJob.getId() + ".", runThisJob.getId());

                    }
                
                } //note that there might be other exceptions such as the XML file not reading properly. 
                //these are not handled in this catch block

            } //end file for

        } //end pc config for

        return successful;

    } //end import from file

    public DataPorterConfig getConfigFromXML() throws JAXBException {
        
        //shorthand for reading xml file
        DataPorterConfig dpc = FileIO.readXML(".\\client-data-files\\" + runThisJob.getClient() + "\\" + runThisJob.getFile());

        return dpc;
    }

}