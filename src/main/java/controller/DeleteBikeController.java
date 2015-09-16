/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import model.GenericProduct;

/**
 *
 * @author Merijn
 */
@Controller
@SessionAttributes("products")//een session voor de lijst met product objecten, anders moet je voor iedere request opnieuw een query uitvoeren.
public class DeleteBikeController {
    
@Autowired
    dao.GenericDAO dao;
    
    @ModelAttribute// definiëren van het model attribute dat in de sessie gebruikt wordt
    public List<model.Bike> products(){
        return new ArrayList<>();
    }
    
    //Alvorens te verwijderen moeten eerst alle betreffenden producten (uit URL parameters) in de database gevonden worden.
    @RequestMapping(value="deleteproducts/bike", method=RequestMethod.GET)
    public String deleteConfirmDialog(@RequestParam("rmv") List<Integer> deletables, Model model){
        List<model.Bike> products = dao.findProductListById(model.Bike.class, deletables);
         model.addAttribute("products", products); //gevonden producten in sessie attribuut zetten
        for(model.Bike item : products){
            
            System.out.println(item.getName() + " wordt verwijderd");
        }
    		    	
    	return "productlist_delete_bike";
        
    }
    
    @RequestMapping(value="deleteproducts_confirm/bike", method=GET)
    public String deleteConfirmed(@ModelAttribute("products") List<model.Bike> products, SessionStatus status){
        dao.deleteProducts(products); //producten worden nu daadwerkelijk verwijderd
        System.out.println("producten verwijderd");
        status.setComplete(); //sessie opruimen
        
        return "products_deleted_bike";
    }
}
