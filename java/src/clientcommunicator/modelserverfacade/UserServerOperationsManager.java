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
     *
     * @param creds
     * @return
     */
    public int loginUser(LoginCredentials creds)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     *
     * @param creds
     * @return
     */
    public int registerUser(LoginCredentials creds)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
