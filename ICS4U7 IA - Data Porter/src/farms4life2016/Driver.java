package farms4life2016;

//thank you vscode for organizing my imports
import java.awt.Container;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import farms4life2016.gui.Display;


public class Driver {
    
//declare variables
public static final int WINDOW_W = 1030+100, WINDOW_H = 639+50;
	
    /**
     * @param args This will run first
     */
    public static void main(String[] args) {
        
        
        
        //variables
        Display bigPanel = new Display(); //make a new window
        JFrame window = new JFrame("Data Porter"); //name it 
        Container c = window.getContentPane(); //make something to add all the things to
        
        //set window size
        window.setSize(WINDOW_W, WINDOW_H);
        
        //allow window to exit
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO change this so that the all IO operations close properly 
        
        //add panel to frame
        c.setLayout(new BorderLayout());
        c.add(bigPanel, BorderLayout.CENTER);
        
        //make window visible
        window.setVisible(true);
        
        //the window must be resized to work
        window.setBounds(0, 0, WINDOW_W, WINDOW_H);
        
        
    } //end main method   

}
