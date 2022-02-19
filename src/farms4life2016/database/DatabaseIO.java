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

    private static final String CONNECTION_URL = "jdbc:sqlserver://localhost:1433;databaseName=OriginC;user=ocm;password=p123456;encrypt=false"; 

    private DatabaseIO() {  
        //never instantatiate this class
    }      
    
    public static List<String[]> readData(String sProcName, String client) throws Exception {

        //stores the rows from the db.
        List<String[]> result = new ArrayList<String[]>();

        try {
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

        } catch (SQLException e) {
            //throw a new exception because we want to specify more information
            throw new Exception("Unable to read the data from the database. Error Message: " + e.getMessage());
        }

        return result;
    }      
    
    public static void WriteData(String dBTable, String[][] tableData) throws Exception { //TODO research this part please
        try (Connection con = DriverManager.getConnection(CONNECTION_URL); Statement stmt = con.createStatement();) {
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            throw new Exception(String.format("Unable to write data to the database. Error Message: %s", e.getMessage()));
        }
    }  
    
    
    private static ResultSet executeSprocNoParameter(String sProcName) throws Exception {
        Connection con = DriverManager.getConnection(CONNECTION_URL); Statement stmt = con.createStatement();
        String sqlString = "EXEC " + sProcName;
        ResultSet rs = stmt.executeQuery(sqlString);

        return rs;
    }         
    
    public static int generateLoadingId() throws Exception {
        int result = 0;

        try {
            ResultSet rs = executeSprocNoParameter("Upload_LoadingRecord_Insert");
            
            rs.next();
            result = rs.getInt(1);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            throw new Exception("Unable to generateLoadingId in the database. Error Message: " + e.getMessage());
        }

        return result;
    } 
    
}
