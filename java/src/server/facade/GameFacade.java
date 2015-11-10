package server.facade;

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
	public void model() {
		// TODO Auto-generated method stub
		
	}

}
