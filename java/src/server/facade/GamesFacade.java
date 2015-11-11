package server.facade;

import java.util.List;

import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.JoinGameRequest;
import model.ClientModel;
import model.player.User;

/**
 * Facade to the server model that deals with games operations
 * @author Madison Brooks
 *
 */
public class GamesFacade implements IGamesFacade 
{
	private List<User> users;
	private List<ClientModel> games;
	
	/**
	 * Constructor
	 * @param users Non-persistent list of Users
	 * @param games Non-persistent list of games
	 */
	public GamesFacade(List<User> users, List<ClientModel> games)
	{
		this.users = users;
		this.games = games;
	}
	
	/**
	 * Gets the list of games
	 * @return list of games in the form of ClientModels
	 */
	@Override
	public List<ClientModel> list() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a new game
	 */
	@Override
	public boolean create(CreateGameRequest request) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Joins a user to a game
	 * @param user the User to be joined
	 * @param gameIndex the index of the game in the list of ClientModels
	 */
	@Override
	public ClientModel join(User user, JoinGameRequest request) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
