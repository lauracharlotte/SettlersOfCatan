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
import server.facade.GamesFacade;
import clientcommunicator.operations.JoinGameRequest;
import server.model.UserManager;
import server.model.GameManager;
import model.ClientModel;
import model.player.User;
import shared.definitions.CatanColor;

/**
 *
 * @author Michael
 */
public class JoinCommandTest
{
    
    public JoinCommandTest()
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
        System.out.println("Testing execute join game...");
        User newUser = new User("Bobby", "bobby");
        newUser.setPlayerId(0);
        GameManager gmanager = new GameManager();
        ClientModel model = new ClientModel(false, false, false, "Game1");
        gmanager.addNewGame(model);
        IModelFacade facade = new GamesFacade(gmanager);
        JoinGameRequest request = new JoinGameRequest(0, CatanColor.WHITE);
        String requestBody = request.serialize();
        Cookie currentCookie = new Cookie();
        currentCookie.setUser(newUser);
        JoinCommand instance = new JoinCommand();
        String expResult = "Success";
        String result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test join game passed.");
        
        //Test 2
        System.out.println("Testing join game already joined...");
        request = new JoinGameRequest(0, CatanColor.PURPLE);
        requestBody = request.serialize();
        currentCookie = new Cookie();
        currentCookie.setUser(newUser);
        expResult = "Success";
        result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test rejoin game passed.");
    }
}
