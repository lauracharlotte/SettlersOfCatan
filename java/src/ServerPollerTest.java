import static org.junit.Assert.*;

import org.junit.Test;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.Server.ServerPoller;
import clientcommunicator.Server.ServerProxy;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import model.ClientModel;
import model.ClientModelSupplier;

public class ServerPollerTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	@Test
	public void test1() {
		String thisString = "hello";
		assertEquals("hello", thisString);
	}
	
	@Test //the clientModel should be null because we haven't put anything into it yet.
	public void beforeRunPollerTest() {
		ClientModel clientMod = ClientModelSupplier.getInstance().getModel();
		if(clientMod == null)
		{
			return;
		}
		else
		{
			fail();
		}
	}
	
	@Test
	public void callRunOnPollerTest() {
		ServerProxy server = new ServerProxy();
		ServerProxy server2 = new ServerProxy();
		ModelServerFacadeFactory serFacFact = new ModelServerFacadeFactory(server);
		
		ServerPoller testPoller = new ServerPoller(serFacFact, server2);
		testPoller.setPollingMilliseconds(30);
		testPoller.run();
	}
	
	@Test
	public void rightAfterRunPollerTest() {
		/*ServerProxy server = new ServerProxy();
		ServerProxy server2 = new ServerProxy();
		ModelServerFacadeFactory serFacFact = new ModelServerFacadeFactory(server);
		
		ServerPoller testPoller = new ServerPoller(serFacFact, server2);
		testPoller.setPollingMilliseconds(20);
		testPoller.run();
		
		ClientModel clientMod = ClientModelSupplier.getInstance().getModel();
		if(clientMod == null)
		{
			fail();
		}
		else
		{
			return;
		}*/
	}

}
