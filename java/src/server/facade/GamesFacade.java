package server.facade;

import java.util.List;

import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import model.ClientModel;
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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a new game
	 * @param request a CreateGameRequest object
	 * @return true if created successfully, false otherwise
	 */
	@Override
	public boolean create(CreateGameRequest request) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Joins a user to a game
	 * @param request a JoinGameRequest object
	 * @return the ClientModel of the game the user has joined
	 */
	@Override
	public ClientModel join(User user, JoinGameRequest request) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
