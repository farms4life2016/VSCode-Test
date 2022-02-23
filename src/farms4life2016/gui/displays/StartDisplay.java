package farms4life2016.gui.displays;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.IconButton;
import farms4life2016.gui.buttons.NPButton;

/**
 * Dumb display that I only made so my program looked more professional XD
 */
public class StartDisplay extends GenericDisplay {

    private Button toMenu;
    private NPButton welcome, title;
    private IconButton icon;
    
    /**
     * All this does is display a title, the logo,
     * some more text, and a single button that let's you
     * proceed to the main menu
     * @param p
     */
    public StartDisplay(Container p) {
        super(p);

        backgroundColour = Colours.GRAY40;
        welcome = new NPButton(false, 0);
        title = new NPButton(false, 0);
        icon = new IconButton(new Rectangle(550, 100, 500, 500), ".\\icons\\Logo.png") {

            @Override
            public void onClick(MouseEvent e) {
                
            }
            
        };
        toMenu = new Button() {

            @Override
            public void onClick(MouseEvent e) {

                if (dimensions.contains(e.getPoint())) {
                    Controller.cdLayout.show(Controller.window.getContentPane(), "menu");
                    Controller.setTitle("Data Porter - Main Menu");
                }
                    
            }

            @Override
            public void drawSelf(Graphics2D g) {
                super.fillBgRect(g);
                super.drawBorders(g, 3, Colours.BLACK);
                super.drawText(g);
                
            }

        };
        toMenu.setDimensions(new Rectangle(50, Controller.WINDOW_H-300, 400, 100));
        toMenu.setText("Get Started >>");
        toMenu.setTextColour(Colours.WHITE);
        toMenu.setUnselectedColour(Colours.OCEAN);
        toMenu.setSelected(false);
        toMenu.setFontSize(30);
        toMenu.setTextFormat(Button.LEFT_ALIGN);

        welcome.setDimensions(new Rectangle(50, 200, 500, 50));
        welcome.setText("Welcome to");
        welcome.setTextColour(Colours.WHITE);
        welcome.setUnselectedColour(Colours.GRAY40);
        welcome.setSelected(false);
        welcome.setFontSize(20);
        welcome.setTextFormat(Button.LEFT_ALIGN);

        title.setDimensions(new Rectangle(50, 250, 500, 100));
        title.setText("DATA PORTER");
        title.setTextColour(Colours.WHITE);
        title.setUnselectedColour(Colours.GRAY40);
        title.setSelected(false);
        title.setFontSize(69); //why not?
        title.setTextFormat(Button.LEFT_ALIGN);


    }

    @Override
    public void keyTyped(KeyEvent e) {
        //no typing         
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        toMenu.onClick(e);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(fps)) {
            repaint();
        }
        
    }

    @Override
    protected void paintComponent(Graphics2D g) {
        
        toMenu.drawSelf(g);
        welcome.drawSelf(g);
        title.drawSelf(g);
        icon.drawSelf(g);
        
    }
    
}
