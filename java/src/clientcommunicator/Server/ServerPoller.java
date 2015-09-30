/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.Server;

import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

/**
 *
 * @author Michael
 */
public class ServerPoller 
{
    private long numberOfMillisecondsBetweenPoll;
    private ModelServerFacadeFactory communicator;
    private IServerProxy server;
    private Timer timer;
    
    
    /**
     * @param communicator
     * @post The communicator uses the passed in communicator for future polling.
     */
    public ServerPoller(ModelServerFacadeFactory communicator)
    {
        this.communicator = communicator;
    }
    
    /**
     *
     */
    public ServerPoller()
    {}

    public ServerPoller(ModelServerFacadeFactory communicator, IServerProxy server)
    {
        this.communicator = communicator;
        this.server = server;
    }
    
    
    
    /**
     * @param communicator The communicator that should be used
     * @post The poller will send any model updates to the given facade when polling.
     */
    public void setFacade(ModelServerFacadeFactory communicator)
    {
        this.communicator = communicator;
    }
    
    /**
     * @pre The polling is not currently going
     * @post The poller uses the passed in IServerProxy for future polling.
     * @param server
     */
    public void setServer(IServerProxy server)
    {
        this.server = server;
    }
    
    /**
     * @pre The polling is not currently going.
     * @post When run is called, it will poll the server number of milliseconds time.
     * @param numberOfMilliseconds The number of milliseconds between times when the poller polls. 
     */
    public void setPollingMilliseconds(long numberOfMilliseconds)
    {
        this.numberOfMillisecondsBetweenPoll = numberOfMilliseconds;
    }
    
    /**
     * @pre A server has been set using setServer() or the parameterized constructor. Also, a polling time has been set using setPollingMilliseconds(). Finally, a ModelServerFacadeFactory has been set.
     * @post This ServerPoller is polling the given IServerProxy class checking for model updates and giving the facade updates.
     */
    public void run()
    {
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                try {
                    communicator.updateModel(server.getModel(-1));
                } catch (ClientException ex) {
                    Logger.getLogger(ServerPoller.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };
        this.timer = new Timer();
        this.timer.schedule(timerTask, 0, this.numberOfMillisecondsBetweenPoll);
    }
    
    /**
     * @pre run() has been called on this object without a corresponding stop() call.
     * @post The object stops polling the server.
     */
    public void stop()
    {
        this.timer.cancel();
    }
}
