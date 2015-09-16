/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Merijn
 */
import java.util.List;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.User;

/**
 *
 * @author Merijn
 * Database object class. Werkt nu nog zonder interface maar je kunt er ook een implementatie van maken.
 */
@Repository //deze Sring annotatie is equivalent aan 'component' maar dan specifiek voor database objects/repositories
public class UserDao {
    

    @Autowired
    private SessionFactory sf;
    
    @Transactional //Wrapper Hibernate-transactie.
    public void saveUser(User user){
        sf.getCurrentSession().save(user);
        System.out.println("opgeslagen user is: " + user.getUsername());
    }
   
    @Transactional
    public User findUserByUsername(String username){// retourneert user == null als geen gebruiker gevonden wordt
    	User user = null;
    	try{
             String hql = "from User where username=" + username;
             Query query = sf.getCurrentSession().createQuery(hql);
             List<User> listUser = (List<User>) query.list();
             user = listUser.get(0);
	    	
    	}catch(NoResultException nre){
    	}
    	return user;
    }
    
}

