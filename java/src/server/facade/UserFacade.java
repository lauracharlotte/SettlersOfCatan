/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.facade;

import model.player.User;
import server.model.UserManager;

/**
 *
 * @author Michael
 */
public class UserFacade implements IUserFacade
{

    UserManager manager;
    
    public UserFacade(UserManager myManager)
    {
        this.manager = myManager;
    }
    
    @Override
    public int login(User attemptingUser)
    {
        User result = manager.getUserWithUsername(attemptingUser.getUsername());
        if(result == null)
            return -1;
        else
            return result.getUsername().equals(attemptingUser.getUsername()) 
                    && result.getPassword().equals(attemptingUser.getPassword()) 
                    ? result.getPlayerId() : -1;
    }

    @Override
    public int register(User attemptingUser)
    {
        return manager.addUser(attemptingUser);
    }
    
}
