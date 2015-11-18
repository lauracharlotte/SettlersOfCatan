/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.FinishTurnRequest;
import clientcommunicator.operations.MaritimeTradeRequest;
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
import shared.definitions.ResourceType;

/**
 *
 * @author Michael
 */
public class MaritimeTradeCommandTest
{
    
    public MaritimeTradeCommandTest()
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
        System.out.println("execute maritime trade");
        GameManager manager = new GameManager();
        IModelFacade facade = new MovesFacade(manager);
        MaritimeTradeRequest req = new MaritimeTradeRequest(new PlayerIdx(0), 3, ResourceType.SHEEP, ResourceType.BRICK);
        manager.addNewGame(new ClientModel(true, false, true, "name"));
        ClientModel currentModel = manager.getGameWithNumber(0);
        String requestBody = req.serialize();
        Cookie currentCookie = new Cookie();
        currentCookie.setGameNumber(0);
        User us = new User("bobby", "bobby");
        us.setPlayerId(0);
        currentCookie.setUser(us);
        Collection<Player> players = new ArrayList<Player>();
        DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
        ResourceCards ResCards = new ResourceCards(0, 3, 3, 3, 3);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 11, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(0));
        ResCards = new ResourceCards(0, 3, 3, 3, 3);
        newPlayer = new Player(1, CatanColor.PURPLE, false, 0, "bobby2", null, false, 
                        emptyDevCards, 1, 11, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(1));
        currentModel.setPlayers(players);
        currentModel.getTurnTracker().setCurrentTurn(new PlayerIdx(0));
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.playing);
        manager.replaceGame(0, currentModel);
        MaritimeTradeCommand command = new MaritimeTradeCommand();
        command.execute(facade, requestBody, currentCookie);
        assert(manager.getGameWithNumber(0).getPlayers().iterator().next().getHand().getResourceCards().getBrick() == 1);
        assert(manager.getGameWithNumber(0).getPlayers().iterator().next().getHand().getResourceCards().getWool() == 0);
        command.execute(facade, requestBody, currentCookie);
        assert(manager.getGameWithNumber(0).getPlayers().iterator().next().getHand().getResourceCards().getWool() == 0);
    }
    
}
