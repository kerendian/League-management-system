package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final DBConnector instance = new DBConnector();

    public static DBConnector getInstance() {
        return instance;
    }

    private DBConnector() {
    }

    public  Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // db parameters
            String url = "jdbc:sqlite:src/main/java/DataAccess/db.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url,"root","root");
//            System.out.println("Connection to SQLite has been established.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;

    }
    public  void disconnect(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    }

