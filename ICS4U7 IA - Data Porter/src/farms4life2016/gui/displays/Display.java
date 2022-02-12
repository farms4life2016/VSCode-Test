package farms4life2016.gui.displays;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.Container;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.InfoBox;
import farms4life2016.gui.buttons.NPButton;
import farms4life2016.gui.buttons.TextField;
import farms4life2016.gui.tables.Table;


public class Display extends GenericDisplay {
    
	private Table jobTable;
	private static Button extraInfo, createNewJob;
	private TextField textField;
	
	
	

	/**
	 * Currently, I'm displaying some stuff.
	 */
	public Display(Container p) {

		super(p); //init timers and add listeners
		
		//variables
		mouse = new Point(0,0);
		jobTable = new Table(Controller.jobList);
		extraInfo = new InfoBox();
		textField = new TextField();
		createNewJob = new Button() {
			@Override
			public void onClick(MouseEvent e) {
				if (dimensions.contains(e.getPoint())) {
					System.out.println(text);
				}
				
				
			}

			@Override
			public void drawSelf(Graphics2D g) {
				super.fillBgRect(g);
				
			}
			
		};

		//new job test
		createNewJob.setDimensions(new Rectangle(5, 70, 500, 50));
		createNewJob.setText("Click me to add a job");
		createNewJob.setUnselectedColour(Colours.WHITE);
		createNewJob.setSelected(false);
		
		//format extra info button
		extraInfo.setDimensions(new Rectangle(50, 200+12*30, 950, 50));
		extraInfo.setUnselectedColour(Color.CYAN);
		extraInfo.setSelected(false);
		extraInfo.setFontSize(32);
		extraInfo.setFontStyle(Font.BOLD);
		extraInfo.setText(NPButton.DEFAULT_INFO_STRING);

		//code for testing type-in button fun
		textField.setDimensions(new Rectangle(0, 0, 500, 50));

		backgroundColour = (Colours.GRAY40);
		
		//String[] headers = {"ID", "Name", "Client", "Type", "File", "Date"};

		//start timer
		fps.start();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//refresh the screen
		if (e.getSource().equals(fps)) {
			repaint();

			//and other refreshes
			textField.onRefresh();
		}
		
		
	}
	
	@Override
	public void paintComponent(Graphics2D g) {
		
		//draw all the "buttons"
		jobTable.drawSelf(g);
		extraInfo.drawSelf(g);
		textField.drawSelf(g);
		createNewJob.drawSelf(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		//detect left-click
		if (e.getButton() == MouseEvent.BUTTON1) {
			jobTable.onClick(e);
			textField.onClick(e);
			createNewJob.onClick(e);
		}
		
	}

	/*
	 * For the search bar later on 
	 */
	
	@Override
	public void keyTyped(KeyEvent e) {
		textField.onType(e);
		
	}

	/*
	 * Scrollwheel might be easier to implement than buttons to change pages,
	 * plus the former is more user-friendly
	 */

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		jobTable.onScroll(e);
		
	}

	/*
 	 * We have to specify more about how to close the program
 	 */


	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("you tried to close the program!"); //more debugging stuff
		Controller.terminate();
		System.exit(0);
		
	}

	public static void setInfoText(String newText) {
		extraInfo.setText(newText);
	}

}
