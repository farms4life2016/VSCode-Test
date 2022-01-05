import java.io.BufferedWriter;
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  


public class App {
    public static void main(String[] args) throws Exception {

        BufferedWriter f = new BufferedWriter( new FileWriter( new File("output.txt")));
        System.out.println("Hello, World!");
        
        try {
            f.write("your world is 99% crimson. you are screwed.");
            f.close();
            System.out.println("Success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
}

class ReadXLSXDemo {

    static void run() {

        FileInputStream fis = null;

        try {

            //read in file
            fis = new FileInputStream(new File(".\\input.xlsx"));


            //make stuff to represent the excel workbook
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

           sheet.iterator();



           wb.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        


    }

}
