package farms4life2016.fileio;

import java.util.List;

public class PorterConfig {
        
    private String name;
    private String delimiter;
    private String remotePath;
    private String filename;
    private String dbTable;
    private String dbSproc;
    private List<Column> columns;      

    public PorterConfig() {}  

    public PorterConfig(String name, String delimiter, String remotePath, 
        String filename, String dbTable, String dbSproc, List<Column> columns) {  
        super();  
        this.name = name;  
        this.delimiter = delimiter;  
        this.remotePath = remotePath;  
        this.filename = filename;  
        this.dbTable = dbTable;   
        this.dbSproc = dbSproc;  
        this.columns = columns;  
    }  

    public String getName() {
        return name;
    }

    public void setName(String name) {  
        this.name = name;  
    }   

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {  
        this.delimiter = delimiter;  
    }   

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {  
        this.remotePath = remotePath;  
    }   

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {  
        this.filename = filename;  
    }   

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {  
        this.dbTable = dbTable;  
    }   

    public String getDbSproc() {
        return dbSproc;
    }

    public void setDbSproc(String dbSproc) {  
        this.dbSproc = dbSproc;  
    }  

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {  
        this.columns = columns;  
    }  
}
