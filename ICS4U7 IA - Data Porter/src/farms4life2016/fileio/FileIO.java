package farms4life2016.fileio;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import farms4life2016.dataprocessing.Job;

public class FileIO {
    
    //  https://www.javatpoint.com/how-to-read-excel-file-in-java
    static List<Job> list = new LinkedList<Job>();

    public static void main(String[] args) {
        init();
        exit();
    }

    /**
     * Test to see if I can read excel files
     */
    public static void init() {

        try { 

            //file reader 
            FileInputStream reader = new FileInputStream(".\\src\\farms4life2016\\init\\hardcode these jobs.xlsx");

            //create workbook to represent the file
            XSSFWorkbook wb = new XSSFWorkbook(reader);
            XSSFSheet sheet = wb.getSheetAt(0); //I'm guessing this means get the first sheet of the excel workbook

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { //I detest reading documentation pages to find a single method

                //make a new job
                Job job = new Job(); 

                job.setId( (int) sheet.getRow(i).getCell(0).getNumericCellValue()); //make sure when I make init file on exit, this style also follows
                job.setClient(sheet.getRow(i).getCell(1).getStringCellValue());
                job.setType(sheet.getRow(i).getCell(2).getStringCellValue().charAt(0));
                job.setName(sheet.getRow(i).getCell(3).getStringCellValue());
                job.setFile(sheet.getRow(i).getCell(4).getStringCellValue());

                list.add(job);

            }

            wb.close();

        } catch (Exception e) {
            e.printStackTrace();
            
        }

       

        for (Job job : list) {
            System.out.println(job);
        }


    }


    public static void exit() {

        try {

            //file writer 
            FileOutputStream writer = new FileOutputStream("output.xlsx");

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

            

            //fill in the table
            for (int i = 0; i < list.size(); i++) {

                row = sheet.createRow(i+1);
                Job j = list.get(i);
                int counter = 0;
                
                c = row.createCell(counter); //set id
                c.setCellValue(j.getId());
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
