package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import farms4life2016.gui.Colours;

public class MultipleChoice extends Button {

    private Button left, right;

    public MultipleChoice() {

        left = new Button() {

            @Override
            public void onClick(MouseEvent e) {
               
            }

            @Override
            public void drawSelf(Graphics2D g) {
                super.fillBgRect(g);
                super.drawText(g);
                super.drawBorders(g, 3, Colours.BLACK);
                
            }
            
        };
        left.setUnselectedColour(Colours.RED);
        left.setSelectedColour(Colours.GREEN);
        left.setSelected(true);

        right = new Button() {

            @Override
            public void onClick(MouseEvent e) {
                
            }

            @Override
            public void drawSelf(Graphics2D g) {
                super.fillBgRect(g);
                super.drawText(g);
                super.drawBorders(g, 3, Colours.BLACK);
                
            }
            
        };
        right.setUnselectedColour(Colours.RED);
        right.setSelectedColour(Colours.GREEN);
        right.setSelected(false);

        //check mark for more visibility
        //in case the colours aren't enough
        left.setText('✓' + "  Import");
        right.setText("   Export");

    }

    @Override
    public void onClick(MouseEvent e) {
        
        //only 1 button can be selected at once
        if (left.dimensions.contains(e.getPoint())) {
            left.setSelected(true);
            right.setSelected(false);
            left.setText('✓' + "  Import");
            right.setText("   Export");
        } else if (right.dimensions.contains(e.getPoint())) {
            left.setSelected(false);
            right.setSelected(true);
            left.setText("   Import");
            right.setText('✓' + "  Export");
        }
        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        left.drawSelf(g); //draw both buttons
        right.drawSelf(g);
        
    }

    @Override
    public void setDimensions(Rectangle dimensions) {
        super.setDimensions(dimensions);
        //divide the space evenly width-wise for both buttons
        left.setDimensions(new Rectangle(getX(), getY(), getWidth()/2, getHeight()));
        right.setDimensions(new Rectangle(getX() + getWidth()/2 + 1, getY(), getWidth()/2, getHeight()));
    }
    
}
