import static org.junit.Assert.*;

import org.junit.Test;

import clientcommunicator.operations.AcceptTradeRequest;
import clientcommunicator.operations.BuildCityRequest;
import clientcommunicator.operations.BuildRoadRequest;
import clientcommunicator.operations.BuildSettlementRequest;
import clientcommunicator.operations.BuyDevCardRequest;
import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.DiscardCardsRequest;
import clientcommunicator.operations.FinishTurnRequest;
import clientcommunicator.operations.JoinGameRequest;
import clientcommunicator.operations.LoginCredentials;
import clientcommunicator.operations.MaritimeTradeRequest;
import clientcommunicator.operations.MonopolyRequest;
import clientcommunicator.operations.MonumentRequest;
import clientcommunicator.operations.OfferTradeRequest;
import clientcommunicator.operations.PlaySoldierRequest;
import clientcommunicator.operations.RoadBuildingCardRequest;
import clientcommunicator.operations.RobPlayerRequest;
import clientcommunicator.operations.RollNumberRequest;
import clientcommunicator.operations.SendChatRequest;
import clientcommunicator.operations.YearOfPlentyRequest;
import model.cards.ResourceCards;
import model.player.PlayerIdx;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class RequestOperationsTests 
{
	/**
	 * Tests the serialize function of the AcceptTradeRequest class.
	 */
	@Test
	public void acceptTradeRequestTest() 
	{
		System.out.println("\nTesting serialization of acceptTradeRequest.");
		
    	PlayerIdx index = new PlayerIdx(1);
    	Boolean accepting = true;
    	AcceptTradeRequest thisTrade = new AcceptTradeRequest(index, accepting);
    	String work = thisTrade.serialize();
    	assertEquals("{type: \"acceptTrade\", playerIndex: 1, willAccept: true}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the BuildCityRequest class.
	 */
	@Test
	public void buildCityRequestTest() 
	{
		System.out.println("\nTesting serialization of BuildCityRequest.");
		
    	PlayerIdx index = new PlayerIdx(1);
    	HexLocation hexLoc = new HexLocation(1,1);
    	VertexDirection vertDir = VertexDirection.SouthWest;
    	VertexLocation newLocation = new VertexLocation(hexLoc, vertDir);
    	BuildCityRequest thisTrade = new BuildCityRequest(index, newLocation);
    	String work = thisTrade.serialize();
    	
    	assertEquals("{type: \"buildCity\", playerIndex: 1, vertexLocation: {x: 1, y: 1, direction: \"SW\"}}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the BuildRoadRequest class.
	 */
	@Test
	public void buildRoadRequestTest() 
	{
		System.out.println("\nTesting serialization of BuildRoadRequest.");
		
    	PlayerIdx index = new PlayerIdx(2);
    	HexLocation hexLoc = new HexLocation(1,1);
    	EdgeDirection edgeDir = EdgeDirection.SouthWest;
    	EdgeLocation newLocation = new EdgeLocation(hexLoc, edgeDir);
    	Boolean isFree = true;
    	BuildRoadRequest thisTrade = new BuildRoadRequest(index, newLocation, isFree);
    	String work = thisTrade.serialize();
    	
    	assertEquals("{type: \"buildRoad\", playerIndex: 2, roadLocation: {x: 1, y: 1, direction: \"SW\"}, free: true}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the BuildSettlementRequest class.
	 */
	@Test
	public void buildSettlementRequestTest() 
	{
		System.out.println("\nTesting serialization of BuildSettlementRequest.");
		
    	PlayerIdx index = new PlayerIdx(2);
    	HexLocation hexLoc = new HexLocation(1,1);
    	VertexDirection vertDir = VertexDirection.NorthWest;
    	VertexLocation newLocation = new VertexLocation(hexLoc, vertDir);
    	Boolean isFree = true;
    	BuildSettlementRequest thisTrade = new BuildSettlementRequest(index, newLocation, isFree);
    	String work = thisTrade.serialize();
    	
    	assertEquals("{type: \"buildSettlement\", playerIndex: 2, vertexLocation: {x: 1, y: 1, direction: \"NW\"}, free: true}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the BuyDevCardRequest class.
	 */
	@Test
	public void buyDevCardRequestTest() 
	{
		System.out.println("\nTesting serialization of buyDevCardRequest.");
		
    	PlayerIdx index = new PlayerIdx(1);
    	BuyDevCardRequest thisDiscard = new BuyDevCardRequest(index);
    	String work = thisDiscard.serialize();
    	
    	assertEquals("{type: \"buyDevCard\", playerIndex: 1}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the CreateGameRequest class.
	 */
	@Test
	public void createGameRequestTest() 
	{
		System.out.println("\nTesting serialization of createGameRequest.");
		
    	Boolean tiles = true;
    	Boolean numbers = true;
    	Boolean ports = true;
    	String gameName = "SweetLaurasGame";
    	CreateGameRequest thisGame = new CreateGameRequest(tiles, numbers, ports, gameName);
    	String work = thisGame.serialize();
    	System.out.println(work);
    	
    	assertEquals("{randomTiles: true, randomNumbers: true, randomPorts: true, name: \"SweetLaurasGame\"}", work);
	}
	
	/**
	 * Tests the serialize function of the DiscardCardsRequest class.
	 */
	@Test
	public void discardCardsRequestTest() 
	{
		 System.out.println("\nTesting serialization of discardCardsRequest.");
		
	     ResourceCards rec = new ResourceCards (1,4,5,2,3);
	     PlayerIdx index = new PlayerIdx(3);
	     DiscardCardsRequest thisDiscard = new DiscardCardsRequest(index, rec);
	     String work = thisDiscard.serialize();

	     assertEquals("{type: \"discardCards\", playerIndex: 3, discardedCards: {brick: 1, ore: 2, sheep: 3, wheat: 4, wood: 5}}", work);
	     
	     System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the FinishTurnRequest class.
	 */
	@Test
	public void finishTurnRequestTest() 
	{
		System.out.println("\nTesting serialization of finishTurnRequest.");
		
    	PlayerIdx index = new PlayerIdx(1);
    	FinishTurnRequest finTurnReq = new FinishTurnRequest(index);
    	String work = finTurnReq.serialize();
    	
    	assertEquals("{type: \"finishTurn\", playerIndex: 1}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the JoinGameRequest class.
	 */
	@Test
	public void joinGameRequestTest() 
	{//NEED TO CHANGE PERAMETERS, ASK IF IT AFFECTS ANYTHING ALSO ???
		System.out.println("\nTesting serialization of JoinGameRequest.");
    	int theGameId = 3;
    	CatanColor theColor = CatanColor.YELLOW;
    	JoinGameRequest joinGameReq = new JoinGameRequest(theGameId, theColor);
    	String work = joinGameReq.serialize();
    	
    	assertEquals("{id: 3, color: \"yellow\"}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the LoginCredentials class.
	 */
	@Test
	public void loginCredentialsTest() 
	{
		System.out.println("\nTesting serialization of LoginCredentials.");
		
    	String theUsername = "LauraH";
    	String thePassword = "sweetpea";
    	LoginCredentials loginCredentials = new LoginCredentials(theUsername, thePassword);
    	String work = loginCredentials.serialize();
    	
    	assertEquals("{username: \"LauraH\", password: \"sweetpea\"}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the MaritimeRequest class.
	 */
	@Test
	public void maritimeRequestTest() 
	{
		System.out.println("\nTesting serialization of maritimeRequest.");
		
    	PlayerIdx index = new PlayerIdx(1);
    	int theRatio = 3;
    	ResourceType inputRes = ResourceType.WHEAT;
    	ResourceType outputRes = ResourceType.ORE;
    	MaritimeTradeRequest MarTradeReq = new MaritimeTradeRequest(index, theRatio, inputRes, outputRes);
    	String work = MarTradeReq.serialize();
    	
    	assertEquals("{type: \"maritimeTrade\", playerIndex: 1, ratio: 3, inputResource: \"wheat\", outputResource: \"ore\"}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the MonopolyRequest class.
	 */
	@Test
	public void monopolyRequestTest() 
	{
		System.out.println("\nTesting serialization of MonopolyRequest.");
		
    	ResourceType resType = ResourceType.WHEAT;
    	PlayerIdx index = new PlayerIdx(2);
    	MonopolyRequest monoReq = new MonopolyRequest(index, resType);
    	String work = monoReq.serialize();
    	
    	assertEquals("{type: \"Monopoly\", resource: \"wheat\", playerIndex: 2}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the MonumentRequest class.
	 */
	@Test
	public void monumentRequestTest() 
	{
		System.out.println("\nTesting serialization of MonumentRequest.");
		
    	PlayerIdx index = new PlayerIdx(2);
    	MonumentRequest monuReq = new MonumentRequest(index);
    	String work = monuReq.serialize();
    	
    	assertEquals("{type: \"Monument\", playerIndex: 2}", work); 
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the OfferTradeRequest class.
	 */
	@Test
	public void offerTradeRequestTest()
	{
		System.out.println("\nTesting serialization of OfferTradeRequest");
		
		PlayerIdx index = new PlayerIdx(2);
		ResourceCards offer = new ResourceCards (1,4,5,2,3);
		int receiver = 3;
		
		OfferTradeRequest offerReq = new OfferTradeRequest(index, offer, receiver);
		String work = offerReq.serialize();

		assertEquals("{type: \"offerTrade\", playerIndex: 2, offer: {brick: 1, ore: 2, sheep: 3, wheat: 4, wood: 5}, receiver: 3}", work);
		
		System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the PlaySoldierRequest class.
	 */
	@Test
	public void playSoldierRequestTest() 
	{
		System.out.println("\nTesting serialization of PlaySoldierRequest.");
		
    	PlayerIdx index = new PlayerIdx(2);
    	int vicIndex = 1;
    	HexLocation hexLoc = new HexLocation(1,1);
    	
    	PlaySoldierRequest soldierReq = new PlaySoldierRequest(index, vicIndex, hexLoc);
    	String work = soldierReq.serialize();

    	assertEquals("{type: \"Soldier\", playerIndex: 2, victimIndex: 1, location: {x: 1, y: 1}}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the RoadBuildingCardRequest class.
	 */
	@Test
	public void roadBuildingCardRequestTest() 
	{
		System.out.println("\nTesting serialization of RoadBuildingCardRequest.");
		
    	PlayerIdx index = new PlayerIdx(2);
    	int vicIndex =1;
    	HexLocation hexLoc = new HexLocation(1,1);
    	EdgeDirection edgeDir = EdgeDirection.SouthWest;
    	EdgeLocation newLocation = new EdgeLocation(hexLoc, edgeDir);
    	
    	HexLocation hexLoc2 = new HexLocation(1,1);
    	EdgeDirection edgeDir2 = EdgeDirection.North;
    	EdgeLocation newLocation2 = new EdgeLocation(hexLoc2, edgeDir2);
    	    	
    	RoadBuildingCardRequest soldierReq = new RoadBuildingCardRequest(index, newLocation, newLocation2);
    	String work = soldierReq.serialize();
    	
    	assertEquals("{type: \"Road_Building\", playerIndex: 2, spot1: {x: 1, y: 1, direction: \"SW\"}, spot2: {x: 1, y: 1, direction: \"N\"}}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the RobPlayerRequest class.
	 */
	@Test
	public void robPlayerRequestTest() 
	{
		System.out.println("\nTesting serialization of RobPlayerRequest.");
		
    	PlayerIdx index = new PlayerIdx(1);
    	PlayerIdx vicIndex = new PlayerIdx(2);
    	HexLocation hexLoc = new HexLocation(1,0);
    	RobPlayerRequest robPlayReq = new RobPlayerRequest(index, vicIndex, hexLoc);
    	String work = robPlayReq.serialize();
    	
    	assertEquals("{type: \"robPlayer\", playerIndex: 1, victimIndex: 2, location: {x: 1, y: 0}}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the RollNumberRequest class.
	 */
	@Test
	public void rollNumberRequestTest() 
	{
		System.out.println("\nTesting serialization of RollNumberRequest.");
		
    	PlayerIdx index = new PlayerIdx(0);
    	int rollNumber = 3;

    	RollNumberRequest roleNumReq = new RollNumberRequest(index, rollNumber);
    	String work = roleNumReq.serialize();

    	assertEquals("{type: \"rollNumber\", playerIndex: 0, number: 3}", work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the SendChatRequest class.
	 */
	@Test
	public void sendChatRequestTest() 
	{
		System.out.println("\nTesting serialization of SendChatRequest.");
		
    	PlayerIdx index = new PlayerIdx(0);
    	String theContent = "This is Laura!";

    	SendChatRequest sendChatReq = new SendChatRequest(index, theContent);
    	String work = sendChatReq.serialize();

    	assertEquals("{type: \"sendChat\", playerIndex: 0, content: \"This is Laura!\"}",work);
    	
    	System.out.println(work);
	}
	
	/**
	 * Tests the serialize function of the YearOfPlentyRequest class.
	 */
	@Test
	public void yearOfPlentyRequestTest() 
	{
		System.out.println("\nTesting serialization of YearOfPlentyRequest.");
		
    	PlayerIdx index = new PlayerIdx(2);
    	ResourceType firstResource = ResourceType.WHEAT;
    	ResourceType secondResource = ResourceType.ORE;
    	YearOfPlentyRequest yearOfPlentReq = new YearOfPlentyRequest(index, firstResource, secondResource);
    	String work = yearOfPlentReq.serialize();
    	
    	assertEquals("{type: \"Year_of_Plenty\", playerIndex: 2, resource1: \"wheat\", resource2: \"ore\"}", work);
    	
    	System.out.println(work);
	}

}
