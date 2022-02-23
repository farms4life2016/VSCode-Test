package farms4life2016.gui.buttons; 

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import farms4life2016.gui.Colours;

/**
 * Stands for Non-Pressable Button 
 * (reference to Non-Playable Characters from video games)
 */
public class NPButton extends Button {

    private boolean drawOutline;
    protected int outlineWidth;
    
    /**
     * Creates a new non-pressable button
     * @param hasOutline should the button have its outline drawn?
     * @param outlineWidth if yes to outline, how thick should the ouline be? 
     * Please input an int larger than 0 if yes.
     */
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
