package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import server.ServerException;

/**
 * Executes the List request.
 * @author madisonbrooks
 *
 */
public class ListCommand implements ICommand
{

	/**
	 * Lists all of the games on the server
	 */
	@Override
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException{
		// TODO Auto-generated method stub
		return null;
	}


}
