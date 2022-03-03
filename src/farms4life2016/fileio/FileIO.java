package farms4life2016.fileio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.DLinkedList;
import farms4life2016.dataprocessing.Job;


public class FileIO {

    // https://www.javatpoint.com/how-to-read-excel-file-in-java
    // https://www.geeksforgeeks.org/how-to-write-data-into-excel-sheet-using-java/

    public static int nextId = 1;

    private FileIO() {} //this class is strictly static

    /**
     * Read the init file
     * @throws IOException
     */
    public static void init() throws IOException {

        List<String> list = readAllTxt(".\\init\\init.txt");  
        nextId = list.size() + 1;


        for (int i = 0; i < list.size(); i++) {
            String[] items = list.get(i).split("\t");
            Job j = new Job();
            
            j.setActive(items[6].trim());

            //only add jobs that have not been deleted
            if (j.isActive()) {
                j.setId(Integer.parseInt(items[0]));
                j.setName(items[1]);
                j.setClient(items[2]); 
                j.setType(items[3].charAt(0));
                j.setFile(items[4]);;
                j.setDate(items[5]);

                Controller.jobList.add(j);
            }

        }

    }

    /**
     * Edit a job that is already stored on the init file.
     * This method basically copies down the changes made through
     * the GUI into a file
     * @param j
     * @throws IOException
     */
    public static void edit(Job j) throws IOException {

        //use randomaccessfile
        RandomAccessFile initFile = new RandomAccessFile(".\\init\\init.txt", "rw");
        
        String output = "";
        output += j.getId();
        output += "\t" + j.getName();
        output += "\t" + j.getClient();
        output += "\t" + j.getType();
        output += "\t" + j.getFile();
        output += "\t" + j.getLongDate();
        output += "\t" + j.getActiveString() + " ";
        String temp = "\r\n";

        for (int i = output.length(); i < 118; i++) {
            temp = ' ' + temp;
        }
        output += temp;        

        //clear previous data
        initFile.setLength((nextId-1)*120);
        initFile.seek((j.getId()-1)*120);
        initFile.write(output.getBytes());

        initFile.close();

    }

    /**
     * Similar to edit, but a new ID is assigned to the
     * job because we are adding a job
     * @param j
     * @throws IOException
     */
    public static void add(Job j) throws IOException {
        nextId++;
        edit(j);
    }

