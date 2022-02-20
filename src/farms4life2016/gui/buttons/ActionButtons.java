package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.DNode;
import farms4life2016.dataprocessing.Job;
import farms4life2016.dataprocessing.JobRunner;
import farms4life2016.fileio.FileIO;
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

                //run the job
                if (dimensions.contains(e.getPoint())) {

                    //start counting errors as well
                    Controller.mainMenu.errorBar.resetErrorCount();
                    JobRunner jr = new JobRunner(parent.getJob());
                    jr.run();
                    
                }
            }
            
        };
        edit = new IconButton(".\\icons\\Edit.png") {

            @Override
            public void onClick(MouseEvent e) {
                //open job editor
                if (dimensions.contains(e.getPoint())) {
                    Controller.jobUpdater.setVisible(true, parent.getJob());
                }
            }
            
        };
        delete = new IconButton(".\\icons\\Delete.png") {

            @Override
            public void onClick(MouseEvent e) {
                
                if (dimensions.contains(e.getPoint())) {

                    try {
                    //delete job
                    parent.getJob().setActive(false);
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

                    //edit the init file
                    FileIO.edit(parent.getJob());

                    //update table in menu
                    Controller.mainMenu.setInfoText(InfoBox.DEFAULT_INFO_STRING);
                    parent.getParent().fillJobs(Controller.jobList, true);

                    } catch (IOException ex) {
                        ex.printStackTrace(); //if init file cannot be edited
                    }

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
