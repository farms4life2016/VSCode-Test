package farms4life2016.dataprocessing;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import farms4life2016.database.DatabaseIO;
import farms4life2016.fileio.DataPorterConfig;
import farms4life2016.fileio.FileIO;
import farms4life2016.fileio.PorterConfig;

public class JobRunner {

    private Job runThisJob;
    private String errorMessage;

    public JobRunner(Job j) {
        runThisJob = j;
    }

    public void run() throws Exception {

        if (runThisJob.getType() == 'I') {
            importFromFile();
        } else {
            exportToFile();
        }

    }

    public void exportToFile() throws Exception {

        DataPorterConfig dpc = getConfigFromXML();
        List<PorterConfig> portConfig = dpc.getPorterConfigs();

        //loop through all subjobs in the config file
        for (PorterConfig pc : portConfig) {

            //get export file's path
            String exportPath = dpc.getWorkingFolder() + pc.getRemotePath() + pc.getFilename();

            //delete the file if it already exists to prevent overriding issues
            File target = new File(exportPath);
            if (target.exists()) {
                Files.delete(target.toPath());
            } //do we even need this if the fileio methods default to overwriting existing files????

            //read data from db
            List<String[]> dbData = DatabaseIO.readData(pc.getDbSproc(), runThisJob.getClient());

            //TODO write to file


        }
    }

    public void importFromFile() throws Exception {

        DataPorterConfig dpc = getConfigFromXML(); //read XML
        List<PorterConfig> portConfig = dpc.getPorterConfigs();

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
                    if (FileIO.getFileExt(f.getName()).equals(FileIO.getFileExt(pc.getFilename()))) {
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
                    if (FileIO.getFileExt(f.getName()).equals("txt")) {

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
                    } else if (FileIO.getFileExt(f.getName()).equals("xlsx")) {

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

                    //move to sucess folder TODO with current timestamp? !!CURRENTLY COPYING!!! 
                    FileIO.copyFiles(f.getPath(), f.getParent() + "\\Success\\" + f.getName() + Job.SHORT_DATE_FORMATER.format(Calendar.getInstance().getTime()));
                    
                } catch (Exception e) { //handle errors regarding the current file

                    //log message TODO and also display on menu
                    Controller.LOGGER4J.error(e.getMessage());

                    //move to the failure folder with date stamp
                    FileIO.copyFiles(f.getPath(), f.getParent() + "\\Failure\\" + f.getName() + Job.SHORT_DATE_FORMATER.format(Calendar.getInstance().getTime()));
                    
                } //note that there might be other exceptions such as the XML file not reading properly. 
                //these are not handled in this catch block

            }
        }

    } //end import from file

    public DataPorterConfig getConfigFromXML() throws FileNotFoundException {

        //read xml file
        DataPorterConfig dpc = FileIO.readXML(".\\client-data-files\\" + runThisJob.getClient() + "\\" + runThisJob.getFile());

        if (dpc == null) { //null check
            errorMessage = "The config file is not right.";
            throw new FileNotFoundException("The configuration file is missing or written using the incorrect format.");
        }

        return dpc;
    }

}