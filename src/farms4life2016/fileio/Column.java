package farms4life2016.fileio;

/**
 * Used to parse the XML configs
 */
public class Column {
        
    private String name;
    
    private int length;

    public Column() {}  

    public Column(String name, int length) {  
        super();  
        this.name = name;  
        this.length = length;  
    } 
    
    /*
     * setters and getters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {  
        this.name = name;  
    }  

    public int getLength() {
        return length;
    }

    public void setLength(int length) {  
        this.length = length;  
    }  
}
