/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.AcceptTradeRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IModelFacade;
import server.facade.IMovesFacade;

/**
 * Executes the Accept Trade request.
 * @author Scott
 */
public class AcceptTradeCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        AcceptTradeRequest accept = new AcceptTradeRequest(null, false);
        
        try
        {
            accept.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = accept.getAcceptingPlayerIdx();
        boolean willAccept = accept.willAccept();
        
        ClientModel result = myMovesFacade.acceptTrade(playerIdx, willAccept, game, playerId);
        
        // return result.serialize(); or whatever
        return "";
    }
    
}
