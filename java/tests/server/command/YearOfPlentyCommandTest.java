/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.RobPlayerRequest;
import clientcommunicator.operations.YearOfPlentyRequest;
import java.util.ArrayList;
import java.util.Collection;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
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
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

/**
 *
 * @author Michael
 */
public class YearOfPlentyCommandTest
{
    
    public YearOfPlentyCommandTest()
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
        DevelopmentCards emptyDevCards = new DevelopmentCards(1, 1, 1, 1, 1);
        ResourceCards ResCards = new ResourceCards(3, 3, 3, 3, 3);
        Player newPlayer = new Player(1, CatanColor.PUCE, false, 0, "bobby", null, false, 
                        emptyDevCards, 0, 11, 3, 0, 0, new Hand(ResCards, emptyDevCards));
        newPlayer.setSoldiers(2);
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
        currentModel.getTurnTracker().setStatus(TurnStatusEnumeration.playing);
        manager.replaceGame(0, currentModel);
        IModelFacade facade = new MovesFacade(manager);
        YearOfPlentyRequest req = new YearOfPlentyRequest(new PlayerIdx(0), ResourceType.BRICK, ResourceType.ORE);
        String requestBody = req.serialize();
        Cookie currentCookie = new Cookie();
        currentCookie.setGameNumber(0);
        User newUser = new User("bobby", "bobby");
        newUser.setPlayerId(0);
        currentCookie.setUser(newUser);
        ICommand cmd = new YearofPlentyCommand();
        cmd.execute(facade, requestBody, currentCookie);
        assert(manager.getGameWithNumber(0).getPlayers().iterator().next().getHand().getResourceCards().getBrick() == 4);
        assert(manager.getGameWithNumber(0).getPlayers().iterator().next().getHand().getResourceCards().getOre() == 4);
    }
    
}
