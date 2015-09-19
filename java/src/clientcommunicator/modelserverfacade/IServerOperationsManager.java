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

    /**
     * @pre The IServerProxy is not null--and is a valid IServerProxy
     * @post The operations manager will use the passed in "serverToUse" for future requests.
     * @param serverToUse The IServerProxy that the operations manager should use to send requests to.
     */
    public void setServer(IServerProxy serverToUse);
}
