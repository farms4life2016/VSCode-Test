package farms4life2016.gui.tables;

import java.awt.Font;
import java.awt.event.MouseEvent;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.InfoBox;
import farms4life2016.gui.displays.MenuDisplay;

public class TableHeaderCell extends TableCell {

    
    public TableHeaderCell(TableRow r) {
        super(r);

        //different colours
        unselectedColour = Colours.D_JARATE;
        selectedColour = Colours.JARATE;
        setSelected(false);

        //different font
        fontStyle = Font.BOLD;
        fontSize = 20;
    }

    @Override
    public void onClick(MouseEvent e) {
        //sort stuff
        if (dimensions.contains(e.getPoint())) {

            parent.resetColours();
            if (!text.equals("Actions")) setSelected(true);

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
            MenuDisplay.setInfoText(InfoBox.DEFAULT_INFO_STRING);


        }
        
    }
}
