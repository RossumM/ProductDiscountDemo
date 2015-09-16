/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Merijn
 */
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Merijn
 * Dit model object wordt gebruikt voor Spring validatie, vandaar het gebruik van annotation constraints.
 */
@MappedSuperclass
public class GenericProduct  {
    @Id
    private long id;
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    @Size(max = 50)
    private String category;
    @NotNull
    @Size(min = 1, max = 4000)
    private String description;
    @NotNull
    private double originalPrice;
    @NotNull
    private int discount;
    private List<String> features;

    //Er moet een manier komen om te checken of een gegenereerde id echt uniek is
    // Als tussenoplossing wordt een getal gebruikt dat zo groot is dat de kans op een duplicate entry enorm klein is.
    public GenericProduct() { 
        id = (long) (1000000000*Math.random());
    }

    public GenericProduct(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCategory() {
        if(category == null){
            return "null"; //we willen 'null' als string omdat de template dit kan gebruiken voor logica van de weergave.
        }else{
        return category;
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }
    
    

    
    @Override
    public String toString() {
        return "models.Products[ id=" + id + " ]";
    }
    
}
