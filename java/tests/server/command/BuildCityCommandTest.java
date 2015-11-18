/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.BuildCityRequest;
import clientcommunicator.operations.IJSONSerializable;
import java.util.ArrayList;
import java.util.Collection;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.map.CatanMap;
import model.map.EdgeObject;
import model.map.VertexObject;
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
public class BuildCityCommandTest
{
    private static GameManager manager;
    private static Cookie currentCookie;
    private static IModelFacade facade;
    
    public BuildCityCommandTest()
    {
        manager = new GameManager();
        manager.addNewGame(new ClientModel(true, false, true, "name"));
        ClientModel currentModel = manager.getGameWithNumber(0);
        Collection<Player> players = new ArrayList<>();
        DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
        ResourceCards ResCards = new ResourceCards(0, 2, 0, 3, 0);
        Player newPlayer = new Player(4, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 12, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(0));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(1));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(2));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(3));
        currentModel.setPlayers(players);
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.playing);
        currentModel.getTurnTracker().setCurrentTurn(new PlayerIdx(0));
        
        Collection<EdgeObject> roads = new ArrayList<>();
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North), newPlayer.getPlayerIndex()));
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.South), newPlayer.getPlayerIndex()));
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.South), newPlayer.getPlayerIndex()));
        
        Collection<VertexObject> settlements = new ArrayList<>();
        settlements.add(new VertexObject(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthEast), newPlayer.getPlayerIndex()));
        settlements.add(new VertexObject(new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest), newPlayer.getPlayerIndex()));
        
        CatanMap map = currentModel.getMap();
        map.setRoads(roads);
        map.setSettlements(settlements);
        currentModel.setMap(map);
        currentModel.getBank().getResourceCards().setOre(16);
        currentModel.getBank().getResourceCards().setGrain(17);
        manager.replaceGame(0, currentModel);
        
        
        facade = new MovesFacade(manager);
        currentCookie = new Cookie();
        currentCookie.setGameNumber(0);
        currentCookie.setUser(new User("bobby", "bobby"));
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
        VertexLocation location = new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast);
        IJSONSerializable req = new BuildCityRequest(new PlayerIdx(0), location);
        String requestBody = req.serialize();
        BuildCityCommand instance = new BuildCityCommand();
        instance.execute(facade, requestBody, currentCookie);
        ClientModel model = manager.getGameWithNumber(0);
        Player player = model.getPlayers().iterator().next();
        
        assert(player.getCities() == 4);
        assert(model.getMap().getCities().isEmpty());
        
        // Test valid building settlement
        location = new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthEast);
        req = new BuildCityRequest(new PlayerIdx(0), location);
        requestBody = req.serialize();
        instance = new BuildCityCommand();
        instance.execute(facade, requestBody, currentCookie);
        model = manager.getGameWithNumber(0);
        player = model.getPlayers().iterator().next();
        
        assert(player.getCities() == 3);
        assert(model.getMap().getCities().size() == 1);
        // assert that the resources were taken
        assert(player.getHand().getResourceCards().getGrain() == 0);
        assert(player.getHand().getResourceCards().getOre() == 0);
        // assert that the bank got these cards back.
        assert(model.getBank().getResourceCards().getGrain() == 19);
        assert(model.getBank().getResourceCards().getOre() == 19);
        
        // Test that you can't build when you don't have the resources.
        location = new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest);
        req = new BuildCityRequest(new PlayerIdx(0), location);
        requestBody = req.serialize();
        instance = new BuildCityCommand();
        instance.execute(facade, requestBody, currentCookie);
        model = manager.getGameWithNumber(0);
        player = model.getPlayers().iterator().next();
        
        assert(player.getCities() == 3);
        assert(model.getMap().getCities().size() == 1);
    }
    
}
