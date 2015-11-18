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

/**
 *
 * @author Michael
 */
public class SoldierCommandTest
{
    
    public SoldierCommandTest()
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
        System.out.println("execute");
        IModelFacade facade = null;
        String requestBody = "";
        Cookie currentCookie = null;
        SoldierCommand instance = new SoldierCommand();
        String expResult = "";
        String result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
