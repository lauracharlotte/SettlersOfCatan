package server.command;

import server.facade.IGamesFacade;
import server.facade.IModelFacade;

import org.json.JSONException;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import server.ServerException;
/**
 * Executes the Create Game request.
 * @author Madison Brooks
 *
 */
@SuppressWarnings("serial")
public class CreateCommand implements ICommand
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
	 * Creates a new game
	 * @throws ServerException
	 * @throws JSONException 
	 */
	@Override
	public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException, JSONException
	{
                this.requestBody = requestBody;
                this.currentCookie = currentCookie;
		IGamesFacade myFacade = (IGamesFacade)facade;
		CreateGameRequest create = new CreateGameRequest(false, false, false, null);
		
		create.deserialize(requestBody);
		
		GameJSONResponse result = myFacade.create(create);		
		return result.serialize();
	}

}
