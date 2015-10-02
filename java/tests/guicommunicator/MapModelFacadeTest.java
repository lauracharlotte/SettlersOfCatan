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
import model.map.Hex;
import model.map.VertexObject;
import model.player.Player;
import model.player.PlayerIdx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
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
        Set<Hex> hexes = new HashSet<>();
        for(int i = -1; i<2; i++)
            for(int j = -1; j<2; j++)
                hexes.add(new Hex(new HexLocation(i, j), HexType.BRICK, -1));
        int l=2;
        for(int j=-2; j<=1; j++)
            hexes.add(new Hex(new HexLocation(l,j), HexType.WATER, -1));
        hexes.add(new Hex(new HexLocation(1,2), HexType.WATER, -1));
        hexes.add(new Hex(new HexLocation(1,-2), HexType.WATER, -1));
        myMap.setHexes(hexes);
        System.out.println("canPlaceRoad...");
        EdgeLocation location = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        MapModelFacade instance = new MapModelFacade();
        boolean expResult = false;
        boolean result = instance.canPlaceRoad(location);
        assertEquals(expResult, result);
        System.out.println("Empty road list passed.");
        Collection<EdgeObject> roads;
        roads = new HashSet<>();
        assertNotNull(roads);
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
        System.out.println("Cannot build on invalid location passed.");
        roads.clear();
        myMap.setRoads(roads);
        Set<VertexObject> settlements = new HashSet<>();
        settlements.add(new VertexObject(new VertexLocation(new HexLocation(0,0), VertexDirection.West).getNormalizedLocation(), otherPlayerIndex));
        myMap.setSettlements(settlements);
        location = new EdgeLocation(new HexLocation(-1, 0), EdgeDirection.South).getNormalizedLocation();
        assertFalse(instance.canPlaceRoad(location));
        System.out.println("Cannot build next to other settlement passed.");
        settlements.clear();
        settlements.add(new VertexObject(new VertexLocation(new HexLocation(0,0), VertexDirection.West).getNormalizedLocation(), index));
        myMap.setSettlements(settlements);
        assertTrue(instance.canPlaceRoad(location));
        location = new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest).getNormalizedLocation();
        assertTrue(instance.canPlaceRoad(location));
        System.out.println("Can build next to owned settlement passed.");
        roads.add(new EdgeObject(location, index));
        myMap.setRoads(roads);
        settlements.clear();
        settlements.add(new VertexObject(new VertexLocation(new HexLocation(0,0), VertexDirection.West).getNormalizedLocation(), otherPlayerIndex));
        myMap.setSettlements(settlements);
        location = new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthWest).getNormalizedLocation();
        assertFalse(instance.canPlaceRoad(location));
        location = new EdgeLocation(new HexLocation(-1,0), EdgeDirection.South).getNormalizedLocation();
        assertFalse(instance.canPlaceRoad(location));
        location = new EdgeLocation(new HexLocation(0,0), EdgeDirection.North).getNormalizedLocation();
        assertTrue(instance.canPlaceRoad(location));
        roads.clear();
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthWest).getNormalizedLocation(), index));
        myMap.setRoads(roads);
        assertFalse(instance.canPlaceRoad(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest)));
        System.out.println("Building road next to another player's settlement tests passed.");
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
        Set<Hex> hexes = new HashSet<>();
        for(int i = -1; i<1; i++)
            for(int j = -1; j<1; j++)
                hexes.add(new Hex(new HexLocation(i, j), HexType.BRICK, -1));
        int l=2;
        for(int j=-2; j<=1; j++)
            hexes.add(new Hex(new HexLocation(l,j), HexType.WATER, -1));
        hexes.add(new Hex(new HexLocation(1,2), HexType.WATER, -1));
        hexes.add(new Hex(new HexLocation(1,-2), HexType.WATER, -1));
        hexes.add(new Hex(new HexLocation(1,0), HexType.BRICK, -1));
        myMap.setHexes(hexes);
        Set<EdgeObject> roads = new HashSet<>();
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
        System.out.println("Testing normalization passed");
        myMap.setSettlements(otherVertexObjects);
        location = new VertexLocation(new HexLocation(0,0), VertexDirection.West);
        result = instance.canPlaceSettlement(location);
        assertFalse(result);
        System.out.println("Two vertex rule passed.");
        location = new VertexLocation(new HexLocation(2,0), VertexDirection.NorthEast);
        assertFalse(instance.canPlaceSettlement(location));
        location = new VertexLocation(new HexLocation(2,0), VertexDirection.East);
        assertFalse(instance.canPlaceSettlement(location));
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(1,0), EdgeDirection.SouthEast), index));
        location = new VertexLocation(new HexLocation(2,0), VertexDirection.NorthWest);
        assertTrue(instance.canPlaceSettlement(location));
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(1,-2), EdgeDirection.SouthWest), index));
        location = new VertexLocation(new HexLocation(1, -2), VertexDirection.West);
        assertTrue(instance.canPlaceSettlement(location));
        location = new VertexLocation(new HexLocation(1, -2), VertexDirection.East);
        assertFalse(instance.canPlaceSettlement(location));
        System.out.println("All settlements water tests passed.");
        System.out.println("All CanPlaceSettlement tests passed.");
    }

    /**
     * Test of canPlaceCity method, of class MapModelFacade.
     */
    @Test
    public void testCanPlaceCity()
    {
        System.out.println("canPlaceCity...");
        CatanMap myMap = this.currentMap();
        PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        PlayerIdx otherPlayerIndex = new PlayerIdx((index.getIndex() + 1) % 4);
        VertexLocation location = new VertexLocation(new HexLocation(0,0), VertexDirection.East);
        MapModelFacade instance = new MapModelFacade();
        boolean expResult = false;
        boolean result = instance.canPlaceCity(location);
        assertEquals(expResult, result);
        System.out.println("Empty settlement list passed.");
        Collection<VertexObject> otherVertexObjects = new HashSet<>();
        otherVertexObjects.add(new VertexObject(new VertexLocation(new HexLocation(0, 0), VertexDirection.East), otherPlayerIndex));
        myMap.setSettlements(otherVertexObjects);
        result = instance.canPlaceCity(location);
        System.out.println("Settlement owned by other player passed.");
        assertFalse(result);
        location = new VertexLocation(new HexLocation(0,0), VertexDirection.NorthWest);
        assertFalse(instance.canPlaceCity(location));
        System.out.println("No object there passed.");
        otherVertexObjects.add(new VertexObject(new VertexLocation(new HexLocation(0,0), VertexDirection.NorthWest), index));
        myMap.setSettlements(otherVertexObjects);
        assertTrue(instance.canPlaceCity(location));
        System.out.println("Valid city placement passed.");
        myMap.setSettlements(new ArrayList<VertexObject>());
        myMap.setCities(otherVertexObjects);
        assertFalse(instance.canPlaceCity(location));
        System.out.println("Already city there test passed.");
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("All canPlaceCity tests passed.");
    }

    /**
     * Test of canPlaceRobber method, of class MapModelFacade.
     */
    @Test
    public void testCanPlaceRobber()
    {
        System.out.println("canPlaceRobber...");
        Random rand = new Random();
        int x, y;
        x = rand.nextInt(11) - 5;
        y = rand.nextInt(11) - 5;
        CatanMap myMap = this.currentMap();
        myMap.setRobber(new HexLocation(x, y));
        MapModelFacade instance = new MapModelFacade();
        
        Collection<Hex> hexes = new HashSet<Hex>();
        
        for(int i = -5; i<=5; i++)
            for(int j = -5; j<=5; j++)
                hexes.add(new Hex(new HexLocation(i, j), HexType.SHEEP, -1));
        
        myMap.setHexes(hexes);
        
        for(int i = -5; i<=5; i++)
            for(int j = -5; j<=5; j++)
                assertTrue(instance.canPlaceRobber(new HexLocation(i, j)) || (i == x && j == y));
        System.out.println("All canPlaceRobber tests passed.");
    }
    
    private CatanMap currentMap()
    {
        return ClientModelSupplier.getInstance().getModel().getMap();
    }
}
