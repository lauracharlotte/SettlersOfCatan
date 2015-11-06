package server.facade;

import java.util.List;

import model.ClientModel;
import model.player.User;

/**
 * Interface for facades that deal with games operations
 * @author Madison Brooks
 *
 */
public interface IGamesFacade extends IModelFacade 
{
	/**
	 * Gets the list of games
	 * @return list of games in the form of ClientModels
	 */
	public List<ClientModel> list();
	
	/**
	 * Creates a new game
	 */
	public ClientModel create();
	
	/**
	 * Joins a user to a game
	 * @param user the User to be joined
	 * @param gameIndex the index of the game in the list of ClientModels
	 */
	public ClientModel join(User user, int gameIndex);

}
