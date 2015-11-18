/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.BuyDevCardRequest;
import clientcommunicator.operations.IJSONSerializable;
import clientcommunicator.operations.MonopolyRequest;
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
public class MonopolyCommandTest
{
    
    public MonopolyCommandTest()
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
        GameManager manager= new GameManager();
        manager.addNewGame(new ClientModel(true, false, true, "name"));
        ClientModel currentModel = manager.getGameWithNumber(0);
        Collection<Player> players = new ArrayList<>();
        DevelopmentCards devCards = new DevelopmentCards(1, 1, 1, 1, 1);
        ResourceCards ResCards = new ResourceCards(3, 3, 3, 3, 3);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        devCards, 0, 11, 3, 0, 0, new Hand(ResCards, devCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(0));
        ResCards = new ResourceCards(3, 3, 3, 3, 3);
        Player otherPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby2", null, false, 
                        devCards, 0, 11, 3, 0, 0, new Hand(ResCards, devCards)); 
        otherPlayer.setPlayerIndex(new PlayerIdx(1));
        players.add(otherPlayer);
        currentModel.setPlayers(players);
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.playing);
        currentModel.getTurnTracker().setCurrentTurn(new PlayerIdx(0));
        manager.replaceGame(0, currentModel);
        IModelFacade facade = new MovesFacade(manager);
        Cookie currentCookie = new Cookie();
        currentCookie.setGameNumber(0);
        User newUser = new User("bobby", "bobby");
        newUser.setPlayerId(0);
        currentCookie.setUser(newUser);
        IJSONSerializable req = new MonopolyRequest(new PlayerIdx(0), ResourceType.ORE);
        String requestString = req.serialize();
        ICommand cmd = new MonopolyCommand();
        cmd.execute(facade, requestString, currentCookie);
        Player p = (Player)manager.getGameWithNumber(0).getPlayers().toArray()[1];
        assert(p.getHand().getResourceCards().getOre() == 0);
        p = (Player)manager.getGameWithNumber(0).getPlayers().toArray()[0];
        assert(p.getHand().getResourceCards().getOre() == 6);
    }
    
}
