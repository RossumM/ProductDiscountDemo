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

import model.User;




/**
 *
 * @author Merijn
 * Mapping en logica voor het registratieformulier. Deze maakt gebruik van zowel server side (@Valid) als client side (HTML5/javascript) validatie.
 */
@Controller
public class RegisterController {
    
    @Autowired
    dao.UserDao dao; //om gebruikers toe te kunnen voegen
    
    
    @RequestMapping(value="register", method=GET) //GET request voor register geeft model met lege User-entity door aan de viewResolver
    public String showRegistrationForm(Model model) {
      model.addAttribute(new User());
      return "registerForm";
    }
    
    @RequestMapping(value="register", method=POST) //de form input wordt getoets aan de User velden, bij fouten terug naar het formulier
    public String processRegistration( @Valid User user, Errors errors, RedirectAttributes model) { //RedirectAttributes zijn nodig om de gebruiker ook na redirect te onthouden
    	System.out.println(errors.hasErrors());
    	System.out.println(errors.hasFieldErrors());
      if (errors.hasErrors() || errors == null) {
    	  
        return "registerForm";
      }else{
    	  user.encryptPassword();
	      dao.saveUser(user);
	      model.addAttribute("username", user.getUsername());
	      model.addFlashAttribute("user", user); //maakt tijdelijke sessie aan voor onthouden gebruiker
	      return "redirect:/registered/" + user.getUsername();
	      }
    }
    
    @RequestMapping(value="registered/{username}", method=GET)
    public String showProfile(@PathVariable String username, Model model){
    	if(!model.containsAttribute("user")){ //failsafe voor als Flashattribute toch niet blijkt te werken. Dan opnieuw gebruiker opzoeken via URL path variable 'username'
    		System.out.println("Geen user in model");
    		model.addAttribute(dao.findUserByUsername(username));
    	}
    	return "registered";
    }

 }
