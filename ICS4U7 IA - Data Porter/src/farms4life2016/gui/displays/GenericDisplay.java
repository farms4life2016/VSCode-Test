package farms4life2016.gui.displays;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.Timer;

import farms4life2016.dataprocessing.Controller;

public abstract class GenericDisplay extends JPanel implements ActionListener, 
    MouseMotionListener, MouseListener, KeyListener, MouseWheelListener, WindowListener { //too many listeners

    protected Point mouse; //tracks mouse position
    protected Timer fps; //for refreshing the screen
    protected Color backgroundColour;

    public GenericDisplay() {

        mouse = new Point();
        fps = new Timer(60, this);

        //add listeners. WindowListener is added in Controller
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);

    }

    /*
     * These two listener methods still need to be implemented by child classes
     */

    @Override
    public abstract void keyTyped(KeyEvent e);

    @Override
    public abstract void mouseClicked(MouseEvent e);

    @Override
    public abstract void actionPerformed(ActionEvent e);
    
    @Override
    public void mouseMoved(MouseEvent e) {

        //change the location of the mouse
        mouse = e.getPoint();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        //fill in background
        g.setColor(backgroundColour);
        g.fillRect(0, 0, Controller.WINDOW_W, Controller.WINDOW_H);

        //paint the rest using Graphics2D
        paintComponent((Graphics2D)g);

    }

    /**
     * 
     * @param g
     */
    protected abstract void paintComponent(Graphics2D g);

    @Override
    public void addNotify() { //to get KeyListener to work
        super.addNotify();
        requestFocus();
    }

    /*
     * Rest of these methods I never use lol.
     * To reduce clutter of child classes, I will define them
     * here as empty methods. Override them if necessary
     */

    @Override
    public void keyPressed(KeyEvent e) {
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        
    }

    @Override
	public void windowOpened(WindowEvent e) {
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}


	@Override
	public void windowActivated(WindowEvent e) {
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

    @Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

   

}
