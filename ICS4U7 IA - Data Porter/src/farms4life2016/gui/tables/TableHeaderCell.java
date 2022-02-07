package farms4life2016.gui.tables;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import farms4life2016.gui.StringDrawer;

public class TableHeaderCell extends TableCell {

    public TableHeaderCell() {
        super();

        //different colours
        unselectedColour = Color.YELLOW;
        currentColor = unselectedColour;

        //different font
        fontStyle = Font.BOLD;
        fontSize = 20;
    }

    @Override
    public void drawSelf(Graphics2D g) {
        super.drawSelf(g);
        
    }
    
    

    @Override
    public void onClick(MouseEvent e) {
        //sort stuff
        
    }
}
