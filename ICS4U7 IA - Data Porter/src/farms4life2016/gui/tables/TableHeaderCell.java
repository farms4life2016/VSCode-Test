package farms4life2016.gui.tables;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.Display;
import farms4life2016.gui.StringDrawer;
import farms4life2016.gui.buttons.NPButton;

public class TableHeaderCell extends TableCell {

    public TableHeaderCell() {
        super();

        //different colours
        unselectedColour = Color.YELLOW;
        currentColor = unselectedColour;

        //different font
        fontStyle = Font.BOLD;
        fontSize = 20;
    }

    public TableHeaderCell(TableRow r) {
        this();
        parent = r;
    }

    @Override
    public void drawSelf(Graphics2D g) {
        super.drawSelf(g);
        
    }
    
    

    @Override
    public void onClick(MouseEvent e) {
        //sort stuff
        if (dimensions.contains(e.getPoint())) {
            System.out.println("Sort " + text);

            parent.resetColours();
            Display.setInfoText(NPButton.DEFAULT_INFO_STRING);

            if (text.equals("ID")) {
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
            System.out.println(Controller.jobList);
            parent.getParent().clearData();
            parent.getParent().fillJobs(Controller.jobList);

        }
        


        
    }
}
