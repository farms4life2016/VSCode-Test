package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.DNode;
import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.Colours;
import farms4life2016.gui.displays.MenuDisplay;
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
                    //delete job
                    int removeId = parent.getJob().getId(); //all ids are unique

                    DNode n = Controller.jobList.getNode(0);

                    //find the tablerow in the table with the matching id
                    for (int i = 0; i < Controller.jobList.length(); i++) {
                        if (((Job)(n.getData())).getId() == removeId) {
                            //remove from joblist in controller
                            Controller.jobList.remove(i);
                            break;
                        }
                        n = n.getNext();
                    }

                    Controller.mainMenu.setInfoText(InfoBox.DEFAULT_INFO_STRING);
                    parent.getParent().fillJobs(Controller.jobList, true);

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
