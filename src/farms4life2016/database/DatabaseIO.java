package farms4life2016.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;

//https://docs.microsoft.com/en-us/sql/connect/jdbc/step-3-proof-of-concept-connecting-to-sql-using-java?view=sql-server-2016

public class DatabaseIO {

    private static final String CONNECTION_URL = "jdbc:sqlserver://localhost:1433;databaseName=OriginC;user=ocm;password=p123456;encrypt=false"; 

    private DatabaseIO() {  
        //never instantatiate this class
    }      
    
    public static List<String[]> readData(String sProcName, String client) throws SQLException {

        //stores the rows from the db.
        List<String[]> result = new ArrayList<String[]>();
        
        //connect to the database
        Connection connect = DriverManager.getConnection(CONNECTION_URL);

        //execute statement used to get data from db
        Statement statement = connect.createStatement();
        SQLServerCallableStatement callStatement = (SQLServerCallableStatement) connect.prepareCall("{call " + sProcName + "(?)}");
        callStatement.setString(1, client);  
        callStatement.execute();
        ResultSet resSet = callStatement.getResultSet();

        //Get column count
        ResultSetMetaData metaData = resSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        if (columnCount < 1) { //means no data
            return result;
        }
        
        //add rows from db data to java list
        while (resSet.next()) {
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {  
                row[i] = resSet.getString(i + 1);
            }
            result.add(row);
        }

        //close connections and statments
        callStatement.close();
        statement.close();
        connect.close();
       
        return result;
    }      
    
    //TODO reformat code XD
    public static void WriteData(String dBTable, String[][] tableData) throws SQLException {    
        
        //do not write if input is empty
        if (tableData.length < 1 || tableData[0].length < 1){
            return;
        } 

        try (Connection con = DriverManager.getConnection(CONNECTION_URL); 
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);) { 

            // Get data from the source table as a ResultSet.
            ResultSet rsData = stmt.executeQuery("SELECT TOP 0 * FROM " + dBTable);            

            // Insert records into the table.
            int rowCount = tableData.length;
            int columnCount = tableData[0].length;
            for (int i = 0; i < rowCount; i++) {  
                // Move to insert row and add column data with updateXXX() 
                rsData.moveToInsertRow();           
                for (int j = 0; j < columnCount; j++) {  
                    rsData.updateString(j+1, tableData[i][j]);
                }
                // Commit row
                rsData.insertRow();
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            throw e;
        }
    }   
    

    public static int generateLoadingId() throws SQLException {
        Connection connect = DriverManager.getConnection(CONNECTION_URL); Statement statement = connect.createStatement();
        String sqlString = "EXEC " + "Upload_LoadingRecord_Insert";
        ResultSet resSet = statement.executeQuery(sqlString);
        int result = 0;

        resSet.next();
        result = resSet.getInt(1);

        //close io
        statement.close();
        connect.close();
        
        return result;
    } 
    
}
