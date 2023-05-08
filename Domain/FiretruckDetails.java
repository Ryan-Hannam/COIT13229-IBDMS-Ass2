package Domain;

import java.io.Serializable;

public class FiretruckDetails implements Serializable {
    
    // Set serialVersion so classes are the same
    private static final long serialVersionUID = 6786453198267757690L;
    
    // Values in this object
    private int id;
    private String name;
    private int designatedFireID;
    
    // Constructor
    public FiretruckDetails (int id, String name, int designatedFireID) {
        this.id = id;
        this.name = name;
        this.designatedFireID = designatedFireID;
    }
    
    // Accessors / Getters
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
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
               "Designated Fire ID: " + designatedFireID + "\n";
    }
}
