package clientcommunicator.Server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//import static org.junit.Assert.*;

/**
 *
 * @author Scott
 */
public class ServerProxyTest {
    
    public ServerProxyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registerUser method, of class ServerProxy.
     * @throws Exception
     */
    @Test
    public void testPost() throws Exception {
        System.out.println("Testing POST Request");
        String body = "{'username': 'test_user', 'password': 'test_password'}";
        ServerProxy instance = new ServerProxy();
        try 
        {
            int result = instance.registerUser(body);
        }
        catch (Exception e)
        {
            int result = instance.loginUser(body);
        }
        
        System.out.println("POST request test passed");
    }

    /**
     * Test of listGames method, of class ServerProxy.
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception {
        System.out.println("Testing GET Request");
        ServerProxy instance = new ServerProxy();
        String result = instance.listGames();
        System.out.println("GET request test passed");
    }
    
}
