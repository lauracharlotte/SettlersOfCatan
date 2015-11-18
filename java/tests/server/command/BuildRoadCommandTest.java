/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.BuildRoadRequest;
import clientcommunicator.operations.IJSONSerializable;
import java.util.ArrayList;
import java.util.Collection;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.player.Player;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import model.player.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import server.facade.IModelFacade;
import server.facade.MovesFacade;
import server.model.GameManager;
import shared.definitions.CatanColor;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

/**
 *
 * @author Michael
 */
public class BuildRoadCommandTest
{
    GameManager manager;
    Cookie currentCookie;
    IModelFacade facade;
    
    public BuildRoadCommandTest()
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
        manager= new GameManager();
        manager.addNewGame(new ClientModel(true, false, true, "name"));
        ClientModel currentModel = manager.getGameWithNumber(0);
        Collection<Player> players = new ArrayList<>();
        DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
        ResourceCards ResCards = new ResourceCards(3, 3, 3, 3, 3);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 15, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(0));
        currentModel.setPlayers(players);
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.firstround);
        currentModel.getTurnTracker().setCurrentTurn(new PlayerIdx(0));
        
        facade = new MovesFacade(manager);
        currentCookie = new Cookie();
        currentCookie.setGameNumber(0);
        currentCookie.setUser(new User("bobby", "bobby"));
    }
    
    @After
    public void tearDown()
    {
    }

//    @Test
//    public void testExecute() throws Exception
//    {
//
//        
//        System.out.println("execute");
//        IModelFacade facade = null;
//        String requestBody = "";
//        Cookie currentCookie = null;
//        BuildRoadCommand instance = new BuildRoadCommand();
//        String expResult = "";
//        String result = instance.execute(facade, requestBody, currentCookie);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test building a free road during the setup round
     * @throws Exception 
     */
    @Test
    public void testSetUp() throws Exception
    {        
        EdgeLocation location = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North);
        IJSONSerializable req = new BuildRoadRequest(new PlayerIdx(0), location, true);
        String requestBody = req.serialize();
        BuildRoadCommand instance = new BuildRoadCommand();
        instance.execute(facade, requestBody, currentCookie);
        
        ClientModel model = manager.getGameWithNumber(0);
        Player player = model.getPlayers().iterator().next();

        assert(player.getRoads() == 14);
        assert(model.getMap().getRoads().size() == 1);
    }
    
}
