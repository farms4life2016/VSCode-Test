package farms4life2016.gui.tables;

import java.awt.event.MouseEvent;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.InfoBox;

/**
 * A table cell at the top of the table that can sort 
 * the rest of the table if you click on the cell
 */
public class TableHeaderCell extends TableCell {
    
    public TableHeaderCell(TableRow r) {
        super(r);

        //different colours
        unselectedColour = Colours.D_JARATE;
        selectedColour = Colours.JARATE;
        setSelected(false);

        //different font
        fontSize = 20;
    }

    @Override
    public void onClick(MouseEvent e) {
        //sort stuff
        if (dimensions.contains(e.getPoint())) {

            //reset colours for all header cells and change colour for current cell
            parent.resetColours();
            if (!text.equals("Actions")) setSelected(true);

            //sorting by fields 
            if (text.equals("ID") || text.equals("Actions")) {
                Job.mergesort(Controller.jobList, Job.SORT_BY_ID);
            } else if (text.equals("Name")) {
                Job.mergesort(Controller.jobList, Job.SORT_BY_NAME);
            } else if (text.equals("Client")) {
                Job.mergesort(Controller.jobList, Job.SORT_BY_CLIENT);
            } else if (text.equals("Type")) {
                Job.mergesort(Controller.jobList, Job.SORT_BY_TYPE);
            } else if (text.equals("File Name")) {
                Job.mergesort(Controller.jobList, Job.SORT_BY_FILE);
            } else if (text.equals("Last Date Modified")) {
                Job.mergesort(Controller.jobList, Job.SORT_BY_DATE);
            } else {
                System.out.println("non-functional button");
            }
            
            //refill jobs list with sorted items
            parent.getParent().fillJobs(Controller.jobList, false);

            //nothing is selected anymore
            Controller.mainMenu.setInfoText(InfoBox.DEFAULT_INFO_STRING);


        }
        
    }
}
