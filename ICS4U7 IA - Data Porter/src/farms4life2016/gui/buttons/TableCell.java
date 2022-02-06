package farms4life2016.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import farms4life2016.gui.StringDrawer;
import farms4life2016.gui.tables.Table;

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
        g.fillRect(dimensions.x, dimensions.y, Table.OUTLINE_WIDTH, dimensions.height);
		g.fillRect(dimensions.x, dimensions.y, dimensions.width, Table.OUTLINE_WIDTH);
        g.fillRect(dimensions.x + dimensions.width, dimensions.y, Table.OUTLINE_WIDTH, dimensions.height);
		g.fillRect(dimensions.x, dimensions.y + dimensions.height, dimensions.width,  Table.OUTLINE_WIDTH);

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
    
}
