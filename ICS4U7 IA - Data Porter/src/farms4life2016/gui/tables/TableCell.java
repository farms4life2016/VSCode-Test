package farms4life2016.gui.tables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.NPButton;
import farms4life2016.gui.displays.Display;

public class TableCell extends Button {

    protected TableRow parent;

    public static final int OUTLINE_WIDTH = 3;

    public TableCell() {
        this(null);
    }

    public TableCell(TableRow r) {
        super();
        parent = r;
        selectedColour = Color.CYAN;
        textColour = Colours.WHITE;
        unselectedColour = Colours.GRAY20;
        setSelected(false);
        text = "";
                
    }

    @Override
    public void drawSelf(Graphics2D g) {
        
        super.fillBgRect(g);
        super.drawBorders(g, OUTLINE_WIDTH, Colours.GRAY160);
        super.drawText(g);
        
    }

    @Override
    public void onClick(MouseEvent e) {
        
        if (dimensions.contains(e.getPoint())) {

            setSelected( !isSelected() );

            //update info text box
            if (isSelected()) {
                Display.setInfoText(this.text);
            } else {
                Display.setInfoText(NPButton.DEFAULT_INFO_STRING);
            }

        } else {
            setSelected(false);
        }
        
    }

    @Override
    public void setDimensions(Rectangle dimensions) {
        //I don't get why the super version of this method
        //does not work as intented. Maybe cuz I don't
        //inherit Drawable directly???
        this.dimensions = dimensions;
    }
    
}
