package server.facade;

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
	 */
	void modelCommand();
}
