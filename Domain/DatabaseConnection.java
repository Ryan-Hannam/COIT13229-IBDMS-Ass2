//package definition
package Domain;

//component importers
import java.sql.Connection;
import java.sql.DriverManager;

//begin DatabaseConnection class

public class DatabaseConnection {
    //instantiates connection
    private static Connection connection = null;
    
    //null constructor
    private DatabaseConnection() {
    }

    //attempts to get a connection to the database using preset username and password
    public static Connection getConnection() {
        if(connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection= DriverManager.getConnection("jdbc:mysql://localhost/ibdms_server", "root", "root");
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return connection;
    }
}

//end DatabaseConnection class
