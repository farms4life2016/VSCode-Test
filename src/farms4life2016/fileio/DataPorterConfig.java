package farms4life2016.fileio;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement; 

/**
 * Used to parse the XML config files
 */
@XmlRootElement
public class DataPorterConfig {    

    private String workingFolder; 
        
    private List<PorterConfig> porterConfigs;  

    public DataPorterConfig() {}  

    public DataPorterConfig(String workingFolder, List<PorterConfig> porterConfigs) {  
        super();  
        this.workingFolder = workingFolder;  
        this.porterConfigs = porterConfigs; 
    }      
    
    @XmlAttribute 
    public String getWorkingFolder() {
        return workingFolder;
    }

    public void setWorkingFolder(String workingFolder) {  
        this.workingFolder = workingFolder;  
    } 

    @XmlElement     
    public List<PorterConfig> getPorterConfigs() {
        return porterConfigs;
    }

    public void setPorterConfigs(List<PorterConfig> porterConfigs) {  
        this.porterConfigs = porterConfigs;  
    }  
}  
