package clientcommunicator.modelserverfacade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import clientcommunicator.operations.Abbreviate;
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

/**
 * 
 * @author LaurasAdventurePC
 *
 */
public class JSONSerializer {
	
	public JSONSerializer()
	{
		
	}

	public static JSONObject devCardsJSON(DevelopmentCards devCards)
	{
		JSONObject devCardObject = new JSONObject();
		try {
			devCardObject.put("monopoly", devCards.getMonopoly());
			devCardObject.put("monument", devCards.getMonument());
			devCardObject.put("roadBuilding:", devCards.getRoadBuilding());
			devCardObject.put("soldier", devCards.getSoldier());
			devCardObject.put("yearOfPlenty", devCards.getYearOfPlenty());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return devCardObject;	
	}
	
	public static JSONObject recCardsJSON(ResourceCards recCards)
	{
		JSONObject recCardsObject = new JSONObject();		
		try {
			recCardsObject.put("brick", recCards.getBrick());
			recCardsObject.put("ore", recCards.getOre());
			recCardsObject.put("sheep", recCards.getWool());
			recCardsObject.put("wheat", recCards.getGrain());
			recCardsObject.put("wood", recCards.getLumber());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return recCardsObject;
	}
	
	public static JSONObject messageListJSON(MessageList messList)
	{
		JSONObject theMessageListObject = new JSONObject();
		JSONArray messListArray = new JSONArray();
		for(MessageLine curLine : messList.getLines())
		{
			JSONObject messLineObject = new JSONObject();
			try {
				messLineObject.put("message", curLine.getMessage());
				messLineObject.put("source", curLine.getSource());
				messListArray.put(messLineObject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			theMessageListObject.put("lines", messListArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return theMessageListObject;
	}
	
	public static JSONObject roadCitySettlementJSON(VertexObject settlementOrCity)
	{
		JSONObject settlementOrCityObject = new JSONObject();
		JSONObject settlementorCityLocObject = new JSONObject();
    	String theDirection = settlementOrCity.getLocation().getDir().name();//.getLocation().getDir();
    	Abbreviate abrev = new Abbreviate();
    	theDirection = abrev.abbreviate(theDirection);
		
		try {
			settlementOrCityObject.put("owner", settlementOrCity.getOwner().getIndex());
			settlementorCityLocObject.put("x", settlementOrCity.getLocation().getHexLoc().getX());
			settlementorCityLocObject.put("y", settlementOrCity.getLocation().getHexLoc().getY());
			settlementorCityLocObject.put("direction", theDirection);
			settlementOrCityObject.put("location", settlementorCityLocObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return settlementOrCityObject;
	}
	
	public static String SerializeModel(ClientModel theModel)
	{
		JSONObject object = new JSONObject();
		
		JSONObject mapObject = new JSONObject();
		ResourceCards theBank = theModel.getBank().getResourceCards();	
		MessageList chatMessages = theModel.getChat();
		MessageList logMessages = theModel.getLog();
		
		try {
			//Bank Section
			object.put("bank", recCardsJSON(theBank)); //use the function for the bank instead
			//Chat/Log Section
			object.put("chat", messageListJSON(chatMessages));
			object.put("log", messageListJSON(logMessages));
			//Map Section------------------------------------------------------------------------
			//Hex for Map
			JSONArray hexArray = new JSONArray();
			for(Hex curHex: theModel.getMap().getHexes())
			{
				JSONObject hexObject = new JSONObject();
				JSONObject hexLocObject = new JSONObject();
				hexLocObject.put("x", curHex.getLocation().getX());
				hexLocObject.put("y", curHex.getLocation().getY());
				hexObject.put("location", hexLocObject);
				String stringResource = curHex.getType().name();
		    	String lowerCaseResource = stringResource.toLowerCase();
				hexObject.put("resource", lowerCaseResource);
				hexObject.put("number", curHex.getNumber());
				hexArray.put(hexObject);
			}
			mapObject.put("hexes", hexArray);
			//Ports for Map
			JSONArray portArray = new JSONArray();
			for(Port curPort : theModel.getMap().getPorts())
			{
				JSONObject portObject = new JSONObject();
				JSONObject portHexLocObject = new JSONObject();
				String stringResource = curPort.getResource().name().toString();
		    	String lowerCaseResource = stringResource.toLowerCase();
				portObject.put("resource", lowerCaseResource);
				portHexLocObject.put("x", curPort.getHex().getX());
				portHexLocObject.put("y", curPort.getHex().getY());
				portObject.put("location", portHexLocObject);
				String theDirection = curPort.getDirection().name();
		    	Abbreviate abrev = new Abbreviate();
		    	theDirection = abrev.abbreviate(theDirection);
				portObject.put("direction", theDirection);
				portObject.put("ratio", curPort.getRatio());
				portArray.put(portObject);
			}
			mapObject.put("ports", portArray);
			//Roads for Map
			JSONArray roadArray = new JSONArray();
			for(EdgeObject curRoad : theModel.getMap().getRoads())
			{
				JSONObject roadObject = new JSONObject();
				JSONObject roadEdgeLocObject = new JSONObject();
				String theDirection = curRoad.getLocation().getDir().name();
		    	Abbreviate abrev = new Abbreviate();
		    	theDirection = abrev.abbreviate(theDirection);
				
				roadObject.put("owner", curRoad.getOwner().getIndex());
				roadEdgeLocObject.put("x", curRoad.getLocation().getHexLoc().getX());
				roadEdgeLocObject.put("y", curRoad.getLocation().getHexLoc().getY());
				roadEdgeLocObject.put("direction", theDirection);
				roadObject.put("location", roadEdgeLocObject);
				roadArray.put(roadObject);
			}
			mapObject.put("roads", roadArray);
			//Settlements for Map
			JSONArray settlementArray = new JSONArray();
			for(VertexObject curSettlement : theModel.getMap().getSettlements())
			{
				settlementArray.put(roadCitySettlementJSON(curSettlement));
			}
			mapObject.put("settlements", settlementArray);
			//Cities for Map
			JSONArray citiesArray = new JSONArray();
			for(VertexObject curCity: theModel.getMap().getCities())
			{
				citiesArray.put(roadCitySettlementJSON(curCity));
			}
			mapObject.put("cities", citiesArray);
			//Other factors Map
			mapObject.put("radius", theModel.getMap().getRadius());
			//Robber for Map
			JSONObject robberObject = new JSONObject();
			robberObject.put("x", theModel.getMap().getRobber().getX());
			robberObject.put("y", theModel.getMap().getRobber().getY());
			mapObject.put("robber", robberObject);
			object.put("map", mapObject);
			
			//PlayerObject----------------------------------------------------------------------------------------
			JSONArray playerArray = new JSONArray();
			for(Player curPlayer : theModel.getPlayers())
			{
				JSONObject playerObject = new JSONObject();
				playerObject.put("cities", curPlayer.getCities());
				String stringColor = curPlayer.getColor().name().toString();
		    	String lowerCaseColor = stringColor.toLowerCase();
				playerObject.put("color", lowerCaseColor);
				playerObject.put("discarded", curPlayer.getDiscarded());
				playerObject.put("monuments", curPlayer.getMonuments());
				playerObject.put("name", curPlayer.getName());
				playerObject.put("newDevCards", devCardsJSON(curPlayer.getNewDevCards()));
				playerObject.put("oldDevCards", devCardsJSON(curPlayer.getHand().getDevelopmentCards()));
				playerObject.put("playerIndex", curPlayer.getPlayerIndex().getIndex());
				playerObject.put("playedDevCard", curPlayer.canPlayDev());//COULD BE THE OPPOSITE, CHECK THIS!!!!!
				playerObject.put("playerID", curPlayer.getPlayerId());
				playerObject.put("resources", recCardsJSON(curPlayer.getHand().getResourceCards()));
				playerObject.put("roads", curPlayer.getRoads());
				playerObject.put("cities", curPlayer.getCities());
				playerObject.put("soldier", curPlayer.getSoldiers());
				playerObject.put("victoryPoints", curPlayer.getVictoryPoints());
				playerArray.put(playerObject);
			}
			object.put("players", playerArray);
			//TradeOfferObject-------------------------------------------------------------------------------------
			TradeOffer curTradeOffer = theModel.getTradeOffer();
			JSONObject tradeOfferObject = new JSONObject();
			tradeOfferObject.put("sender", curTradeOffer.getSenderNumber().getIndex());
			tradeOfferObject.put("receiver", curTradeOffer.getReceiverNumber().getIndex());
			tradeOfferObject.put("offer", recCardsJSON(curTradeOffer.getResourceCards()));
			object.put("tradeOffer", tradeOfferObject);
			//TurnTrackerObject------------------------------------------------------------------------------------
			TurnTracker curTurnTracker = theModel.getTurnTracker();
			JSONObject turnTrackerObject = new JSONObject();
			turnTrackerObject.put("currentTurn", curTurnTracker.getCurrentTurn().getIndex());
			turnTrackerObject.put("status", curTurnTracker.getStatus());
			turnTrackerObject.put("longestRoad", curTurnTracker.getLongestRoad().getIndex());
			turnTrackerObject.put("largestArmy", curTurnTracker.getLargestArmy().getIndex());
			object.put("turnTracker", turnTrackerObject);
			//VersionNumber and Winner
			object.put("version", theModel.getVersion());
			object.put("winner", theModel.getWinner().getIndex());
			
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return object.toString();
	}
	
	public static void main(String[] args)
	{
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
		//
		
        //trying to do ports
        Set<Port> ports = new HashSet<>();
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
        roads = new HashSet<>();
        PlayerIdx index = new PlayerIdx(2);
        PlayerIdx otherPlayerIndex = new PlayerIdx((index.getIndex() + 1) % 4);
        roads.add(new EdgeObject(new EdgeLocation(new HexLocation(0,0), EdgeDirection.North), otherPlayerIndex));
        //
        
        //trying to do city/settlement
        PlayerIdx aPlayerIndex = new PlayerIdx((index.getIndex() + 1) % 4);
        VertexLocation location = new VertexLocation(new HexLocation(0,0), VertexDirection.East);
        Collection<VertexObject> otherVertexObjects = new HashSet<>();
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
		String answerSoFar = SerializeModel(testModel);
		System.out.println(answerSoFar);
	}
	
}
