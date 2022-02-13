package farms4life2016.fileio;

public abstract class ConfigBase {
    protected String[] columnNames;
    protected int[] columnLengths;
    protected String xmlns, workingFolder, connenectionString;
    protected String sectionName, ioName;
    protected String[][] data;

    public ConfigBase() {
        //intentionally blank
    }

    /* Setters and getters */

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public int[] getColumnLengths() {
        return columnLengths;
    }

    public void setColumnLengths(int[] columnLengths) {
        this.columnLengths = columnLengths;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getWorkingFolder() {
        return workingFolder;
    }

    public void setWorkingFolder(String workingFolder) {
        this.workingFolder = workingFolder;
    }

    public String getConnenectionString() {
        return connenectionString;
    }

    public void setConnenectionString(String connenectionString) {
        this.connenectionString = connenectionString;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getIoName() {
        return ioName;
    }

    public void setIoName(String ioName) {
        this.ioName = ioName;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    

}
