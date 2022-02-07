package farms4life2016.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import java.awt.event.MouseEvent;

import farms4life2016.gui.Drawable;


public abstract class Button extends Drawable {

    protected boolean isSelected;
    protected Color unselectedColour, selectedColour, textColour, currentColor;
    protected Rectangle dimensions;
    protected String text;
    protected int textFormat, fontStyle;
    protected float fontSize;

    public static final int CENTERED = 0, LEFT_ALIGN = 1;

    public Button() {

        //set vars to some non-null values
        super(0, 0, 100, 100);
        isSelected = false;
        unselectedColour = selectedColour = currentColor = Color.WHITE;
        textColour = Color.BLACK;
        text = "farms4life2016";
        textFormat = LEFT_ALIGN;
        fontStyle = Font.PLAIN;
        fontSize = 16;        

    }

    public abstract void onClick(MouseEvent e);

    @Override
    public String toString() {
        return dimensions.toString();
    }

    /**
     * Setters and getters 
     */

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Color getUnselectedColour() {
        return unselectedColour;
    }

    public void setUnselectedColour(Color unselectedColour) {
        this.unselectedColour = unselectedColour;
    }

    public Color getSelectedColour() {
        return selectedColour;
    }

    public void setSelectedColour(Color selectedColour) {
        this.selectedColour = selectedColour;
    }

    public Color getTextColour() {
        return textColour;
    }

    public void setTextColour(Color textColour) {
        this.textColour = textColour;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextFormat() {
        return textFormat;
    }

    public void setTextFormat(int textFormat) {
        this.textFormat = textFormat;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

}
