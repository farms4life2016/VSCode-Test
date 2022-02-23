package farms4life2016.dataprocessing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import farms4life2016.fileio.FileIO;
import farms4life2016.gui.StringDrawer;
import farms4life2016.gui.displays.JobUpdateDialogue;
import farms4life2016.gui.displays.MenuDisplay;
import farms4life2016.gui.displays.StartDisplay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class Controller { 

    //I decided to go against my plans and use a linked list instead of an array list
    public static DLinkedList jobList = new DLinkedList();

    //gui things. make static and public so association becomes easier
    public static JFrame window;
    public static JobUpdateDialogue jobUpdater;
    public static MenuDisplay mainMenu;
    public static CardLayout cdLayout;
    private static StartDisplay startMenu;

    //window size
    public static final int WINDOW_W = 1200, WINDOW_H = 720; //50 px for the top bar thingy

    //logger
    public static final Logger LOGGER4J = LogManager.getLogger();

    /**
     * I'm not sure we need a fancy constructor.
     * Since there is only 1 Controller in the entire program,
     * all methods/variables can be and should be static.
     */
    private Controller() {}

    /**
     * Start the program
     */
    public static void run() {

        initFromFile();
        initGUI();
        
    }

    /**
     * Exit the program
     */
    public static void terminate() {
        System.exit(0);
    }

    private static void initFromFile() {
        try {
            FileIO.init();
        } catch (IOException e) { 
            System.out.println("The initialization file cannot be accessed by the program.");
            System.out.println("Please create a new and empty \"init.txt\" or find an existing one.");
            e.printStackTrace();
        }
        
    }

    /**
     * Creates the GUI. Most of this method is copied from the GUI programs that I wrote in grade 10.
     */
    public static void initGUI() {        
        
        //variables
        new StringDrawer();
        
        cdLayout = new CardLayout();
        window = new JFrame("Data Porter - Welcome"); //name it 
        mainMenu = new MenuDisplay(window); //make a new window
        startMenu = new StartDisplay(window);
        Container c = window.getContentPane(); //make something to add all the things to
        jobUpdater = new JobUpdateDialogue(window, "Add/Update a Job", true);   

        //set window size, cannot resize
        window.setSize(WINDOW_W, WINDOW_H);
        window.setResizable(false);
        window.setIconImage(new ImageIcon("icons\\Logo.png").getImage());
        
        //add panel to frame
        c.setLayout(cdLayout);
        c.add("menu", mainMenu);
        c.add("start", startMenu);
        cdLayout.show(c, "start");
        mainMenu.setBackground(Color.GRAY);

        //why do we have to add this listener here I'm so confused
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        window.addWindowListener(mainMenu);
        
        //final touches to make window visible and usable
        window.setVisible(true);

    }

    /**
     * changes the title of the window
     * @param newName
     */
    public static void setTitle (String newName) {
        window.setTitle(newName);
    }

}