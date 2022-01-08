package farms4life2016.dataprocessing;

public class Job {
    
    //variables
    private int id;
    private String client;
    private char type;
    private String name;
    private String file; //should I change this to java.util.File?

    /**
     * Make a new Job instance with specified parameters
     * @param i ID
     * @param c Client name
     * @param t Type of job
     * @param n Name of job
     * @param f File associated with job
     */
    public Job(int i, String c, char t, String n, String f) {

        setId(i);
        setClient(c);
        setType(t);
        setName(n);
        setFile(f);

    }

    /**
     * Make an empty job and then use setters manually and externally
     */
    public Job() {
       this(0, null, (char) 0, null, null);

    }

    /*
     * Setters and getters (thank you vscode for auto-genning this)
     */

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    @Override
    public String toString() {
        String out = id + "\t" + client + "\t" + type + "\t" + name + "\t" + file;
        return out;
    }
    


}
