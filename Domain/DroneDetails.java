package Domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 *
 * @author diamo
 */
public class DroneDetails  implements Serializable {
    
    // Set serialVersion so classes are the same
    static final long serialVersionUID = -687991492884005033L;
    
    // Values in this object
    private int id;
    private String name;
    private int xpos;
    private int ypos;
    private boolean active;
    
    // Constructor
    
    public DroneDetails (int id, String name, int xpos, int ypos, boolean active) {
        this.id = id;
        this.name = name;
        this.xpos = xpos;
        this.ypos = ypos;
        this.active = active;
    }
    
    // Accessors / Getters
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getXpos() {
        return xpos;
    }
    
    public int getYpos() {
        return ypos;
    }
    
    public boolean getActive() {
        return active;
    }
    
    // Mutators / Setters
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setXpos(int xpos) {
        this.xpos = xpos;
    }
    
    public void setYpos(int ypos) {
        this.ypos = ypos;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    // toString() Method
    @Override
    public String toString() {
        return 
               "ID: " + id + "\n" +
               "Name: " + name + "\n" +
               "X Position: " + xpos + "\n" +
               "Y Position: " + ypos + "\n";
    }
}
