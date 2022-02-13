package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Color;

import farms4life2016.gui.Colours;

public class InfoBox extends NPButton {

    public static final String DEFAULT_INFO_STRING = "Click on a cell and its contents will be displayed here!";
    public static final Color INFO_BG_COLOUR = Colours.BLACK;

    public InfoBox() {
        super(true, 3);

        setDimensions(new Rectangle(50, 200+12*30, 960, 50));
		setUnselectedColour(INFO_BG_COLOUR);
		setSelected(false);
		setFontSize(32);
		setFontStyle(Font.BOLD);
		setText(DEFAULT_INFO_STRING);
        setTextColour(Colours.GRAY120);
        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        ((Button)this).fillBgRect(g);
        ((Button)this).drawBorders(g, outlineWidth, Colours.GRAY160);

        //draw the text
        if (text.equals(DEFAULT_INFO_STRING)) {
            fontStyle = Font.ITALIC; 
            setTextColour(Colours.GRAY120);
        } else {
            fontStyle = Font.BOLD;
            setTextColour(Colours.WHITE);
        }

        ((Button)this).drawText(g);
        
    }
    
}
