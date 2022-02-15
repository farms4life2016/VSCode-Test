package farms4life2016.gui.tables;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.InfoBox;
import farms4life2016.gui.displays.MenuDisplay;

public class TableCell extends Button {

    protected TableRow parent;
    private int maxLen;
    private String fullText;

    public static final int OUTLINE_WIDTH = 3;


    public TableCell(TableRow r) {
        super();
        parent = r;
        selectedColour = InfoBox.INFO_BG_COLOUR;
        textColour = Colours.WHITE;
        unselectedColour = Colours.GRAY20;
        setSelected(false);
        text = "";
        maxLen = 200;
                
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
                MenuDisplay.setInfoText(this.fullText);
            } else {
                MenuDisplay.setInfoText(InfoBox.DEFAULT_INFO_STRING);
            }

        } else {
            setSelected(false);
        }
        
    }

    @Override
    public void setText(String text) {
        fullText = text;
        super.setText(text);
        
        //dealing with super long text that doesn't fit
        //the cell dimensions
        if (fullText.length() > maxLen) {
            this.text = fullText.substring(0, maxLen-1) + "..";
        }

    }

    public int getMaxLen() {
        return maxLen;
    }

    public void setMaxLen(int maxLen) {
        this.maxLen = maxLen;
        //make sure u call settext() afterwards
    }
    
}
