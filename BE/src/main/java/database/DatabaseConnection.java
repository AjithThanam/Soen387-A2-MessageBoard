package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_NAME = "soen_387";

    static final String DB_USER = "root";
    static final String DB_PASSWORD = "";

    static Connection conn = null;

    public static Connection getConnection() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL+DB_NAME,DB_USER,DB_PASSWORD);
            return conn;
        } catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException("Error connecting to database",e);
        }
    }

    public static void closeConnection() throws SQLException{
        if(conn!=null) conn.close();
    }
}
