/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;

/**
 *
 * @author Michael
 */
public interface IServerOperationsManager 
{
    public void setServer(IServerProxy serverToUse);
}
