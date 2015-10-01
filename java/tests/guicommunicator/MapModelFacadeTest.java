/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guicommunicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import model.ClientModel;
import model.ClientModelSupplier;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.map.CatanMap;
import model.map.EdgeObject;
import model.map.VertexObject;
import model.player.Player;
import model.player.PlayerIdx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class MapModelFacadeTest
{
    
    public MapModelFacadeTest()
    {
    }
    
    private static final int clientPlayerID = 55;
    
    @Before
    public void setUp()
    {
        ClientModelSupplier.getInstance().setModel(new ClientModel());
        ClientModelSupplier.getInstance().getModel().setMap(new CatanMap());
        ClientModelSupplier.getInstance().setClientPlayerID(clientPlayerID);
        Collection<Player> players = new ArrayList<>();
        Random rand = new Random();
        int index = rand.nextInt(4);
        for(int i=0; i<4; i++)
        {
            int cities = rand.nextInt(5);
            CatanColor playerColor = CatanColor.values()[rand.nextInt(CatanColor.values().length)];
            boolean discarded = rand.nextBoolean();
            int monuments = rand.nextInt(3);
            String name = Double.toHexString(rand.nextDouble());
            PlayerIdx playerIndex = new PlayerIdx(i);
            boolean playedDevCard = rand.nextBoolean();
            int playerId = rand.nextInt(clientPlayerID);
            if(i == index)
                playerId = clientPlayerID;
            int roads = rand.nextInt(15);
            int settlements = rand.nextInt(4);
            int soldiers = rand.nextInt(3);
            int victoryPoints = rand.nextInt(9);
            ResourceCards resources = new ResourceCards(0, 0, 0, 0, 0);
            DevelopmentCards devCards = new DevelopmentCards(0, 0, 0, 0, 0);
            Hand hand = new Hand(resources, devCards);
            Player newPlayer = new Player(cities, playerColor, discarded, monuments, name, playerIndex, playedDevCard, devCards, playerId, roads, settlements, soldiers, victoryPoints, hand);
            players.add(newPlayer);
        }
        ClientModelSupplier.getInstance().getModel().setPlayers(players);
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of canPlaceRoad method, of class MapModelFacade.
     */
    @Test
    public void testCanPlaceRoad()
    {
        CatanMap myMap = this.currentMap();
        System.out.println("canPlaceRoad...");
        EdgeLocation location = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        MapModelFacade instance = new MapModelFacade();
        boolean expResult = false;
        boolean result = instance.canPlaceRoad(location);
        assertEquals(expResult, result);
        System.out.println("Empty road list passed.");
        Collection<EdgeObject> roads;
        roads = new HashSet<>();
        PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        PlayerIdx otherPlayerIndex = new PlayerIdx((index.getIndex() + 1) % 4);
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.North), otherPlayerIndex));
        myMap.setRoads(roads);
        result = instance.canPlaceRoad(location);
        assertEquals(expResult, result);
        System.out.println("No adjacent road passed.");
        location = new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast);
        result = instance.canPlaceRoad(location);
        assertEquals(expResult, result);
        System.out.println("Road adjacent owned by another player passed.");
        location = new EdgeLocation(new HexLocation(0, -1), EdgeDirection.South);
        result = instance.canPlaceRoad(location);
        assertEquals(expResult, result);
        System.out.println("Road already at location passed.");
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0, -1), EdgeDirection.SouthWest), index));
        myMap.setRoads(roads);
        location = new EdgeLocation(new HexLocation(-1,0), EdgeDirection.SouthEast);
        result = instance.canPlaceRoad(location);
        assertTrue(result);
        System.out.println("Can build on valid location passed.");
        location = new EdgeLocation(new HexLocation(1,-1), EdgeDirection.SouthWest);
        result = instance.canPlaceRoad(location);
        assertFalse(result);
        System.out.println("All canPlaceRoad tests passed.");
    }

    /**
     * Test of canPlaceSettlement method, of class MapModelFacade.
     */
    @Test
    public void testCanPlaceSettlement()
    {
        System.out.println("canPlaceSettlement...");
        CatanMap myMap = this.currentMap();
        Set<EdgeObject> roads = new HashSet<EdgeObject>();
        PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        PlayerIdx otherPlayerIndex = new PlayerIdx((index.getIndex() + 1) % 4);
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.North), otherPlayerIndex));
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.South).getNormalizedLocation(), index));
        myMap.setRoads(roads);
        VertexLocation location = new VertexLocation(new HexLocation(0,0), VertexDirection.SouthEast);
        MapModelFacade instance = new MapModelFacade();
        boolean expResult = true;
        boolean result = instance.canPlaceSettlement(location);
        assertEquals(expResult, result);
        location = new VertexLocation(new HexLocation(0,0), VertexDirection.SouthWest);
        result = instance.canPlaceSettlement(location);
        assertEquals(expResult, result);
        System.out.println("Testing both sides of road passed");
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthWest), index));
        myMap.setRoads(roads);
        result = instance.canPlaceSettlement(location);
        assertEquals(expResult, result);
        System.out.println("Placing in middle of roads passed.");
        Collection<VertexObject> otherVertexObjects = new HashSet<>();
        otherVertexObjects.add(new VertexObject(new VertexLocation(new HexLocation(-1, 1), VertexDirection.NorthWest), index));
        result = instance.canPlaceSettlement(location);
        assertTrue(result);
        location = new VertexLocation(new HexLocation(0,0), VertexDirection.West);
        result = instance.canPlaceSettlement(location);
        assertFalse(result);
        System.out.println("Two vertex rule passed.");
        System.out.println("All CanPlaceSettlement tests passed.");
    }

    /**
     * Test of canPlaceCity method, of class MapModelFacade.
     */
    @Test
    public void testCanPlaceCity()
    {
        System.out.println("canPlaceCity");
        VertexLocation location = null;
        MapModelFacade instance = new MapModelFacade();
        boolean expResult = false;
        boolean result = instance.canPlaceCity(location);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canPlaceRobber method, of class MapModelFacade.
     */
    @Test
    public void testCanPlaceRobber()
    {
        System.out.println("canPlaceRobber");
        HexLocation location = null;
        MapModelFacade instance = new MapModelFacade();
        boolean expResult = false;
        boolean result = instance.canPlaceRobber(location);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    private CatanMap currentMap()
    {
        return ClientModelSupplier.getInstance().getModel().getMap();
    }
}
