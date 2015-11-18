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
import java.util.Arrays;
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
    private static GameManager manager;
    private static Cookie currentCookie;
    private static IModelFacade facade;
    
    public BuildRoadCommandTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        manager= new GameManager();
        manager.addNewGame(new ClientModel(true, false, true, "name"));
        ClientModel currentModel = manager.getGameWithNumber(0);
        Collection<Player> players = new ArrayList<>();
        DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
        ResourceCards ResCards = new ResourceCards(0, 0, 0, 0, 0);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 15, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(0));
        currentModel.setPlayers(players);
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.firstround);
        currentModel.getTurnTracker().setCurrentTurn(new PlayerIdx(0));
        manager.replaceGame(0, currentModel);
        
        facade = new MovesFacade(manager);
        currentCookie = new Cookie();
        currentCookie.setGameNumber(0);
        currentCookie.setUser(new User("bobby", "bobby"));
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
    public void testSetUpRound1() throws Exception
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
        // assert that no brick or wood were taken (it was free)
        assert(player.getHand().getResourceCards().getBrick() == 0);
        assert(player.getHand().getResourceCards().getLumber() == 0);
    }
    
    @Test
    public void testSetUpRound2() throws Exception
    {   
        EdgeLocation location = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast);
        IJSONSerializable req = new BuildRoadRequest(new PlayerIdx(0), location, true);
        String requestBody = req.serialize();
        BuildRoadCommand instance = new BuildRoadCommand();
        instance.execute(facade, requestBody, currentCookie);
        
        ClientModel model = manager.getGameWithNumber(0);
        Player player = model.getPlayers().iterator().next();

        assert(player.getRoads() == 13);
        assert(model.getMap().getRoads().size() == 2);
        // assert that no brick or wood were taken (it was free)
        assert(player.getHand().getResourceCards().getBrick() == 0);
        assert(player.getHand().getResourceCards().getLumber() == 0);
    }
    
    @Test
    public void testPlaying() throws Exception
    {
        EdgeLocation location = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest);
        IJSONSerializable req = new BuildRoadRequest(new PlayerIdx(0), location, false);
        String requestBody = req.serialize();
        BuildRoadCommand instance = new BuildRoadCommand();
        instance.execute(facade, requestBody, currentCookie);
        
        ClientModel model = manager.getGameWithNumber(0);
        Player player = model.getPlayers().iterator().next();
        
        // The road should not have been built, as they do not have the resources to build it.
        assert(player.getRoads() == 13);
        assert(model.getMap().getRoads().size() == 2);
        
        // Give the player one brick and one wood
        player.getHand().getResourceCards().setBrick(1);
        player.getHand().getResourceCards().setLumber(1);
        Player[] players = model.getPlayers().toArray(new Player[4]);
        players[player.getPlayerIndex().getIndex()] = player;
        model.setPlayers(Arrays.asList(players));
        model.getBank().getResourceCards().setBrick(18);
        model.getBank().getResourceCards().setLumber(18);
        manager.replaceGame(0, model);
        
        // Player should have enough resources to build a road now.
        location = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest);
        req = new BuildRoadRequest(new PlayerIdx(0), location, false);
        requestBody = req.serialize();
        instance = new BuildRoadCommand();
        instance.execute(facade, requestBody, currentCookie);
        
        model = manager.getGameWithNumber(0);
        player = model.getPlayers().iterator().next();
        
        assert(player.getRoads() == 12);
        assert(model.getMap().getRoads().size() == 3);
        // assert that the brick and wood were taken away.
        assert(player.getHand().getResourceCards().getBrick() == 0);
        assert(player.getHand().getResourceCards().getLumber() == 0);
        // assert that the bank got these cards back.
        assert(model.getBank().getResourceCards().getBrick() == 19);
        assert(model.getBank().getResourceCards().getLumber() == 19);
    }
    
}
