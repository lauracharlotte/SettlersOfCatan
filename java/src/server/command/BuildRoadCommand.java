/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONSerializer;
import clientcommunicator.operations.BuildRoadRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
import shared.locations.EdgeLocation;
/**
 * Executes the Build Road request.
 * @author Scott
 */
public class BuildRoadCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        BuildRoadRequest road = new BuildRoadRequest(null, null, false);
        
        try
        {
            road.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = road.getPlayerIndex();
        EdgeLocation resource = road.getLocation();
        boolean free = road.isFree();
        
        ClientModel result = myMovesFacade.buildRoad(playerIdx, resource, free, game, playerId);
        
        return JSONSerializer.SerializeModel(result);
    }
    
}
