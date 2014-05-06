/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.security;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Sebastian
 */
public class SecurityFilter implements ContainerRequestFilter {

    @PersistenceUnit(unitName="com.tasker_tasker_war_1.0-SNAPSHOTPU")
    EntityManagerFactory emf; 
    @Resource
    UserTransaction utx;
    
    @Override
    public ContainerRequest filter(ContainerRequest cr) {
//        String token = cr.getHeaderValue("token");
//        if( token == null ){
//            throw new WebApplicationException(
//                    Response.status(Response.Status.UNAUTHORIZED)
//                            .entity("Token not found")
//                            .build());
//        }else{
////            UserTokenJpaController tokenController = new UserTokenJpaController(utx, emf);
//            
//        }
        return cr;
    }

}
