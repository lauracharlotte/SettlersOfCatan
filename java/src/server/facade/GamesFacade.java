package server.facade;

import java.util.List;

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
	 */
	@Override
	public void list() 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Creates a new game
	 */
	@Override
	public void create() 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	@Override
	public void join() 
	{
		// TODO Auto-generated method stub
		
	}

}
