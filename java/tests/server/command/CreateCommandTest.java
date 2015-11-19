/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import server.facade.IModelFacade;
import server.model.UserManager;
import model.player.User;
import server.model.GameManager;
import server.facade.GamesFacade;
import clientcommunicator.operations.CreateGameRequest;

/**
 *
 * @author Michael
 */
public class CreateCommandTest
{
    
    public CreateCommandTest()
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
    	//Test 1
        System.out.println("Testing execute create game...");
        User newUser = new User("Bobby", "bobby");
        newUser.setPlayerId(0);
        GameManager gmanager = new GameManager();
        IModelFacade facade = new GamesFacade(gmanager);
        CreateGameRequest request = new CreateGameRequest(false, false, false, "Game1");
        String requestBody = request.serialize();
        Cookie currentCookie = new Cookie();
        currentCookie.setUser(newUser);
        CreateCommand instance = new CreateCommand();
        String expResult = "{\"players\":[],\"id\":0,\"title\":\"Game1\"}";
        String result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test create game passed.");
        
        //Test 2
        System.out.println("Testing create with random resource tiles...");
        request = new CreateGameRequest(true, false, false, "Game2");
        requestBody = request.serialize();
        expResult = "{\"players\":[],\"id\":1,\"title\":\"Game2\"}";
        result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test create with random tiles passed.");
        
        //Test 3
        System.out.println("Testing create with random numbers...");
        request = new CreateGameRequest(false, true, false, "Game3");
        requestBody = request.serialize();
        expResult = "{\"players\":[],\"id\":2,\"title\":\"Game3\"}";
        result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test create with random numbers passed.");
        
        //Test 4
        System.out.println("Testing create with random ports...");
        request = new CreateGameRequest(false, false, true, "Game4");
        requestBody = request.serialize();
        expResult = "{\"players\":[],\"id\":3,\"title\":\"Game4\"}";
        result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test create with random ports passed.");
    }
    
}
