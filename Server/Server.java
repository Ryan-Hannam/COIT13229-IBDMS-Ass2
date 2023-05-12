/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.*;

import Domain.Database;
import Domain.DroneDetails;
import Domain.FireDetails;
import Domain.FiretruckDetails;


/**
 *
 * @author diamo
 */
public class Server extends JFrame implements ActionListener, Runnable {
    
    // If recall has been called
    static boolean recallStatus = false;
    
    // ArrayLists for Drone and Fire Objects
    static ArrayList<Domain.DroneDetails> drones = new ArrayList<>();
    static ArrayList<Domain.FireDetails> fires = new ArrayList<>();
    static ArrayList<Domain.FiretruckDetails> trucks = new ArrayList<>();
    
    // GUI Setup, all elements of GUI declared
    private JLabel titleText = new JLabel("Drone Server");
    private static JTextArea outputText = new JTextArea(25, 25);
    private JLabel headingText = new JLabel("Server Output Log");
    private JLabel mapText = new JLabel("Drone and Fire Map");
    private JLabel buttonText = new JLabel("Admin Controls");
    private JButton deleteButton = new JButton("Delete Fire");
    private JButton recallButton = new JButton("Recall Drones");
    private JButton moveButton = new JButton("Move Drone");
    private JButton shutDownButton = new JButton("Shut Down");
    private JScrollPane scrollPane; // Scroll pane for the text area
    private MapPanel mapPanel;
    
    // Hash Maps to store positions of drones that need to be moved
    static HashMap<Integer, Integer> newXPositions = new HashMap<>();
    static HashMap<Integer, Integer> newYPositions = new HashMap<>();
    
    public class MapPanel extends JPanel {

        private ArrayList<Domain.DroneDetails> drones;
        private ArrayList<Domain.FireDetails> fires;
        private ArrayList<Domain.FiretruckDetails> trucks;

        public MapPanel(ArrayList<Domain.DroneDetails> drones, ArrayList<Domain.FireDetails> fires, ArrayList<Domain.FiretruckDetails> trucks) {
            this.drones = drones;
            this.fires = fires;
			this.trucks = trucks;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Set background color of map panel
            setBackground(Color.WHITE);
            
            // Draw drones as blue circles with drone id
            for (Domain.DroneDetails p : drones) {
                if (p.getActive()) {
                    // Converts coordinates for use on 400 by 400 grid
                    int x = (100 - p.getXpos()) * 2;
                    int y = (100 - p.getYpos()) * 2;
                    int size = 10;
                    g.setColor(Color.BLUE);
                    g.fillOval(x - size/2, y - size/2, size, size);
                    g.setColor(Color.BLACK);
                    g.drawString("Drone " + p.getId(), x - 30, y - 5);
                }
            }
            
            // Draw fires as red circles with fire id and severity
            for (Domain.FireDetails p : fires) {
                // Converts coordinates for use on 400 by 400 grid
                int x = (100 - p.getXpos()) * 2;
                int y = (100 - p.getYpos()) * 2;
                int severity = p.getIntensity();
                int size = 10;
                g.setColor(Color.RED);
                g.fillOval(x - size/2, y - size/2, size, size);
                g.setColor(Color.BLACK);
                g.drawString("Fire " + p.getId() + " (" + severity + ")", x - 30, y - 5);
            }
			
            // Draw firetrucks as blue rectangles with truck id
            for (Domain.FiretruckDetails p : trucks) {   
                //get the designated fire details
                Domain.FireDetails f = fires.get(p.getDesignatedFireID()); 
                //add 14 pixels to the x position to move the rectangle adjacent to the fire truck
                int x = (100 - f.getXpos()) * 2 + 14; 
                int y = (100 - f.getYpos()) * 2;
                int size = 20;
                g.setColor(Color.BLUE);
                g.fillRect(x - size/2, y - size/2, size, size);
                g.setColor(Color.BLACK);
                g.drawString("Truck " + p.getId(), x - 30, y - 5);          
            }  
        }
    }
    
    Server() {
        // Sets settings for java swing GUI Frame
        super("Server GUI");
        
        // Sets font for title
        titleText.setFont(new Font("Arial", Font.PLAIN, 30));
        
        // Sets X button to do nothing, shut down should be used to exit
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Other GUI settings
        setSize(750, 600);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        
        // Heading Panel
        JPanel headingPanel = new JPanel();
        headingPanel.setPreferredSize(new Dimension(750, 40));
        headingPanel.add(titleText);
        
        // Set Text Area Wrapping and read-only
        outputText.setEditable(false);
        outputText.setLineWrap(true);
        outputText.setWrapStyleWord(true);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(750, 40));
        buttonPanel.add(deleteButton);
        buttonPanel.add(recallButton);
        buttonPanel.add(moveButton);
        buttonPanel.add(shutDownButton);
        
