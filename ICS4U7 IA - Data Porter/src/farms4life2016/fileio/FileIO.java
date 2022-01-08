package farms4life2016.fileio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import farms4life2016.dataprocessing.Job;

public class FileIO {
    
    //  https://www.javatpoint.com/how-to-read-excel-file-in-java
    //  https://www.geeksforgeeks.org/how-to-write-data-into-excel-sheet-using-java/

    /**
     * Read init file. This method is also proof of concept of an excel file reader.
     * @param list 
     */
    public static void init(List<Job> list) {

        try { 

            //file reader 
            FileInputStream reader = new FileInputStream(".\\src\\farms4life2016\\init\\hardcode these jobs.xlsx");

            //create workbook to represent the file
            XSSFWorkbook wb = new XSSFWorkbook(reader);
            XSSFSheet sheet = wb.getSheetAt(0); //I'm guessing this means get the first sheet of the excel workbook

            //loop through each row
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { //I detest reading documentation pages to find a single method

                //make a new job, each row represents one job
                Job job = new Job(); 

                //reading and assigning variables to the job instance
                job.setId( (int) sheet.getRow(i).getCell(0).getNumericCellValue()); //make sure when I make init file on exit, this style also follows
                job.setClient(sheet.getRow(i).getCell(1).getStringCellValue());
                job.setType(sheet.getRow(i).getCell(2).getStringCellValue().charAt(0));
                job.setName(sheet.getRow(i).getCell(3).getStringCellValue());
                job.setFile(sheet.getRow(i).getCell(4).getStringCellValue());

                //add to job list
                list.add(job);

            }

            //close io
            wb.close();

        } catch (IOException e) { //there should be no exceptions
            e.printStackTrace();
            
        }

        //this is so I can see what is going on
        for (Job job : list) {
            System.out.println(job);
        }


    }

    /**
     * Write an init file. This method is also proof of concept of an excel file writer.
     * @param list
     */
    public static void exit(List<Job> list) {

        try {

            //file writer 
            FileOutputStream writer = new FileOutputStream(".\\src\\farms4life2016\\init\\initialization.xlsx");

            //create a new workbook 
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("progress");

            //create headers (although I guess we don't really need them?)
            XSSFRow row = sheet.createRow(0);
            Cell c = row.createCell(0);
            c.setCellValue("Id");
            c = row.createCell(1);
            c.setCellValue("Client");
            c = row.createCell(2);
            c.setCellValue("Type");
            c = row.createCell(3);
            c.setCellValue("Name");
            c = row.createCell(4);
            c.setCellValue("File");

            
            //loop for every job in the list
            for (int i = 0; i < list.size(); i++) {

                //fill in the file with the job list
                row = sheet.createRow(i+1);
                Job j = list.get(i);
                int counter = 0;
                
                c = row.createCell(counter); //set id
                c.setCellValue(j.getId()); //this adds a double to the cell, others add strings
                counter++;

                c = row.createCell(counter); //set client
                c.setCellValue(j.getClient());
                counter++;

                c = row.createCell(counter); //set type
                c.setCellValue(j.getType() + ""); //convert from char to String
                counter++;

                c = row.createCell(counter); //set name
                c.setCellValue(j.getName());
                counter++;

                c = row.createCell(counter); //set file
                c.setCellValue(j.getFile());
                counter++;

            }
            
            //write to file
            wb.write(writer); //NOTE: THIS WILL OVERWRITE THE PREVIOUS FILE OF THE SAME NAME! THERE IS NO UNDO!

            //close resources
            wb.close();
            writer.close();

            //let me know if things are going smoothly
            System.out.println("Successful wrote to excel file.");
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        

    }


}
