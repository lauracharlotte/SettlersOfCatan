/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.IJSONSerializable;
import clientcommunicator.operations.PlayerJSONResponse;

import java.util.ArrayList;
import java.util.Collection;

import model.ClientModel;
import model.ClientModelSupplier;
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
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import org.json.*;

/**
 *
 * @author Michael
 */
public class JSONParser 
{

    /**
     *
     * @param serializable An object that needs to be serialized
     * @return The given object as a JSON string
     */
    public static String toJSON(IJSONSerializable serializable)
    {
        return serializable.serialize();
    }
    
    /**
     * @pre modelJSon is valid JSON for ClientModel. There are no ocean hexes in the modelJSON.
     * @param modelJSON A string that contains a ClientModel JSON object
     * @return The clientModel that the model JSON represented
     * @throws JSONException 
     */
    public static ClientModel fromJSONToModel(String modelJSON) throws JSONException
    {
    	JSONObject model = new JSONObject(modelJSON);
    	JSONObject bank = model.getJSONObject("bank");
    	JSONObject deck = model.getJSONObject("deck");
    	Hand newBank = new Hand(fromJSONToResourceCards(bank), fromJSONToDevelopmentCards(deck));
    	JSONObject chat = model.getJSONObject("chat");
    	MessageList newChat = fromJSONToMessageList(chat);
    	JSONObject log = model.getJSONObject("log");
    	MessageList newLog = fromJSONToMessageList(log);
    	JSONObject map = model.getJSONObject("map");
    	CatanMap newMap = fromJSONToMap(map);
    	JSONArray players = model.getJSONArray("players");
    	ArrayList<Player> newPlayers = fromJSONToPlayers(players);
    	JSONObject tradeOffer;
    	TradeOffer newTradeOffer;
    	if (model.has("tradeOffer"))
		{
    		tradeOffer = model.getJSONObject("tradeOffer");
    		newTradeOffer = fromJSONToTradeOffer(tradeOffer);
    	}
    	else
    	{
    		newTradeOffer = null;
    	}
    	JSONObject turnTracker = model.getJSONObject("turnTracker");
    	TurnTracker newTurnTracker = fromJSONToTurnTracker(turnTracker);
    	int version = model.getInt("version");
    	int winner = model.getInt("winner");
    	
    	NullablePlayerIdx newWinner = new NullablePlayerIdx(-1);//new NullablePlayerIdx(winner);
    	if(ClientModelSupplier.getInstance().getModel() != null)
    	{
	    	for(Player curPlayer: ClientModelSupplier.getInstance().getModel().getPlayers())
	    	{
	    		if(curPlayer.getPlayerId() == winner)
	    		{
	    			newWinner = curPlayer.getPlayerIndex();
	    		}
	    	}
    	}
    	
    	ClientModel newModel = new ClientModel(newBank, newChat, newLog, newMap, newPlayers,
    			newTradeOffer, newTurnTracker, version, newWinner);
    	return newModel;
    }
    
    /**
     * @pre gameListJSON is valid JSON for a game list.
     * @param gameListJSON Represents a list of games -- as in response to listGames API.
     * @return Collection of GameJSONResponses which holds all games that were specified in the JSON
     * @throws JSONException 
     */
    public static Collection<GameJSONResponse> fromJSONToGameCollection(String gameListJSON) throws JSONException
    {
    	ArrayList<GameJSONResponse> games = new ArrayList<>();
    	JSONArray gamesJSON = new JSONArray(gameListJSON);
    	for (int i = 0; i < gamesJSON.length(); i++)
    	{
    		if (!gamesJSON.isNull(i))
    		{
    			games.add(fromJSONToGame(gamesJSON.getJSONObject(i).toString()));
    		}
    	}
    	return games;
    }
    
    /**
     * @pre gameJSON is valid JSON for a game.
     * @param gameJSON Represents valid JSON for a game -- as in listGames.
     * @return The information extracted from the JSON string
     * @throws JSONException 
     */
    public static GameJSONResponse fromJSONToGame(String gameJSON) throws JSONException
    {
    	JSONObject gameObj = new JSONObject(gameJSON);
    	String title = gameObj.getString("title");
    	int gameId = gameObj.getInt("id");
    	JSONArray playersJSON = gameObj.getJSONArray("players");
    	ArrayList<PlayerJSONResponse> players = fromJSONToPlayerResponses(playersJSON);
    	GameJSONResponse game = new GameJSONResponse(title, gameId, players);
    	return game;
    }
    
