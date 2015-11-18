/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.SendChatRequest;
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
import server.facade.IMovesFacade;
import server.facade.MovesFacade;
import server.model.GameManager;
import shared.definitions.CatanColor;
import shared.locations.HexLocation;

/**
 *
 * @author Michael
 */
public class SendChatCommandTest
{
    
    public SendChatCommandTest()
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
        System.out.println("execute send chat");
        GameManager manager= new GameManager();
        manager.addNewGame(new ClientModel(true, false, true, "name"));
        IMovesFacade facade = new MovesFacade(manager);
        ClientModel currentModel = manager.getGameWithNumber(0);
        Collection<Player> players = new ArrayList<>();
        DevelopmentCards emptyDevCards = new DevelopmentCards(1, 1, 1, 1, 1);
        ResourceCards ResCards = new ResourceCards(3, 3, 3, 3, 3);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 11, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        newPlayer.setSoldiers(2);
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
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.playing);
        manager.replaceGame(0, currentModel);
        SendChatRequest req = new SendChatRequest(new PlayerIdx(0), "request");
        String request = req.serialize();
        ICommand sendChatCmd = new SendChatCommand();
        Cookie currentCookie = new Cookie();
        currentCookie.setGameNumber(0);
        User newUser = new User("bobby", "bobby");
        newUser.setPlayerId(0);
        currentCookie.setUser(newUser);
        sendChatCmd.execute(facade, request, currentCookie);
        assert(1 == manager.getGameWithNumber(0).getChat().getLines().size());
        sendChatCmd.execute(facade, request, currentCookie);
        assert(manager.getGameWithNumber(0).getChat().getLines().size() == 2);
    }
    
}
