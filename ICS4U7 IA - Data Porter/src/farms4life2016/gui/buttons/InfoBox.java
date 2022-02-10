package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Font;

import farms4life2016.gui.Colours;

public class InfoBox extends NPButton {

    public InfoBox() {
        super(true, 3);
        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        ((Button)this).fillBgRect(g);
        ((Button)this).drawBorders(g, outlineWidth, Colours.GRAY160);

        //draw the text TODO fix this part wtf
        if (text.equals(DEFAULT_INFO_STRING)) {
            fontStyle = Font.ITALIC;
            textColour = Colours.GRAY120;
        } else {
            fontStyle = Font.BOLD;
            textColour = Colours.BLACK;
        }

        ((Button)this).drawText(g);
        
    }
    
}