    private static ResourceCards fromJSONToResourceCards(JSONObject resources) throws JSONException
    {
    	int brick = resources.getInt("brick");
    	int ore = resources.getInt("ore");
    	int sheep = resources.getInt("sheep");
    	int wheat = resources.getInt("wheat");
    	int wood = resources.getInt("wood");
    	ResourceCards cards = new ResourceCards(brick, wheat, wood, ore, sheep);
    	return cards;
    }
    
    private static MessageList fromJSONToMessageList(JSONObject list) throws JSONException
    {
    	JSONArray lines = list.getJSONArray("lines");
    	ArrayList<MessageLine> messages = new ArrayList<>();
    	for (int i = 0; i < lines.length(); i++)
    	{
    		if (!lines.isNull(i))
    		{
	    		String message = lines.getJSONObject(i).getString("message");
	    		String source = lines.getJSONObject(i).getString("source");
	    		MessageLine newLine = new MessageLine(message, source);
	    		messages.add(newLine);
    		}
    	}
    	MessageList newList = new MessageList(messages);
    	return newList;
    }
    
    private static HexLocation fromJSONToHexLocation(JSONObject location) throws JSONException
    {
    	int x = location.getInt("x");
    	int y = location.getInt("y");
    	HexLocation newLoc = new HexLocation(x, y);
    	return newLoc;
    }
    
    private static Hex fromJSONToHex(JSONObject hex) throws JSONException
    {
    	HexLocation location = fromJSONToHexLocation(hex.getJSONObject("location"));
    	String resource;
    	HexType type = null;
    	int number;
    	if (hex.has("resource"))
    	{
    		resource = hex.getString("resource");
    		resource = resource.toLowerCase();
    		switch (resource) {
    		case "brick":
    			type = HexType.BRICK;
    			break;
    		case "wood":
    			type = HexType.WOOD;
    			break;
    		case "sheep":
    			type = HexType.SHEEP;
    			break;
    		case "wheat":
    			type = HexType.WHEAT;
    			break;
    		case "ore":
    			type = HexType.ORE;
    			break;
    		default:
    			type = HexType.DESERT;
    			break;
    		}
    	}
    	else
    	{
    		int x = location.getX();
    		int y = location.getY();
    		if (x == -3 || x == 3)
    		{
    			type = HexType.WATER;
    		}
    		else if (y == -3 || y == 3)
    		{
    			type = HexType.WATER;
    		}
    		else if ((x == -2 && y == -1) || (x == -1 && y == -2))
    		{
    			type = HexType.WATER;
    		}
    		else if ((x == 2 && y == 1) || (x == 1 && y == 2))
    		{
    			type = HexType.WATER;
    		}
    		else
    		{
    			type = HexType.DESERT;
    		}
    	}
    	if (hex.has("number"))
    	{
    		number = hex.getInt("number");
    	}
    	else
    	{
    		number = -1;
    	}
    	Hex newHex = new Hex(location, type, number);
    	return newHex;
    }
    
    private static void addOceanHexes(ArrayList<Hex> hexes)
    {
    	Hex hex;
    	HexLocation location;
    	HexType type = HexType.WATER;
    	int number = -1;
    	int x;
    	int y = -3;
    	for (x = 0; x <= 3; x++)
    	{
    		location = new HexLocation(x, y);
    		hex = new Hex(location, type, number);
    		hexes.add(hex);
    	}
    	y = 3;
    	for (x = -3; x <= 0; x++)
    	{
    		location = new HexLocation(x, y);
    		hex = new Hex(location, type, number);
    		hexes.add(hex);
    	}
    	x = -3;
    	for (y = 0; y <= 2; y++)
    	{
    		location = new HexLocation(x, y);
    		hex = new Hex(location, type, number);
    		hexes.add(hex);
    	}
    	x = 3;
    	for (y = -2; y <= 0; y++)
    	{
    		location = new HexLocation(x, y);
    		hex = new Hex(location, type, number);
    		hexes.add(hex);
    	}
    	x = -2;
    	y = -1;
    	location = new HexLocation(x, y);
		hex = new Hex(location, type, number);
		hexes.add(hex);
		x = -1;
		y = -2;
		location = new HexLocation(x, y);
		hex = new Hex(location, type, number);
		hexes.add(hex);
		x = 2;
		y = 1;
		location = new HexLocation(x, y);
		hex = new Hex(location, type, number);
		hexes.add(hex);
		x = 1;
		y = 2;
		location = new HexLocation(x, y);
		hex = new Hex(location, type, number);
		hexes.add(hex);
    }
    
