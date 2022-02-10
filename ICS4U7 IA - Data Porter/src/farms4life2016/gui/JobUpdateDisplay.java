package farms4life2016.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.MultipleChoice;
import farms4life2016.gui.buttons.NPButton;
import farms4life2016.gui.buttons.TextField;

public class JobUpdateDisplay extends JPanel implements ActionListener, MouseMotionListener, MouseListener, KeyListener {
    
    NPButton textboxes;
    TextField inputBoxes;
    Button updateJob, cancelUpdate;
    MultipleChoice chooseIO;
    Timer fps;

    public JobUpdateDisplay() {

        chooseIO = new MultipleChoice();
        chooseIO.setDimensions(new Rectangle(10, 10, 200, 50));

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);


        fps = new Timer(60, this);
        fps.start();



    }

    @Override
    protected void paintComponent(Graphics graphics) {
        
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        chooseIO.drawSelf(g);

    }

    
	public void addNotify() { //to get KeyListener to work
		super.addNotify();
		requestFocus();
	}


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        chooseIO.onClick(e);
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(fps)) {
            super.repaint();
        }
        
    }

}
