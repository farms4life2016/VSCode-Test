package farms4life2016.gui.displays;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.MultipleChoice;
import farms4life2016.gui.buttons.NPButton;
import farms4life2016.gui.buttons.TextField;

public class JobUpdateDisplay extends GenericDisplay {
    
    NPButton title, textboxes[];
    TextField inputBoxes[];
    Button updateJob, cancelUpdate;
    MultipleChoice chooseIO;
    Timer fps;

    public JobUpdateDisplay() {

        //init variables
        chooseIO = new MultipleChoice();
        backgroundColour = (Colours.GRAY60);
        fps = new Timer(60, this);
        title = new NPButton(false , 0);
        textboxes = new NPButton[4];
        inputBoxes = new TextField[3];

        //format title textbox
        title.setFontSize(40);
        title.setFontStyle(Font.BOLD);
        title.setUnselectedColour(backgroundColour);
        title.setTextColour(Colours.WHITE);
        title.setDimensions(new Rectangle(50, 20, 400, 100));
        title.setSelected(false);
        title.setText("Add/Update a Job");

        //format textboxes and input boxes
        for (int i = 0; i < textboxes.length; i++) {
            textboxes[i] = new NPButton(false, 0);
            textboxes[i].setFontSize(20);
            textboxes[i].setFontStyle(Font.BOLD);
            textboxes[i].setTextColour(Colours.WHITE);
            textboxes[i].setUnselectedColour(backgroundColour);
            textboxes[i].setDimensions(new Rectangle(50, 150 + 50*i, 100, 30));
            textboxes[i].setSelected(false);
            
        }

        for (int i = 0; i < inputBoxes.length; i++) {
            inputBoxes[i] = new TextField();
            inputBoxes[i].setFontSize(18);
            inputBoxes[i].setFontStyle(Font.PLAIN);
            inputBoxes[i].setTextColour(Colours.BLACK);
            inputBoxes[i].setUnselectedColour(Colours.GRAY160);
            inputBoxes[i].setSelectedColour(Colours.WHITE);
            inputBoxes[i].setDimensions(new Rectangle(150, 150 + 50*i, 250, 30));
            inputBoxes[i].setSelected(false);
        }

        //multiple choice field
        chooseIO.setDimensions(new Rectangle(150, 150+50*3, 250, 30));

        //set text
        textboxes[0].setText("Name:");
        textboxes[1].setText("Client:");
        textboxes[2].setText("File:");
        textboxes[3].setText("Type:");
        //TODO remember to export date

        

        


        
        fps.start();



    }

    @Override
    protected void paintComponent(Graphics2D g) {

        chooseIO.drawSelf(g);
        title.drawSelf(g);

        for (int i = 0; i < textboxes.length; i++) {
            textboxes[i].drawSelf(g);
        }
        for (int i = 0; i < inputBoxes.length; i++) {
            inputBoxes[i].drawSelf(g);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        for (int i = 0; i < inputBoxes.length; i++) {
            inputBoxes[i].onType(e);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            chooseIO.onClick(e);
            for (int i = 0; i < inputBoxes.length; i++) {
                inputBoxes[i].onClick(e);
            }

        }
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(fps)) {
            repaint();
            for (int i = 0; i < inputBoxes.length; i++) {
                inputBoxes[i].onRefresh();
            }
        }
    }

}
