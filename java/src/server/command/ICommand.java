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
     * @param facade The facade needed by the given command class to execute
     * @param requestBody The body that was sent in the http request
     * @return 
     */
    public String execute(IModelFacade facade, String requestBody);
}
