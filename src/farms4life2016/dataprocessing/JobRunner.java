package farms4life2016.dataprocessing;

import java.io.IOException;
import java.util.List;

import farms4life2016.fileio.FileIO;
import farms4life2016.fileio.PorterConfig;

public class JobRunner {
    
    String[] colHeaders;
    String[][] data;
    String name, delimiter, fileName, dbTable, remotePath;

    public JobRunner(String workingFolder, PorterConfig pconfig) {

        //copy strings from porter config
        name = pconfig.getName();
        delimiter = pconfig.getDelimiter();
        fileName = pconfig.getFilename();
        dbTable = pconfig.getDbTable();
        remotePath = pconfig.getRemotePath();

        //copy headers
        int length = pconfig.getColumns().size();
        colHeaders = new String[length];
        for (int i = 0; i < length; i++) {
            colHeaders[i] = pconfig.getColumns().get(i).getName();
        }
        
        //read data  TODO what if file name is *?
        String filePath = workingFolder + remotePath + fileName;
        String ext = FileIO.getFileExt(filePath);
        try {
            if (ext.equals("txt")) {
                List<String> input = FileIO.readAllTxt(filePath);

                data = new String[input.size()][colHeaders.length];
                for (int i = 0; i < input.size(); i++) {
                    data[i] = input.get(i).split(delimiter);
                }

            } else if (ext.equals("xlsx")) {
                data = FileIO.readGrid(filePath, colHeaders.length, -1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       
    }

    public void runJob() {
        
    }

    public void runExport() {
        
    }


}
