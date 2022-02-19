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
        setChoice(true);

    }

    @Override
    public void onClick(MouseEvent e) {
        
        //only 1 button can be selected at once
        if (left.dimensions.contains(e.getPoint())) {
            setChoice(true);
        } else if (right.dimensions.contains(e.getPoint())) {
            setChoice(false);
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

    public String getChoice() {
        if (left.isSelected()) {
            return left.getText();

        } else {
            return right.getText();
        }
    }

    public void setChoice(boolean isLeft) {
        if (isLeft) {
            left.setSelected(true);
            right.setSelected(false);
            left.setText('\u2713' + "  Import"); // '\u2713' is a check mark characater in UTF-8
            right.setText("   Export");
        } else {
            left.setSelected(false);
            right.setSelected(true);
            left.setText("   Import");
            right.setText('\u2713' + "  Export");
        }
    }
    
}
