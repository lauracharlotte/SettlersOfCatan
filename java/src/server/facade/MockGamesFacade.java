package server.facade;

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
	 */
	@Override
	public void list() 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns a sample result for creating a new game
	 */
	@Override
	public void create() 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns a sample result for joining a game
	 */
	@Override
	public void join() 
	{
		// TODO Auto-generated method stub
		
	}
	
}
