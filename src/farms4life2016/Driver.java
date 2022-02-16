package farms4life2016;

import java.io.File;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
/*
import java.util.logging.Level;
import java.util.logging.Logger;*/

import java.awt.Rectangle;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import farms4life2016.dataprocessing.Controller;

import farms4life2016.dataprocessing.DLinkedList;
import farms4life2016.dataprocessing.Job;
import farms4life2016.fileio.*;


public class Driver {
	
    /**
     * This class is solely used for the main method.
     * @param args 
     */
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Driver.class);
        
        Controller.run(); //I spent a lot of time trying to sync github with my project. I'm glad that it worked out in the end.

        //FileIO.readXML(".\\client-data-files\\DRX\\I_Solicitation.xml");
        try {
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
            if(dataPorterConfig == null){
                System.out.println("The config file is not right.");
                return;
            }
            
            //get PorterConfig
            List<PorterConfig> porterConfigs=dataPorterConfig.getPorterConfigs();  
            for(PorterConfig porterConfig:porterConfigs) 
            {  
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
            }
        } catch (Exception e) {  
            logger.error(e.getMessage());
            //e.printStackTrace();  
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

    

}
