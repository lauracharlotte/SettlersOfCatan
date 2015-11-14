package server.command;

import server.facade.IGamesFacade;
import server.facade.IModelFacade;

import org.json.JSONException;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.CreateGameRequest;
import model.ClientModel;
import server.ServerException;
/**
 * Executes the Create Game request.
 * @author Madison Brooks
 *
 */
public class CreateCommand implements ICommand
{

	/**
	 * Creates a new game
	 */
	@Override
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
	{
		IGamesFacade myFacade = (IGamesFacade)facade;
		CreateGameRequest create = new CreateGameRequest(false, false, false, null);
		
		try 
		{
			create.deserialize(requestBody);
		} 
		catch (JSONException e) 
		{
			return "Invalid JSON in request";
		}
		
		boolean result = myFacade.create(create);
		
		if (result) 
		{
			return "Success";
		}
		else 
		{
			return "Failed to create new game";
		}
	}


}
