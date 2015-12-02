package server.command;

import server.facade.IGamesFacade;
import server.facade.IModelFacade;

import java.util.List;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.GameJSONResponse;
import server.ServerException;

import org.json.*;

/**
 * Executes the List request.
 * @author madisonbrooks
 *
 */
public class ListCommand implements ICommand
{
    
    private String requestBody;
    private Cookie currentCookie;
    
    @Override
    public String getRequestBody() {
        return this.requestBody;
    }

    @Override
    public Cookie getCurrentCookie() {
        return this.currentCookie;
    }
	/**
	 * Lists all of the games on the server
	 */
	@Override
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
	{
                this.requestBody = requestBody;
                this.currentCookie = currentCookie;
		IGamesFacade myFacade = (IGamesFacade)facade;
		List<GameJSONResponse> games = myFacade.list();
		JSONArray gamesJSON = new JSONArray();
		for (int i = 0; i < games.size(); i++)
		{
			gamesJSON.put(games.get(i).serializeToObject());
		}
		return gamesJSON.toString();
	}
}
