package org.boshed;
import org.boshed.Fakes.Fields;

import java.sql.*;

public class SQLEngine {
    static private final String DB_URL = "jdbc:postgresql://localhost:32770/postgres";
    static private final String USER = "postgres";
    static private final String PASS = "Tunnel27";
    Statement stmt;
    PreparedStatement p_stmt;
    Connection conn;

    public SQLEngine() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("something gone wrong: "+e);
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
    public <T extends Fakes & Fields> int sqlInsert(T object){
        PreparedStatement stmt = object.SQLInsertPreparedStatement(conn);
        int records_affected;
        try {
            records_affected = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("somethings gone wrong" +e);
        }
        return records_affected;
        
    }

    /** 
     * This function cannot use prepared statements due to the SQL injection vulnerability.
     * @param object an object of T extends Fakes and Implements Fields
     */ 
    public <T extends Fakes & Fields> void prepTable(T object) {
    System.out.println("Building Fake OBJECT TABLE\n");
    String SQLString = String.format("CREATE TABLE %s (id SERIAL PRIMARY KEY NOT NULL, ", object.getTableName());
    Object[] fields = object.listFields();
    for (int x=0; x< fields.length; x++) {
        if (fields[x].toString() == "dob") {SQLString += String.format("%s DATE, ", fields[x].toString());}
        else {
        SQLString += String.format("%s VARCHAR, ", object.listFields()[x]);
        }
    }
    SQLString = SQLString.strip();
    SQLString = SQLString.substring(0, SQLString.length()-1)+");";
    try {
        Statement drop_stmt = conn.createStatement();
        drop_stmt.execute(String.format("DROP TABLE IF EXISTS %s CASCADE", object.getTableName()));
        Statement stmt = conn.createStatement();
        // System.out.println(SQLString);
        stmt.execute(SQLString);
       
    } catch (SQLException e) {
        throw new RuntimeException("something gone wrong" +e );
        }

    }

}

   




