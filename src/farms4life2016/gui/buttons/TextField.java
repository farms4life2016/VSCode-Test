package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;

import farms4life2016.gui.Colours;

public class TextField extends Button {

    private int underscoreCounter;
    private boolean underscore;
    private String input;
    private int maxLen;
    private Color normalColour;

    private static final Color OVERFLOW_COLOR = Colours.RED;
    

    public TextField() {
        super();
        input = "";
        text = "";
        selectedColour = new Color(200, 200, 200);
        underscore = false;
        underscoreCounter = 0;
        maxLen = 200;
    }

    @Override
    public void drawSelf(Graphics2D g) {

        super.fillBgRect(g);
        super.drawBorders(g, 1, Colours.GRAY100);
        super.drawText(g);
        
    }

    @Override
    public void onClick(MouseEvent e) {
        if (dimensions.contains(e.getPoint())) {
            setSelected(!isSelected());

        } else {
            setSelected(false);
        }
        
    }

    public void onRefresh() {
        
        //trying to recreate the funny flashy | or _ that appears at the end of a textbox
        if (isSelected() && input.length() < maxLen) { //do not underscore if no more chars left

            underscoreCounter += 1;
            if (underscoreCounter == 10) {
                underscore = !underscore;
                underscoreCounter = 0;
    
                if (underscore) {
                    text = input + '|';
                } else {
                    text = input;
                }
    
            }
            
        } else {
            underscoreCounter = 0;
            underscore = false;
            text = input;

        }

        //warn user if they have hit char limit
        if (isSelected() && input.length() == maxLen) {
            textColour = OVERFLOW_COLOR;
        } else {
            textColour = normalColour; 
        }
        
    }

    public void onType(KeyEvent e) {

        //only allow text input if textbox is selected
        if (isSelected()) {   
            
            //only allow certain characters that are allowed in file names
            if (Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyChar() == ' ' || e.getKeyChar() == '_' || e.getKeyChar() == '.') {
                
                //make sure there are still characters left for the textbox
                if (input.length() < maxLen) {
                    input += e.getKeyChar();
                }
                
            } else {

                //backspace means delete character
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {

                    if (input.length() > 0) { //can only delete when there are characters
                        input = input.substring(0, input.length() - 1);
                    }
                   
                } 
            }

            //immediately update displayed text
            if (underscore) {
                text = input + '|';
            } else {
                text = input;
            }

        }

    }

    @Override
    public void setText(String text) {
        this.input = text;
    }

    @Override
    public String getText() {
        return input;
    }

    @Override
    public void setTextColour(Color textColour) {
        normalColour = textColour;
    }

    public int getMaxLen() {
        return maxLen;
    }

    public void setMaxLen(int maxLen) {
        this.maxLen = maxLen;
    }
    
    
}
