package farms4life2016.dataprocessing;

import java.util.List;

import farms4life2016.fileio.PorterConfig;

public class JobRunner {
    
    String[] colHeaders;
    String[][] data;
    String name, delimiter, fileName, dbTable, remotePath;

    public JobRunner(PorterConfig pconfig, String[] headers, List<String> inputData) {

        //copy strings from porter config
        name = pconfig.getName();
        delimiter = pconfig.getDelimiter();
        fileName = pconfig.getFilename();
        dbTable = pconfig.getDbTable();
        remotePath = pconfig.getRemotePath();

        //copy headers

        //copy data from arrays
        colHeaders = headers;
        data = new String[inputData.size()][];
        for (int i = 0; i < data.length; i++) {
            data[i] = inputData.get(i).split(delimiter);
        }
    }

}
