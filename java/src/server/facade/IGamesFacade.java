package server.facade;

/**
 * Interface for facades that deal with games operations
 * @author Madison Brooks
 *
 */
public interface IGamesFacade extends IModelFacade 
{
	public void list();
	public void create();
	public void join();
}
