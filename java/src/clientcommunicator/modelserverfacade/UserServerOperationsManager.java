/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.LoginCredentials;

/**
 *
 * @author Michael
 */
public class UserServerOperationsManager implements IServerOperationsManager
{

    @Override
    public void setServer(IServerProxy serverToUse)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * @pre The user was not logged in.  The credentials are valid for a registered user.
     * @post The user is logged in and the player id in the model is set.
     * @param creds The username and password as put into the gui by the client
     * @return The unique player id of the player
     */
    public int loginUser(LoginCredentials creds)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @pre The user was not logged in and the username is not in use by another user.
     * @post The user is logged in, and the player id in the model is set.  The credentials are now valid for login.
     * @param creds The username and password as put into the gui by the client
     * @return The unique player id of the player
     */
    public int registerUser(LoginCredentials creds)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
