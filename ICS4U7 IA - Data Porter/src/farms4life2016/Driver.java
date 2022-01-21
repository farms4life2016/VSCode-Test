package farms4life2016;

//import farms4life2016.dataprocessing.Controller;
import farms4life2016.fileio.FileIO;


public class Driver {
	
    /**
     * This class is solely used for the main method.
     * @param args 
     */
    public static void main(String[] args) {
        
        //Controller.run(); //I spent a lot of time trying to sync github with my project. I'm glad that it worked out in the end.

        String inputData = ".\\src\\farms4life2016\\testing\\sample data.xlsx";
        String outputData = ".\\src\\farms4life2016\\testing\\snowdin.xlsx";

        String[][] readIn = FileIO.readGrid(inputData, (int)('T' - 'A'),  12 );

        //printA(readIn);
        System.out.println(FileIO.writeGrid(outputData, readIn));
        
       
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
