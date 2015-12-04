package server.facade;

import model.ClientModel;
import server.model.GameManager;
import server.model.UserManager;

/**
 * 
 * @author LaurasAdventurePC
 *
 *The Game Facade Class
 */
public class GameFacade implements IGameFacade 
{
        @SuppressWarnings("unused")
		private UserManager userManager;
        private GameManager gameManager;

	/**
	 * The Constructor
	 * @param userManager
	 * @param gameManager
	 */
	public GameFacade(UserManager userManager, GameManager gameManager)
	{
            this.userManager = userManager;
            this.gameManager = gameManager;
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
		for(ClientModel correctModel: gameManager.getAllGames())
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
