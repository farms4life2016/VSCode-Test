package farms4life2016.gui.buttons; 

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import farms4life2016.gui.Colours;
import farms4life2016.gui.StringDrawer;

/**
 * Stands for Non-Pressable Button 
 * (reference to Non-Playable Characters from video games)
 */
public class NPButton extends Button {

    private boolean drawOutline;
    protected int outlineWidth;

    public static final String DEFAULT_INFO_STRING = "Click on a cell and its contents will be displayed here!";

    public NPButton(boolean hasOutline, int outlineWidth) {
        super();
        drawOutline = hasOutline;
        this.outlineWidth = outlineWidth;
    }

    @Override
    public void drawSelf(Graphics2D g) {
        
        //draw in background colour
        super.fillBgRect(g);

        //draw in the borders if indicated
        if (drawOutline) {        
            super.drawBorders(g, outlineWidth, Colours.BLACK);
        }

        super.drawText(g); //draw text

    }

    @Override
    public void onClick(MouseEvent e) {
        //intentionally blank
        
    }
    
}
