package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.gui.Colours;
import farms4life2016.gui.tables.TableRow;

public class ActionButtons extends Button {

    IconButton run, edit, delete;
    TableRow parent;

    public ActionButtons(TableRow parent) {
        run = new IconButton(".\\icons\\Run.png") {

            @Override
            public void onClick(MouseEvent e) {
                // TODO run the job
                if (dimensions.contains(e.getPoint())) {
                    System.out.println("run the job");
                }
                
                
            }
            
        };
        edit = new IconButton(".\\icons\\Edit.png") {

            @Override
            public void onClick(MouseEvent e) {
                // TODO open editor
                if (dimensions.contains(e.getPoint())) {
                    Controller.jobUpdater.setVisible(true, parent.getJob());
                }
                
                
            }
            
        };
        delete = new IconButton(".\\icons\\Delete.png") {

            @Override
            public void onClick(MouseEvent e) {
                
                if (dimensions.contains(e.getPoint())) {
                    //TODO delete job
                }
            }
            
        };

    }

    @Override
    public void onClick(MouseEvent e) {
        run.onClick(e);
        edit.onClick(e);
        delete.onClick(e);        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        //draw all 3 icons
        run.drawSelf(g);
        edit.drawSelf(g);
        delete.drawSelf(g);

        super.drawBorders(g, 3, Colours.GRAY160);
        
    }

    @Override
    public void setDimensions(Rectangle dimensions) {
        super.setDimensions(dimensions);
        int side = Math.min(getWidth(), getHeight());
        run.setDimensions(new Rectangle(getX(), getY(), side, side)); //these icons are squares
        edit.setDimensions(new Rectangle(getX() + side + 1, getY(), side, side)); 
        delete.setDimensions(new Rectangle(getX() + 2*side + 2, getY(), side, side));
    }
    
}
