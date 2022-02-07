package farms4life2016.fileio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import farms4life2016.dataprocessing.DLinkedList;
import farms4life2016.dataprocessing.Job;

public class FileIO {

    // https://www.javatpoint.com/how-to-read-excel-file-in-java
    // https://www.geeksforgeeks.org/how-to-write-data-into-excel-sheet-using-java/

    /**
     * Read init file. This method is also proof of concept of an excel file reader.
     * 
     * @param list
     */
    public static void init(DLinkedList list) {

        String[][] input = readGrid(".\\src\\farms4life2016\\init\\hardcode these jobs.xlsx", 6, -1);

        for (int i = 1; i < input.length; i++) {
            Job j = new Job();
            j.setId( (int)Double.parseDouble(input[i][0]));
            j.setClient(input[i][2]);
            j.setType(input[i][3].charAt(0));
            j.setName(input[i][1]);
            j.setFile(input[i][4]);
            j.setDate(Calendar.getInstance());
            list.add(j);
            
        }

    }

    /**
     * Write an init file. This method is also proof of concept of an excel file
     * writer.
     * 
     * @param list
     */
    public static void exit(DLinkedList list) {

        //sort data by id first
        Job.mergesort(list, Job.SORT_BY_ID);

        //get data from list and create headers in excel file
        String[][] temp = Job.convertListIntoArray(list), output = new String[temp.length+1][temp[0].length];
        output[0][0] = "Id";
        output[0][1] = "Name";
        output[0][2] = "Client";
        output[0][3] = "Type";
        output[0][4] = "File";
        output[0][5] = "Date";

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                output[i+1][j] = temp[i][j];
            }
        }
        
        //write stuff out
        writeGrid(".\\src\\farms4life2016\\init\\initialization.xlsx", output);


    }

    /**
     * Reads part of an Excel file. 
     * @param fileName
     * @param height -1 means read all the possible rows in the excel file.
     * @param width
     * @param sheetNum
     * @return
     */
    public static String[][] readGrid(String fileName, int width, int height, int sheetNum) {

        // variables
        String[][] output = null;

        try {

            // file reader
            FileInputStream reader = new FileInputStream(fileName);

            // create workbook to represent the file
            XSSFWorkbook wb = new XSSFWorkbook(reader);
            XSSFSheet sheet = wb.getSheetAt(sheetNum); 

            if (height == -1) height = sheet.getPhysicalNumberOfRows();

            output = new String[height][width]; 
            //yeah we have to swap w and h when creating the array due to how java stores information

            // assume input is valid TODO

            // loop through each row and column
            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    Cell c = sheet.getRow(i).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    // read in data and cast everything to String
                    if (c.getCellType().equals(CellType.STRING) || c.getCellType().equals(CellType.BLANK)) {
                        output[i][j] = c.getStringCellValue();
                    } else if (c.getCellType().equals(CellType.NUMERIC) || c.getCellType().equals(CellType.FORMULA)) {
                        output[i][j] = Double.toString(c.getNumericCellValue());
                    } else {
                        // assume that boolean and error cell types are never used
                        output[i][j] = "data type not supported";
                    }

                }

            } // end fors

            // close io
            wb.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // return data from spreadsheet
        return output;

    }

    /**
     * 
     * @param fileName
     * @param width
     * @param height
     * @return
     */
    public static String[][] readGrid(String fileName, int width, int height) {
        return readGrid(fileName, width, height, 0);
    }

    /**
     * 
     * @param fileName
     * @param data
     * @return
     */
    public static boolean writeGrid(String fileName, String[][] data) {

        boolean success = false;

        try {

            // file writer
            FileOutputStream writer = new FileOutputStream(fileName);

            // create a new workbook
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Sheet 1");

            // loop through all the data
            for (int i = 0; i < data.length; i++) {

                // make a row
                XSSFRow row = sheet.createRow(i);

                for (int j = 0; j < data[i].length; j++) {
                    Cell c = row.createCell(j);

                    // convert strings into other types if possible
                    if (isNumeric(data[i][j])) {
                        c.setCellValue(Double.parseDouble(data[i][j]));
                    } else if (data[i][j].equals("")) {
                        c.setBlank();
                    } else {
                        c.setCellValue(data[i][j]); // otherwise just write string into the cell
                    }

                }

            }

            // write to file ***NOTE: THIS WILL OVERWRITE THE PREVIOUS FILE OF THE SAME
            // NAME! THERE IS NO UNDO!!!
            wb.write(writer);

            // close resources
            wb.close();
            writer.close();

            // successful io
            success = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        // let me know if things are going smoothly
        return success;

    }

    /**
     * Does not overwrite data into an Excel file if that file already exists.
     * 
     * @param fileName
     * @param data
     * @return
     */
    public static boolean safeWriteGrid(String fileName, String[][] data) {

        // check if file already exists
        File f = new File(fileName);
        if (f.exists()) {
            return false;
        }

        return writeGrid(fileName, data);
    }

    /**
     * Determines whether this String is just a Double stored as a String
     * 
     * @param s A non-null String to be tested
     * @return true if this String is parsable by the Double class, false otherwise.
     */
    public static boolean isNumeric(String s) {
        boolean answer;

        try {
            Double.parseDouble(s);
            answer = true;
        } catch (NumberFormatException e) {
            answer = false;
        }

        return answer;
    }

    // https://www.javatpoint.com/how-to-read-xml-file-in-java

}
