/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.service;

import com.tasker.controller.UserController;
import com.tasker.entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Sebastian
 */
@Stateless
@Path("SignUpService")
public class SignUpService {
    
    @PersistenceContext(unitName = "com.tasker_tasker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Context
    private UriInfo context;
    
    /**
     * Retrieves representation of an instance of com.tasker.service.SignUpService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String handleGet() {
        //TODO return proper representation object
        return "GET method for SignUpService not supported";
    }

    /**
     * PUT method for updating or creating an instance of SignUpService
     * @param user representation for the resource
     * @return 
     */
    @POST
    @Consumes("application/json")
    public Response handlePost(User user) {
        
        UserController userController = new UserController(em);
        
        if( userController.checkUserNameAvailability(user.getUserName()) ){            
            userController.save(user);
            return Response.status(Response.Status.OK)
                    .entity("User: " + user.getUserName() +
                            " created correctly").build();
            
        }else{
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity("User name already taken").build();
        }
    }
}
