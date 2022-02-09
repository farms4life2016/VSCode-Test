package farms4life2016.gui.buttons; 

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import java.awt.Color;
import java.awt.Font;

import farms4life2016.gui.StringDrawer;

/**
 * Stands for Non-Pressable Button 
 * (reference to Non-Playable Characters from video games)
 */
public class NPButton extends Button {

    private boolean drawOutline;
    private int outlineWidth;

    public static final String DEFAULT_INFO_STRING = "Click on a cell and its contents will be displayed here!";

    public NPButton(boolean hasOutline, int outlineWidth) {
        super();
        drawOutline = hasOutline;
        this.outlineWidth = outlineWidth;
    }

    @Override
    public void drawSelf(Graphics2D g) {
        
        //draw in background colour
        g.setColor(getCurrentColour());
        g.fillRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);

        //draw in the borders if indicated
        if (drawOutline) {        
            g.setColor(Color.BLACK);
            g.fillRect(dimensions.x, dimensions.y, outlineWidth, dimensions.height);
            g.fillRect(dimensions.x, dimensions.y, dimensions.width, outlineWidth);
            g.fillRect(dimensions.x + dimensions.width, dimensions.y, outlineWidth, dimensions.height);
            g.fillRect(dimensions.x, dimensions.y + dimensions.height, dimensions.width, outlineWidth);
        }

        //draw the text
        if (text.equals(DEFAULT_INFO_STRING)) {
            fontStyle = Font.ITALIC;
            textColour = Color.GRAY;
        } else {
            fontStyle = Font.BOLD;
            textColour = Color.BLACK;
        }
        
        g.setColor(textColour);
        if (textFormat == LEFT_ALIGN) {
            StringDrawer.drawStringCenteredYLeftAligned(g, text, dimensions, fontStyle, fontSize);
        } else if (textFormat == CENTERED) {
            StringDrawer.drawStringSuperCentered(g, text, dimensions, fontStyle, fontSize);
        }
        
    }

    @Override
    public void onClick(MouseEvent e) {
        //intentionally blank
        
    }
    
}
