package farms4life2016.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;

public class Tables {
	
	public static final int WINDOW_W = 1200, WINDOW_H = 720; //w = 1030+100 h = 639+50

	static JPanel bigPanel, smallPanel;
	
	public static void main(String[] args) {
		
		//variables
		CardLayout kaard = new CardLayout();
		bigPanel = new FoodDisplayer(kaard); //make a new window
		smallPanel = new PartyDisplayer(kaard); //make a new window
		JFrame window = new JFrame("Table time!"); //name it Vasebreaker Endless
		Container c = window.getContentPane(); //make something to add all the things to

		//set window size
		window.setSize(WINDOW_W, WINDOW_H);

		//allow window to exit
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//add panel to frame
		c.setLayout(kaard);
		c.add(bigPanel);
		

		//make window visible
		window.setVisible(true);

		//the window must be resized to work
		window.setBounds(0, 0, WINDOW_W, WINDOW_H);

		
	}
	
}

class FoodDisplayer extends JPanel implements ActionListener, MouseListener {
	
	String[][] data = {
			{"Ice cream", "14 min.", "Ice Slimes, Ice Bats, Spiked Ice Slimes", "0.67%"},
			{"Nachos", "16 min.", "Sand Sharks, Angry Tumbler", "3.33%"},
			{"Shrimp 'Po Boy", "18 min.", "Crab, Shark", "2%"}
	};
	String[] headers = {"Item", "Duration of Buff", "Source", "Drop chance"};
	JTable table;
	
	StringDrawer strDrawer;
	
	
	private Timer fps; 
	CardLayout cards;
	
	public FoodDisplayer(CardLayout card) {
		
		cards  = card;
		strDrawer = new StringDrawer();
		
		table = new JTable(data, headers);
		//table.setBounds(200, 200, 200, 200);
		
		this.add(table);
		table.setSize(200, 200);
		table.repaint();
		
		this.addMouseListener(this);
		
		//update the screen once every 60 milliseconds
		fps = new Timer(60, this); 
		fps.start();
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		g.drawString("On no you're meeting all my standards", 100, 100);
		
		g.setFont(strDrawer.getFont().deriveFont(Font.PLAIN, 20));
		g.setColor(Color.GREEN);
		g.fillRect(100, 200, 700, 100);
		g.setColor(Color.BLACK);
		strDrawer.drawStringSuperCentered(g2, "I love balloons!", new Rectangle(100, 200, 700, 100), 30);
		strDrawer.drawStringCenteredYLeftAligned(g2, "Left Alignment test", new Rectangle(100, 200, 700, 100), 30);
		
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
			System.out.println("Click!");
			getParent().add(Tables.smallPanel);
			getParent().remove(this);
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
	JTable table;
	
	
	private Timer fps; 
	CardLayout cards;
	
	public PartyDisplayer(CardLayout card) {
		
		cards  = card;
		
		table = new JTable(data, headers);
		//table.setBounds(200, 200, 200, 200);
		
		this.add(table);
		table.setSize(200, 200);
		table.repaint();
		
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
			getParent().add(Tables.bigPanel);
			getParent().remove(this);
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
