package server.command;

import server.facade.IGameFacade;
import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import server.ServerException;
/**
 * This class gives the model of the game.
 * @author LaurasAdventurePC
 * 
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
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
        {
		// TODO Auto-generated method stub //facade to Game Model
		IGameFacade gameFacade = (IGameFacade) facade;
		
		int gameNumber = currentCookie.getGameNumber();
		gameFacade.model(gameNumber);
		
		
		
		return null;
	}

}
