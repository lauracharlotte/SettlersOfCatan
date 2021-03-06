/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONSerializer;
import clientcommunicator.operations.RoadBuildingCardRequest;
import model.ClientModel;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
import shared.locations.EdgeLocation;
/**
 * Executes the Road Building request.
 * @author Scott
 */
@SuppressWarnings("serial")
public class RoadBuildingCommand implements ICommand {
    
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
        
        ClientModel result = myMovesFacade.roadBuilding(playerIdx, location1, location2, game, playerId);
        
        myMovesFacade.saveCommand(this, game);

        return JSONSerializer.SerializeModel(result);
    }
    
}
