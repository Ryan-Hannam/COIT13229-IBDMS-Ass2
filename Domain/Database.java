package Domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Database {
    private static Connection connection;
    public Database() {
        connection = DatabaseConnection.getConnection();
    }
    
    public static LinkedList<FireDetails> readFire(){
        LinkedList<FireDetails> fireDetails = new LinkedList<>();
        try {
            String query = "SELECT * FROM fire";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("id");
                int isActive = result.getInt("isActive");
                int intensity = result.getInt("intensity");
                double burningAreaRadius = result.getDouble("burningAreaRadius");
                int xpos = result.getInt("xpos");
                int ypos = result.getInt("ypos");
                FireDetails fd = new FireDetails(id, isActive, intensity, burningAreaRadius, xpos, ypos);
                fireDetails.add(fd);
            }
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return fireDetails;
    }

    public static LinkedList<DroneDetails> readDrone(){
        LinkedList<DroneDetails> droneDetails = new LinkedList<>();
        try {
            String query = "SELECT * FROM drone";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int xpos = result.getInt("xpos");
                int ypos = result.getInt("ypos");
                DroneDetails d = new DroneDetails(id, name, xpos, ypos, true);
                droneDetails.add(d);
            }
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return droneDetails;
    }

    public static LinkedList<FiretruckDetails> readFiretruck(){
        LinkedList<FiretruckDetails> firetruckDetails = new LinkedList<>();
        try {
            String query = "SELECT * FROM firetrucks";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int designatedFireID = result.getInt("designatedFireID");
                FiretruckDetails ftd = new FiretruckDetails(id, name, designatedFireID);
                firetruckDetails.add(ftd);
            }
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return firetruckDetails;
    }

    public boolean saveFireDetails(FireDetails fireDetails) {
        try {
            String query = "INSERT INTO fire (id, isActive, intensity," +
                    " burningAreaRadius, xpos, ypos) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,fireDetails.getId());
            statement.setInt(2, fireDetails.getIsActive());
            statement.setInt(3,fireDetails.getIntensity());
            statement.setDouble(4, fireDetails.getBurningAreaRadius());
            statement.setInt(5, fireDetails.getXpos());
            statement.setInt(6, fireDetails.getYpos());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean saveDroneDetails(DroneDetails droneDetails) {
        try {
            String query = "INSERT INTO drone (id, name, xpos," +
                    " ypos) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,droneDetails.getId());
            statement.setString(2, droneDetails.getName());
            statement.setInt(3, droneDetails.getXpos());
            statement.setInt(4, droneDetails.getYpos());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean saveFiretruckDetails(FiretruckDetails firetruckDetails) {
        try {
            String query = "INSERT INTO firetruck (id, name, designatedFireId) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,firetruckDetails.getId());
            statement.setString(2, firetruckDetails.getName());
            statement.setInt(3,firetruckDetails.getDesignatedFireID());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean deleteFire(int fireID){
        try {
            String query = "DELETE FROM fire WHERE id ='" +fireID+"'";
            PreparedStatement statement = connection.prepareStatement(query);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //change this to return drone ID?? Could leave if just checking if new drone exists.
    public boolean searchDroneID(DroneDetails drone){
        try{
            String query = "SELECT * FROM drone WHERE id ='" +drone.getId()+"'";
            PreparedStatement statement = connection.prepareStatement(query);
            int rowsFound = statement.executeUpdate();
            if(rowsFound == 1){
                return true;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
