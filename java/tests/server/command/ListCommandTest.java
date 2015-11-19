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
import model.player.User;
import server.model.GameManager;
import server.facade.GamesFacade;
import model.ClientModel;


/**
 *
 * @author Michael
 */
public class ListCommandTest
{
    
    public ListCommandTest()
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
        System.out.println("Testing execute list games, empty list...");
        User newUser = new User("Bobby", "bobby");
        newUser.setPlayerId(0);
        GameManager gmanager = new GameManager();
        IModelFacade facade = new GamesFacade(gmanager);
        String requestBody = "";
        Cookie currentCookie = new Cookie();
        currentCookie.setUser(newUser);
        ListCommand instance = new ListCommand();
        String expResult = "[]";
        String result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test list with no games passed.");
        
        //Test 2
        System.out.println("Testing execute list games, populated list...");
        ClientModel game1 = new ClientModel(false, false, false, "Game1");
        gmanager.addNewGame(game1);
        ClientModel game2 = new ClientModel(true, true, true, "Game2");
        gmanager.addNewGame(game2);
        facade = new GamesFacade(gmanager);
        expResult = "[{\"players\":[],\"id\":0,\"title\":\"Game1\"},"
        		+ "{\"players\":[],\"id\":1,\"title\":\"Game2\"}]";
        result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test list with multiple games passed.");
    }
    
}
