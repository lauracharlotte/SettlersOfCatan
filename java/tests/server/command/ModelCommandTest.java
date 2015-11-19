/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import server.facade.IModelFacade;

//Imports for test
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.cards.TradeOffer;
import model.map.CatanMap;
import model.map.EdgeObject;
import model.map.Hex;
import model.map.Port;
import model.map.VertexObject;
import model.messages.MessageLine;
import model.messages.MessageList;
import model.player.NullablePlayerIdx;
import model.player.Player;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import model.player.TurnTracker;
import server.facade.MockGameFacade;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import clientcommunicator.modelserverfacade.JSONParser;
import clientcommunicator.modelserverfacade.JSONSerializer;
import org.json.JSONException;


/**
 *
 * @author Michael
 */
public class ModelCommandTest
{
    
    public ModelCommandTest()
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
    public void testExecute() throws Exception //does this work?
    {
        System.out.println("execute");
        IModelFacade facade = null;
        String requestBody = "";
        Cookie currentCookie = null;
        ModelCommand instance = new ModelCommand();
        String expResult = "";
        String result = instance.execute(facade, requestBody, currentCookie);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
	
	    @Test
    public void testExecute2() throws Exception
    {
		System.out.println("Big test of Command Execute");
		
		MockGameFacade mockmock = null;
		
		ClientModel testClientModel = new ClientModel();
		
		ResourceCards recCards = new ResourceCards(3,3,3,3,3);
		DevelopmentCards devCards = new DevelopmentCards(3,3,3,3,3);
		DevelopmentCards newDevCards = new DevelopmentCards(1,0,1,0,0);
		Hand playHand = new Hand(recCards, devCards);
		
		Player player1 = new Player(0, CatanColor.RED, false, 0, "Laura1", new PlayerIdx(0), false, newDevCards, 1, 1, 1, 1, 1, playHand);
		Player player2 = new Player(0, CatanColor.BLUE, false, 0, "Laura2", new PlayerIdx(1), false, newDevCards, 2, 1, 1, 1, 1, playHand);
		Player player3 = new Player(0, CatanColor.PURPLE, false, 0, "Laura3", new PlayerIdx(2), false, newDevCards, 3, 1, 1, 1, 1, playHand);
		Player player4 = new Player(0, CatanColor.BROWN, false, 0, "Laura4", new PlayerIdx(3), false, newDevCards, 4, 1, 1, 1, 1, playHand);

		Collection<Player> playerCollect = new ArrayList<>();
		playerCollect.add(player1);
		playerCollect.add(player2);
		playerCollect.add(player3);
		playerCollect.add(player4);
		testClientModel.setPlayers(playerCollect);
		
		ResourceCards recCardsGame = new ResourceCards(10,10,10,10,10);
		DevelopmentCards devCardsGame = new DevelopmentCards(2,2,2,2,2);
		Hand playHandGame = new Hand(recCards, devCards);
		testClientModel.setBank(playHandGame);
		
		Collection<MessageLine> lines = new ArrayList<MessageLine>();
		lines.add(new MessageLine("Hello, how are you?", "Laura"));
		MessageList chat = new MessageList(lines);
		
		
		testClientModel.setChat(chat);
		testClientModel.setLog(chat);
		
		//trying to do hexes
        List<Hex> hexes = new ArrayList<>();
        for(int i = -1; i<1; i++)
            for(int j = -1; j<1; j++)
                hexes.add(new Hex(new HexLocation(i, j), HexType.BRICK, -1));
        int l=2;
        for(int j=-2; j<=1; j++)
            hexes.add(new Hex(new HexLocation(l,j), HexType.WATER, -1));
        hexes.add(new Hex(new HexLocation(1,2), HexType.WATER, -1));
        hexes.add(new Hex(new HexLocation(1,-2), HexType.WATER, -1));
        hexes.add(new Hex(new HexLocation(1,0), HexType.BRICK, -1));
		//
		
        //trying to do ports
        List<Port> ports = new ArrayList<>();
        for(int i = -1; i<1; i++)
            for(int j = -1; j<1; j++)
            	ports.add(new Port(new HexLocation(i, j), ResourceType.BRICK, EdgeDirection.North, -1));
        int h=2;
        for(int j=-2; j<=1; j++)
        	ports.add(new Port(new HexLocation(h,j), ResourceType.WHEAT, EdgeDirection.North, -1));
        ports.add(new Port(new HexLocation(1,2), ResourceType.WHEAT, EdgeDirection.North, -1));
        ports.add(new Port(new HexLocation(1,-2), ResourceType.WHEAT, EdgeDirection.North, -1));
        ports.add(new Port(new HexLocation(1,0), ResourceType.BRICK, EdgeDirection.North, -1));
        //
        
        //trying to do roads
        Collection<EdgeObject> roads;
        roads = new ArrayList<>();
        PlayerIdx index = new PlayerIdx(2);
        PlayerIdx otherPlayerIndex = new PlayerIdx((index.getIndex() + 1) % 4);
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.North), otherPlayerIndex));
        //
        
        //trying to do city/settlement
        PlayerIdx aPlayerIndex = new PlayerIdx((index.getIndex() + 1) % 4);
        VertexLocation location = new VertexLocation(new HexLocation(0,0), VertexDirection.East);
        Collection<VertexObject> otherVertexObjects = new ArrayList<>();
        otherVertexObjects.add(new VertexObject(new VertexLocation(new HexLocation(0, 0), VertexDirection.East), otherPlayerIndex));
        otherVertexObjects.add(new VertexObject(new VertexLocation(new HexLocation(0,0), VertexDirection.NorthWest), index));
        //
        
		CatanMap map = new CatanMap();
		map.setHexes(hexes);
		map.setPorts(ports);
		map.setRoads(roads);
		map.setCities(otherVertexObjects);
		map.setSettlements(otherVertexObjects);
		map.setRobber(new HexLocation(1,2));
		testClientModel.setMap(map);
		
		TradeOffer tradeOffer = new TradeOffer(new PlayerIdx(1), new PlayerIdx(2), recCardsGame);
		testClientModel.setTradeOffer(tradeOffer);
		
		TurnTracker turnTracker = new TurnTracker(new PlayerIdx(1), TurnStatusEnumeration.firstround, new NullablePlayerIdx(-1), new NullablePlayerIdx(-1));
		testClientModel.setTurnTracker(turnTracker);
		
		int version = 0;
		testClientModel.setVersion(version);
		
		NullablePlayerIdx winner = new NullablePlayerIdx(-1);
		testClientModel.setWinner(winner);
		
		ClientModel testModel = testClientModel;
		
		JSONSerializer testSerializer = new JSONSerializer();
		String answerSoFar = testSerializer.SerializeModel(testModel);
		//System.out.println(answerSoFar);
		
		JSONParser testParser = new JSONParser();
		ClientModel parsedModelTest  = new ClientModel();
		try {
			parsedModelTest = testParser.fromJSONToModel(answerSoFar);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("ParsedModelTest");
		//System.out.println(parsedModelTest.toString());
		
		//System.out.println("Before Parsed");
		//System.out.println(testClientModel.toString());
		 
		//System.out.println(answerSoFar);
		//System.out.println(testSerializer.SerializeModel(parsedModelTest));
		 
		assertEquals(testModel, parsedModelTest);
		//System.out.println("Finished big test of Model Command Execute");
    }
    
}
