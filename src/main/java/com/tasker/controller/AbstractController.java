/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Sebastian
 */
public abstract class AbstractController {
    
    protected abstract EntityManager getEntityManager();
    
    public void save( Object entity ){
        getEntityManager().persist(entity);
    }
    
    public void delete( Object entity ){
        getEntityManager().remove(entity);
    }
    
    public Object findById(Class<?> entity, Object id ){
        return getEntityManager().find(entity, id );
    }
    
    public List<?> executeNameQuery(String namedQuery, Map<String, String> parameters){
        
        Query createNamedQuery = getEntityManager().createNamedQuery(namedQuery);
        
        for (Map.Entry<String, String> queryParameter : parameters.entrySet()) {
            String key = queryParameter.getKey();
            String value = queryParameter.getValue();
            
            createNamedQuery.setParameter(key, value);
        }
        
        return createNamedQuery.getResultList();
    }
    
    public Map<String, String> buildParameters(){
        return new HashMap<String, String>();
    }
}
