/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author Merijn
 */
@Controller
public class ViewBikeController {
 
    @Autowired
    DAOs.GenericDAO dao; //om producten toe te kunnen voegen

    
    @RequestMapping(value="productlist/bike", method=GET)
    public String showProducts(Model model){
        List<models.Bike> products = dao.findAllProducts(models.Bike.class);
        
        for(models.Bike item : products){
            System.out.println("product : " + item.getName());
        }
    		model.addAttribute("products", products);
    	
    	return "productlistBike";
    }
}