package clientcommunicator.modelserverfacade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import clientcommunicator.operations.Abbreviate;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.ResourceCards;
import model.cards.TradeOffer;
import model.map.EdgeObject;
import model.map.Hex;
import model.map.Port;
import model.map.VertexObject;
import model.messages.MessageLine;
import model.messages.MessageList;
import model.player.Player;
import model.player.TurnTracker;
import shared.definitions.HexType;

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
			devCardObject.put("roadBuilding", devCards.getRoadBuilding());
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
    	String theDirection = settlementOrCity.getLocation().getDir().name();
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
		DevelopmentCards theDeck = theModel.getBank().getDevelopmentCards();
		try {
			//Bank Section
			object.put("bank", recCardsJSON(theBank)); //use the function for the bank instead
			object.put("deck", devCardsJSON(theDeck));
			//Chat/Log Section
			object.put("chat", messageListJSON(chatMessages));
			object.put("log", messageListJSON(logMessages));
			//Map Section------------------------------------------------------------------------
			//Hex for Map
			JSONArray hexArray = new JSONArray();
			for(Hex curHex: theModel.getMap().getHexes())
			{
				if (curHex.getType() != HexType.WATER)
				{
					JSONObject hexObject = new JSONObject();
					JSONObject hexLocObject = new JSONObject();
					hexLocObject.put("x", curHex.getLocation().getX());
					hexLocObject.put("y", curHex.getLocation().getY());
					hexObject.put("location", hexLocObject);
					if(curHex.getType() != HexType.DESERT)
					{
						String stringResource = curHex.getType().name();
				    	String lowerCaseResource = stringResource.toLowerCase();
						hexObject.put("resource", lowerCaseResource);
						if (curHex.getNumber() != -1)
						{
							hexObject.put("number", curHex.getNumber());
						}
					}
					hexArray.put(hexObject);
				}
			}
			mapObject.put("hexes", hexArray);
			//Ports for Map
			JSONArray portArray = new JSONArray();
			for(Port curPort : theModel.getMap().getPorts())
			{
				JSONObject portObject = new JSONObject();
				JSONObject portHexLocObject = new JSONObject();
				if(curPort.getResource()!=null)
				{
					String stringResource = curPort.getResource().name().toString();
			    	String lowerCaseResource = stringResource.toLowerCase();
					portObject.put("resource", lowerCaseResource);
				}
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
				if(curPlayer.getPlayerIndex() != null)
				{
					playerObject.put("playerIndex", curPlayer.getPlayerIndex().getIndex());
				}
				
				playerObject.put("playedDevCard", !curPlayer.canPlayDev());
				playerObject.put("playerID", curPlayer.getPlayerId());
				playerObject.put("resources", recCardsJSON(curPlayer.getHand().getResourceCards()));
				playerObject.put("roads", curPlayer.getRoads());
				playerObject.put("settlements", curPlayer.getSettlements());
				playerObject.put("soldiers", curPlayer.getSoldiers());
				playerObject.put("victoryPoints", curPlayer.getVictoryPoints());
				playerArray.put(playerObject);
			}
			object.put("players", playerArray);
			//TradeOfferObject-------------------------------------------------------------------------------------
			if(theModel.getTradeOffer()!=null)
			{
				TradeOffer curTradeOffer = theModel.getTradeOffer();
				JSONObject tradeOfferObject = new JSONObject();
				tradeOfferObject.put("sender", curTradeOffer.getSenderNumber().getIndex());
				tradeOfferObject.put("receiver", curTradeOffer.getReceiverNumber().getIndex());
				tradeOfferObject.put("offer", recCardsJSON(curTradeOffer.getResourceCards()));
				object.put("tradeOffer", tradeOfferObject);
			}
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
			int winnerIndex = theModel.getWinner().getIndex();
			int i = 0;
			int thePlayerID = -1;
			for(Player curPlayer : theModel.getPlayers())
			{
				if(i == winnerIndex)
				{
					thePlayerID = curPlayer.getPlayerId();
				}
			}
			object.put("winner", thePlayerID);
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return object.toString();
	}
}
