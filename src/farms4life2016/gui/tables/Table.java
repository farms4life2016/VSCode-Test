package farms4life2016.gui.tables;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.DLinkedList;
import farms4life2016.dataprocessing.DNode;
import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.buttons.Button;

/**
 * I hate the built-in JTable so I made one myself
 */
public class Table extends Button {

    //list for storing all the table rows
    private DLinkedList rows;
    private DNode displayJob;
    private int jobIndex;
    private int[] columnWidths;
    private int rowHeight;
    private String[] headers;

    public static final int UNIVERSAL_ROW_HEIGHT = 30;

    /**
     * make a new table using a job list
     * @param jobList
     */
    public Table(DLinkedList jobList) {

        //initialize variables
        super(50, 200, Controller.WINDOW_W-130, Controller.WINDOW_H-330); //width and height don't actually matter
        rows = new DLinkedList();

        //create headers
        headers = new String[7];
        headers[0] = "ID";
        headers[1] = "Name";
        headers[2] = "Client";
        headers[3] = "Type";
        headers[4] = "File Name";
        headers[5] = "Last Date Modified";
        headers[6] = "Actions";
        rows.add(new TableRow(headers, this).setIndex(0));

        //specify some column widths
        columnWidths = new int[7];
        columnWidths[0] = 60;
        columnWidths[1] = 150;
        columnWidths[2] = 100;
        columnWidths[3] = 60;
        columnWidths[4] = 200;
        columnWidths[5] = 300;
        columnWidths[6] = 90;

        rowHeight = UNIVERSAL_ROW_HEIGHT;

        fillJobs(jobList, true);

    }

    /**
     * Part of the constructor. Also useful for refreshing the table in case
     * the order of jobs changed (when sorting for example).
     * @param jobList
     */
    public void fillJobs(DLinkedList jobList, boolean resetHeaders) {

        //remove previous jobs
        clearData();
        
        //fill up with jobs
        if (jobList.length() > 0) { //only if list has jobs
            displayJob = jobList.getNode(0);
            for (int i = 0; i < jobList.length(); i++) {
                rows.add(new TableRow( (Job) displayJob.getData(), this).setIndex(-1));
                displayJob = displayJob.getNext();
            }
            displayJob = rows.getNode(1);
            jobIndex = 1;

        } else {
            displayJob = null;
            jobIndex = -1;
        }
        
        //used to change all the table header cell colours
        //back to their default
        if (resetHeaders) {
            ((TableRow)(rows.get(0))).resetColours();
        }

    }

    /**
     * Remove all the rows in the table except the header
     */
    private void clearData() {
        
        while (rows.length() > 1) {
            rows.remove(1);
        }
        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        
        //draw 10 rows only plus the header
        ((TableRow)(rows.get(0))).drawSelf(g);

        DNode n = displayJob;
        for (int i = 0; i < 10; i++) {
            //if there are less than 10 jobs, n will point
            //to dummy-tail, which is null, indicating no more
            //elements left
            if (n == null || n.getData() == null) break;

            ((TableRow)(n.getData())).setIndex(i+1);
            ((TableRow)(n.getData())).drawSelf(g);
            n = n.getNext();

            
        }
        
    }

    /**
     * Display the next row and hide the first row
     */
    public void nextRow() {

        //user can see jobs after the displayed 10
        //only if >10 jobs and current 10 is not the ending 10
        if (Controller.jobList.length() > 10 && jobIndex < Controller.jobList.length() - 9) {
            displayJob = displayJob.getNext();
            jobIndex++;
        }
        
    }

    /**
     * Display the previous row and hide the last row
     */
    public void prevRow() {

        //user can see jobs before the displayed 10
        //only if >10 jobs and current 10 is not the starting 10
        if (Controller.jobList.length() > 10 && jobIndex > 1) {
            displayJob = displayJob.getPrev();
            jobIndex--;
        }

    }

    /**
     * Handles mousewheel input.
     * @param e
     */
    public void onScroll(MouseWheelEvent e) {

        //scroll up
        if (e.getWheelRotation() < 0) {
            prevRow();
        //scoll down
        } else if (e.getWheelRotation() > 0) {
            nextRow();
        }
        //idk what to do with scroll 0
    }
    
    @Override
    public void onClick(MouseEvent e) {

        //only visible rows should have click interactions
        //i.e. header and 10 displayed rows
        ((TableRow)(rows.get(0))).onClick(e);

        if (rows.length() > 1) {
            DNode n = displayJob;
            for (int i = 0; i < 10; i++) {
                if (n == null || n.getData() == null) break; //in case there are less than 10 elements
                ((TableRow) (n.getData())).onClick(e);
                n = n.getNext();
               
            }
        }
        
    }


    /* Setters and Getters */
    
	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeights(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public int[] getColumnWidths() {
		return columnWidths;
	}

	public void setColumnWidths(int[] columnWidths) {
		this.columnWidths = columnWidths;
	}
    
}
