package org.boshed;
import java.util.Locale;
import java.sql.*;

public class SQLEngine {
    static private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static private final String USER = "postgres";
    static private final String PASS = "Tunnel27";
    Statement stmt;
    PreparedStatement p_stmt;
    private Connection conn;

    public SQLEngine() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("something gone wrong");
        }
        
        
    }

    /** Returns a java.sql.ResultSet from a properly formatted SQL string.  Used the Initialised Statement stmt from the SQLEngine constructor.
     * @param sqlString
     * @return ResultSet
     */
    public ResultSet sqlQuery(String sqlString) {
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sqlString);

        } catch (SQLException e) {
            throw new RuntimeException("something gone wrong");
           
        }

        return rs;
    }


    public <T> boolean sqlInsert(T object){
       
        return true;
        
    }
}


