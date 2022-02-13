package farms4life2016.gui.tables;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.InfoBox;
import farms4life2016.gui.displays.MenuDisplay;

public class TableCell extends Button {

    protected TableRow parent;

    public static final int OUTLINE_WIDTH = 3;

    public TableCell() {
        this(null);
    }

    public TableCell(TableRow r) {
        super();
        parent = r;
        selectedColour = InfoBox.INFO_BG_COLOUR;
        textColour = Colours.WHITE;
        unselectedColour = Colours.GRAY20;
        setSelected(false);
        text = "";
                
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
                MenuDisplay.setInfoText(this.text);
            } else {
                MenuDisplay.setInfoText(InfoBox.DEFAULT_INFO_STRING);
            }

        } else {
            setSelected(false);
        }
        
    }

}