    private static ArrayList<Hex> fromJSONToHexes(JSONArray hexes) throws JSONException
    {
    	ArrayList<Hex> newHexes = new ArrayList<>();
    	for (int i = 0; i < hexes.length(); i++)
    	{
    		if (!hexes.isNull(i))
    		{
    			newHexes.add(fromJSONToHex(hexes.getJSONObject(i)));
    		}
    	}
    	addOceanHexes(newHexes);
    	return newHexes;
    }
    
    private static Port fromJSONToPort(JSONObject port) throws JSONException
    {
    	String resource;
    	ResourceType type;
    	if (port.has("resource"))
    	{
    		resource = port.getString("resource");
    		resource = resource.toLowerCase();
    		switch (resource) {
    		case "brick":
    			type = ResourceType.BRICK;
    			break;
    		case "wood":
    			type = ResourceType.WOOD;
    			break;
    		case "sheep":
    			type = ResourceType.SHEEP;
    			break;
    		case "wheat":
    			type = ResourceType.WHEAT;
    			break;
    		case "ore":
    			type = ResourceType.ORE;
    			break;
    		default:
    			type = null;
    			break;
    		}
    	}
    	else
    	{
    		type = null;
    	}
    	HexLocation location = fromJSONToHexLocation(port.getJSONObject("location"));
    	EdgeDirection direction;
    	String direct = port.getString("direction");
    	direct = direct.toLowerCase();
    	switch (direct)
    	{
    	case "nw":
    		direction = EdgeDirection.NorthWest;
    		break;
    	case "n":
    		direction = EdgeDirection.North;
    		break;
    	case "ne":
    		direction = EdgeDirection.NorthEast;
    		break;
    	case "s":
    		direction = EdgeDirection.South;
    		break;
    	case "sw":
    		direction = EdgeDirection.SouthWest;
    		break;
    	case "se":
    		direction = EdgeDirection.SouthEast;
    		break;
    	default:
    		direction = null;
    		break;
    	}
    	int ratio = port.getInt("ratio");
    	Port newPort = new Port(location, type, direction, ratio);
    	return newPort;
    }
    
    private static ArrayList<Port> fromJSONToPorts(JSONArray ports) throws JSONException
    {
    	ArrayList<Port> newPorts = new ArrayList<>();
    	for (int i = 0; i < ports.length(); i++)
    	{
    		if (!ports.isNull(i))
    		{
    			newPorts.add(fromJSONToPort(ports.getJSONObject(i)));
    		}
    	}
    	return newPorts;
    }
    
    private static VertexObject fromJSONToVertexObject(JSONObject object) throws JSONException
    {
    	PlayerIdx owner = new PlayerIdx(object.getInt("owner"));
    	JSONObject location = object.getJSONObject("location");
    	HexLocation newLocation = fromJSONToHexLocation(location);
    	VertexDirection direction;
    	String direct = location.getString("direction");
    	direct = direct.toLowerCase();
    	switch (direct)
    	{
    	case "e":
    		direction = VertexDirection.East;
    		break;
    	case "ne":
    		direction = VertexDirection.NorthEast;
    		break;
    	case "nw":
    		direction = VertexDirection.NorthWest;
    		break;
    	case "se":
    		direction = VertexDirection.SouthEast;
    		break;
    	case "sw":
    		direction = VertexDirection.SouthWest;
    		break;
    	case "w":
    		direction = VertexDirection.West;
    		break;
    	default:
    		direction = null;
    		break;
    	}
    	
    	VertexLocation loc = new VertexLocation(newLocation, direction);
    	VertexObject newObject = new VertexObject(loc, owner);
    	return newObject;
    }
    
    private static EdgeObject fromJSONToEdgeObject(JSONObject road) throws JSONException
    {
    	PlayerIdx owner = new PlayerIdx(road.getInt("owner"));
    	JSONObject location = road.getJSONObject("location");
    	HexLocation newLocation = fromJSONToHexLocation(location);
    	EdgeDirection direction;
    	String direct = location.getString("direction");
    	direct = direct.toLowerCase();
    	switch (direct)
    	{
    	case "nw":
    		direction = EdgeDirection.NorthWest;
    		break;
    	case "n":
    		direction = EdgeDirection.North;
    		break;
    	case "ne":
    		direction = EdgeDirection.NorthEast;
    		break;
    	case "s":
    		direction = EdgeDirection.South;
    		break;
    	case "sw":
    		direction = EdgeDirection.SouthWest;
    		break;
    	case "se":
    		direction = EdgeDirection.SouthEast;
    		break;
    	default:
    		direction = null;
    		break;
    	}
    	
    	EdgeLocation loc = new EdgeLocation(newLocation, direction);
    	EdgeObject newRoad = new EdgeObject(loc, owner);
    	return newRoad;
    }
    
