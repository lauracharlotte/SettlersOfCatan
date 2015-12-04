package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONSerializer;
import clientcommunicator.operations.YearOfPlentyRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
import shared.definitions.ResourceType;
/**
 * Executes the Year of Plenty request.
 * @author Scott
 */
@SuppressWarnings("serial")
public class YearofPlentyCommand implements ICommand {
    
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

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
    {
        this.requestBody = requestBody;
        this.currentCookie = currentCookie;
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        YearOfPlentyRequest yop = new YearOfPlentyRequest(null, null, null);
        
        try 
        {
            yop.deserialize(requestBody);
        } 
        catch (JSONException ex) 
        {
            return "Invalid JSON in request";        
        }
        
        PlayerIdx playerIdx = yop.getPlayerIndex();
        ResourceType resource1 = yop.getResource1();
        ResourceType resource2 = yop.getResource2();
        
        ClientModel result = myMovesFacade.yearOfPlenty(playerIdx, resource1, resource2, game, playerId);
        
        myMovesFacade.saveCommand(this, game);

        return JSONSerializer.SerializeModel(result);
    }
    
}
