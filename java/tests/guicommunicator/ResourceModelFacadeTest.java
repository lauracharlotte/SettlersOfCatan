/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guicommunicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import model.ClientModel;
import model.ClientModelSupplier;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.player.Player;
import model.player.PlayerIdx;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shared.definitions.CatanColor;

/**
 *
 * @author Michael
 */
public class ResourceModelFacadeTest
{
    
    private static int clientPlayerID = 55;
    
    public ResourceModelFacadeTest()
    {
    }
    
    @Before
    public void setUp()
    {
        ClientModelSupplier.getInstance().setClientPlayerID(clientPlayerID);
        Collection<Player> players = new ArrayList<Player>();
        for(int i=0; i<4; i++)
        {
            Random rand = new Random();
            int cities = rand.nextInt(5);
            CatanColor playerColor = CatanColor.values()[rand.nextInt(CatanColor.values().length)];
            boolean discarded = rand.nextBoolean();
            int monuments = rand.nextInt(3);
            String name = Double.toHexString(rand.nextDouble());
            PlayerIdx playerIndex = new PlayerIdx(i);
            boolean playedDevCard = rand.nextBoolean();
            int playerId = rand.nextInt(clientPlayerID);
            int roads = rand.nextInt(15);
            int settlements = rand.nextInt(4);
            int soldiers = rand.nextInt(3);
            int victoryPoints = rand.nextInt(9);
            ResourceCards resources = new ResourceCards(0, 0, 0, 0, 0);
            DevelopmentCards devCards = new DevelopmentCards(0, 0, 0, 0, 0);
            Hand hand = new Hand(resources, devCards);
            Player newPlayer = new Player(cities, playerColor, discarded, monuments, name, playerIndex, playedDevCard, playerId, roads, settlements, soldiers, victoryPoints, hand);
            players.add(newPlayer);
        }
        ClientModelSupplier.getInstance().setModel(new ClientModel());
        ClientModelSupplier.getInstance().getModel().setPlayers(players);
    }
    
    
    /**
     * Test of canBuildCity method, of class ResourceModelFacade.
     */
    @Test
    public void testCanBuildCity()
    {
        System.out.println("Setting up player for canBuildCityTest...");
        Iterator<Player> players = ClientModelSupplier.getInstance().getModel().getPlayers().iterator();
        Random rand = new Random();
        int index = rand.nextInt(4);
        for(int i=0; i<index; i++)
            players.next();
        Player currentPlayer = players.next();
        currentPlayer.setPlayerId(clientPlayerID);
        currentPlayer.setSettlements(3);
        currentPlayer.setCities(4);
        currentPlayer.setHand(new Hand(new ResourceCards(5, 0, 5, 0, 5), new DevelopmentCards(0,0,0,0,0)));
        ResourceModelFacade instance = new ResourceModelFacade();
        System.out.println("canBuildCity test starting...");
        boolean expResult = false;
        boolean result = instance.canBuildCity();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(5, 2, 5, 0, 5), new DevelopmentCards(0,0,0,0,0)));
        expResult = false;
        result = instance.canBuildCity();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(0, 0, 0, 3, 0), new DevelopmentCards(0,0,0,0,0)));
        expResult = false;
        result = instance.canBuildCity();
        assertEquals(expResult, result);
        System.out.println("ResourceCards lacking test passed");
        currentPlayer.setHand(new Hand(new ResourceCards(0, 2, 0, 3, 0), new DevelopmentCards(0,0,0,0,0)));
        expResult = true;
        result = instance.canBuildCity();
        assertEquals(expResult, result);
        System.out.println("Test build city when resources are enough passed.");
        currentPlayer.setCities(0);
        expResult = false;
        result = instance.canBuildCity();
        assertEquals(expResult, result);
        System.out.println("Test when out of cities passed");
        currentPlayer.setCities(2);
        currentPlayer.setSettlements(5);
        expResult = false;
        result = instance.canBuildCity();
        assertEquals(expResult, result);
        System.out.println("Test when no settlements on map passed.");
        System.out.println("canBuildCity test ended successfully");
    }

    /**
     * Test of canBuildRoad method, of class ResourceModelFacade.
     */
    @Test
    public void testCanBuildRoad()
    {
        System.out.println("Setting up player for canBuildRoad Test...");
        Iterator<Player> players = ClientModelSupplier.getInstance().getModel().getPlayers().iterator();
        Random rand = new Random();
        int index = rand.nextInt(4);
        for(int i=0; i<index; i++)
            players.next();
        Player currentPlayer = players.next();
        currentPlayer.setRoads(15);
        currentPlayer.setPlayerId(clientPlayerID);
        currentPlayer.setHand(new Hand(new ResourceCards(0, 5, 0, 5, 5), new DevelopmentCards(0,0,0,0,0)));
        ResourceModelFacade instance = new ResourceModelFacade();
        System.out.println("canBuildRoad test starting...");
        boolean expResult = false;
        boolean result = instance.canBuildRoad();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(1, 5, 0, 5, 5), new DevelopmentCards(0,0,0,0,0)));
        expResult = false;
        result = instance.canBuildRoad();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(0, 5, 1, 5, 5), new DevelopmentCards(0,0,0,0,0)));
        expResult = false;
        result = instance.canBuildRoad();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(1, 5, 1, 5, 5), new DevelopmentCards(0,0,0,0,0)));
        expResult = true;
        result = instance.canBuildRoad();
        assertEquals(expResult, result);
        System.out.println("All resource tests for canBuildRoad completed successfully.");
        currentPlayer.setRoads(0);
        // TODO review the generated test code and remove the default call to fail.
        expResult = false;
        result = instance.canBuildRoad();
        assertEquals(expResult, result);
        System.out.println("Out of roads test completed successfully.");
        System.out.println("CanBuildRoad test ended successfully.");
    }

    /**
     * Test of canBuildSettlement method, of class ResourceModelFacade.
     */
    @Test
    public void testCanBuildSettlement()
    {
        System.out.println("Setting up player for canBuildSettlement Test...");
        Iterator<Player> players = ClientModelSupplier.getInstance().getModel().getPlayers().iterator();
        Random rand = new Random();
        int index = rand.nextInt(4);
        for(int i=0; i<index; i++)
            players.next();
        Player currentPlayer = players.next();
        currentPlayer.setPlayerId(clientPlayerID);
        currentPlayer.setSettlements(3);
        currentPlayer.setCities(4);
        currentPlayer.setHand(new Hand(new ResourceCards(5, 0, 5, 0, 5), new DevelopmentCards(0,0,0,0,0)));
        ResourceModelFacade instance = new ResourceModelFacade();
        System.out.println("canBuildSettlement test starting...");
        boolean expResult = false;
        boolean result = instance.canBuildSettlement();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(5, 1, 5, 0, 5), new DevelopmentCards(0,0,0,0,0)));
        expResult = true;
        result = instance.canBuildSettlement();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(5, 1, 5, 0, 0), new DevelopmentCards(0,0,0,0,0)));
        expResult = false;
        result = instance.canBuildSettlement();
        assertEquals(expResult, result);
        System.out.println("Resource cost testing for settlement building completed successfully.");
        currentPlayer.setSettlements(0);
        expResult = false;
        result = instance.canBuildSettlement();
        assertEquals(expResult, result);
        System.out.println("Test for settlement building when player is out of settlements passed");
        System.out.println("All canBuildSettlement tests from resource standpoint passed.");
    }

    /**
     * Test of canBuyDevCard method, of class ResourceModelFacade.
     */
    @Test
    public void testCanBuyDevCard()
    {
        System.out.println("canBuyDevCard setting up player");
         Iterator<Player> players = ClientModelSupplier.getInstance().getModel().getPlayers().iterator();
        Random rand = new Random();
        int index = rand.nextInt(4);
        for(int i=0; i<index; i++)
            players.next();
        Player currentPlayer = players.next();
        currentPlayer.setPlayerId(clientPlayerID);
        System.out.println("Starting devCardBuilding test");
        currentPlayer.setHand(new Hand(new ResourceCards(5, 1, 5, 0, 0), new DevelopmentCards(0,0,0,0,0)));
        ResourceModelFacade instance = new ResourceModelFacade();
        boolean expResult = false;
        boolean result = instance.canBuyDevCard();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(5, 1, 5, 0, 1), new DevelopmentCards(0,0,0,0,0)));
        expResult = false;
        result = instance.canBuyDevCard();
        assertEquals(expResult, result);
        currentPlayer.setHand(new Hand(new ResourceCards(0, 4, 0, 1, 3), new DevelopmentCards(0,0,0,0,0)));
        expResult = true;
        result = instance.canBuyDevCard();
        assertEquals(expResult, result);
        System.out.println("All devCardBuilding tests from resource standpoint finished");
    }
    
}
