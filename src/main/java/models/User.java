/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Merijn
 */
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 *
 * @author Merijn
 * Aanmaken Users. Bij registratie (via Model) maakt Spring gebruik van de no-parameter constructor en gebruikt vervolgens na invullen data formulier de getters en setters om data toe te voegen.
 */
@Entity
@Table(name="USERS")
public class User implements Serializable {
    
    @Id
    private long id;
    @NotNull
    @Size(min=3, max=40, message="Gebruikersnaam moet tussen de {min} en {max} tekens zijn ")
    private String username;
    @NotEmpty
    private String password;
    private String ROLE_USER;
    private boolean enabled;
    
    public User() {
    	this.ROLE_USER= "visitor";
        this.enabled = true;
    	
    }
    
                
    public String getUsername(){
        return this.username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
    	this.password = password;
    }
    
    public void encryptPassword(){
    	StandardPasswordEncoder encoder = new StandardPasswordEncoder();
    	System.out.println(encoder.encode(password));
    	this.password = encoder.encode(this.password);
    }
    
    public String getRoleUser(){
        return this.ROLE_USER;
    }
    
    public void setRoleUser(String role){
        this.ROLE_USER = role;
    }
    
    public boolean getEnabled(){
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}