    /**
     * Write an init file. This method is also proof of concept of an excel file
     * writer.
     * 
     * @param list
     */
    public static void exit(DLinkedList list) throws IOException {

        // sort data by id first
        Job.mergesort(list, Job.SORT_BY_ID);

        // get data from list and create headers in excel file
        String[][] temp = Job.convertListIntoArray(list), output = new String[temp.length + 1][temp[0].length];
        output[0][0] = "Id";
        output[0][1] = "Name";
        output[0][2] = "Client";
        output[0][3] = "Type";
        output[0][4] = "File";
        output[0][5] = "Date";
        output[0][6] = "Status";

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                output[i + 1][j] = temp[i][j];
            }
        }

        // write stuff out
        writeGrid(".\\init\\initialization.xlsx", output);

    }

    /**
     * Reads part of an Excel file.
     * 
     * @param fileName
     * @param height   -1 means read all the possible rows in the excel file.
     * @param width
     * @param sheetNum
     * @return
     */
    public static String[][] readGrid(String fileName, int width, int height, int sheetNum) throws IOException {

        // variables
        String[][] output = null;

        // file reader
        FileInputStream reader = new FileInputStream(fileName);

        // create workbook to represent the file
        XSSFWorkbook wb = new XSSFWorkbook(reader);
        XSSFSheet sheet = wb.getSheetAt(sheetNum);

        if (height == -1)
            height = sheet.getPhysicalNumberOfRows();

        output = new String[height][width];
        // yeah we have to swap w and h when creating 
        // the array due to how java stores information

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

        // return data from spreadsheet
        return output;

    }

    /**
     * Read a rectangular selection on an excel workbook. Only the first sheet will be read.
     * @param fileName
     * @param width
     * @param height
     * @return
     */
    public static String[][] readGrid(String fileName, int width, int height) throws IOException {
        return readGrid(fileName, width, height, 0);
    }

    /**
     * Writes a rectangular selection onto an Excel workbook
     * @param fileName
     * @param data
     * @return
     */
    public static boolean writeGrid(String fileName, String[][] data) throws IOException {

        boolean success = false;

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
    public static boolean safeWriteGrid(String fileName, String[][] data) throws IOException {

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

    //https://www.javatpoint.com/jaxb-unmarshalling-example
    /**
     * Reads an XML file and constructs an Object to with the data from the file.
     * @param filePath the file's path
     * @return
     */
    public static DataPorterConfig readXML(String filePath) throws JAXBException {

        DataPorterConfig dataPorterConfig = null;
       
        // creating a file object and parsing an XML file
        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(DataPorterConfig.class);  
    
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  

        //this one line can throw three different exceptions
        try {
            dataPorterConfig = (DataPorterConfig) jaxbUnmarshaller.unmarshal(file);  
        } catch (Exception e) {
            throw new JAXBException(e);
        }
        

        return dataPorterConfig;
        
    }

    /**
     * Copies a file to another destination. The destination file will be automatically created
     * if it does not yet exist, but this method will never override an existing file.
     * @param originalFile The name of the original file plus its path
     * @param destinationFile The name of the output file plus its path
     * @throws IOException Please handle in the class that is calling this method
     */
    public static void copyFiles(String originalFile, String destinationFile) throws IOException {

        File original = new File(originalFile);
        File destination = new File(destinationFile);

        Files.copy(original.toPath(), destination.toPath());

    }

    /**
     * Moves a file to another destination, renaming the moved file if
     * the filename specified by {@code destination} is different from {@code originalFile}
     * @param originalFile
     * @param destinationFile
     * @throws IOException
     */
    public static void moveFiles(String originalFile, String destinationFile) throws IOException {
        File original = new File(originalFile);
        File destination = new File(destinationFile);

        Files.move(original.toPath(), destination.toPath());
    }

    /**
     * Deletes a file as Minecraft would call it, "for a very very long time!"
     * @param target
     * @throws IOException
     */
    public static void deleteFile(String target) throws IOException {
        File toDelete = new File(target);
        Files.delete(toDelete.toPath()); //say bye bye! THERE IS NO UNDO!!!
    }

    /**
     * Reads all the lines in a txt file. I think it also works for
     * non-txt files, but you may end up with complied gibberish 
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> readAllTxt(String filePath) throws IOException {

        File f = new File(filePath);
        return Files.readAllLines(f.toPath());

    }

    /**
     * Writes the contents of a 2D string array to a file using a specified delimiter
     * @param filepath
     * @param data
     * @param separator
     * @throws IOException
     */
    public static void writeTableToTxt(String filePath, String[][] data, String separator) throws IOException {
        writeTableToTxt(new File(filePath), data, separator);
    }

    /**
     * Writes the contents of a 2D string array to a file using a specified delimiter
     * @param file
     * @param data
     * @param separator
     * @throws IOException
     */
    public static void writeTableToTxt(File file, String[][] data, String separator) throws IOException {

        //make a new writer
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        String row = "";

        //loop through data to extract the content in all cells
        for (int i = 0; i < data.length; i++) {

            row = ""; //reset variables
            
            for (int j = 0; j < data[i].length; j++) {
                row += data[i][j]; //append to row

                //add a delimitor/separator afterwards, but append newline after last data point
                if (j != data[i].length-1) row += separator;
                else row += "\n";
            }

            //write to file
            bw.write(row);
        }

        //close writer
        bw.close();

    }

    /**
     * Finds the file extension for a specified file. For example, 
     * <code>PartyGirl.txt</code> would return <code>txt</code>.
     * @param fileName The file name of the file. No, you cannot input the 
     * path of the file. Use {@code getFileExt(File f)} instead if you want
     * to input the full file path.
     * @return The file extension, such as txt or xlsx. The period
     * is NOT included.
     */
    public static String getFileExt(String fileName) {

        //find last index of '.'
        int index = fileName.lastIndexOf('.');

        //no '.' in string means no extension. '.' at end also means no ext
        if (index == -1 || index == fileName.length()) return "";

        //else return everything after the period
        return fileName.substring(index+1);

    }

    /**
     * An alternative to {@code getFileExt(String fileName)}
     * @param f
     * @return
     */
    public static String getFileExt(File f) {

       return getFileExt(f.getName());

    }



}

