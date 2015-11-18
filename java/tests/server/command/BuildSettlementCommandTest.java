/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.BuildSettlementRequest;
import clientcommunicator.operations.IJSONSerializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.map.CatanMap;
import model.map.EdgeObject;
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
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
public class BuildSettlementCommandTest
{
    private static GameManager manager;
    private static Cookie currentCookie;
    private static IModelFacade facade;
    
    public BuildSettlementCommandTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        manager = new GameManager();
        manager.addNewGame(new ClientModel(true, false, true, "name"));
        ClientModel currentModel = manager.getGameWithNumber(0);
        Collection<Player> players = new ArrayList<>();
        DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
        ResourceCards ResCards = new ResourceCards(0, 0, 0, 0, 0);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 14, 5, 0, 0, new Hand(ResCards, emptyDevCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(0));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(1));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(2));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(3));
        currentModel.setPlayers(players);
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.firstround);
        currentModel.getTurnTracker().setCurrentTurn(new PlayerIdx(0));
        Collection<EdgeObject> roads = new ArrayList<>();
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North), newPlayer.getPlayerIndex()));
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.South), newPlayer.getPlayerIndex()));
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.South), newPlayer.getPlayerIndex()));
        CatanMap map = currentModel.getMap();
        map.setRoads(roads);
        currentModel.setMap(map);
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

    @Test
    public void testSetup() throws Exception
    {
        VertexLocation location = new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast);
        IJSONSerializable req = new BuildSettlementRequest(new PlayerIdx(0), location, true);
        String requestBody = req.serialize();
        BuildSettlementCommand instance = new BuildSettlementCommand();
        instance.execute(facade, requestBody, currentCookie);
        ClientModel model = manager.getGameWithNumber(0);
        Player player = model.getPlayers().iterator().next();
        
        assert(player.getSettlements() == 4);
        assert(model.getMap().getSettlements().size() == 1);
        // assert that no resources were taken (it was free)
        assert(player.getHand().getResourceCards().getBrick() == 0);
        assert(player.getHand().getResourceCards().getLumber() == 0);
        assert(player.getHand().getResourceCards().getWool() == 0);
        assert(player.getHand().getResourceCards().getGrain() == 0);
        
        // round two
        location = new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest);
        req = new BuildSettlementRequest(new PlayerIdx(0), location, true);
        requestBody = req.serialize();
        instance = new BuildSettlementCommand();
        instance.execute(facade, requestBody, currentCookie);
        model = manager.getGameWithNumber(0);
        player = model.getPlayers().iterator().next();
        
        assert(player.getSettlements() == 3);
        assert(model.getMap().getSettlements().size() == 2);
        // assert that no resources were taken (it was free)
        assert(player.getHand().getResourceCards().getBrick() == 0);
        assert(player.getHand().getResourceCards().getLumber() == 0);
        assert(player.getHand().getResourceCards().getWool() == 0);
        assert(player.getHand().getResourceCards().getGrain() == 0);
    }
    
    @Test
    public void testPlaying() throws Exception
    {
        VertexLocation location = new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthEast);
        IJSONSerializable req = new BuildSettlementRequest(new PlayerIdx(0), location, false);
        String requestBody = req.serialize();
        BuildSettlementCommand instance = new BuildSettlementCommand();
        instance.execute(facade, requestBody, currentCookie);
        ClientModel model = manager.getGameWithNumber(0);
        Player player = model.getPlayers().iterator().next();
        
        // No settlement should have been taken (they don't have the resources)
        assert(player.getSettlements() == 3);
        assert(model.getMap().getSettlements().size() == 2);
        
        // Give the player the needed resources
        player.getHand().getResourceCards().setBrick(1);
        player.getHand().getResourceCards().setLumber(1);
        player.getHand().getResourceCards().setWool(1);
        player.getHand().getResourceCards().setGrain(1);
        Player[] players = model.getPlayers().toArray(new Player[4]);
        players[player.getPlayerIndex().getIndex()] = player;
        model.setPlayers(Arrays.asList(players));
        model.getBank().getResourceCards().setBrick(18);
        model.getBank().getResourceCards().setLumber(18);
        model.getBank().getResourceCards().setWool(18);
        model.getBank().getResourceCards().setGrain(18);
        manager.replaceGame(0, model);
        
        instance.execute(facade, requestBody, currentCookie);
        model = manager.getGameWithNumber(0);
        player = model.getPlayers().iterator().next();

        
        assert(player.getSettlements() == 2);
        assert(model.getMap().getSettlements().size() == 3);
        // assert that the resources were taken
        assert(player.getHand().getResourceCards().getBrick() == 0);
        assert(player.getHand().getResourceCards().getLumber() == 0);
        assert(player.getHand().getResourceCards().getWool() == 0);
        assert(player.getHand().getResourceCards().getGrain() == 0);
        // assert that the bank got these cards back.
        assert(model.getBank().getResourceCards().getBrick() == 19);
        assert(model.getBank().getResourceCards().getLumber() == 19);
        assert(model.getBank().getResourceCards().getWool() == 19);
        assert(model.getBank().getResourceCards().getGrain() == 19);
    }
    
}
