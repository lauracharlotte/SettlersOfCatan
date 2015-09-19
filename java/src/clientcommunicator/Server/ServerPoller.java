/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.Server;

import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;

/**
 *
 * @author Michael
 */
public class ServerPoller 
{

    /**
     * @post The poller uses the passed in IServerProxy for future polling.
     * @param server
     */
    public ServerPoller(IServerProxy server)
    {
        
    }
    
    /**
     *
     */
    public ServerPoller()
    {
        
    }
    
    /**
     * @pre The polling is not currently going
     * @post The poller uses the passed in IServerProxy for future polling.
     * @param server
     */
    public void setServer(IServerProxy server)
    {
        
    }
    
    /**
     * @pre The polling is not currently going
     * @post The poller will send any model updates to the given facade when polling.
     * @param facade
     */
    public void setFacade(ModelServerFacadeFactory facade)
    {
        
    }
    
    
    /**
     * @pre The polling is not currently going.
     * @post When run is called, it will poll the server number of milliseconds time.
     * @param numberOfMilliseconds The number of milliseconds between times when the poller polls. 
     */
    public void setPollingMilliseconds(int numberOfMilliseconds)
    {
        
    }
    
    /**
     * @pre A server has been set using setServer() or the parameterized constructor. Also, a polling time has been set using setPollingMilliseconds(). Finally, a ModelServerFacadeFactory has been set.
     * @post This ServerPoller is polling the given IServerProxy class checking for model updates and giving the facade updates.
     */
    public void run()
    {
        
    }
    
    /**
     * @pre run() has been called on this object without a corresponding stop() call.
     * @post The object stops polling the server.
     */
    public void stop()
    {
        
    }
}
