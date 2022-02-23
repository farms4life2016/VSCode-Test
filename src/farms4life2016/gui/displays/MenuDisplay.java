package farms4life2016.gui.displays;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.Container;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.ErrorBox;
import farms4life2016.gui.buttons.InfoBox;
import farms4life2016.gui.buttons.NPButton;
import farms4life2016.gui.buttons.SearchBar;
import farms4life2016.gui.tables.Table;

/**
 * The main menu displayer
 */
public class MenuDisplay extends GenericDisplay {
    
	//these two are public since a lot of other classes
	//interact with them
	public Table jobTable;
	public ErrorBox errorBar;

	private Button extraInfo, createNewJob, toStart;
	private SearchBar searchBar;
	private NPButton scrollBarReplacement, title;
	
	public MenuDisplay(Container p) {

		super(p); //init timers and add listeners
		
		//variables
		jobTable = new Table(Controller.jobList);
		extraInfo = new InfoBox();
		searchBar = new SearchBar();
		createNewJob = new Button() {
			@Override
			public void onClick(MouseEvent e) {
				if (dimensions.contains(e.getPoint())) {
					Controller.jobUpdater.setVisible(true, null);
				}
			}

			@Override
			public void drawSelf(Graphics2D g) {
				super.fillBgRect(g);
				super.drawText(g);
			}
			
		};
		createNewJob.setText("Add a job");
		createNewJob.setFontSize(20);
		createNewJob.setTextColour(Colours.WHITE);
		createNewJob.setDimensions(new Rectangle(260, 140, 200, 40));
		createNewJob.setTextFormat(Button.CENTERED);
		createNewJob.setUnselectedColour(Colours.GREEN);
		createNewJob.setSelected(false);

		//format search bar
		searchBar.setDimensions(new Rectangle(760, 140, 250, 40));
		searchBar.setFontSize(20);

		//this buttons brings the user back to the start page
		//useful is you're wiping down the keyboard and don't want
		//to delete a job by mistake
		toStart = new Button() {

			@Override
			public void onClick(MouseEvent e) {
				if (dimensions.contains(e.getPoint())) {
					Controller.cdLayout.show(Controller.window.getContentPane(), "start");
					Controller.setTitle("Data Porter - Welcome");
				}
					
				
			}

			@Override
			public void drawSelf(Graphics2D g) {
				super.fillBgRect(g);
				super.drawText(g);
				
			}

		};
		toStart.setDimensions(new Rectangle(50, 140, 200, 40));
		toStart.setText("Welcome screen");
		toStart.setFontSize(20);
		toStart.setTextColour(Colours.WHITE);
		toStart.setTextFormat(Button.CENTERED);
		toStart.setUnselectedColour(Colours.BLUE);
		toStart.setSelected(false);

		errorBar = new ErrorBox();

		//we don't have a scrollbar, so this is how we tell
		//the user how many jobs are left
		scrollBarReplacement = new NPButton(false, 0);
		scrollBarReplacement.setDimensions(new Rectangle(Controller.WINDOW_W - 400, Controller.WINDOW_H-65, 300, 20));
		scrollBarReplacement.setUnselectedColour(Colours.GRAY40);
		scrollBarReplacement.setFontSize(12);
		scrollBarReplacement.setSelected(false);
		
		title = new NPButton(false, 0);
		title.setText("Main Menu");
		title.setDimensions(new Rectangle(50, 30, 200, 100));
		title.setUnselectedColour(Colours.GRAY40);
		title.setFontSize(40);
		title.setTextColour(Colours.GRAY100); //don't make the title too distracting
		title.setSelected(false);

		backgroundColour = (Colours.GRAY40);
		
		//start timer
		fps.start();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//refresh the screen
		if (e.getSource().equals(fps)) {
			repaint();

			//and other refreshes
			searchBar.onRefresh();
			updateScrollBarReplacement();
		}
		
	}
	
	@Override
	public void paintComponent(Graphics2D g) {
		
		//draw all the "buttons"
		jobTable.drawSelf(g);
		extraInfo.drawSelf(g);
		searchBar.drawSelf(g);
		createNewJob.drawSelf(g);
		toStart.drawSelf(g);
		errorBar.drawSelf(g);
		scrollBarReplacement.drawSelf(g);
		title.drawSelf(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		//detect left-click
		if (e.getButton() == MouseEvent.BUTTON1) {
			jobTable.onClick(e);
			createNewJob.onClick(e);
			toStart.onClick(e);

			//java is dumb. when a dialogue goes visible, all keyboard input
			//goes to dialogue, even when dialogue closes; so we have to manually redirect
			//key input back to main menu
			if (searchBar.getDimensions().contains(e.getPoint())) {
				addNotify();
				searchBar.onClick(e);
			}
			
		}
		
	}

	//search bar
	@Override
	public void keyTyped(KeyEvent e) {
		searchBar.onType(e);
		
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

	/**
	 * This method allows other classes to change the info box's text
	 * @param newText
	 */
	public void setInfoText(String newText) {
		extraInfo.setText(newText);
	}

	/**
	 * tell user what is the first job in the list and the last one;
	 * then they know when they have hit the bottom or top of the job list
	 */
	private void updateScrollBarReplacement() {

		if (Controller.jobList.length() > 0) {
			scrollBarReplacement.setText( "First Job's ID: " + ((Job)(Controller.jobList.get(0))).getId() + 
			"  --  Last Job's ID: " + ((Job)(Controller.jobList.get(Controller.jobList.length()-1))).getId() );
		} else {
			scrollBarReplacement.setText("No jobs are on display, but you can add one!");
		}

		//the text will also change to reflect changes after sorting

	}

}
