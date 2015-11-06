package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
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
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException{
		// TODO Auto-generated method stub
		return null;
	}


}
