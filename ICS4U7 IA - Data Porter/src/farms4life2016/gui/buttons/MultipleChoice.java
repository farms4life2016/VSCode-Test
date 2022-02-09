package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

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

                
            }
            
        };

    }

    @Override
    public void onClick(MouseEvent e) {
        // TODO Auto-generated method stub
        if (left.dimensions.contains(e.getPoint())) {
            left.setSelected(true);
            right.setSelected(false);
        } else if (right.dimensions.contains(e.getPoint())) {
            left.setSelected(false);
            right.setSelected(true);
        }
        
    }

    @Override
    public void drawSelf(Graphics2D g) {
        left.drawSelf(g);
        right.drawSelf(g);
        
    }

    @Override
    public void setDimensions(Rectangle dimensions) {
        super.setDimensions(dimensions);
        left.setDimensions(new Rectangle(getX(), getY(), getWidth()/2, getHeight()));
        right.setDimensions(new Rectangle(getX() + getWidth()/2, getY(), getWidth()/2, getHeight()));
    }
    
}
