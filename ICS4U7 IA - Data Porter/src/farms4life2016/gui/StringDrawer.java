package farms4life2016.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StringDrawer {
	
	private static Font inconsolata;
	
	public StringDrawer() {
		
		try {
			FileInputStream in = new FileInputStream( new File(".\\src\\farms4life2016\\gui\\Inconsolata-Regular.ttf"));
			inconsolata = Font.createFont(Font.TRUETYPE_FONT, in);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	public static Font getFont() {
		return inconsolata;
	}
	
	public static void drawStringSuperCentered(Graphics2D g, String text, Rectangle textbox, int style, float size) {
		
		// Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(inconsolata.deriveFont(style, size));
	    // Determine the X coordinate for the text
	    int x = textbox.x + (textbox.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = textbox.y + ((textbox.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(inconsolata.deriveFont(size));
	    // Draw the String
	    g.drawString(text, x, y);
		
	} //https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
	
	
	public static void drawStringCenteredYLeftAligned(Graphics2D g, String text, Rectangle textbox, int style, float size) {
		
	    FontMetrics metrics = g.getFontMetrics(inconsolata.deriveFont(style, size));
	    // do not modify X because we want it left aligned 
	    int y = textbox.y + ((textbox.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(inconsolata.deriveFont(size));
	    g.drawString(" " + text, textbox.x, y);
		
	}
	
	

}
