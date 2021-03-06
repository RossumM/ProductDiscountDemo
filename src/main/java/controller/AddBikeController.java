/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Merijn
 */
@Controller
public class AddBikeController {
    
static Logger logger;
    @Autowired
    dao.GenericDAO dao;
    
    @RequestMapping(value="addproduct/bike", method=GET) //GET request voor register geeft model met lege Product-entity door aan de viewResolver
    public String showProductAdd(Model model) {
        model.addAttribute(new model.Bike());     
      return "addBike";
    }
    
    @RequestMapping(value="addproduct/bike", method=POST) //de form input wordt getoets aan de Product velden, bij fouten terug naar het formulier
    public String processProductAdd( @Valid model.Bike product, Errors errors, RedirectAttributes model) { //RedirectAttributes zijn nodig om de gebruiker ook na redirect te onthouden
    	System.out.println(errors.hasErrors());
    	System.out.println(errors.hasFieldErrors());
      if (errors.hasErrors() || errors == null) {
    	  
        return "addBike";
      }else{ //Is de validatie geslaagd van kan het object opgeslagen worden
	      dao.saveProduct(product);
	      model.addAttribute("id", product.getId());
	      model.addFlashAttribute("product", product); //maakt tijdelijke sessie aan voor onthouden van product voorbij de redirect
	      return "redirect:/addedbike/" + product.getId();
	      }
    }
    
    @RequestMapping(value="addedproduct/bike/{producttype}/{id}", method=GET)
    public String showProfile(@PathVariable String producttype, @PathVariable String id, Model model){
    	if(!model.containsAttribute("id")){ //failsafe voor als Flashattribute toch niet blijkt te werken. Dan opnieuw gebruiker opzoeken via URL path variable 'username'
            logger.debug("Geen product in model");
            model.addAttribute(dao.findProductById(Long.parseLong(id), model.Bike.class));  		
    	}
        
       
    	return "addedBike";
    }
}
