package Domain;

//imports
import java.io.*;
//class start
public class Drone implements Serializable{
    //global variables
    private int droneID;
    private String droneName;
    private double droneXPos;
    private double droneYPos;
    private static long serialVersionUID = 123789L;
    //null constructor
    public Drone(){

    }
    //object constructor
    public Drone(int droneID, String droneName, double droneXPos, double droneYPos){
        this.droneID = droneID;
        this.droneName = droneName;
        this.droneXPos = droneXPos;
        this.droneYPos = droneYPos;
    }
    //object constructor
    public Drone(Drone another){
        this(another.getDroneID(),another.getDroneName(),another.getDroneXPos(),another.getDroneYPos());
    }
    //getters and setters
    public int getDroneID(){
        return droneID;
    }

    public void setDroneID(int droneID){
        this.droneID = droneID;
    }

    public String getDroneName(){
        return droneName;
    }

    public void setDroneName(String droneName){
        this.droneName = droneName;
    }

    public double getDroneXPos(){
        return droneXPos;
    }

    public void setDroneXPos(double droneXPos){
        this.droneXPos = droneXPos;
    }

    public double getDroneYPos(){
        return droneYPos;
    }

    public void setDroneYPos(double droneYPos){
        this.droneYPos = droneYPos;
    }
    //end getters and setters

    //to string
    @Override
    public String toString(){
        return String.format("Drone ID: %d Drone Name: %s\n x Position: %.2f y Position %.2f\n", this.droneID, this.droneName,this.droneXPos,this.droneYPos);
    }
}