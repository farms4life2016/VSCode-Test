package farms4life2016;

import farms4life2016.dataprocessing.DLinkedList;
import farms4life2016.dataprocessing.Job;
import farms4life2016.fileio.FileIO;

import java.util.Calendar;

import farms4life2016.dataprocessing.Controller;


public class Driver {
	
    /**
     * This class is solely used for the main method.
     * @param args 
     */
    public static void main(String[] args) {
        
        Controller.run(); //I spent a lot of time trying to sync github with my project. I'm glad that it worked out in the end.

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
