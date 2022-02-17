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
//https://docs.microsoft.com/en-us/sql/connect/jdbc/using-bulk-copy-with-the-jdbc-driver?view=sql-server-2017
public class DatabaseIO {

    private static String connectionUrl; 

    public DatabaseIO() {  
        connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=OriginC;user=ocm;password=p123456;encrypt=false"; 
    }      
    
    public static List<String[]> readData(String sProcName, String client) throws Exception {
        List<String[]> result = new ArrayList<String[]>();

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            // Query data by using a stored procedure.
            try (SQLServerCallableStatement cstmt = (SQLServerCallableStatement) con
                    .prepareCall("{call " + sProcName + "(?)}")) {
                cstmt.setString(1, client);  
                cstmt.execute();
                ResultSet rs = cstmt.getResultSet();

                //Get column count
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                if(columnCount < 1){
                    return result;
                }
                
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int index = 0; index < columnCount; index++) {  
                        row[index] = rs.getString(index + 1);
                    }
                    result.add(row);
                }
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            throw new Exception(String.format("Unable to read the data from the database. Error Message: %s", e.getMessage()));
        }

        return result;
    }      
    
    public static void WriteData(String dBTable, String[][] tableData) throws Exception {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            throw new Exception(String.format("Unable to write data to the database. Error Message: %s", e.getMessage()));
        }
    }  
    
    public ResultSet executeSprocNoParameter(String sProcName) throws Exception {
        Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();
        String sqlString = "EXEC " + sProcName;
        ResultSet rs = stmt.executeQuery(sqlString);

        return rs;
    }         
    
    public int generateLoadingId() throws Exception {
        int result = 0;

        try {
            ResultSet rs = executeSprocNoParameter("Upload_LoadingRecord_Insert");
            
            rs.next();
            result = rs.getInt(1);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            throw new Exception(String.format("Unable to generateLoadingId in the database. Error Message: %s", e.getMessage()));
        }

        return result;
    } 
    
}
