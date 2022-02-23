package farms4life2016.gui.displays;

import javax.swing.JDialog;
import javax.swing.JFrame;

import farms4life2016.dataprocessing.Job;

/**
 * I wasn't happy with JDialog and wanted to add some
 * stuff of my own to the Job adding/updating dialogue.
 */
public class JobUpdateDialogue extends JDialog {

    private JobUpdateDisplay display;

    /**
     * Basically the same as the superclass' constructor, but with
     * more initializtion of variables such as size
     * @param frame
     * @param title
     * @param modial
     */
    public JobUpdateDialogue(JFrame frame, String title, boolean modial) {
        super(frame, title, modial);

        display = new JobUpdateDisplay(this);
        setSize(500, 500);
        add(display);
        setVisible(false);

    }

    /**
     * Basically the same thing as {@code super.setVisible(...)}
     * but I added some stuff to load the job's pre-existing information
     * into the updater when it becomes visible
     * @param isVisible
     * @param j
     */
    public void setVisible(boolean isVisible, Job j) {
        
        if (isVisible) {
            if (j == null) {
                display.setMode(JobUpdateDisplay.ADD, null);
                setTitle("Add a Job");
            } else {
                display.setMode(JobUpdateDisplay.UPDATE, j);
                setTitle("Update a Job");
            }
        } else {
            
        }

        super.setVisible(isVisible);
    }
    
}
