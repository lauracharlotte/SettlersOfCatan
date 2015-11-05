package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
/**
 * 
 * @author LaurasAdventurePC
 * This class mainly gives the model of the game.
 */
public class ModelCommand implements ICommand 
{
	/**
	 * The constructor
	 */
	public ModelCommand()
	{
		
	}
	
	/**
	 * Gives the Model for that game.
	 */
	@Override
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) {
		// TODO Auto-generated method stub
		return null;
	}

}
