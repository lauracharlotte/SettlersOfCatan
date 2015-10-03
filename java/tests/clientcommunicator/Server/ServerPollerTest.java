package clientcommunicator.Server;

import static org.junit.Assert.*;

import org.junit.Test;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.Server.MockServerProxy;
import clientcommunicator.Server.ServerPoller;
import clientcommunicator.Server.ServerProxy;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModel;
import model.ClientModelSupplier;

public class ServerPollerTest {
	
    @Test
    public void callRunOnPollerTest() 
    {
		
        ClientModel clientMod = ClientModelSupplier.getInstance().getModel();
        assertNull(clientMod);
		
        IServerProxy server = new MockServerProxy();
        ModelServerFacadeFactory serFacFact = new ModelServerFacadeFactory(server);
		
        ServerPoller testPoller = new ServerPoller(serFacFact, server);
        testPoller.setPollingMilliseconds(30);
        testPoller.run();
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(ServerPollerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClientModel clientMod2 = ClientModelSupplier.getInstance().getModel();
        testPoller.stop();
        assertNotNull(clientMod2);
    }

}
