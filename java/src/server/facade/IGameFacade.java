package server.facade;

import model.ClientModel;

/**
 * 
 * @author LaurasAdventurePC
 *
 * The GameFacade Interface. Holds all the methods for GameFacade class and MockGameFacade class
 */
public interface IGameFacade extends IModelFacade 
{
	/**
	 * Calls on the class ModelCommand to perform functions.
	 * @return the model
	 */
	public  ClientModel model(int gameIndex);
}
