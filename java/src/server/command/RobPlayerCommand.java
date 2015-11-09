/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.RobPlayerRequest;
import model.ClientModel;
import model.player.NullablePlayerIdx;
import model.player.PlayerIdx;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
import shared.locations.HexLocation;
/**
 * Executes the Rob Player request.
 * @author Scott
 */
public class RobPlayerCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
    {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        RobPlayerRequest rob = new RobPlayerRequest(null, null, null);
        
        try
        {
            rob.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = rob.getPlayerThatsRobbingIndex();
        NullablePlayerIdx victimIdx = rob.getVictimIndex();
        HexLocation location = rob.getLocation();
        
        ClientModel result = myMovesFacade.robPlayer(playerIdx, victimIdx, location);
        
        // return result.serialize(); or whatever
        return "";
    }
    
}
