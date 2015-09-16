/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Merijn
 * 
 */
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Merijn
 * De data access objects zijn hier te vinden. Ze maken vooralsnog geen gebruik van een interface
 * maar die kan ook nog als laag toegevoegd worden. Spring biedt zelfs functionaliteit waarbij de 
 * implementatie van DAOs automatisch plaats vindt, op basis van de naamgeving van de methode.
 * Alle DAOs maken gebruik van Hibernate's session factory en zijn voor het gemak geplaatst binnen
 * een @Transaction wrapper (in plaats van session.commit/ session.close e.d..
 */
@Repository
public class ProductDao {
    
    @Autowired //autowired uit EntityFactoryManager Bean
    private SessionFactory sf;
    
    @Transactional //Wrapper Hibernate-transactie.
    public void saveProduct(GenericProduct product){ //combinatiemethode voor zowel save als update
        sf.getCurrentSession().saveOrUpdate(product);
        System.out.println("opgeslagen product is: " + product.getName() );
    }
    
    @Transactional 
    public void deleteProducts(List<GenericProduct> products){// verwijdert lijst van producten
        for(GenericProduct item : products){
            sf.getCurrentSession().delete(item);
            System.out.println("het volgende product is verwijderd: " + item.getName() );
        }
    }
    
    
    @Transactional
    public GenericProduct findProductById(long id){//vindt één product met gegeven id
    	GenericProduct product = null;
    	try{
             String hql = "from Product where id=" + id;
             Query query = sf.getCurrentSession().createQuery(hql);
             List<GenericProduct> listProduct = (List<GenericProduct>) query.list();
             product = listProduct.get(0);
    	}catch(NoResultException nre){
            System.out.println("geen producten gevonden met opgegeven id");
    	}
    	return product;
    }
    
    // vindt lijst met producten via een lijst met id's, deze producten kunnen dan in één keer verwijderd worden.
    @Transactional
    public List<GenericProduct> findProductListById(List<Integer> id){
        GenericProduct product = null;   
        List<GenericProduct> listOfProducts = new ArrayList<>();
        for(int item : id){
            try{
             String hql = "from Product where id=" + item;
             Query query = sf.getCurrentSession().createQuery(hql);
             List<GenericProduct> tempList = (List<GenericProduct>) query.list();
             product = tempList.get(0);
             
            }catch(NoResultException nre){
                System.out.println("geen resultaten uit query");
            }
            if(product != null){ //bij geen resultaat retourneert de methode een lege lijst    
                listOfProducts.add(product);
            }
        }
    	return listOfProducts;
    }
    
    
    @Transactional
    public List<GenericProduct> findAllProducts(){ // Vindt lijst met alle producten
        List<GenericProduct> results = null;
    	try{
            String hql = "from Product";
            Query query = sf.getCurrentSession().createQuery(hql);
            results = (List<GenericProduct>) query.list();
    	}catch(NoResultException nre){
    	}
    	return results;
    }
}
