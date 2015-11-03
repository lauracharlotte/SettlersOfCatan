package server.command;

import server.facade.IModelFacade;

/**
 *
 * @author Michael
 */
public interface ICommand
{
    /**
     * Executes the requested function using the given facade.
     * @param facade
     * @param requestBody
     * @return 
     */
    public String execute(IModelFacade facade, String requestBody);
}
