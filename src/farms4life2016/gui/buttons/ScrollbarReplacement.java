package farms4life2016.gui.buttons;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.gui.tables.Table;

public class ScrollbarReplacement extends NPButton {

    private Table parent;
    private int total, start, end;

    public ScrollbarReplacement(Table t) {
        super(false, 0);
        parent = t;

        total = Controller.jobList.length();
        //start = parent.displayJob.index;
        
    }

    public void onUpdate() {
        
    }
    
}
