/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.RoadBuildingCardRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
import shared.locations.EdgeLocation;
/**
 * Executes the Road Building request.
 * @author Scott
 */
public class RoadBuildingCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
    {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        RoadBuildingCardRequest rbc = new RoadBuildingCardRequest(null, null, null);
        
        try 
        {
            rbc.deserialize(requestBody);
        } 
        catch (JSONException ex) 
        {
            return "Invalid JSON in request";        
        }
        
        PlayerIdx playerIdx = rbc.getPlayerIndex();
        EdgeLocation location1 = rbc.getSpot1();
        EdgeLocation location2 = rbc.getSpot2();
        
        ClientModel result = myMovesFacade.roadBuilding(playerIdx, location1, location2);
        // return result.serialize(); or whatever
        return "";
    }
    
}
