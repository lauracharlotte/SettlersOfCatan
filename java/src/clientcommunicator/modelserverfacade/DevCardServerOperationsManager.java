/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.MonopolyRequest;
import clientcommunicator.operations.MonumentRequest;
import clientcommunicator.operations.PlaySoldierRequest;
import clientcommunicator.operations.RoadBuildingCardRequest;
import clientcommunicator.operations.YearOfPlentyRequest;

/**
 *
 * @author Michael
 */
public class DevCardServerOperationsManager implements IServerOperationsManager
{
    private IServerProxy currentServer;
    
    @Override
    public void setServer(IServerProxy serverToUse) 
    {
        if(serverToUse == null)
            throw new IllegalArgumentException("Cannot set server to null.");
        this.currentServer = serverToUse;
    }

    /**
     * @pre The server has been set and the player has a yearOfPlenty card
     * @post The year of plenty card is played and the player has the resulting resources
     * @param request A valid YearOfPlentyRequest
     */
    public void yearOfPlenty(YearOfPlentyRequest request)
    {
        this.currentServer.yearOfPlenty(JSONParser.toJSON(request));
    }
    
    /**
     * @pre The player has a roadbuilding card and the server has been set. Also, the player hasn't played a dev card this turn.
     * @post The player has two less roads, because they are put on the map in the given locations.
     * @param request A valid RoadBuildingCardRequest
     */
    public void playRoadBuilding(RoadBuildingCardRequest request)
    {
        this.currentServer.playRoadBuilding(JSONParser.toJSON(request));
    }
    
    /**
     * @pre The player has a soldier card and the server has been set. Also, the player hasn't played a dev card this turn.
     * @post The robber is moved.  The player playing the card may or may not steal a card from another player.
     * @param request A valid PlaySoldierRequest
     */
    public void playSoldier(PlaySoldierRequest request) 
    {
        this.currentServer.playSoldier(JSONParser.toJSON(request));
    }
    
    /**
     * @pre The player has a monopoly card and the server has been set.  Also, the player hasn't played a dev card this turn.
     * @param request A valid MonopolyRequest 
     */
    public void playMonopoly(MonopolyRequest request)
    {
        this.currentServer.playMonopoly(JSONParser.toJSON(request));
    }
    
    /**
     * @pre The player has enough victory points to win (with the monument) -- the player has a monument card
     * @post The player wins
     * @param request A valid MonumentRequest
     */
    public void playMonument(MonumentRequest request)
    {
        this.currentServer.playMonument(JSONParser.toJSON(request));
    }
    
}
