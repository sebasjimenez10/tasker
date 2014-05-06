/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.controller;

import com.tasker.entity.User;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author Sebastian
 */
public class UserController extends AbstractController {

    private EntityManager em;
    
    public UserController(EntityManager em) {
        this.em = em;
    }

    public void save(User entity) {
        super.save(entity);
    }

    public void delete(User entity) {
        super.delete(entity);
    }

    public Object findById(User entity, Object id) {
        return super.findById(entity.getClass(), id);
    }

    @Override
    public List<?> executeNameQuery(String namedQuery, Map<String, String> parameters) {
        return super.executeNameQuery(namedQuery, parameters);
    }
    
    public boolean checkUserNameAvailability(String userName){
        boolean result = true;
        Map<String, String> parameters = super.buildParameters();
        parameters.put("userName", userName);
        
        List<?> usersList = executeNameQuery("User.findByUserName", parameters);
        
        if( usersList.size() > 0 ){
            result = false;
        }
        
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
