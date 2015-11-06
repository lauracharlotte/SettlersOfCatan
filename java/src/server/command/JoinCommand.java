package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import server.ServerException;
/**
 * 
 * @author Madison Brooks
 *
 */
public class JoinCommand implements ICommand
{

	/**
	 * Joins a user to a game
	 */
	@Override
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException{
		// TODO Auto-generated method stub
		return null;
	}


}
