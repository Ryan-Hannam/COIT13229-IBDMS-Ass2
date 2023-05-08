package Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection connection = null;
    private DatabaseConnection() {

    }
    public static Connection getConnection() {
        if(connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection= DriverManager.getConnection("jdbc:mysql://localhost/ibdms", "root", "root");
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return connection;
    }
}
