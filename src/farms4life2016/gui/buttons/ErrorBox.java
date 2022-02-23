package farms4life2016.gui.buttons;

import java.awt.Rectangle;
import java.util.Calendar;

import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.Colours;

public class ErrorBox extends NPButton {

    private int errorCount;

    public ErrorBox() {
        super(false, 0);
        setDimensions(new Rectangle(50, 200+12*30+60, 960, 30));
		setFontSize(16);
		setText("Errors will be displayed here. The first job likely run will take longer.");

        errorCount = 0;
    }

    /*
     * List of possible errors to handle:
     * - Cannot read or find XML file, includes XML bad format (JAXBException)
     * - Cannot read or write to init file, affects job adding/updating too (IOException)
     * - Cannot read or write to DB, maybe no connection (SQLException)
     * - Cannot read or write to txt (IOException)
     * - Cannot read or write to Excel (IOException)
     * - Cannot delete or move or copy a specified file (IOException)
     * 
     * - Successfully ran a job, deleted a job, updated a job, etc. Give a timestamp too 
     */
    
    //
    public void increaseErrorCount(String msg, int jobId) {
        errorCount++;
        setTextColour(Colours.RED);
        //if more than one error, combine all errors into one message
        if (errorCount > 1) {
            setText("Multiple errors encountered while running Job #" + jobId + ". Please check the log for more details.");

        } else if (errorCount == 1) { //only one error, set text
            setText(msg);
        }

    }

    public void resetErrorCount() {
        errorCount = 0;
    }

    public void showSuccess(int jobId) {
        setTextColour(Colours.GREEN);
        setText("Successfully ran Job #" + jobId + ".");
    }

    @Override
    public void setText(String text) {
        //add date to error message
        super.setText("[" + Job.LONG_DATE_FORMATER.format(Calendar.getInstance().getTime()) + "]  " + text);
    }


}
