package Server;

//imports
import Domain.Drone;
import Domain.Fire;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Timer;
//class start
public class Server {

    //global variables
    private static DataStorage dataStorage;
    private static GUI guiFrame;
    private static LinkedList<Drone> droneList;
    private static LinkedList<Fire> fireList;
    private static ArrayList<Connection> connectedClients;
    private static final String droneFileName = "drone.ser";
    private static final String fireFileName = "fires.csv";
    private static final int serverPort = 8888;
    private static final int timerInterval = 10*1000;

    //null constructor
    Server() { 

    }

    //main
    public static void main (String args[]) {
        //variables
        droneList = new LinkedList<Drone>();
        fireList = new LinkedList<Fire>();
        connectedClients = new ArrayList<Connection>();
        dataStorage = new DataStorage();

        //need to populate arrays from text files
        fireList = dataStorage.readFiresFromFile(fireFileName);
        droneList = dataStorage.readDroneFromFile(droneFileName);

        //setup GUI
        guiFrame = new GUI();
        guiFrame.setVisible(true);

        //schedule time to redraw map every 10 seconds
        Timer mapUpdateTimer = new Timer();
        mapUpdateTimer.schedule(new TimerTask() {
            @Override
            public void run() {    
                guiFrame.redrawMap();
            }      
        }, timerInterval, timerInterval);
        
        //show file loading messagesß
        guiFrame.addMessageToConsole("Loaded fires from file - " + fireList.size() + " total");
        guiFrame.addMessageToConsole("Loaded drones from file - " + droneList.size() + " total");

        //start listening for incoming connection
        try { 
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true){
                //accept the connection
                Socket clientSocket = listenSocket.accept();
                System.out.println("socket connected");
                //create connection instance to handle client communication
                Connection connection = new Connection(clientSocket, droneList);             
                //store reference to connection for later
                connectedClients.add(connection);
            }
        }
        catch(IOException e){
            System.out.println("Listen socket: "+e.getMessage());
        }
    }

    //shutdown the server & recall all drones
    public static void shutdown(){
        //save fires to file
        dataStorage.writeFiresToFile(fireFileName, fireList);
        //save drones to file
        dataStorage.writeDroneToFile(droneFileName, droneList);
        guiFrame.addMessageToConsole("Drones recalled to base");
        //iterate over each connection and run RTB and shutdown
        for (Connection connection : connectedClients) {
            connection.recallDroneBackToBase();
            connection.shutdown();
        }
        guiFrame.addMessageToConsole("All drones back at base");
        guiFrame.addMessageToConsole("Shutting down ...");
        System.exit(0);
    }

    //admin controls

    //delete fire
    public static void deleteFire(){
        //get fireID from input
        Integer delFireID = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the FireID you wish to delete."));
        //find fire by ID
        Fire toBeDeletedFire = fireList.stream()
            .filter(fire -> delFireID.equals(Integer.valueOf(fire.getfireID())))
            .findAny()
            .orElse(null);
        if (toBeDeletedFire == null) {
            //let user know that fire doesn't exist with ID
            System.out.println("No fire exists with ID: " + delFireID);
            guiFrame.addMessageToConsole("No fire exists with ID: " + delFireID);
        } else {
            //delete the fire that matches
            fireList.remove(toBeDeletedFire);
            //let user know that fire was deleted
            System.out.println("Fire deleted for ID: " + delFireID);
            guiFrame.addMessageToConsole("Fire deleted for ID: " + delFireID);
        }
        //get map to redraw
        guiFrame.redrawMap();
    }

    //recall all
    public static void recallAll(){
        //print line to console
        guiFrame.addMessageToConsole("Drones recalled to base");
        //iterate over each connection and run RTB
        for (Connection connection : connectedClients) {
            connection.recallDroneBackToBase();
        }
        guiFrame.addMessageToConsole("All drones back at base");
    }

    //move drone - not implemented
    public static void moveDrone(){
        int droneID = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the Drone ID for the drone you want to move."));
        int droneXPos = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the new x Coord."));
        int droneYPos = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the new y Coord."));
        if((droneXPos <= 400 && droneXPos >= 0) && (droneYPos <= 400 && droneYPos >= 0)){
            //Drone.setDroneXPos(droneXPos); 
            //Drone.setDroneYPos(droneYPos);
            JOptionPane.showMessageDialog(null, "Drone " + droneID + "is moved to (x,y): " + droneXPos + ", " + droneYPos);
        }
        else{
            JOptionPane.showMessageDialog(null, "Please enter an x and y value between 0 and 400. Drone returning to previous location.");
        }
        //get map to redraw
        guiFrame.redrawMap();
    }
    //end admin controls

    //prints messages to console
    public static void addMessageToConsoleInGUI(String message) {
        guiFrame.addMessageToConsole(message);
    }

    //gets fires
    public static LinkedList<Fire> getFireList() {
        return Server.fireList;
    }

    //gets drones
    public static LinkedList<Drone> getDroneList() {
        return Server.droneList;
    }
}