        // Bottom panel
        JPanel bottomPanel = new JPanel();
        
        // Output Panel
        JPanel outputPanel = new JPanel();
        outputPanel.setPreferredSize(new Dimension(300, 500));
        outputPanel.add(headingText);
        outputPanel.add(outputText);
        
        // Text Area Vertical ScrollBar
        scrollPane = new JScrollPane(outputText);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outputPanel.add(scrollPane);
        
         // Map Panel
        mapPanel = new MapPanel(drones, fires, trucks);
        mapPanel.setPreferredSize(new Dimension(400, 400));
        
        // Outer Map Panel with text
        JPanel outerMapPanel = new JPanel();
        outerMapPanel.setPreferredSize(new Dimension(400, 500));
        
        // Add panels and text to GUI
        add(headingPanel);
        add(buttonText);
        add(buttonPanel);
        
        outerMapPanel.add(mapText);
        outerMapPanel.add(mapPanel);
        bottomPanel.add(outputPanel);
        bottomPanel.add(outerMapPanel);
        
        add(bottomPanel);
        
        // Makes the GUI visible
        this.setVisible(true);
        
        // Action Listeners for Buttons
        deleteButton.addActionListener(this);
        recallButton.addActionListener(this);
        moveButton.addActionListener(this);
        shutDownButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // This runs when an object action is clicked
        // Gets the name of the object clicked and finds the case
        // Runs the corresponding method and breaks the switch
        String actionString=e.getActionCommand();
        switch(actionString) {
            case "Delete Fire":
                deleteFire();
                break;
                
            case "Recall Drones":
                recallDrones();
                break;
                
            case "Move Drone":
                moveDrone();
                break;
                
            case "Shut Down":
                shutDown();
                break;
        }
    }
    
    public static void main(String[] args) {
        // Calls function to read data from files
        Domain.Database.Database();
        readData();
        
        // Starts thread to update map and GUI because that's how it works apparently
        Server obj = new Server();
        Thread thread = new Thread(obj);
        thread.start();
        
        // Sets up connection listener with port 8888
        try {
            int serverPort = 8888;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            
            // Constantly on loop, checks for connections and sends connections to new thread
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
            
        }   catch(IOException e) {System.out.println("Listen Socket : " + e.getMessage());}
    }
    
    static boolean ifRecall() {
        // Returns if the recall status is true
        return recallStatus;
    }
    
    //Begin admin functions

    static void addDrone(Domain.DroneDetails tempDrone) {
        //attempt to save new drone
        if(Database.saveDroneDetails(tempDrone)){
            outputLog("New Drone Registered. ID: " + tempDrone.getId() + " Name: " + tempDrone.getName());
        }
        outputLog("Something went wrong, try again later");
    }
    
    //convert this to use databse
    static void addFire(Domain.FireDetails tempFire) {
        
        /*
        Assigns ID to the new fire object then adds it to the ArrayList
        If the fire ArrayList is empty it will just give the Fire an ID of 0
        If it's not it'll find the highest Fire ID and set it to one above that
        Then makes a fire object and adds it to the arraylist and prints fire details
        */
        if (fires.isEmpty()) {
            Domain.FireDetails fire = new Domain.FireDetails(0, tempFire.getIsActive(), tempFire.getIntensity(), tempFire.getBurningAreaRadius(), tempFire.getXpos(), tempFire.getYpos());
            fires.add(fire);
            outputLog("New Fire Spotted at " + fire.getXpos() + ", " + fire.getYpos() + " with intensity " + fire.getIntensity() + " burning area radius " + fire.getBurningAreaRadius() + ".");
        } else {
            int max = 0;
            
            for (Domain.FireDetails p : fires) {
                if (p.getId() > max) {
                    max = p.getId();
                }
            }
            
            int fireId = max + 1;
            
            Domain.FireDetails fire = new Domain.FireDetails(fireId, tempFire.getIsActive(), tempFire.getIntensity(), tempFire.getBurningAreaRadius(), tempFire.getXpos(), tempFire.getYpos());
            fires.add(fire);
            outputLog("New Fire Spotted at " + fire.getXpos() + ", " + fire.getYpos() + " with intensity " + fire.getIntensity() + " burning area radius " + fire.getBurningAreaRadius() + ".");
        }
    }
    
    static void readData() {
        // Reads data in using Database() object's queries
        try {
            LinkedList<Domain.DroneDetails> tempDrones = Database.readDrone();
            outputLog(tempDrones.size() + " drones loaded");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            LinkedList<Domain.FireDetails> tempFires = Database.readFire();
            outputLog(tempFires.size() + " fires loaded");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            LinkedList<Domain.FiretruckDetails> tempFiretrucks = Database.readFiretruck();
            outputLog(tempFiretrucks.size() + " firetrucks loaded");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    static void saveData() {
        // Saves drones arraylist to database
        try {
            for (DroneDetails drones : drones){
                Database.saveDroneDetails(drones);
            }
            }
            catch (Exception e) {
            outputLog(e.getMessage());
        } 
        //saves fires arraylist into database
        try {
            for(FireDetails fires : fires){
                Database.saveFireDetails(fires);
            }
        }
        catch (Exception e) {
        outputLog(e.getMessage());
        }
        //saves firetruck arraylist into database
        try {
            for(FiretruckDetails trucks : trucks){
                Database.saveFiretruckDetails(trucks);
            }
        }
        catch (Exception e) {
        outputLog(e.getMessage());
        }
        outputLog("All Data saved successfully");
    }

    public void deleteFire() {
        // Triggered by Delete Fire Button
        // intId is the id that'll be entered
        int intId = -1;
        
        while (true) {
            String enteredId = JOptionPane.showInputDialog(null, "Enter a Fire ID");
            if (enteredId == null) {
                return;
            }
            try {
                intId = Integer.parseInt(enteredId);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID must be numerical.");
            }
        }
        
        if (!Database.deleteFire(intId)) {
            outputLog("Fire " + intId + " not found.");
        }
        else{
            outputLog("Fire " + intId + " deleted");
        }
    }
    
    public void recallDrones() {
        // Checks if a recall is initiated
        if (recallStatus) {
            recallStatus = false;
            outputLog("Recall uninitiated.");
        } else {
            recallStatus = true;
            outputLog("Recall initiated.");
        }
    }
    
    public void moveDrone() {
        // Triggered by move drone button
        // Initialisation of variables, -0 does not exist as a coordinate
        int intId = -1;
        int newX = -0;
        int newY = -0;
        boolean droneExists = false;
        
        /*
        Opens Option Pane prompting for a Drone ID
        If cancel is pressed, null will be returned causing the loop to break
        otherwise it'll attempt to parse the ID to int, if this fails the user will be reprompted after an error message
        */
        while (true) {
            String enteredId = JOptionPane.showInputDialog(null, "Enter ID of drone to be moved.");
            if (enteredId == null) {
                return;
            }
            try {
                intId = Integer.parseInt(enteredId);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID must be numerical.");
            }
        }
        
        // Searches for ArrayList to check if a drone with the ID entered exists
        // If drone is active then it is good to be moved and droneExists is changed to true
        for (Domain.DroneDetails p : drones) {
            if (p.getId() == intId) {
                if (p.getActive()) {
                    droneExists = true;
                }
            }
        }
        
        // If no drone exists that is active then droneExists will be false and the user will be given an error message
        if (!droneExists) {
            JOptionPane.showMessageDialog(null, "Drone with ID " + intId + " does not exist or is not active.");
            return;
        }
        
        // Opens option pane prompting user to enter an X position for the drone to be moved to
        while (true) {
            String enteredX = JOptionPane.showInputDialog(null, "Enter new X position for drone " + intId + ".");
            if (enteredX == null) {
                return;
            }
            try {
                newX = Integer.parseInt(enteredX);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID must be numerical.");
            }
        }
        
        // Opens option pane prompting user to enter an X position for the drone to be moved to
        while (true) {
            String enteredY = JOptionPane.showInputDialog(null, "Enter new Y position for drone " + intId + ".");
            if (enteredY == null) {
                return;
            }
            try {
                newY = Integer.parseInt(enteredY);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID must be numerical.");
            }
        }
        
        // Removes drone id from hash map in case it's there
        newXPositions.remove(intId);
        newYPositions.remove(intId);
        
        // When all information has been inputted, ids and new positions are added to hash maps
        newXPositions.put(intId, newX);
        newYPositions.put(intId, newY);
        
        // Outputs message to confirm move
        outputLog("Drone " + intId + " will be moved to " + newX + ", " + newY + ".");
    }
    
    public void shutDown() {
        /*
        Sets recall status to true
        drones active is set to false before each loop
        Checks each object of the ArrayList to see if a drone is still active
        If one is, dronesActive is set to true
        
        If dronesActive is false that means there's no drones active
        The program saves that data (saveData()) and exits
        
        If there is a drone still active it will loop until no drones are active
        */
        recallStatus = true;
        boolean dronesActive;
        
        outputLog("Recall Intiated.");
        
        while (true) {
            dronesActive = false;
            for (Domain.DroneDetails p : drones) {
                if (p.getActive()) {
                    dronesActive = true;
                }
            }
            
            if (!dronesActive) {
                outputLog("Shut Down Commencing.");
               // saveData();
                System.exit(0);
            }
        }
    }
    
    //end admin functions

    public static void outputLog(String message) {
        // Outputs message given through the output text area along with a newline
        outputText.append(message + "\n");
        // Moves scrollbar straight to bottom to make textarea act as a log
        outputText.setCaretPosition(outputText.getDocument().getLength());
    }

    @Override
    public void run() {
        // Runs constantly
        while (true) {
            
            // Repaints mapPanel
            mapPanel.repaint();
        }
    }
}

class Connection extends Thread {
    // Sets up input and output streams for socket
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;
    
    public Connection (Socket aClientSocket) {
        
        // Assigns streams to the socket and starts the thread run()
        try {
            clientSocket = aClientSocket;
            in = new ObjectInputStream( clientSocket.getInputStream());
            out =new ObjectOutputStream( clientSocket.getOutputStream());
            this.start();
	} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }
    
    @Override
    public void run() {
        try {
            
            String message = "";
            String clientMessage = "";
            
            // Movement variables if drone is outside of boundaries
            // New positions will be set if required
            boolean outOfBounds = false;
            boolean movementRequired = false;
            
            // Gets drone object from client and adds it to tempDrone object
            Domain.DroneDetails tempDrone = (Domain.DroneDetails)in.readObject();
            
            // Confirm drone object
            message = "confirmed";
            out.writeObject(message);
            
            // Receives how many fires there are and confirms receival
            Integer numFires = (Integer)in.readObject();
            out.writeObject(message);
            
            // Loops for how many fires there are and receives the fire objects
            // Sends fire object to addFire(); for it to be added, sends confirmation message
            if (numFires > 0) {
                for (int i = 0; i < numFires; i++) {
                    Domain.FireDetails tempFire = (Domain.FireDetails)in.readObject();
                    //Server.addFire(tempFire);
                    message = "confirmed";
                    out.writeObject(message);
                }
            }
            
            // Checks if drone is in hashmaps for movements
            // If so sets movementRequired to true, updates drone X and Y positions
            for (Integer i : Server.newXPositions.keySet()) {
                if (i == tempDrone.getId()) {
                    movementRequired = true;
                   // tempDrone.setX_pos(Server.newXPositions.get(i));
                    Server.newXPositions.remove(i);
                }
            }
            
            for (Integer i : Server.newYPositions.keySet()) {
                if (i == tempDrone.getId()) {
                    movementRequired = true;
                   // tempDrone.setY_pos(Server.newYPositions.get(i));
                    Server.newYPositions.remove(i);
                }
            }
            
            // Check x positions, set if out of bounds
            if (tempDrone.getXpos() > 100) {
                outOfBounds = true;
                //tempDrone.setX_pos(80);
            } else if (tempDrone.getXpos() < -100) {
                outOfBounds = true;
               // tempDrone.setX_pos(-80);
            }
            
            // Check y positions, set if out of bounds
            if (tempDrone.getYpos() > 100) {
                outOfBounds = true;
               // tempDrone.setY_pos(80);
            } else if (tempDrone.getYpos() < -100) {
                outOfBounds = true;
              //  tempDrone.setY_pos(-80);
            }
            
            // If a Recall is active it will respond to the client saying so
            // Recall is done first since it matters the most, position doesn't matter if it's being recalled to 0,0 regardless
            if (Server.ifRecall()) {
                message = "recall";
                out.writeObject(message);
                clientMessage = (String)in.readObject();
                if (clientMessage.equals("Recall Confirmed")) {
                    // If drone confirms recall, set the drone active to false
                    tempDrone.setActive(false);
                   // tempDrone.setX_pos(0);
                   // tempDrone.setY_pos(0);
                }
            } else if (movementRequired || outOfBounds) {
                // Sends move message and receives confirmation between object writes
                message = "move";
                out.writeObject(message);
                clientMessage = (String)in.readObject();
                out.writeObject(tempDrone.getXpos());
                clientMessage = (String)in.readObject();
                out.writeObject(tempDrone.getYpos());
                clientMessage = (String)in.readObject();
                
                // Messages outputed based on if the drone was moved or out of bounds
                // Not an if else because both messages could be required
                if (movementRequired) {
                    Server.outputLog("Drone " + tempDrone.getId() + " successfully moved.");
                }
                
                if (outOfBounds) {
                    Server.outputLog("Drone " + tempDrone.getId() + " outside of boundaries. Moved back.");
                }
            } else {
                // Otherwise just confirms to the client it received the object
                message = "confirmed";
                out.writeObject(message);
            }
            
            // Sends tempDrone to the addDrone function to get it in the ArrayList
            // Server.addDrone(tempDrone);
            
            System.out.println(tempDrone);
            
            System.out.println("There are " + numFires + " new fires.");
            System.out.println("There are " + Server.fires.size() + " fires.");
            
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
	} catch(ClassNotFoundException ex){ ex.printStackTrace();
	} finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
}
