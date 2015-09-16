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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author Merijn
 */
@Controller
@SessionAttributes("products")//een session voor de lijst met product objecten, anders moet je voor iedere request opnieuw een query uitvoeren.
public class DeleteCarController {
    
@Autowired
    dao.GenericDAO dao;
    
    @ModelAttribute// definiëren van het model attribute dat in de sessie gebruikt wordt
    public List<model.Car> products(){
        return new ArrayList<>();
    }
    
    //Alvorens te verwijderen moeten eerst alle betreffenden producten (uit URL parameters) in de database gevonden worden.
    @RequestMapping(value="deleteproducts/car", method=RequestMethod.GET)
    public String deleteConfirmDialog(@RequestParam("rmv") List<Integer> deletables, Model model){
        List<model.Car> products = dao.findProductListById(model.Car.class, deletables);
         model.addAttribute("products", products); //gevonden producten in sessie attribuut zetten
        for(model.Car item : products){
            
            System.out.println(item.getName() + " wordt verwijderd");
        }
    		    	
    	return "productlist_delete_car";
        
    }
    
    @RequestMapping(value="deleteproducts_confirm/car", method=GET)
    public String deleteConfirmed(@ModelAttribute("products") List<model.Car> products, SessionStatus status){
        dao.deleteProducts(products); //producten worden nu daadwerkelijk verwijderd
        System.out.println("producten verwijderd");
        status.setComplete(); //sessie opruimen
        
        return "products_deleted_car";
    }
}
