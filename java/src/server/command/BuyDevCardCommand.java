/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.BuyDevCardRequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
/**
 * Executes the Buy Development Card request.
 * @author Scott
 */
public class BuyDevCardCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException{
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        BuyDevCardRequest buy = new BuyDevCardRequest(null);
        
        try 
        {
            buy.deserialize(requestBody);
        } 
        catch (JSONException ex) 
        {
            return "Invalid JSON in request";        
        }
        
        PlayerIdx playerIdx = buy.getPlayerIndex();
        
        ClientModel result = myMovesFacade.buyDevCard(playerIdx, game, playerId);
        // return result.serialize(); or whatever
        return "";
    }
    
}
