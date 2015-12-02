package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONSerializer;
import clientcommunicator.operations.PlaySoldierRequest;
import model.ClientModel;
import model.player.NullablePlayerIdx;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
import shared.locations.HexLocation;
/**
 * Executes the Soldier request.
 * @author Scott
 */
public class SoldierCommand implements ICommand {
    
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
        
        PlaySoldierRequest soldier = new PlaySoldierRequest(null, null, null);
        
        try
        {
            soldier.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = soldier.getPlayerIndex();
        NullablePlayerIdx victimIdx = soldier.getVictimIndex();
        HexLocation location = soldier.getNewLocation();
        
        ClientModel result = myMovesFacade.soldier(playerIdx, victimIdx, location, game, playerId);
        
        myMovesFacade.saveCommand(this, game);
        
        return JSONSerializer.SerializeModel(result);
    }
    
}
