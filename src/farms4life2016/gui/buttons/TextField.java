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
    

    public TextField() {
        super();
        input = "";
        text = "";
        selectedColour = new Color(200, 200, 200);
        underscore = false;
        underscoreCounter = 0;
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
        if (isSelected()) {
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
        
    }

    public void onType(KeyEvent e) {

        //only allow text input if textbox is selected
        if (isSelected()) {   
            
            //only allow certain characters that are allowed in file names
            if (Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyChar() == ' ' || e.getKeyChar() == '_' || e.getKeyChar() == '.') {
                input += e.getKeyChar();
                
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
    
}