    private static ArrayList<VertexObject> fromJSONToVertexObjects(JSONArray objects) throws JSONException
    {
    	ArrayList<VertexObject> newObjects = new ArrayList<>();
    	for (int i = 0; i < objects.length(); i++)
    	{
    		if (!objects.isNull(i))
    		{
    			newObjects.add(fromJSONToVertexObject(objects.getJSONObject(i)));
    		}
    	}
    	return newObjects;
    }
    
    private static ArrayList<EdgeObject> fromJSONToEdgeObjects(JSONArray roads) throws JSONException
    {
    	ArrayList<EdgeObject> newRoads = new ArrayList<>();
    	for (int i = 0; i < roads.length(); i++)
    	{
    		if (!roads.isNull(i))
    		{
    			newRoads.add(fromJSONToEdgeObject(roads.getJSONObject(i)));
    		}
    	}
    	return newRoads;
    }
    
    private static CatanMap fromJSONToMap(JSONObject map) throws JSONException
    {
    	ArrayList<Hex> hexes = fromJSONToHexes(map.getJSONArray("hexes"));
    	ArrayList<Port> ports = fromJSONToPorts(map.getJSONArray("ports"));
    	ArrayList<EdgeObject> roads = fromJSONToEdgeObjects(map.getJSONArray("roads"));
    	ArrayList<VertexObject> settlements = fromJSONToVertexObjects(map.getJSONArray("settlements"));
    	ArrayList<VertexObject> cities = fromJSONToVertexObjects(map.getJSONArray("cities"));
    	int radius = map.getInt("radius");
    	HexLocation robber = fromJSONToHexLocation(map.getJSONObject("robber"));
    	CatanMap newMap = new CatanMap(hexes, ports, roads, settlements, cities, radius, robber);
    	return newMap;
    }
    
    private static DevelopmentCards fromJSONToDevelopmentCards(JSONObject devCards) throws JSONException
    {
    	int monopoly = devCards.getInt("monopoly");
    	int monument = devCards.getInt("monument");
    	int roadBuilding = devCards.getInt("roadBuilding");
    	int soldier = devCards.getInt("soldier");
    	int yearOfPlenty = devCards.getInt("yearOfPlenty");
    	DevelopmentCards cards = new DevelopmentCards(monopoly, monument, roadBuilding, soldier,
    			yearOfPlenty);
    	return cards;
    }
    
    private static Player fromJSONToPlayer(JSONObject player) throws JSONException
    {
    	int cities = player.getInt("cities");
    	String color = player.getString("color");
    	CatanColor newColor;
    	color = color.toLowerCase();
    	switch (color)
    	{
    	case "blue":
    		newColor = CatanColor.BLUE;
    		break;
    	case "brown":
    		newColor = CatanColor.BROWN;
    		break;
    	case "green":
    		newColor = CatanColor.GREEN;
    		break;
    	case "orange":
    		newColor = CatanColor.ORANGE;
    		break;
    	case "puce":
    		newColor = CatanColor.PUCE;
    		break;
    	case "purple":
    		newColor = CatanColor.PURPLE;
    		break;
    	case "red":
    		newColor = CatanColor.RED;
    		break;
    	case "white":
    		newColor = CatanColor.WHITE;
    		break;
    	case "yellow":
    		newColor = CatanColor.YELLOW;
    		break;
    	default:
    		newColor = null;
    		break;
    	}
    	boolean discarded = player.getBoolean("discarded");
    	int monuments = player.getInt("monuments");
    	String name = player.getString("name");
    	DevelopmentCards newDevCards = fromJSONToDevelopmentCards(player.getJSONObject("newDevCards"));
    	DevelopmentCards devCards = fromJSONToDevelopmentCards(player.getJSONObject("oldDevCards"));
    	ResourceCards resources = fromJSONToResourceCards(player.getJSONObject("resources"));
    	Hand hand = new Hand(resources, devCards);
    	PlayerIdx index = new PlayerIdx(player.getInt("playerIndex"));
    	boolean playedDevCard = player.getBoolean("playedDevCard");
    	int playerID = player.getInt("playerID");
    	int roads = player.getInt("roads");
    	int settlements = player.getInt("settlements");
    	int soldiers = player.getInt("soldiers");
    	int victoryPoints = player.getInt("victoryPoints");
    	Player newPlayer  = new Player(cities, newColor, discarded, monuments, name, index,
    			playedDevCard, newDevCards, playerID, roads, settlements, soldiers, victoryPoints, hand);
    	return newPlayer;
    }
    
