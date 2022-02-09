package farms4life2016.gui.tables;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.apache.poi.hslf.record.SoundData;

import java.awt.Rectangle;

import farms4life2016.gui.Display;
import farms4life2016.gui.StringDrawer;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.NPButton;

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
    }

    @Override
    public void drawSelf(Graphics2D g) {
        
        //draw in background colour
        g.setColor(currentColor);
        g.fillRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);

        //draw in the borders
        g.setColor(Color.BLACK);
        g.fillRect(dimensions.x, dimensions.y, OUTLINE_WIDTH, dimensions.height);
		g.fillRect(dimensions.x, dimensions.y, dimensions.width, OUTLINE_WIDTH);
        g.fillRect(dimensions.x + dimensions.width, dimensions.y, OUTLINE_WIDTH, dimensions.height);
		g.fillRect(dimensions.x, dimensions.y + dimensions.height, dimensions.width,  OUTLINE_WIDTH);

        //draw the text
        g.setColor(textColour);
        if (textFormat == LEFT_ALIGN) {
            StringDrawer.drawStringCenteredYLeftAligned(g, text, dimensions, fontStyle, fontSize);
        } else if (textFormat == CENTERED) {
            StringDrawer.drawStringSuperCentered(g, text, dimensions, fontStyle, fontSize);
        }
        
        
    }

    @Override
    public void onClick(MouseEvent e) {
        
        if (dimensions.contains(e.getPoint())) {

            if (currentColor.equals(selectedColour)) {
                currentColor = unselectedColour;
                Display.setInfoText(NPButton.DEFAULT_INFO_STRING);
                
            } else if (currentColor.equals(unselectedColour)) {

                //only one cell should be selected at a time
                parent.getParent().resetColours();
                currentColor = selectedColour;
                Display.setInfoText(this.text);
                
            }
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
