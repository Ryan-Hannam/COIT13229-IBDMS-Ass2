package Server;

//imports
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import Domain.Drone;
//class start
class Connection extends Thread {
    //global variables
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket clientSocket;
    //private java.rmi.registry.Registry registry; - never implemented
    private LinkedList<Drone> droneLinkedList;
    
    //constructor
    public Connection (Socket aClientSocket, LinkedList<Drone> drones){
        droneLinkedList = drones;
        clientSocket = aClientSocket;
        //listen for connections and create new thread for each
        try{
            
            in = new ObjectInputStream(aClientSocket.getInputStream());
            out = new ObjectOutputStream(aClientSocket.getOutputStream());
            
            System.out.println("thread started "+in.getClass());

            this.start();
        }

        catch(IOException e){
            e.printStackTrace();
            System.out.println("Connection:"+e.getMessage());
        }
    }

    //run
    @Override
    public void run(){
        System.out.println("thread running");
        //attempt to read in drone object from client
        try {
            while(true) {
                Object object = in.readObject();

                if (object instanceof Drone) {
                    //typecase object to drone
                    Drone droneFromClient = (Drone) object;
                    Server.addMessageToConsoleInGUI("Drone connected: " + droneFromClient.toString() );
                    //check if drone array already contains a drone with the same id
                    if (droneLinkedList.contains(droneFromClient)) {
                    } else {
                        droneLinkedList.add(droneFromClient);
                        System.out.println("Drone added");
                        Server.addMessageToConsoleInGUI(droneFromClient.toString());

                    }
                }
            }
        } 
        catch ( EOFException e) {
            System.out.println("EOF:"+e.getMessage());
            e.printStackTrace();
        }
        catch( IOException e) {
            System.out.println("readline:"+e.getMessage());
            e.printStackTrace();
        }
        catch( ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        finally
        { try {
                clientSocket.close();
            }
            catch ( IOException e)
            {/*close failed*/}
        }
    }

    //send recall command to clients
    public void recallDroneBackToBase() {
        try {
            //send return to base message to client
            out.writeObject("DroneReturnToBase");
        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        } 
    }

    //send shutdown command to clients
    public void shutdown(){
        recallDroneBackToBase();
        try{
            out.writeObject("Shutdown");
        } 
        catch (IOException e){
            System.err.println("IOException: " + e.getMessage());
        }
    }
}
