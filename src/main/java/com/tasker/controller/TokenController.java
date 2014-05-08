/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.controller;

import com.tasker.entity.User;
import com.tasker.entity.UserToken;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class TokenController extends AbstractController {

    private static final int EXPIRATION_HOURS = 2;
    private EntityManager em;
    
    public UserToken createToken( User user ){
        UserToken userToken = getUserValidToken(user.getUserTokenList());
        
        if( userToken == null ){
            userToken = new UserToken();
            userToken.setIdUser( user );

            Date[] calculateCreatedAndExpirationDates
                    = this.calculateCreatedAndExpirationDates();

            userToken.setCreationDate( calculateCreatedAndExpirationDates[0] );
            userToken.setExpirationDate( calculateCreatedAndExpirationDates[1] );
            userToken.setToken( UUID.randomUUID().toString() );

            super.save(userToken);
        }
        
        return userToken;
    }
    
    public TokenController(EntityManager em) {
        this.em = em;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private Date[] calculateCreatedAndExpirationDates(){
        Calendar createdDatetime = Calendar.getInstance();
        createdDatetime.setTime( new Date( System.currentTimeMillis() ) );
        
        Calendar expirationDatetime = Calendar.getInstance();
        expirationDatetime.setTime( createdDatetime.getTime() );
        expirationDatetime.add(Calendar.HOUR_OF_DAY, EXPIRATION_HOURS);
        
        return new Date[]{ createdDatetime.getTime(), expirationDatetime.getTime() };
        
    }

    private UserToken getUserValidToken(Collection<UserToken> userTokenCollection) {
        
        UserToken selectedToken = null;
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(new Date( System.currentTimeMillis() ) );
        Date currentDateTime = currentCalendar.getTime();
        
        for (UserToken userToken : userTokenCollection) {
            if( currentDateTime.compareTo(userToken.getExpirationDate()) < 0 ){
                selectedToken = userToken;
                break;
            }
        }
        
        return selectedToken;
    }
}
