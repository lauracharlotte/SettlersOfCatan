/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.LoginCredentials;
import model.player.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import server.facade.IModelFacade;
import server.facade.UserFacade;
import server.model.UserManager;

/**
 *
 * @author Michael
 */
public class LoginCommandTest
{
    
    public LoginCommandTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void testExecute() throws Exception
    {
        System.out.println("Login User execute");
        UserManager manager = new UserManager();
        User newUser = new User("bobby", "bobby");
        newUser.setPlayerId(0);
        manager.addUser(newUser);
        IModelFacade facade = new UserFacade(manager);
        LoginCredentials creds = new LoginCredentials("bobby", "bobby");
        String requestBody = creds.serialize();
        Cookie currentCookie = new Cookie();
        LoginCommand instance = new LoginCommand();
        String result = instance.execute(facade, requestBody, currentCookie);
        String expResult = "Success";
        assertEquals(expResult, result);
        assert(!"".equals(currentCookie.getCompleteCookieString()));
        creds = new LoginCredentials("bobby", "notbobby"); 
        requestBody = creds.serialize();
        result = instance.execute(facade, requestBody, new Cookie());
        assertNotEquals(expResult, result);
    }
    
}
