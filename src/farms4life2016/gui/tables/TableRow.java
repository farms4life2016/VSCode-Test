package farms4life2016.gui.tables;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.buttons.ActionButtons;
import farms4life2016.gui.buttons.Button;

public class TableRow extends Button {
    
    private Job rowJob;
    private Button[] cells;
    private Point position;
    private Table parent;
    private int index;

    public TableRow(Job job, Table t) {

        //init values
        rowJob = job;
        position = new Point( t.getX(), t.getY()); 
        parent = t;
        cells = new Button[7];
        index = 0;

        //create cells in the table
        int counter = 0;
        for (int i = 0; i < cells.length; i++) {

            //the last column should have action buttons to run the job
            if (i == 6) cells[i] = new ActionButtons(this);
            else cells[i] = new TableCell(this);

            //make sure you set the dimensions NOT the same as the header
            //or else sorting and selecting a cell will get really funky
            cells[i].setDimensions(new Rectangle(position.x + counter, position.y + parent.getRowHeight(), parent.getColumnWidths()[i], parent.getRowHeight() ) );
            counter += parent.getColumnWidths()[i];
            cells[i].setTextFormat(Button.LEFT_ALIGN);
            cells[i].setFontSize(20);
            cells[i].setFontStyle(Font.PLAIN);
            
        }

        //set text based on job information
        cells[0].setText( Integer.toString( rowJob.getId() ) );
        cells[1].setText(rowJob.getName());
        cells[2].setText(rowJob.getClient());
        cells[3].setText(Character.toString(rowJob.getType()));
        cells[4].setText(rowJob.getFile());
        cells[5].setText(Job.DATE_FORMAT.format(rowJob.getDate().getTime()));

    }

    public TableRow(String[] headerNames, Table t) {

        position = new Point( t.getX(), t.getY()); 
        parent = t;

        cells = new Button[7];

        //special process for creating table headers
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new TableHeaderCell(this);
            cells[i].setText(headerNames[i]);
        }

    }

    public void resetColours() {
        for (int i = 0; i < cells.length; i++) {
            cells[i].setSelected(false);
        }
    }

    @Override
    public void drawSelf(Graphics2D g) {
        
        int counter = 0;
        for (int i = 0; i < cells.length; i++) {
            cells[i].setDimensions(new Rectangle(position.x + counter, position.y + index*parent.getRowHeight(), parent.getColumnWidths()[i], parent.getRowHeight() ) );
            counter += parent.getColumnWidths()[i];
            cells[i].drawSelf(g);
        }

    }

    @Override
    public void onClick(MouseEvent e) {
        for (int i = 0; i < cells.length; i++) {
            cells[i].onClick(e);
        }
    }

    /*
     * Setters and getters
     */
    
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getIndex() {
        return index;
    }

    public TableRow setIndex(int index) {
        this.index = index;

        //this "return self" allows me to call this method right after
        //I create a new instance of the class
        return this;
    }

    public Table getParent() {
        return parent;
    }

    public Job getJob() {
        return rowJob;
    }

}