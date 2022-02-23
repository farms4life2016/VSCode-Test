package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.Colours;

/**
 * This is the searchbar that you see in the main menu
 */
public class SearchBar extends Button { 

    private IconButton glass; //will be made of an magnifying glass icon and a text field
    private TextField text;

    public SearchBar() {

        //consists of an icon and a text input box
        glass = new IconButton(".\\icons\\Search.png") {

            @Override
            public void onClick(MouseEvent e) {
                search();
            }
            
        };

        text = new TextField() {
            @Override
            public void onType(KeyEvent e) {
                super.onType(e);
                if (isSelected() && e.getKeyChar() == '\n') {
                    search();
                }

            }

            @Override
            public void drawSelf(Graphics2D g) {
                ((Button)this).fillBgRect(g);
                ((Button)this).drawText(g);
            }
        };
        text.setMaxLen(17);
        text.setTextColour(Colours.BLACK);

    }
    

    @Override
    public void onClick(MouseEvent e) {
        text.onClick(e);
        if (glass.dimensions.contains(e.getPoint())) glass.onClick(e);
        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        glass.drawSelf(g);
        text.drawSelf(g);
        super.drawBorders(g, 3, Colours.GRAY160);
        
    }

    /**
     * For typing into the textfield
     * @param e
     */
    public void onType(KeyEvent e) {
        text.onType(e);
    }

    /**
     * For the funny | flashing effect in the textfield
     */
    public void onRefresh() {
        text.onRefresh();
    }

    /**
     * Linear searches the job list and updates the table with search results
     */
    public void search() {
        text.setSelected(false);

        //reset search
        if (text.getText().equals("")) {
            Controller.mainMenu.jobTable.fillJobs(Controller.jobList, true);
        }
        
        //we don't want to set controller's joblist to search results
        Controller.mainMenu.jobTable.fillJobs(Job.linearSearch(Controller.jobList, text.getText()), true);
        //we are just displaying a subset of controller's joblist; we aren't modifying anything
        Controller.mainMenu.setInfoText(InfoBox.DEFAULT_INFO_STRING);
        
    }

    @Override
    public void setDimensions(Rectangle dimensions) {
        super.setDimensions(dimensions);
        if (getHeight() < getWidth()) {
            glass.setDimensions(new Rectangle(getX()+1, getY()+1, getHeight(), getHeight()));
            text.setDimensions(new Rectangle(getX() + getHeight()+1, getY()+1, getWidth()-getHeight(), getHeight()));
        } else {
            System.out.println("Welp, you're not supposed to do that.");
        }
    }

    @Override
    public void setFontSize(float fontSize) {
        text.setFontSize(fontSize);
    }
    
}
