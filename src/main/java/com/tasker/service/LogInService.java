/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.service;

import com.mysql.jdbc.StringUtils;
import com.tasker.controller.TokenController;
import com.tasker.controller.UserController;
import com.tasker.entity.User;
import com.tasker.entity.UserToken;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONObject;

/**
 *
 * @author Sebastian
 */
@Stateless
@Path("LogInService")
public class LogInService {
    
    @PersistenceContext(unitName = "com.tasker_tasker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Context
    private UriInfo context;
    
    /**
     * Retrieves representation of an instance of com.tasker.service.SignUpService
     * @param username
     * @param password
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public Response handleGet(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        
        if( StringUtils.isNullOrEmpty(username) 
                || StringUtils.isNullOrEmpty(password) ){
            
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity("Username or Password must not be empty or null")
                    .build();
        }
        
        JSONObject response = new JSONObject();
        UserController userController = new UserController(em);
        TokenController tokenController = new TokenController(em);
        
        Map<String, String> parameters = userController.buildParameters();
        parameters.put("userName", username);
        
        List<?> foundUser = userController
                .executeNameQuery("User.findByUserName", parameters);
        
        User user = (User)foundUser.get(0);
        
        if( user.getPassword().equals(password) ){
            
            //Generate new token related to the user
            UserToken createdToken = tokenController.createToken(user);
            
            //Return the user without the password and the token
            //in the json object
            
            response.put("token", createdToken.getToken());
            response.put("username", user.getUserName());
            
        }else{
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Password is invalid for the username: "
                            + user.getUserName()).build();
        }
        
        return Response.status(Response.Status.OK).entity(response.toString()).build();
    }
    
}
