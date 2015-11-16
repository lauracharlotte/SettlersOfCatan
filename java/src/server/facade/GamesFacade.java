package server.facade;

import java.util.ArrayList;
import java.util.List;

import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import clientcommunicator.operations.PlayerJSONResponse;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.player.Player;
import model.player.User;
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
		ArrayList<ClientModel> allGames = (ArrayList<ClientModel>)gameManager.getAllGames();
		for (int i = 0; i < allGames.size(); i++)
		{
			ArrayList<Player> allPlayers = (ArrayList<Player>)allGames.get(i).getPlayers();
			ArrayList<PlayerJSONResponse> players = new ArrayList<PlayerJSONResponse>();
			for (int j = 0; j < allPlayers.size(); j++)
			{
				Player p = allPlayers.get(j);
				PlayerJSONResponse player = new PlayerJSONResponse(p.getColor(), p.getName(), p.getPlayerId());
				players.add(player);
			}
			GameJSONResponse game = new GameJSONResponse(allGames.get(i).getTitle(), i, players);
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
	public boolean create(CreateGameRequest request) 
	{
		ClientModel newGame = new ClientModel(request.isRandomTiles(), request.isRandomNumbers(), request.isRandomPorts(), 
				request.getName());
		gameManager.addNewGame(newGame);
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
		ArrayList<Player> players  = (ArrayList<Player>)game.getPlayers();
		DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
		ResourceCards emptyResCards = new ResourceCards(0, 0, 0, 0, 0);
		Player newPlayer = new Player(PLAYER_CITIES, request.getPlayerColor(), false, 0, user.getUsername(), null, false, 
				emptyDevCards, user.getPlayerId(), PLAYER_ROADS, PLAYER_SETTLEMENTS, 0, 0, new Hand(emptyResCards, emptyDevCards));
		players.add(newPlayer);
		game.setPlayers(players);		
		gameManager.replaceGame(request.getGameId(), game);
		return game;
	}
	
}
