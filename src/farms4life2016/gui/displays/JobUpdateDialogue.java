package farms4life2016.gui.displays;

import javax.swing.JDialog;
import javax.swing.JFrame;

import farms4life2016.dataprocessing.Job;

public class JobUpdateDialogue extends JDialog {

    private JobUpdateDisplay display;

    public JobUpdateDialogue(JFrame frame, String title, boolean modial) {
        super(frame, title, modial);

        display = new JobUpdateDisplay(this);
        setSize(500, 500);
        add(display);
        setVisible(false);

    }

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
