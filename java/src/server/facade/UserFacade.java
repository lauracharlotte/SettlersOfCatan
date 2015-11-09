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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int register(User attemptingUser)
    {
        return manager.addUser(attemptingUser);
    }
    
}
