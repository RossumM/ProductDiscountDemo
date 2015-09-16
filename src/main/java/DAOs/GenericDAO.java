/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import models.GenericProduct;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Merijn
 * Gebruik Java Generics om meerdere typen producten te verwerken
 */
@Repository
public class GenericDAO {
    
    static Logger logger;
    
    @Autowired //autowired uit EntityFactoryManager Bean
    private SessionFactory sf;
    
    @Transactional //Wrapper Hibernate-transactie.
    public <T extends GenericProduct > void saveProduct (T product){ //combinatiemethode voor zowel save als update
        sf.getCurrentSession().saveOrUpdate(product);
        logger.debug("opgeslagen product is: " + product.getName());
    }
    
    @Transactional 
    public <T extends GenericProduct > void deleteProducts(List<T> products){// verwijdert lijst van producten
        for(T item : products){
            sf.getCurrentSession().delete(item);
            logger.debug("het volgende product is verwijderd: " + item.getName());
        }
    }
    
    
    @Transactional
    public <T extends GenericProduct > T findProductById(long id, Class c){//vindt één product met gegeven id
    	T product = null;
    	try{
             String hql = "from " + c.getClass().getName() + " where id=" + id;
             Query query = sf.getCurrentSession().createQuery(hql);
             List<T> listProduct = (List<T>) query.list();
             product = listProduct.get(0);
    	}catch(NoResultException nre){
            logger.debug("geen producten gevonden met opgegeven id");
    	}
    	return product;
    }
    
    // vindt lijst met producten via een lijst met id's, deze producten kunnen dan in één keer verwijderd worden.
    @Transactional
    public <T extends GenericProduct > List<T> findProductListById(Class c, List<Integer> id){
        T product = null;   
        List<T> listOfProducts = new ArrayList<>();
        for(int item : id){
            try{
             String hql = "from " + c.getClass().getName() + " where id=" + item;
             Query query = sf.getCurrentSession().createQuery(hql);
             List<T> tempList = (List<T>) query.list();
             product = tempList.get(0);
             
            }catch(NoResultException nre){
                logger.debug("geen resultaten uit query");
            }
            if(product != null){ //bij geen resultaat retourneert de methode een lege lijst    
                listOfProducts.add(product);
            }
        }
    	return listOfProducts;
    }
    
    
    @Transactional
    public <T extends GenericProduct > List<T> findAllProducts(Class c){ // Vindt lijst met alle producten van gegeven class
        List<T> results = null;
    	try{
            String hql = "from " + c.getName() + "";
            Query query = sf.getCurrentSession().createQuery(hql);
            results = (List<T>) query.list();
    	}catch(NoResultException nre){
    	}
    	return results;
    }
    
}
