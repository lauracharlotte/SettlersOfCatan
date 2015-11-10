/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.MonopolyRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IModelFacade;
import server.facade.IMovesFacade;
import shared.definitions.ResourceType;

/**
 *
 * @author Scott
 */
public class MonopolyCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        MonopolyRequest monopoly = new MonopolyRequest(null, null);
        
        try
        {
            monopoly.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = monopoly.getPlayerIndex();
        ResourceType resource = monopoly.getResource();
        
        ClientModel result = myMovesFacade.monopoly(resource, playerIdx, game, playerId);
        
        // return result.serialize(); or whatever
        return "";
    }
    
}