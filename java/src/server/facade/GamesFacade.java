package server.facade;

import java.util.ArrayList;
import java.util.List;

import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.player.Player;
import model.player.User;
import server.ServerException;
import server.model.GameManager;
import server.model.UserManager;

/**
 * Facade to the server model that deals with games operations
 * @author Madison Brooks
 *
 */
public class GamesFacade implements IGamesFacade 
{
	private UserManager userManager;
    private GameManager gameManager;
    
    private final int PLAYER_CITIES = 4;
    private final int PLAYER_ROADS = 15;
    private final int PLAYER_SETTLEMENTS = 5; 
    private final int FULL_GAME = 4;    
    
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
		ArrayList<ClientModel> allGames = new ArrayList<>(gameManager.getAllGames());
		for (int i = 0; i < allGames.size(); i++)
		{
			GameJSONResponse game = new GameJSONResponse(allGames.get(i), i);
			games.add(game);
		}
		return games;
	}

	/**
	 * Creates a new game
	 * @param request a CreateGameRequest object
	 * @return true if created successfully, false otherwise
	 */
	@Override
	public GameJSONResponse create(CreateGameRequest request) 
	{
		ClientModel newGame = new ClientModel(request.isRandomTiles(), request.isRandomNumbers(), request.isRandomPorts(), 
				request.getName());
		gameManager.addNewGame(newGame);
		return new GameJSONResponse(newGame, gameManager.getAllGames().size()-1);
	}

	/**
	 * Joins a user to a game
	 * @param request a JoinGameRequest object
	 * @return the ClientModel of the game the user has joined
	 * @throws ServerException 
	 */
	@Override
	public boolean join(User user, JoinGameRequest request) throws ServerException 
	{
		//Check for valid game ID
		int greatestID = gameManager.getAllGames().size() - 1;
		if (request.getGameId() > greatestID || request.getGameId() < 0)
		{
			throw new ServerException("Invalid game ID");
		}
		ClientModel game = gameManager.getGameWithNumber(request.getGameId());
		ArrayList<Player> players  = (ArrayList<Player>)game.getPlayers();
		int playerIndex = find(user.getUsername(), players);
		if (playerIndex == -1)
		{
			if (players.size() == FULL_GAME)
			{
				throw new ServerException("Cannot join a full game");
			}
			else
			{
				DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
				ResourceCards emptyResCards = new ResourceCards(0, 0, 0, 0, 0);
				Player newPlayer = new Player(PLAYER_CITIES, request.getPlayerColor(), false, 0, user.getUsername(), null, false, 
						emptyDevCards, user.getPlayerId(), PLAYER_ROADS, PLAYER_SETTLEMENTS, 0, 0, new Hand(emptyResCards, emptyDevCards));
				players.add(newPlayer);
			}
		}
		else
		{
			//Change color
			players.get(playerIndex).setColor(request.getPlayerColor());
		}
		game.setPlayers(players);		
		gameManager.replaceGame(request.getGameId(), game);
		return true;
	}
	
	private int find(String username, ArrayList<Player> players)
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getName() == username)
			{
				return i;
			}
		}
		return -1;
	}
}
