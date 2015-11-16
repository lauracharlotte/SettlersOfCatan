package server.command;

import server.facade.IGamesFacade;
import server.facade.IModelFacade;

import org.json.JSONException;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.JoinGameRequest;
import server.ServerException;
/**
 * Executes the Join Game request.
 * @author Madison Brooks
 *
 */
public class JoinCommand implements ICommand
{

	/**
	 * Joins a user to a game
	 */
	@Override
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
	{
		IGamesFacade myFacade = (IGamesFacade)facade;
		JoinGameRequest join = new JoinGameRequest(0, null);
		
		try 
		{
			join.deserialize(requestBody);
		} 
		catch (JSONException e) 
		{
			return "Invalid JSON in request";
		}
		
		boolean result = myFacade.join(currentCookie.getUser(), join);
		
		if (result)
		{
			currentCookie.setGameNumber(join.getGameId());
			return "Success";
		}
		else
		{
			return "Failed to join game";
		}
	}


}
