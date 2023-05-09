package Domain;

import java.io.Serializable;

public class FiretruckDetails implements Serializable {
    
    // Set serialVersion so classes are the same
    private static final long serialVersionUID = 6786453198267757690L;
    
    // Values in this object
    private int id;
    private String name;
    private int x_pos;
    private int y_pos;
    private int designatedFireID;
    
    // Constructor
    public FiretruckDetails (int id, String name, int x_pos, int y_pos, int designatedFireID) {
        this.id = id;
        this.name = name;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.designatedFireID = designatedFireID;
    }
    
    // Accessors / Getters
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX_pos() {
        return x_pos;
    }
    
    public int getY_pos() {
        return y_pos;
    }    
    
    public int getDesignatedFireID() {
        return designatedFireID;
    }
    
    // Mutators / Setters
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDesignatedFireID(int designatedFireID) {
        this.designatedFireID = designatedFireID;
    }
    
    
    // toString() Method
    @Override
    public String toString() {
        return 
               "Firetruck ID: " + id + "\n" +
               "Firetruck Name: " + name + "\n" +
               "X Position: " + x_pos + "\n" +
               "Y Position: " + y_pos + "\n" +
               "Designated Fire ID: " + designatedFireID + "\n";
    }
}