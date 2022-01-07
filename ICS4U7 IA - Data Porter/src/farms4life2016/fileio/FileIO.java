package farms4life2016.fileio;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import farms4life2016.dataprocessing.Job;

public class FileIO {
    
    //  https://www.javatpoint.com/how-to-read-excel-file-in-java


    public static void main(String[] args) {

        List<Job> list = new LinkedList<Job>();

        //test();
        
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

        System.out.println(list.isEmpty());

        for (Job job : list) {
            System.out.println(job);
        }


    }

    public static void test() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter("output location.txt"))) {
            br.write("My favourite class is the spy.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }



}
