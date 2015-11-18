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
        System.out.println("Testing execute create game...");
        UserManager umanager = new UserManager();
        User newUser = new User("Bobby", "bobby");
        newUser.setPlayerId(0);
        umanager.addUser(newUser);
        GameManager gmanager = new GameManager();
        IModelFacade facade = new GamesFacade(umanager, gmanager);
        CreateGameRequest request = new CreateGameRequest(false, false, false, "Game1");
        String requestBody = request.serialize();
        Cookie currentCookie = new Cookie();
        currentCookie.setUser(newUser);
        CreateCommand instance = new CreateCommand();
        String expResult = "{\"players\":[],\"id\":0,\"title\":\"Game1\"}";
        String result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        System.out.println("Test create game passed.");
    }
    
}
