package farms4life2016;

import farms4life2016.dataprocessing.DLinkedList;
import farms4life2016.dataprocessing.Job;
//import farms4life2016.dataprocessing.Controller;
import farms4life2016.fileio.FileIO;


public class Driver {
	
    /**
     * This class is solely used for the main method.
     * @param args 
     */
    public static void main(String[] args) {
        
        //Controller.run(); //I spent a lot of time trying to sync github with my project. I'm glad that it worked out in the end.

        DLinkedList dd = new DLinkedList();

        dd.add(new Job(39, "Sam", 'E', "Fish", "no you.txt"));
        dd.add(new Job(23, "Jerry", 'E', "Dice", "play game.txt"));
        dd.add(new Job(1, "Bambi", 'I', "Balloon", "party girl.txt"));
        dd.add(new Job(900, "Kate", 'I', "Homework", "lolol.txt"));
        dd.add(new Job(39, "Sam", 'E', "Fish", "no you.txt"));

        System.out.println(dd);

        Job.mergesort(dd, Job.SORT_BY_CLIENT);

        System.out.println(dd);
       
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
