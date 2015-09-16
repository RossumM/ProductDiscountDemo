/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Merijn
 */
@Entity
@Table(name="CAR")
public class Car extends Vehicle implements Serializable {
    
     private Engine engine;
     
     
    
}
