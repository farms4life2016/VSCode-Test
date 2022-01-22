package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * Creates a button for a JPanel.
 * @author farms4life2016
 *
 */
public class Button {
	
	private boolean isSelected;
	private Color unselectedColour, selectedColour, textColour;
	private Rectangle dimensions;
	private String text;
	private float size;
	
	/**
	 * Creates a 1 x 1 px button at (1, 1). This button is gray 
	 * when not selected and black when selected. The text will be white and 
	 * drawn in size 16 font. The text will say, "Sample Text".
	 */
	public Button() {
		this(1, 1, 1, 1, "Sample Text");
	}
	
	/**
	 * Creates a new button with the specified parameters. This button is gray 
	 * when not selected and black when selected. The text will be white and 
	 * drawn in size 16 font.
	 * @param x x-position of button on the display
	 * @param y y-position of button on the display
	 * @param w width of the button
	 * @param h height of the button
	 * @param text String to be drawn on the button. Will be drawn super centered on the button.
	 */
	public Button(int x, int y, int w, int h, String text) {
		this(x, y, w, h, Color.GRAY, Color.BLACK, Color.WHITE, text, 16);
	}
	
	/**
	 * Creates a new button with the specified parameters.
	 * @param x x-position of button on the display
	 * @param y y-position of button on the display
	 * @param w width of the button
	 * @param h height of the button
	 * @param uColour colour of the button when it is not selected
	 * @param sColour colour of the button when it is selected (moused over)
	 * @param tColour colour of the text displayed 
	 * @param text String to be drawn on the button. Will be drawn super centered on the button.
	 * @param fontSize The size of the String to be drawn
	 */
	public Button(int x, int y, int w, int h, Color uColour, Color sColour, Color tColour, String text, float fontSize) {
		dimensions = new Rectangle(x, y, w, h);
		unselectedColour = uColour;
		selectedColour = sColour;
		textColour = tColour;
		this.text = text;
		size = fontSize;
		this.textColour = Color.BLACK;
		
	}
	
	/**
	 * Updates the colour of the button. To be used under the actionPerformed(...)
	 * @param mousePos
	 */
	public void update(Point mousePos) {
		if (dimensions.contains(mousePos)) {
			isSelected = true;
		} else {
			isSelected = false;
		}
	}
	
	/**
	 * Checks if this button is being pressed. Used under mouseClicked(...)
	 * @param e MouseEvent 
	 * @return True if this button is being clicked on. False otherwise.
	 */
	public boolean clickedOn(MouseEvent e) {
		return dimensions.contains(e.getPoint()) && (e.getButton() == MouseEvent.BUTTON1);
	}
	
	/**
	 * Draws the button on the display
	 * @param g The display to be drawn on
	 * @param sd StringDrawer used to center the String onto the button.
	 */
	public void drawSelf(Graphics2D g, StringDrawer sd) {
		
		//colour of button depending on selected or not
		if (isSelected) {
			g.setColor(selectedColour);
			
		} else {
			g.setColor(unselectedColour);
		}
		g.fillRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);
		g.setColor(textColour);
		sd.drawStringSuperCentered(g, text, dimensions, size);
		
	}
	
	/*
	 * Encapsulation
	 */

	public void setUnselectedColour(Color unselectedColour) {
		this.unselectedColour = unselectedColour;
	}

	public void setSelectedColour(Color selectedColour) {
		this.selectedColour = selectedColour;
	}

	public void setTextColour(Color textColour) {
		this.textColour = textColour;
	}

	public void setDimensions(Rectangle dimensions) {
		this.dimensions = dimensions;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setSize(float size) {
		this.size = size;
	}

}
