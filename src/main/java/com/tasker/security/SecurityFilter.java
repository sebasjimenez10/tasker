/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.security;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.tasker.controller.TokenController;
import com.tasker.entity.UserToken;
import com.tasker.security.annotations.Secured;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Sebastian
 */
public class SecurityFilter implements ContainerRequestFilter {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory
        ("com.tasker_tasker_war_1.0-SNAPSHOTPU");
    
    EntityManager em = emf.createEntityManager();
    
    @Resource
    UserTransaction utx;
    
    @Override
    public ContainerRequest filter(ContainerRequest cr) {
        
        URI absolutePath = cr.getAbsolutePath();
        String [] pathElements = absolutePath.getPath().split("/");
        String service = pathElements[ pathElements.length - 1 ];
        
        try {
            Class<?> serviceClass = Class.forName("com.tasker.service." + service);
            
            if ( serviceClass.isAnnotationPresent(Secured.class) ) {
                // Check for the token
                String token = cr.getHeaderValue("token");
                if (token == null) {
                    throw new WebApplicationException(
                            Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Token not found")
                            .build());
                } else {
                    // Let's see if the token is still valid
                    TokenController tokenController = new TokenController(em);

                    Map<String, String> parameters
                            = tokenController.buildParameters();

                    parameters.put("token", token);

                    List<?> tokens = tokenController.
                            executeNameQuery("UserToken.findByToken",
                                    parameters);
                    
                    UserToken userToken = (UserToken) tokens.get(0);
                    Date tokenExpirationDate = userToken.getExpirationDate();
                    Date currentDate = getCurrentDate();
                    
                    if( currentDate.compareTo(tokenExpirationDate) > 0 ){
                        // Token is invalid
                        throw new WebApplicationException(
                            Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Token has expired found")
                            .build());
                    }
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SecurityFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cr;
    }
    
    private Date getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime( new Date( System.currentTimeMillis() ) );
        
        return cal.getTime();
    }

}
