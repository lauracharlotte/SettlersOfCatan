package server.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.map.CatanMap;
import model.map.EdgeObject;
import model.map.Hex;
import model.map.Port;
import model.map.VertexObject;
import model.messages.MessageLine;
import model.messages.MessageList;
import model.player.Player;
import model.player.User;
import server.model.GameManager;
import server.model.UserManager;
import shared.definitions.HexType;
import shared.locations.HexLocation;

/**
 * Facade to the server model that deals with games operations
 * @author Madison Brooks
 *
 */
public class GamesFacade implements IGamesFacade 
{
	private UserManager userManager;
    private GameManager gameManager;
        
	/**
	 * The Constructor
	 * @param userManager
	 * @param gameManager
	 */
	public GamesFacade(UserManager userManager, GameManager gameManager)
	{
    	this.userManager = userManager;
    	this.gameManager = gameManager;
	}
	
	/**
	 * Gets the list of games
	 * @return list of games in the form of GameJSONResponses
	 */
	@Override
	public List<GameJSONResponse> list() 
	{
		List<GameJSONResponse> games = new ArrayList<GameJSONResponse>();
		//Add new method to gameManager to get all games?
		return games;
	}

	/**
	 * Creates a new game
	 * @param request a CreateGameRequest object
	 * @return true if created successfully, false otherwise
	 */
	@Override
	public boolean create(CreateGameRequest request) 
	{
		//Create game with randomness
		Hand bank = createNewBank();
		MessageList chat = new MessageList(new ArrayList<MessageLine>());
		MessageList log = new MessageList(new ArrayList<MessageLine>());
		CatanMap map = createNewMap(request.isRandomTiles(), request.isRandomNumbers(), request.isRandomPorts());
		//ClientModel newGame = new ClientModel(bank, chat, log, map);
		//gameManager.addNewGame(newGame);
		return true;
	}

	/**
	 * Joins a user to a game
	 * @param request a JoinGameRequest object
	 * @return the ClientModel of the game the user has joined
	 */
	@Override
	public ClientModel join(User user, JoinGameRequest request) 
	{
		ClientModel game = gameManager.getGameWithNumber(request.getGameId());
		ArrayList<Player> players  = (ArrayList)game.getPlayers();
		DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
		ResourceCards emptyResCards = new ResourceCards(0, 0, 0, 0, 0);
		Player newPlayer = new Player(4, request.getPlayerColor(), false, 0, user.getUsername(), null, false, 
				emptyDevCards, user.getPlayerId(), 15, 5, 0, 0, new Hand(emptyResCards, emptyDevCards));
		//Add new player to list of players
		//Replace players in ClientModel
		//Replace game in gameManager
		return game;
	}
	
	//<-----------------------------ClientModel creation functions---------------------------------->
	
	private Hand createNewBank()
	{
		ResourceCards resourceCards = new ResourceCards(19, 19, 19, 19, 19);
		DevelopmentCards developmentCards = new DevelopmentCards(2, 5, 2, 14, 2);
		Hand bank = new Hand(resourceCards, developmentCards);
		return bank;
	}
	
	private CatanMap createNewMap(boolean randomTiles, boolean randomNumbers, boolean randomPorts)
	{
		Collection<Hex> hexes = createNewHexes(randomTiles, randomNumbers);
		Collection<Port> ports = createNewPorts(randomPorts);
		Collection<EdgeObject> roads = new ArrayList<EdgeObject>();
		Collection<VertexObject> settlements = new ArrayList<VertexObject>();
		Collection<VertexObject> cities = new ArrayList<VertexObject>();
		int radius = 4;
		HexLocation robber = findDesertHex(hexes);
		CatanMap map = new CatanMap(hexes, ports, roads, settlements, cities, radius, robber);
		return map;
	}
	
