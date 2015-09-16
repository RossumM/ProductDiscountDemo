/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.slf4j.Logger;



/**
 *
 * @author Merijn
 */
public class ProductUtilities {
    static Logger logger;
    
    public static Class getProductClassFromUrlString(String productType){
        Class c;
        switch(productType){
            case "car": c = model.Car.class;
                break;
            case "bike": c = model.Bike.class;
                break;
            default: c = model.GenericProduct.class;
                logger.debug("General product selected");
        }
        
        return c;
    }
    
}
