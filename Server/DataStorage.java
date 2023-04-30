package Server;
//imports
import Domain.Drone;
import Domain.Fire;
import java.io.*;
import java.util.*;
//class start
public class DataStorage {
    //defining variables
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    //default null constructor
    public DataStorage() {

    }
    //writes drone data to file when a new drone is registered
    public int writeDroneToFile(String fileName, LinkedList<Drone> droneList) {
        try {
            //create file
            File droneFile = new File(fileName);
            //see if file already exists
            boolean isFileExisting = droneFile.exists();
            int droneCounter = 0;
            this.fileOutputStream = new FileOutputStream(fileName, true);
            //iterate through file and read each object - if file exists or create new file
            if (!isFileExisting)
                this.objectOutputStream = new ObjectOutputStream(fileOutputStream);
            else
                this.objectOutputStream = new AppendingObjectOutputStream (fileOutputStream);
            for (Drone drone:droneList)  {
                objectOutputStream.writeObject(drone);
                droneCounter++;
            }
            objectOutputStream.flush();
            objectOutputStream.close();
            return droneCounter;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }  
        //try to close file stream
        finally {
            try {
                fileOutputStream.close();
            } 
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return -1;
    }
    //reads drone data from file
    public LinkedList<Drone>readDroneFromFile(String fileName) {
        //list stores read objects
        LinkedList<Drone> droneList = new LinkedList<>();
        try {
            File droneFile = new File(fileName);
            //check if the file exists
            if(!droneFile.exists()){
                return null;
            }
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            //read the file and add each line to the list
            while (fileInputStream.available() > 0) {
                droneList.add((Drone)objectInputStream.readObject());
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        //try to close stream
        finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } 
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return droneList;
    }
    //writes fires to a file
    public boolean writeFiresToFile(String fileName, LinkedList<Fire> fireList){
        try {
            //creates new file
            File fireFile = new File(fileName);
            this.fileOutputStream = new FileOutputStream(fileName, false);     
            for (Fire fire : fireList) {
                //formats data as .csv
                fileOutputStream.write((fire.getfireID() + "," + fire.getfireXPos() + "," + fire.getfireYPos() + "," + fire.getFireDroneID() + "," + fire.getFireSeverity() + "\n").getBytes());
            }
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        //try to close file stream 
        finally {
            try {
                fileOutputStream.close();
            } 
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
    //iterate over fire file and read data in
    public LinkedList<Fire> readFiresFromFile(String fileName) {
        //linkedlist to store data
        LinkedList<Fire> fireList = new LinkedList<>();
        try {
            File fireFile = new File(fileName);
             //when the file doesn't exist, return nothing
            if(!fireFile.exists()){
                return null;
            }
            //read over the .csv line by line
            FileReader fileReader = new FileReader(fireFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            //if data exists, read line by line
            while((line=bufferedReader.readLine()) !=null) {
            //split .csv by comma
            String data[] = line.split(",");
            int fireID = Integer.parseInt(data[0]);
            Double fireXPos = Double.parseDouble(data[1]);
            Double fireYPos = Double.parseDouble(data[2]);
            int fireDroneID = Integer.parseInt(data[3]);
            int fireSeverity = Integer.parseInt(data[4]);
            //create a new fire object for each line
            Fire fire = new Fire(fireID, fireXPos, fireYPos,fireDroneID,fireSeverity);
            //add new fire object to the list
            fireList.add(fire);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }       
        return fireList;
    }
    //class to append instead of overwrite files
    class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
            this.writeStreamHeader();
        }
        @Override
        protected void writeStreamHeader() throws IOException {
            return;
        }
    }
}
