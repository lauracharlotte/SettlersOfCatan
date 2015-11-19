/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.RoadBuildingCardRequest;
import java.util.ArrayList;
import java.util.Collection;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
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

/**
 *
 * @author Michael
 */
public class RoadBuildingCommandTest
{
    
    public RoadBuildingCommandTest()
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
        System.out.println("Execute road building");
        GameManager manager= new GameManager();
        manager.addNewGame(new ClientModel(true, false, true, "name"));
        ClientModel currentModel = manager.getGameWithNumber(0);
        Collection<Player> players = new ArrayList<>();
        DevelopmentCards emptyDevCards = new DevelopmentCards(1, 1, 1, 1, 1);
        ResourceCards ResCards = new ResourceCards(3, 3, 3, 3, 3);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 11, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        newPlayer.setPlayerIndex(new PlayerIdx(0));
        players.add(newPlayer);
        ResCards = new ResourceCards(3, 3, 3, 3, 3);
        newPlayer = new Player(1, CatanColor.PURPLE, false, 0, "bobby2", null, false, 
                        emptyDevCards, 1, 11, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        newPlayer.setPlayerIndex(new PlayerIdx(1));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(2));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(3));
        players.add(newPlayer);
        currentModel.setPlayers(players);
        if(currentModel.getMap().getRobber().equals(new HexLocation(0,0)))
            currentModel.getMap().setRobber(new HexLocation(1,0));
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.robbing);
        currentModel.getMap().getRoads().add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast), new PlayerIdx(0)));
        currentModel.getMap().getRoads().add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthEast), new PlayerIdx(0)));
        manager.replaceGame(0, currentModel);
        IModelFacade facade = new MovesFacade(manager);
        RoadBuildingCardRequest req= new RoadBuildingCardRequest(new PlayerIdx(0), 
        new EdgeLocation(new HexLocation(0,0), EdgeDirection.North), 
                new EdgeLocation(new HexLocation(0,0), EdgeDirection.South));
        String body = req.serialize();
        ICommand cmd = new RoadBuildingCommand();
        Cookie currentCookie = new Cookie();
        User us = new User("bobby", "bobby");
        us.setPlayerId(0);
        currentCookie.setUser(us);
        currentCookie.setGameNumber(0);
        cmd.execute(facade, body, currentCookie);
        assert(manager.getGameWithNumber(0).getTurnTracker().getLongestRoad().getIndex() == 0);
    }
    
}
