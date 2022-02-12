package farms4life2016.gui.buttons;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.Point;

/**
 * Draws an image from a file onto a specified rectangluar selection
 * @author farms4life2016
 */
public abstract class IconButton extends Button {

    protected Image image;

    public IconButton(String fileName) {
        this(new Rectangle(), fileName);
    }

    public IconButton(Rectangle dimensions, String fileName) {
        image = new ImageIcon(fileName).getImage();
        this.dimensions = dimensions;
    }

    public IconButton(Point upperLeftCorner, String fileName) {
        image = new ImageIcon(fileName).getImage();
        dimensions = new Rectangle( (int) (upperLeftCorner.getX()), (int)(upperLeftCorner.getY()), image.getWidth(null), image.getHeight(null));
    }

    @Override
    public void drawSelf(Graphics2D g) {
        g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
        
    }
    
}
