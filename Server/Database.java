package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Database {
    private Connection connection;
    public Database() {
        connection = DatabaseConnection.getConnection();
    }

    //TODO: Fill out with each necessary query for Save & Read

}
