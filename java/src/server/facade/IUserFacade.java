/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.facade;

import model.player.User;

/**
 *
 * @author Michael
 */
public interface IUserFacade extends IModelFacade
{
    
    /**
     *
     * @param attemptingUser The username & password information that needs to be validated
     * @return The unique User ID of the validated user or -1 otherwise
     */
    public int login(User attemptingUser);

    /**
     *
     * @param attemptingUser The username & password information that needs to be registered
     * @return The unique User ID of the registered user or -1 otherwise
     */
    public int register(User attemptingUser);
}
