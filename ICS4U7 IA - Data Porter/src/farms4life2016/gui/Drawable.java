package farms4life2016.gui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Implement this class to indicate that this class can draw stuff onto the gui.
 */
public abstract class Drawable {

    protected Rectangle dimensions;

    public Drawable(int x, int y, int w, int h) {
        dimensions = new Rectangle(x, y, w, h);
    }

    public Drawable() {
        dimensions = new Rectangle();
    }
    
    /**
     * 
     * @param g
     */
    public abstract void drawSelf(Graphics2D g);

    
    public Rectangle getDimensions() {
        return dimensions;
    }

    public void setDimensions(Rectangle dimensions) {
        this.dimensions = dimensions;
    }

    public int getX() {
        return dimensions.x;
    }

    public int getY() {
        return dimensions.y;
    }

    public int getWidth() {
        return dimensions.width;
    }

    public int getHeight() {
        return dimensions.height;
    }


}
