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
        } catch (JAXBException e) { 
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
                
                //read data from db
                List<String[]> dbData = DatabaseIO.readData(pc.getDbSproc(), runThisJob.getClient());

                //for excel files, also add the headers to the start of this list
                if (FileIO.getFileExt(pc.getFilename()).equals("xlsx")) {
                    String[] headers = new String[pc.getColumns().size()];
                    for (int k = 0; k < headers.length; k++) {
                        headers[k] = pc.getColumns().get(k).getName();
                    }
                    dbData.add(0, headers);
                }

                // convert to 2D str array
                String[][] data = new String[dbData.size()][pc.getColumns().size()];
                for (int i = 0; i < data.length; i++) {
                    data[i] = dbData.get(i);
                    
                } 
                
                //delete the file if it already exists to prevent overriding issues
                if (target.exists()) {
                    Files.delete(target.toPath());
                } //do we even need this if the fileio methods default to overwriting existing files????


                //write excel
                if (FileIO.getFileExt(pc.getFilename()).equals("xlsx")) {

                    //my client also wants me to write in the headers
                    FileIO.writeGrid(exportPath, data);

                //any other extension means txt
                } else {
                    FileIO.writeTableToTxt(target, data, pc.getDelimiter());
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
                    String destination = target.getParent() + "\\Failure\\" + appendTimestamp(target.getName());

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
                    String[][] data = null, temp = null;
                    
                    //read excel files
                    if (FileIO.getFileExt(f).equals("xlsx")) {

                        temp = FileIO.readGrid(f.getPath(), pc.getColumns().size(), -1);
                        
                    //any other ext means read txt
                    } else {

                        List<String> input = FileIO.readAllTxt(f.getPath());

                        //convert to string array
                        temp = new String[input.size()][];
                        for (int j = 0; j < temp.length; j++) { //data gets split based on delimiter
                            temp[j] = input.get(j).split(pc.getDelimiter());
                        } 

                    }

                    //add loading id and row num; requires the 2D array to expand
                    data = new String[temp.length][temp[0].length+2];
                    for (int i = 0; i < data.length; i++) {

                        //add loading id and row num to each array
                        data[i][0] = Integer.toString(loadingID);
                        data[i][1] = Integer.toString(i+1); //db starts from 1 not 0

                        //copy the rest
                        for (int j = 2; j < data[0].length; j++) {
                            data[i][j] = temp[i][j-2];
                        }
                    }

                    //write to database
                    DatabaseIO.WriteData(pc.getDbTable(), data);

                    //move to sucess folder TODO  !!CURRENTLY COPYING!!! 
                    String destination = f.getParent() + "\\Success\\" + appendTimestamp(f.getName());

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

                    String destination = f.getParent() + "\\Failure\\" + appendTimestamp(f.getName());

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

    public String appendTimestamp(String fileName) {

        //file has no extension
        if (fileName.lastIndexOf('.') == -1) {
            return fileName + '-' + Job.SHORT_DATE_FORMATER.format(Calendar.getInstance().getTime());

        } else {
            return fileName.substring(0, fileName.lastIndexOf('.')) + Job.SHORT_DATE_FORMATER.format(Calendar.getInstance().getTime()) + fileName.substring(fileName.lastIndexOf('.'));
        }

    }

}