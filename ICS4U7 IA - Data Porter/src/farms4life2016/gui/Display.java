package farms4life2016.gui;


import java.awt.Color;
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
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Timer;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.Job;



public class Display extends JPanel implements ActionListener, MouseMotionListener, MouseListener, KeyListener, MouseWheelListener, WindowListener {
    
    private Point mouse;
	private Table jobTable;
	private Timer fps; 
	

	/**
	 * Currently, I'm not displaying a lot. 
	 */
	public Display() {
		
		//variables
		mouse = new Point(0,0);
		String[] headers = {"ID", "Client", "Type", "Name", "File"};
		String[][] data;
		jobTable = new Table(headers, Job.convertListIntoArray(Controller.jobList));

		//format table
		jobTable.setDimensions(new Rectangle(50, 200, Controller.WINDOW_W-130, Controller.WINDOW_H-330));
		
		
		//add listeners. WindowListener is added in Controller
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		addMouseWheelListener(this);
		
		//update the screen once every 60 milliseconds
		fps = new Timer(60, this); 
		fps.start();
		
		
		
	}
	
	
	public void addNotify() { //to get KeyListener to work
		super.addNotify();
		requestFocus();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

		mouse.setLocation(e.getX(), e.getY());
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//refresh the screen
		if (e.getSource().equals(fps)) {
			repaint();
		}
		
		
	}
	
	
	public void paintComponent(Graphics graphics) {

		Graphics2D g = (Graphics2D) graphics;
		
		//currently just a white screen
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//g.setColor(Color.black);
		//g.fillRect(TABLE.x, TABLE.y, TABLE.width, TABLE.height);

		jobTable.drawSelf(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
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


	/*
	 * For the search bar later on 
	 */
	
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

	/*
	 * Scrollwheel might be easier to implement than buttons to change pages,
	 * plus the former is more user-friendly
	 */

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	/*
 	 * We have to specify more about how to close the program
 	 */

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("you tried to close the program!"); //more debugging stuff
		Controller.terminate();
		System.exit(0);
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
