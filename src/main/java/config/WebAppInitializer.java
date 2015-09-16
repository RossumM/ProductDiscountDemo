/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Merijn
 * Spring MVC wordt hier geinitialiseerd. De server zoekt naar classes met 'extends  AbstractAnnotationConfigDispatcherServletInitializer' -
 * -vervolgens wordt deze dus automatisch uitgevoerd.
 */

package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
    @Override// vanuit welke root path moet de servlet requests in behandeling nemen
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    @Override //verwijzing naar configuratiebestand root
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }
    
    @Override//verwijzing naar configuratiebestand servlet
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }
}
