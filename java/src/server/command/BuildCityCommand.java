/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONSerializer;
import clientcommunicator.operations.BuildCityRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IModelFacade;
import server.facade.IMovesFacade;
import shared.locations.VertexLocation;

/**
 * Executes the Build City request.
 * @author Scott
 */
public class BuildCityCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        BuildCityRequest city = new BuildCityRequest(null, null);
        
        try
        {
            city.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = city.getPlayerIndex();
        VertexLocation location = city.getLocation();
        
        ClientModel result = myMovesFacade.buildCity(playerIdx, location, game, playerId);
        
        myMovesFacade.saveCommand(requestBody, currentCookie);
        
        return JSONSerializer.SerializeModel(result);
    }
    
}