    private static ArrayList<Player> fromJSONToPlayers(JSONArray players) throws JSONException
    {
    	ArrayList<Player> newPlayers = new ArrayList<>();
    	for (int i = 0; i < players.length(); i++)
    	{
    		if (!players.isNull(i))
    		{
    			newPlayers.add(fromJSONToPlayer(players.getJSONObject(i)));
    		}
    	}
    	return newPlayers;
    }
    
    private static TradeOffer fromJSONToTradeOffer(JSONObject tradeOffer) throws JSONException
    {
    	PlayerIdx sender = new PlayerIdx(tradeOffer.getInt("sender"));
    	PlayerIdx receiver = new PlayerIdx(tradeOffer.getInt("receiver"));
    	ResourceCards cards = fromJSONToResourceCards(tradeOffer.getJSONObject("offer"));
    	TradeOffer newTradeOffer = new TradeOffer(sender, receiver, cards);
    	return newTradeOffer;
    }
    
    private static TurnTracker fromJSONToTurnTracker(JSONObject turnTracker) throws JSONException
    {
    	PlayerIdx currentTurn = new PlayerIdx(turnTracker.getInt("currentTurn"));
    	String status = turnTracker.getString("status");
    	status = status.toLowerCase();
    	TurnStatusEnumeration newStatus;
    	switch (status)
    	{
    	case "rolling":
    		newStatus = TurnStatusEnumeration.rolling;
    		break;
    	case "robbing":
    		newStatus = TurnStatusEnumeration.robbing;
    		break;
    	case "playing":
    		newStatus = TurnStatusEnumeration.playing;
    		break;
    	case "discarding":
    		newStatus = TurnStatusEnumeration.discarding;
    		break;
    	case "firstround":
    		newStatus = TurnStatusEnumeration.firstround;
    		break;
    	case "secondround":
    		newStatus = TurnStatusEnumeration.secondround;
    		break;
    	default:
    		newStatus = null;
    		break;
    	}
    	NullablePlayerIdx longestRoad = new NullablePlayerIdx(turnTracker.getInt("longestRoad"));
    	NullablePlayerIdx largestArmy = new NullablePlayerIdx(turnTracker.getInt("largestArmy"));
    	TurnTracker newTurnTracker = new TurnTracker(currentTurn, newStatus, longestRoad, largestArmy);
    	return newTurnTracker;
    }
    
    private static ArrayList<PlayerJSONResponse> fromJSONToPlayerResponses(JSONArray playersJSON) throws JSONException
    {
    	ArrayList<PlayerJSONResponse> playersArray = new ArrayList<>();
    	for (int i = 0; i < playersJSON.length(); i++)
    	{
    		if (!playersJSON.isNull(i) && playersJSON.getJSONObject(i).has("name")) //letting through some empty brackets
    		{
	    		String colorStr = playersJSON.getJSONObject(i).getString("color");
	    		colorStr = colorStr.toLowerCase();
	    		String name = playersJSON.getJSONObject(i).getString("name");
	    		int id = playersJSON.getJSONObject(i).getInt("id");
	    		CatanColor color;
	        	switch (colorStr)
	        	{
	        	case "blue":
	        		color = CatanColor.BLUE;
	        		break;
	        	case "brown":
	        		color = CatanColor.BROWN;
	        		break;
	        	case "green":
	        		color = CatanColor.GREEN;
	        		break;
	        	case "orange":
	        		color = CatanColor.ORANGE;
	        		break;
	        	case "puce":
	        		color = CatanColor.PUCE;
	        		break;
	        	case "purple":
	        		color = CatanColor.PURPLE;
	        		break;
	        	case "red":
	        		color = CatanColor.RED;
	        		break;
	        	case "white":
	        		color = CatanColor.WHITE;
	        		break;
	        	case "yellow":
	        		color = CatanColor.YELLOW;
	        		break;
	        	default:
	        		color = null;
	        		break;
	        	}
	        	PlayerJSONResponse player = new PlayerJSONResponse(color, name, id);
	        	playersArray.add(player);
    		}
    	}
    	return playersArray;
    }
}
