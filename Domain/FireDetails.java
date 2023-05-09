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
public class FireDetails  implements Serializable {
    
    // Set serialVersion so classes are the same
    private static final long serialVersionUID = 6529685098267757690L;
    
    // Values in this object
    private int id;
    private int isActive;
    private int intensity;
    private double burningAreaRadius;
    private int xpos;
    private int ypos;
    
    // Constructor
    
    public FireDetails (int id, int isActive, int intensity, double burningAreaRadius, int xpos, int ypos) {
        this.id = id;
        this.isActive = isActive;
        this.intensity = intensity;
        this.burningAreaRadius = burningAreaRadius;
        this.xpos = xpos;
        this.ypos = ypos;
       
    }
    
    // Accessors / Getters
    
    public int getId() {
        return id;
    }
    
    public int getXpos() {
        return xpos;
    }
    
    public int getYpos() {
        return ypos;
    }
    
    public int getIsActive() {
        return isActive;
    }
    
    public int getIntensity() {
        return intensity;
    }
    
    public double getBurningAreaRadius() {
        return burningAreaRadius;
    }

    // Mutators / Setters
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setX_pos(int xpos) {
        this.xpos = xpos;
    }
    
    public void setY_pos(int ypos) {
        this.ypos = ypos;
    }
    
    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
    
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public void setBurningAreaRadius(double burningAreaRadius) {
        this.burningAreaRadius = burningAreaRadius;
    }
    
    // toString() Method
    @Override
    public String toString() {
        return 
               "Fire ID: " + id + "\n" +
               "Is Active?: " + isActive + "\n" +
               "Intensity: " + intensity + "\n" +
               "Burning Area Radius: " + burningAreaRadius + "\n" +
               "x Pos: " + xpos + "\n" +
               "y Pos: " + ypos + "\n";
    }
}
