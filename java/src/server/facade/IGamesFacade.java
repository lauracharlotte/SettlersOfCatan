package server.facade;

import java.util.List;

import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import model.ClientModel;
import model.player.User;

/**
 * Interface for facades that deal with games operations
 * @author Madison Brooks
 *
 */
public interface IGamesFacade extends IModelFacade 
{
	/**
	 * Gets the list of games
	 * @return list of games in the form of GameJSONResponses
	 */
	public List<GameJSONResponse> list();
	
	/**
	 * Creates a new game
	 * @param request a CreateGameRequest object
	 * @return true if created successfully, false otherwise
	 */
	public boolean create(CreateGameRequest request);
	
	/**
	 * Joins a user to a game
	 * @param request a JoinGameRequest object
	 * @return the ClientModel of the game the user has joined
	 */
	public ClientModel join(User user, JoinGameRequest request);

}
