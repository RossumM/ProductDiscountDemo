/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author Merijn
 * Mapping van eenvoudige requests
 */
@Controller
public class HomeController {
    
    @RequestMapping(value={"/","/home"}, method=GET) //waarom werkt een mapping van "/" niet in Tomcat?
    public String loadHome(){
        return "index"; //return type is altijd de naam van de view, die dan door de view resolver naar een echte locatie omgezet wordt.
    }
    
    @RequestMapping(value="/login", method=GET)
    public String loginForm(){
    	return "login";
    }
    
    @RequestMapping(value="/loggedout", method=GET)
    public String loggedout(){
    	return "loggedout";
    }
    @RequestMapping(value="/secured", method=GET)
    public String Secure(){
    	return "secured";
    }
}
