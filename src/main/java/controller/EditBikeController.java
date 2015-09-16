/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.validation.Valid;
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
 * Wijzigen van product entries. 
 */
@Controller
public class EditBikeController {
    
    @Autowired
    dao.GenericDAO dao;
    
    @RequestMapping(value="editproduct/bike/{id}", method=GET)  //betreffende productgegevens ophalen
    public String showProductEdit(@PathVariable String id, Model model) {
        model.addAttribute(dao.findProductById(Long.parseLong(id), model.Car.class));
               
      return "editBike";
    }
    
    
    @RequestMapping(value="editproduct/bike/{id}", method=POST) //de form input wordt getoets aan de Product velden, bij fouten terug naar het formulier
    public String processProductEdit( @PathVariable String id, @Valid model.Car product, Errors errors, RedirectAttributes model) { //RedirectAttributes zijn nodig om de gebruiker ook na redirect te onthouden
      if (errors.hasErrors() || errors == null) {
        return "editBike";
      }else{
            dao.saveProduct(product); //product updaten met formdata
            model.addFlashAttribute("product", product); //maakt tijdelijke sessie aan voor onthouden product voorbij redirect
            return "redirect:/productEditResult/bike/" + product.getId();
        }
    }
    
    @RequestMapping(value="productEditResult/bike/{id}", method=GET)
    public String showChange(@PathVariable String producttype, @PathVariable String id, Model model){
    	if(!model.containsAttribute("id")){ //failsafe voor als Flashattribute toch niet blijkt te werken. Dan opnieuw gebruiker opzoeken via URL path variable 'id'
            System.out.println("Geen product in model");
            model.addAttribute(dao.findProductById(Long.parseLong(id), model.Car.class));
    	}
    	return "editBikeResult";
    }
}
