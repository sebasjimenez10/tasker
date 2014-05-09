/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.service;

import com.tasker.security.annotations.Secured;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Sebastian
 */
@Secured
@Stateless
@Path("UserTasksService")
public class UserTasksService {
    
    @PersistenceContext(unitName = "com.tasker_tasker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Context
    private UriInfo context;
    
    /**
     * 
     * @return 
     */
    @GET
    @Produces("application/json")
    public Response handleGet() {
        
        return Response.status(Response.Status.OK).build();
    }
    
}
