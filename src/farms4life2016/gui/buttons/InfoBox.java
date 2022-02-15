package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

import farms4life2016.gui.Colours;

/**
 * Intended to magnify text in the table's cells, and also display longer strings
 */
public class InfoBox extends NPButton {

    public static final String DEFAULT_INFO_STRING = "Click on a cell and its contents will be displayed here!";
    public static final Color INFO_BG_COLOUR = Colours.OCEAN;

    public InfoBox() {
        super(true, 3);

        setDimensions(new Rectangle(50, 200+12*30, 960, 50));
		setUnselectedColour(INFO_BG_COLOUR);
		setSelected(false);
		setFontSize(32);
		setText(DEFAULT_INFO_STRING);
        setTextColour(Colours.GRAY120);
        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        ((Button)this).fillBgRect(g);
        ((Button)this).drawBorders(g, outlineWidth, Colours.GRAY160);

        //draw the text
        if (text.equals(DEFAULT_INFO_STRING)) {
            setTextColour(Colours.GRAY160);
        } else {
            setTextColour(Colours.WHITE);
        }

        ((Button)this).drawText(g);
        
    }
    
}
