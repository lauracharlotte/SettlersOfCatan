/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.AcceptTradeRequest;
import clientcommunicator.operations.RobPlayerRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.cards.TradeOffer;
import model.player.NullablePlayerIdx;
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
import shared.locations.HexLocation;

/**
 *
 * @author Michael
 */
public class RobPlayerCommandTest
{
    
    public RobPlayerCommandTest()
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
        DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
        ResourceCards ResCards = new ResourceCards(3, 3, 3, 3, 3);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 11, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(0));
        ResCards = new ResourceCards(3, 3, 3, 3, 3);
        newPlayer = new Player(1, CatanColor.PURPLE, false, 0, "bobby2", null, false, 
                        emptyDevCards, 1, 11, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        players.add(newPlayer);
        newPlayer.setPlayerIndex(new PlayerIdx(1));
        currentModel.setPlayers(players);
        if(currentModel.getMap().getRobber().equals(new HexLocation(0,0)))
            currentModel.getMap().setRobber(new HexLocation(1,0));
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.robbing);
        manager.replaceGame(0, currentModel);
        IModelFacade facade = new MovesFacade(manager);
        RobPlayerRequest req = new RobPlayerRequest(new PlayerIdx(0), new NullablePlayerIdx(1), new HexLocation(0,0));
        String requestBody = req.serialize();
        Cookie currentCookie = new Cookie();
        currentCookie.setGameNumber(0);
        User newUser = new User("bobby", "bobby");
        newUser.setPlayerId(0);
        currentCookie.setUser(newUser);
        RobPlayerCommand cmd = new RobPlayerCommand();
        cmd.execute(facade, requestBody, currentCookie);
        assert(manager.getGameWithNumber(0).getMap().getRobber().getX() == 0);
        assert(manager.getGameWithNumber(0).getMap().getRobber().getY() == 0);
        Iterator itr = manager.getGameWithNumber(0).getPlayers().iterator();
        itr.next();
        Player robbedPlayer = (Player) itr.next();
        assert(!robbedPlayer.getHand().getResourceCards().equals(new ResourceCards(3, 3, 3, 3, 3)));
    }
    
}
