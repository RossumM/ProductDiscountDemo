/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.MappedSuperclass;

/**
 *
 * @author Merijn
 */
@MappedSuperclass
public abstract class Vehicle extends GenericProduct {
    
    private int numberOfWheels;
    

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }
    
    
}
