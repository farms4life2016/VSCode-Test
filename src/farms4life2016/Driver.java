package farms4life2016;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
/*
import java.util.logging.Level;
import java.util.logging.Logger;*/

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.Job;
import farms4life2016.dataprocessing.RunJob;
import farms4life2016.fileio.DataPorterConfig;
import farms4life2016.fileio.FileIO;
import farms4life2016.fileio.PorterConfig;


public class Driver {
	
    /**
     * This class is solely used for the main method.
     * @param args 
     */
    public static void main(String[] args) {

        //Controller.run(); //I spent a lot of time trying to sync github with my project. I'm glad that it worked out in the end.

        try {
            System.out.println(Arrays.toString("✓".getBytes("UTF-8")));
            System.out.println(new String("✓".getBytes("UTF-8"), "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            File folder = new File(".\\init\\menu.txt");
            
            System.out.println(folder.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        
    } //end main method  
    
    public static void printA(String[][] a) {
        for (String[] strings : a) {
            for (String strings2 : strings) {
                System.out.print(strings2 + "\t\t");
            }

            System.out.println();
            
        }
    }

    public static void readXMLTest() {

        

        //FileIO.readXML(".\\client-data-files\\DRX\\I_Solicitation.xml");
        try {
            //read xml file
            DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\DRX\\I_Plant.xml");      

            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\DRX\\I_Solicitation.xml");
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\DRX\\I_Supplier.xml");            
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\DRX\\I_BOM.xml");
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\DRX\\E_BOM.xml");            
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\DRX\\E_RequestFile.xml");
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\GYU\\I_Plant.xml");            
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\GYU\\I_Solicitation.xml");
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\GYU\\I_Supplier.xml");            
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\GYU\\I_BOM.xml");
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\GYU\\E_BOM.xml");            
            //DataPorterConfig dataPorterConfig= FileIO.readXML(".\\client-data-files\\GYU\\E_RequestFile.xml");

            //error with reading xml file
            if(dataPorterConfig == null){
                System.out.println("The config file is not right.");
                return;
            }
            
            //get PorterConfig
            List<PorterConfig> porterConfigs = dataPorterConfig.getPorterConfigs();  

            //loop through each porterconfig in the file
            //there may be multiple. call porterConfigs.size() for length
            for (PorterConfig porterConfig : porterConfigs) {  

                //this part is all about reading the .txt file. TODO use BufferedReader

                //get the data file, this is an example, you can use other methods to read the file
                String dataFilename =  dataPorterConfig.getWorkingFolder()
                    + porterConfig.getRemotePath() + porterConfig.getFilename();
                //System.out.println("The data file : " + dataFilename);
                File file = new File(dataFilename);
                
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
                myReader.close();

                //end of file reading

            }
        } catch (Exception e) {  

            //log the error instead of printing to console
            //logger.error(e.getMessage());
            
        }  

      
    }

    public static void runXMLwithDB() {
        
        Job job = new Job (1, "Test", "DRX", 'E', "E_BOM.xml", null);

        //Job job = new Job (1, "Test", "DRX", 'I', "I_Plant.xml", null);

        RunJob runJob = new RunJob(Controller.LOGGER4J);

        try {

            String errorMessage = runJob.run(job);

            if(!errorMessage.isBlank()){

                System.out.println(errorMessage);

            } else {

                System.out.println("The job ran successfully");

            }    

        } catch (Exception e) {  

            Controller.LOGGER4J.error(e.getMessage());

        }          
    }

}
