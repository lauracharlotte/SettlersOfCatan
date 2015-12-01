/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;
import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONSerializer;
import clientcommunicator.operations.MaritimeTradeRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IModelFacade;
import server.facade.IMovesFacade;
import shared.definitions.ResourceType;

/**
 * Executes the Maritime Trade request.
 * @author Scott
 */
public class MaritimeTradeCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        MaritimeTradeRequest maritime = new MaritimeTradeRequest(null, 0, null, null);
        
        try
        {
            maritime.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = maritime.getPlayerIndex();
        int ratio = maritime.getRatio();
        ResourceType input = maritime.getInputResource();
        ResourceType output = maritime.getOutputResource();
        
        ClientModel result = myMovesFacade.maritimeTrade(playerIdx, ratio, input, output, game, playerId);
        
        myMovesFacade.saveCommand(this);
        
        return JSONSerializer.SerializeModel(result);
    }
    
}
