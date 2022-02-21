package farms4life2016.dataprocessing;

import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Job {
    
    //variables
    private int id;
    private String name;
    private String client;
    private char type;
    private String file; //should I change this to java.util.File?
    private Calendar date;
    private boolean active;
    
    public static final SimpleDateFormat LONG_DATE_FORMATER = new SimpleDateFormat("MMM d, yyyy '('hh:mm:ss z')'");
    public static final SimpleDateFormat SHORT_DATE_FORMATER = new SimpleDateFormat("yyyy-MMM-dd-hh-mm-ss");

    
    //sorting constants
    public static final int SORT_BY_ID = 0, SORT_BY_CLIENT = 1, SORT_BY_TYPE = 2, SORT_BY_NAME = 3, SORT_BY_FILE = 4, SORT_BY_DATE = 5;

     /**
      * Make a new Job instance with specified parameters
      * @param i ID
      * @param n Name
      * @param c Client
      * @param t Type
      * @param f File name
      * @param d Date
      */
    public Job(int i, String n, String c, char t, String f, String d) {

        this.date = Calendar.getInstance();

        setId(i);
        setClient(c);
        setType(t);
        setName(n);
        setFile(f);

        if (d != null) setDate(d);

    }

    /**
     * Make an empty job and then use setters manually and externally
     */
    public Job() {
       this(0, null, null, (char) 0, null, null);

    }

    public static void mergesort(DLinkedList tableRows, int field) {

        if (tableRows.length() == 0) return; //can't sort empty list
        
        split(tableRows, 0, tableRows.length()-1, field);
    }

    private static void split(DLinkedList tableRows, int left, int right, int field) {

        if (left < right) { //mergesort algorithm 
            int mid = (left + right)/2; //find mid
            split(tableRows, left, mid, field); //split list into two halves
            split(tableRows, mid+1, right, field);
            merge(tableRows, left, mid, right, field); //then merge the two sorted halves
        }

    }

    private static void merge(DLinkedList tableRows, int left, int mid, int right, int field) {

        int leftPointer = left, rightPointer = mid + 1;

		Job[] temp = new Job[right-left+1];
		
		for (int i = 0; i < temp.length; i++) {
		
            //if either half has finished traversing, copy the remaining elements in the other half
			if (leftPointer > mid) {
				temp[i] = (Job) tableRows.get(rightPointer);
				rightPointer++;
			} else if (rightPointer > right) {
				temp[i] = (Job) tableRows.get(leftPointer);
				leftPointer++;
			} else {

                //for the ease of typing
                Job lJob = (Job) tableRows.get(leftPointer), rJob = (Job) tableRows.get(rightPointer);

                //consider what we are sorting by
                int comparison = 0;
                if (field == SORT_BY_CLIENT) comparison = (lJob).getClient().compareTo((rJob).getClient()); 
                else if (field == SORT_BY_FILE) comparison = (lJob).getFile().compareTo((rJob).getFile());
                else if (field == SORT_BY_ID) comparison = Integer.valueOf((lJob).getId()).compareTo( Integer.valueOf((rJob).getId()));
                else if (field == SORT_BY_NAME) comparison = (lJob).getName().compareTo((rJob).getName());
                else if (field == SORT_BY_TYPE) comparison = Character.valueOf((lJob).getType()).compareTo( Character.valueOf((rJob).getType()) );
                else if (field == SORT_BY_DATE) comparison = lJob.getDate().compareTo(rJob.getDate());
                
                //now sort 
                if (comparison < 0) {
                    temp[i] = lJob;
                    leftPointer++;
                } else if (comparison > 0) {
                    temp[i] = rJob;
                    rightPointer++;
                } else {
                    //if same field, then put the one with smaller id first
                    //all ids are unique according to my mom
                    comparison = Integer.valueOf((lJob).getId()).compareTo( Integer.valueOf((rJob).getId()) );
                    if (comparison < 0) {
                        temp[i] = lJob;
                        leftPointer++;
                    } else if (comparison > 0){
                        temp[i] = rJob;
                        rightPointer++;
                    } else { //handle error lol
                        System.out.println("You have two jobs with the same ID!");
                    }

                }

            }

		}

        //copy from sorted array to unsorted list
        DNode n = tableRows.getNode(left);
		for (int i = 0; i < temp.length; i++) {
            n.setItem(temp[i]);
			n = n.getNext();
		}
		
    }

    public static String[][] convertListIntoArray(DLinkedList list) {

        if (list.length() == 0) {//TODO this is wrong lol
            String[][] empty = {{"", "", "", "", "", ""}};
            return empty;
        }

        String[][] output = new String[list.length()][7]; //there are 7 fields in each job

        DNode n = list.getNode(0);
        //copy from list to array
        for (int i = 0; i < list.length(); i++) {
            Job j = (Job) n.getData(); //assume dlinkedlist holds jobs only
            output[i][0] = Integer.toString(j.id); 
            output[i][1] = j.name;
            output[i][2] = j.client;
            output[i][3] = Character.toString(j.type);
            output[i][4] = j.file;
            output[i][5] = LONG_DATE_FORMATER.format(j.date.getTime());
            if (j.isActive()) output[i][6] = "Active";
            else output[i][6] = "Deleted";
            n = n.getNext();

        }

        return output;
    }

    
    @Override
    public String toString() {
        String out = id + "\t" + client + "\t" + type + "\t" + name + "\t" + file + "\t" + LONG_DATE_FORMATER.format(date.getTime());
        return out;
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setDate(String date) {

        try {
            this.date.setTime(LONG_DATE_FORMATER.parse(date));
        } catch (ParseException e) {
            System.out.println("You have failed to parse the date!");
            e.printStackTrace();
        }
        
    }

    public boolean isActive() {
        return active;
    }

    public String getActiveString() {
        if (active) return "Active";
        else return "Deletd"; //we need to have the same number of chars for both statuses
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLongDate() {
        return LONG_DATE_FORMATER.format(date.getTime());
    }

    public void setActive(String activeString) {
        if (activeString.equals("Active")) {
            active = true;
        } else {
            active = false;
        }
    }

    public static DLinkedList linearSearch(DLinkedList jobList, String key) {

        DNode n = jobList.getNode(0);
        DLinkedList results = new DLinkedList();

        //we will perform the search ignoring capitalization
        mergesort(jobList, SORT_BY_ID);
        key = key.toLowerCase();

        //perform linear search
        for (int i = 0; i < jobList.length(); i++) {

            Job j = (Job)n.getData(); 

            //check every single field for the key
            if (Integer.toString(j.id).contains(key) ||
            (j.name.toLowerCase().contains(key)) ||
            (j.client.toLowerCase().contains(key)) ||
            (Character.toString(j.type).toLowerCase().contains(key)) ||
            (j.file.toLowerCase().contains(key)) ||
            (LONG_DATE_FORMATER.format(j.date.getTime()).toLowerCase().contains(key)) ) {

                //if the key is a substring of any
                //field, then put it in the search results
                results.add(j);

            }

            n = n.getNext();
        }
        
        return results;
    }

}
