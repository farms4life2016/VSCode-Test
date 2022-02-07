package farms4life2016.gui.tables;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

import farms4life2016.gui.StringDrawer;
import farms4life2016.gui.buttons.Button;

public class TableCell extends Button {

    public TableCell() {
        super();
        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        
        //draw in background colour
        g.setColor(currentColor);
        g.fillRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);

        //draw in the borders
        g.setColor(Color.BLACK);
        g.fillRect(dimensions.x, dimensions.y, LegacyTable.OUTLINE_WIDTH, dimensions.height);
		g.fillRect(dimensions.x, dimensions.y, dimensions.width, LegacyTable.OUTLINE_WIDTH);
        g.fillRect(dimensions.x + dimensions.width, dimensions.y, LegacyTable.OUTLINE_WIDTH, dimensions.height);
		g.fillRect(dimensions.x, dimensions.y + dimensions.height, dimensions.width,  LegacyTable.OUTLINE_WIDTH);

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
            System.out.println(text);
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
