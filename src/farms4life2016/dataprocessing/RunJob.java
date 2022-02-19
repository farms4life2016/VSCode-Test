package farms4life2016.dataprocessing;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;

import farms4life2016.databaseRepo.DbRepository;
import farms4life2016.fileio.*;

public class RunJob {   

    private Job toRun;

    private DbRepository dbRepository; //these two vars are not actually needed
    protected Logger logger;

    public RunJob(Logger logger) {   
        this.logger = logger;
        this.dbRepository = new DbRepository(); 
    }  
    
    public RunJob(Job j) {
        
    }

    //if the errorMessage is empty, the job runs successfully.
    public String run(Job job) throws Exception {

        String errorMessage = "";
        if (job.getType() == 'I') {
            errorMessage = importFromFile(job);
        } else {
            errorMessage = exportToFile(job);
        }
        
        return errorMessage;
    } 
    
    public String exportToFile(Job job) throws Exception {
        String errorMessage = "";

        try {
            //Read the config xml file
            DataPorterConfig dataPorterConfig = FileIO.readXML(".\\client-data-files\\" + 
                job.getClient() + "\\" + job.getFile());     
            if(dataPorterConfig == null){
                errorMessage = "The config file is not right.";
                return errorMessage;
            }
            
            //get PorterConfig in the config file
            List<PorterConfig> porterConfigs = dataPorterConfig.getPorterConfigs();  
            for(PorterConfig porterConfig : porterConfigs) 
            {  
                //get the data filename
                String dataFilename =  dataPorterConfig.getWorkingFolder()
                    + porterConfig.getRemotePath() + porterConfig.getFilename();

                //todo: Delete the file if the file exists.

                //Get data from database defined by dbSproc(stored procedure)
                List<String[]> dbData = dbRepository.readData(porterConfig.getDbSproc(), job.getClient());
                //List<String[]> dbData = dbRepository.readData("Upload_MultipleLevelBom_Extract", "DRX");
                /*remove below for test*/
                for(String[] row : dbData) {
                    System.out.println(Arrays.toString(row));
                }
                /*End: remove below for test*/
                
                //todo: Write to the file. (txt or excel file)
            }
        } catch (Exception e) {  
            logger.error(e.getMessage());

            //todo: Move the file to Failure folder if the file exsists.

        }  

        return errorMessage;
    }    

    public String importFromFile(Job job) throws Exception {
        String errorMessage = "";

        try {
            //Read the config xml file
            DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\" + 
                job.getClient().trim() + "\\" + job.getFile().trim());  
            if(dataPorterConfig == null){
                errorMessage = "The config file is not right.";
                return errorMessage;
            }
            
            //get PorterConfig in the config file
            List<PorterConfig> porterConfigs=dataPorterConfig.getPorterConfigs();  
            for(PorterConfig porterConfig:porterConfigs) 
            {  
                //get the data filename, it maybe include "*"
                String dataFilename =  dataPorterConfig.getWorkingFolder()
                    + porterConfig.getRemotePath() + porterConfig.getFilename();
                //System.out.println("The data file : " + dataFilename);

                //todo: get a loop for each file based the above filename
                //For now, just use dataFilename as one filename
                String[] sFilenames = {dataFilename};
                for (String sFilename : sFilenames) {  
                    //todo: Read the file. (txt or excel file)

                    //Get LoadingId from Database
                    int iLoadingID = dbRepository.generateLoadingId();
                    //System.out.println(iLoadingID);

                    //todo: add {LoadingID}, RowNum(start from 1) to each row. 

                    //todo: add to a table defined by DbTable

                    //todo: Move/Copy the file to Success folder
                    
                }
            }
        } catch (Exception e) {    
            logger.error(e.getMessage());
            
            //todo: Move/Copy the file to Failure folder if the file exsists.
        }  

        return errorMessage;
    }     
}
