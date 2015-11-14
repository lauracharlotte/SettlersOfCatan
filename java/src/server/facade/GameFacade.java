package server.facade;

import java.util.List;

import model.ClientModel;
import model.player.User;

/**
 * 
 * @author LaurasAdventurePC
 *
 *The Game Facade Class
 */
public class GameFacade implements IGameFacade 
{
	List<User> theUsers = null;
	List<ClientModel> theGames = null;
	/**
	 * The Constructor
	 * @param users
	 * @param games
	 */
	public GameFacade(List<User> users, List<ClientModel> games)
	{
		theUsers = users;
		theGames = games;
	}
	/**
	 * Access to the model.
	 * @return the model
	 */
	@Override
	public ClientModel model(int gameIndex) {
		// TODO Auto-generated method stub
		ClientModel returnModel = null;
		int gameNumber = 0;//should it start at 0 or at 1??????
		for(ClientModel correctModel: theGames)
		{
			if(gameNumber == gameIndex)
			{
				returnModel = correctModel;			
			}
			gameNumber ++;
		}
		if(returnModel == null)
		{
			//Exception should be thrown or something?
		}
		return returnModel;
	}

}
