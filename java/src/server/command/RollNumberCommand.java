/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.RollNumberRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
/**
 * Executes the Roll Number request.
 * @author Scott
 */
public class RollNumberCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
    {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        RollNumberRequest roll = new RollNumberRequest(null, 0);
        
        try
        {
            roll.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = roll.getPlayerIndex();
        int number = roll.getNumberRolled();
        
        ClientModel result = myMovesFacade.rollNumber(playerIdx, number, game, playerId);
        
        // return result.serialize(); or whatever
        return "";
    }
    
}