	private int[] generateRandomChits()
	{
		int numbers[] = new int[]{2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
		ArrayList<Integer> availableNums = new ArrayList<Integer>();
		for (int i = 0; i < numbers.length; i++)
		{
			availableNums.add(numbers[i]);
		}
		int randomNums[] = new int[18];
		int nextIndex = 0;
		while (!availableNums.isEmpty())
		{
			int randomIndex = (int)(Math.random() * (availableNums.size() + 1));
			randomNums[nextIndex] = availableNums.get(randomIndex);
			nextIndex++;
		}
		return randomNums;
	}
	
	private HexType[] generateRandomTypes()
	{
		HexType types[] = new HexType[]{HexType.BRICK, HexType.BRICK, HexType.BRICK, 
				HexType.ORE, HexType.ORE, HexType.ORE, 
				HexType.SHEEP, HexType.SHEEP, HexType.SHEEP, HexType.SHEEP,
				HexType.WHEAT, HexType.WHEAT, HexType.WHEAT, HexType.WHEAT,
				HexType.WOOD, HexType.WOOD, HexType.WOOD, HexType.WOOD, 
				HexType.DESERT};
		ArrayList<HexType> availableTypes = new ArrayList<HexType>();
		for (int i = 0; i < types.length; i++)
		{
			availableTypes.add(types[i]);
		}
		HexType randomTypes[] = new HexType[19];
		int nextIndex = 0;
		while (!availableTypes.isEmpty())
		{
			int randomIndex = (int)(Math.random() * (availableTypes.size() + 1));
			randomTypes[nextIndex] = availableTypes.get(randomIndex);
			nextIndex++;
		}
		return randomTypes;
	}
	
	private Collection<Hex> createWaterHexes()
	{
		Collection<Hex> hexes = new ArrayList<Hex>();
		int x = -3;
		int y;
		for (y = 0; y <= 3; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, HexType.WATER, -1);
			hexes.add(hex);
		}
		x = 3;
		for (y = 0; y >= -3; y--)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, HexType.WATER, -1);
			hexes.add(hex);
		}
		y = -3;
		for (x = 0; x <= 2; x++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, HexType.WATER, -1);
			hexes.add(hex);
		}
		y = 3;
		for (x = 0; x >= -2; x--)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, HexType.WATER, -1);
			hexes.add(hex);
		}
		HexLocation loc = new HexLocation(-2, -1);
		Hex hex = new Hex(loc, HexType.WATER, -1);
		hexes.add(hex);
		loc = new HexLocation(-1, -2);
		hex = new Hex(loc, HexType.WATER, -1);
		hexes.add(hex);
		loc = new HexLocation(2, 1);
		hex = new Hex(loc, HexType.WATER, -1);
		hexes.add(hex);
		loc = new HexLocation(1, 2);
		hex = new Hex(loc, HexType.WATER, -1);
		hexes.add(hex);
		return hexes;
	}
	
	private Collection<Hex> createLandHexes(HexType[] types, int[] chits)
	{
		Collection<Hex> hexes = new ArrayList<Hex>();
		int typeIndex = 0;
		int chitIndex = 0;
		int x = -2;
		int y;
		for (y = 0; y <= 2; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		x = -1;
		for (y = -1; y <= 2; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		x = 0;
		for (y = -2; y <= 2; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		x = 1;
		for (y = -2; y <= 1; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		x = 2;
		for (y = -2; y <= 0; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		return hexes;
	}
	
	private Collection<Hex> createNewHexes(boolean randomTiles, boolean randomNumbers)
	{
		int chits[] = new int[18];
		if (randomNumbers)
		{
			chits = generateRandomChits();
		}
		else
		{
			chits = new int[]{5, 2, 6, 8, 10, 9, 3, 3, 11, 4, 8, 4, 9, 5, 10, 11, 12, 6};
		}
		HexType types[] = new HexType[19];
		if (randomTiles)
		{
			types = generateRandomTypes();
		}
		else
		{
			types = new HexType[]{HexType.ORE, HexType.WHEAT, HexType.WOOD, HexType.BRICK,
					HexType.SHEEP, HexType.SHEEP, HexType.ORE, HexType.DESERT, HexType.WOOD,
					HexType.WHEAT, HexType.WOOD, HexType.WHEAT, HexType.BRICK, HexType.ORE,
					HexType.BRICK, HexType.SHEEP, HexType.WOOD, HexType.SHEEP, HexType.WHEAT};
		}
		ArrayList<Hex> hexes = new ArrayList<Hex>();
		hexes.addAll(createWaterHexes());
		hexes.addAll(createLandHexes(types, chits));
		return hexes;
	}
	
	private Collection<Port> createNewPorts(boolean randomPorts)
	{
		
		return null;
	}
	
	private HexLocation findDesertHex(Collection<Hex> hexes)
	{
		Iterator<Hex> iterator = hexes.iterator();
		while (iterator.hasNext())
		{
			Hex nextHex = iterator.next();
			if (nextHex.getType() == HexType.DESERT)
			{
				return nextHex.getLocation();
			}
		}
		return null;
	}

}
