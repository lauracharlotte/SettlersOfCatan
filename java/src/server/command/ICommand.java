package server.command;

import org.json.JSONException;

import clientcommunicator.Server.Cookie;
import java.io.Serializable;
import server.ServerException;
import server.facade.IModelFacade;

/**
 *
 * @author Michael
 */
public interface ICommand extends Serializable
{
    /**
     * Executes the requested function using the given facade.
     * @param facade The facade needed by the given command class to execute
     * @param requestBody The body that was sent in the http request
     * @return the response body for the httpExchange
     * @throws server.ServerException If an illegal move/command is requested by the client.
     * @throws JSONException 
     */
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException, JSONException;

    public String getRequestBody();
    
    public Cookie getCurrentCookie();
}
