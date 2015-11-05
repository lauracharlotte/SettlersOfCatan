package server.facade;

import java.util.List;

import model.ClientModel;
import model.player.User;

/**
 * Mock facade that deals with games operations; does not actually access the server model
 * @author Madison Brooks
 *
 */
public class MockGamesFacade implements IGamesFacade 
{
	/**
	 * Constructor
	 */
	public MockGamesFacade()
	{
		
	}
	
	/**
	 * Returns a sample list of games (ClientModels)
	 * @return sample list
	 */
	@Override
	public List<ClientModel> list() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns a sample result for creating a new game
	 */
	@Override
	public ClientModel create() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns a sample result for joining a game
	 */
	@Override
	public ClientModel join(User user, int gameIndex) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
