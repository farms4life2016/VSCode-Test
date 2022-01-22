package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;

import random.Random;

public class Tables {
	
	public static final int WINDOW_W = 1280, WINDOW_H = 720; //w = 1030+100 h = 639+50

	static JPanel bigPanel, smallPanel;
	static CardLayout cl;
	static JFrame window;
	
	public static void main(String[] args) {
		
		//variables
		cl = new CardLayout();
		bigPanel = new FoodDisplayer(); //make a new window
		smallPanel = new PartyDisplayer(); //make a new window
		window = new JFrame("Table time!"); //name it Vasebreaker Endless
		Container c = window.getContentPane(); //make something to add all the things to

		//set window size
		window.setSize(WINDOW_W, WINDOW_H);

		//allow window to exit
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//add panel to frame
		
		c.setLayout(cl);
		c.add("card one", bigPanel);
		c.add("item two", smallPanel);
		

		//make window visible
		window.setVisible(true);

		//the window must be resized to work
		window.setBounds(0, 0, WINDOW_W, WINDOW_H);

		
	}
	
}

class FoodDisplayer extends JPanel implements ActionListener, MouseListener {
	
	
	JDialog jdia;
	
	String[][] data = {
			{"Ice cream", "14 min.", "Ice Slimes, Ice Bats, Spiked Ice Slimes", "0.67%"},
			{"Nachos", "16 min.", "Sand Sharks, Angry Tumbler", "3.33%"},
			{"Shrimp 'Po Boy", "18 min.", "Crab, Shark", "2%"}
	};
	String[] headers = {"Item", "Duration of Buff", "Source", "Drop chance"};
	
	StringDrawer strDrawer;
	
	private Timer fps; 
	
	Button b;
	

	
	public FoodDisplayer() {
		
		strDrawer = new StringDrawer();
		b = new Button(300, 500, 200, 50, "Push for spam.");
		
		
		this.addMouseListener(this);
		
		//update the screen once every 60 milliseconds
		fps = new Timer(60, this); 
		fps.start();
		
		
	}
	
	public void paintComponent(Graphics dummyGraphics) {
		super.paintComponent(dummyGraphics);
		Graphics2D g = (Graphics2D) dummyGraphics;
		g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		g.drawString("On no you're meeting all my standards", 100, 100);
		
		g.setFont(strDrawer.getFont().deriveFont(Font.PLAIN, 20));
		g.setColor(Color.GREEN);
		g.fillRect(100, 200, 700, 100);
		g.setColor(Color.BLACK);
		strDrawer.drawStringSuperCentered(g, "I love balloons!", new Rectangle(100, 200, 700, 100), 30);
		strDrawer.drawStringCenteredYLeftAligned(g, "Left Alignment test", new Rectangle(100, 200, 700, 100), 30);
		b.drawSelf(g, strDrawer);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(fps)) {
			repaint();
		} 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if (b.clickedOn(e)) {
			System.out.println("Pressing your pressure plate");
			jdia = new JDialog(Tables.window, "You have mail!", true);
			jdia.setSize(200, 300);
			jdia.setVisible(true);
		} else if (e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("Click!");
			Tables.cl.next(getParent());
			
		} else {
			System.out.println("I hate drop rates in Terraria!");
		}
		
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
	
}

class PartyDisplayer extends JPanel implements ActionListener, MouseListener {
	
	String[][] data = {
			{"Fog Machine", "1 g", "Party Girl", "At night"},
			{"Bubble Machine", "1 g", "Party Girl", "During the day"},
			{"Balloon Machine", "5 g", "Party Girl", "Always available"}
	};
	String[] headers = {"Item", "Cost", "Source", "Availability"};
	JTable table, table2;
	
	
	/*
	 * new stuff
	 */
	Table t = new Table(headers, data);
	StringDrawer s;
	
	
	private Timer fps; 
	
	public PartyDisplayer(String asdfghjkl) {
		
		s = new StringDrawer();
		//setBounds(0, 0, Tables.WINDOW_W, Tables.WINDOW_H);
		t.setDimensions(new Rectangle( 60, 200, 1280-120, 720 - 200 - 60));
		
		
		//this.addMouseListener(this);
		
		//update the screen once every 60 milliseconds
		fps = new Timer(60, this); 
		fps.start();
	}
	
	public void paintComponent(Graphics dummyGraphics) {
		Graphics2D g = (Graphics2D) dummyGraphics;
		t.drawSelf(g, s);
	}
	
	/*
	 * end of new stuff
	 */
	
	public PartyDisplayer() {
		
		this.setBounds(0, 0, Tables.WINDOW_W, Tables.WINDOW_H);
		
		table = new JTable(data, headers);
		table2 = new JTable(data, headers);
		
		//table.setBounds(200, 200, 200, 200);
		
		
		table.setSize(200, 200);
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
		table.setRowHeight(50);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		
		//componet 1
		cons.fill = cons.HORIZONTAL;
		cons.weightx = 0.5;
		cons.weighty = 0.5;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.anchor = cons.FIRST_LINE_START;
		//cons.ipady = 60;
		
		//cons.gridheight = cons.REMAINDER;
		//cons.insets = new Insets(50, 50, 50, 50);
		this.add(table, cons);
		
		
		//another comp
		cons.anchor = cons.BASELINE_LEADING;
		cons.gridx=0; cons.gridy = 1;
				
		this.add(table2, cons);
		
		this.addMouseListener(this);
		
		//update the screen once every 60 milliseconds
		fps = new Timer(60, this); 
		fps.start();
		
		
	}
	
	public PartyDisplayer(int asdfghjkl) {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.anchor = cons.FIRST_LINE_START;
		JPanel[] panels = new JPanel[9];
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel();
			//panels[i].setPreferredSize(new Dimension(300, 300));
			//panels[i].setSize(300, 300);
			panels[i].setBackground(new Color( Random.randintII(0, 255) , Random.randintII(0, 255), Random.randintII(0, 255)  ));
			//cons.gridheight = cons.REMAINDER;
			this.add(panels[i], cons);
		}
		
		
		
		
		
		
		
		
		this.addMouseListener(this);
		
		//update the screen once every 60 milliseconds
		fps = new Timer(60, this); 
		fps.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(fps)) {
			repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//cards.next(getParent());
		if (e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("Click!2");
			Tables.cl.next(getParent());
		}
		
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
	
	
	
}
