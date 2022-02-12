package farms4life2016.gui.displays;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.awt.Container;

import javax.swing.Timer;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.dataprocessing.Job;
import farms4life2016.gui.Colours;
import farms4life2016.gui.buttons.Button;
import farms4life2016.gui.buttons.MultipleChoice;
import farms4life2016.gui.buttons.NPButton;
import farms4life2016.gui.buttons.TextField;

public class JobUpdateDisplay extends GenericDisplay {
    
    NPButton title, errorMessage, textboxes[];
    TextField inputBoxes[];
    Button updateJob, cancelUpdate;
    MultipleChoice chooseIO;
    int mode;
    Job currentJob;
    
    public static final int ADD = 0, UPDATE = 1;

    public JobUpdateDisplay(Container p) {

        super(p);

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

        //format textboxes 
        for (int i = 0; i < textboxes.length; i++) {
            textboxes[i] = new NPButton(false, 0);
            textboxes[i].setFontSize(20);
            textboxes[i].setFontStyle(Font.BOLD);
            textboxes[i].setTextColour(Colours.WHITE);
            textboxes[i].setUnselectedColour(backgroundColour);
            textboxes[i].setDimensions(new Rectangle(50, 150 + 50*i, 100, 30));
            textboxes[i].setSelected(false);
            
        }

        //format input boxes
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

        //special buttons
        cancelUpdate = new Button() {

            @Override
            public void onClick(MouseEvent e) {
                if (dimensions.contains(e.getPoint())) parent.setVisible(false);
                
            }

            @Override
            public void drawSelf(Graphics2D g) {
                super.fillBgRect(g);
                super.drawBorders(g, 3, Colours.BLACK);
                super.drawText(g);
                
            }

        };
        cancelUpdate.setText("Cancel");
        cancelUpdate.setUnselectedColour(Colours.RED);
        cancelUpdate.setSelected(false);
        cancelUpdate.setDimensions(new Rectangle(400-100, 400, 100, 30));
        
        updateJob = new Button() {

            @Override
            public void onClick(MouseEvent e) {
                if (dimensions.contains(e.getPoint())) {
                    //TODO check for empty strings
                    if (mode == ADD) {
                        Controller.jobList.add(new Job(100, inputBoxes[0].getText(), inputBoxes[1].getText(), chooseIO.getChoice().charAt(3), inputBoxes[2].getText(), null));

                    } else if (mode == UPDATE) {
                        currentJob.setName(inputBoxes[0].getText());
                        currentJob.setClient(inputBoxes[1].getText());
                        currentJob.setType(chooseIO.getChoice().charAt(3));
                        currentJob.setFile(inputBoxes[2].getText());
                        currentJob.setDate(Calendar.getInstance());
                    }

                    Job.mergesort(Controller.jobList, Job.SORT_BY_ID);
                    Controller.mainMenu.jobTable.fillJobs(Controller.jobList, true);
                    parent.setVisible(false);
                }
                
            }

            @Override
            public void drawSelf(Graphics2D g) {
                super.fillBgRect(g);
                super.drawBorders(g, 3, Colours.BLACK);
                super.drawText(g);
                
            }
            
        };
        updateJob.setText("Add/Update");
        updateJob.setUnselectedColour(Colours.GREEN);
        updateJob.setSelected(false);
        updateJob.setDimensions(new Rectangle(50, 400, 100, 30));

        addMode();
        
        fps.start();

    }

    @Override
    protected void paintComponent(Graphics2D g) {

        chooseIO.drawSelf(g);
        title.drawSelf(g);
        cancelUpdate.drawSelf(g);
        updateJob.drawSelf(g);

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
            cancelUpdate.onClick(e);
            updateJob.onClick(e);
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

    public void setMode(int newMode, Job j) {
        mode = newMode;
        currentJob = j;
        if (mode == ADD) addMode();
        else if (mode == UPDATE) updateMode();
    }

    private void addMode() {
        for (int i = 0; i < inputBoxes.length; i++) {
            inputBoxes[i].setText("");
        }
        chooseIO.setChoice(true);
        title.setText("Add a Job");
        updateJob.setText("Add");
    }

    private void updateMode() {
        inputBoxes[0].setText(currentJob.getName());
        inputBoxes[1].setText(currentJob.getClient());
        inputBoxes[2].setText(currentJob.getFile());
        if (currentJob.getType() == 'I') chooseIO.setChoice(true);
        else chooseIO.setChoice(false);
        title.setText("Update a Job");
        updateJob.setText("Update");
    }

}
