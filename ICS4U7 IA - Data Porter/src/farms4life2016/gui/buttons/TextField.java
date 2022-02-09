package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;

import farms4life2016.gui.StringDrawer;

public class TextField extends Button {

    private String input;
    

    public TextField() {
        super();
        text = "";
        input = "";
        selectedColour = new Color(200, 200, 200);
    }

    @Override
    public void drawSelf(Graphics2D g) {
        
        //draw in background colour
        g.setColor(currentColor);
        g.fillRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);

        //draw in the borders
        g.setColor(Color.BLACK);
        g.fillRect(dimensions.x, dimensions.y, 1, dimensions.height);
		g.fillRect(dimensions.x, dimensions.y, dimensions.width, 1);
        g.fillRect(dimensions.x + dimensions.width, dimensions.y, 1, dimensions.height);
		g.fillRect(dimensions.x, dimensions.y + dimensions.height, dimensions.width,  1);

        //draw the text
        g.setColor(textColour);
        if (textFormat == LEFT_ALIGN) {
            StringDrawer.drawStringCenteredYLeftAligned(g, input, dimensions, fontStyle, fontSize);
        } else if (textFormat == CENTERED) {
            StringDrawer.drawStringSuperCentered(g, input, dimensions, fontStyle, fontSize);
        }
        
    }

    @Override
    public void onClick(MouseEvent e) {
        if (dimensions.contains(e.getPoint())) {

            isSelected = !isSelected;

            if (isSelected) {
                currentColor = selectedColour;
            } else {
                currentColor = unselectedColour;
            }
        } else {
            isSelected = false;
        }
        
    }

    public void onType(KeyEvent e) {

        //only allow text input if textbox is selected
        if (isSelected) {
            System.out.println(e.getKeyChar());        
            if (Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyChar() == ' ' || e.getKeyChar() == '_' || e.getKeyChar() == '.') {
                input += e.getKeyChar();
            } else {

                //backspace means delete character
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    input = input.substring(0, input.length()-1); 
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    isSelected = false; //press enter to deselect
                }
            }
        }

    }
    
}
