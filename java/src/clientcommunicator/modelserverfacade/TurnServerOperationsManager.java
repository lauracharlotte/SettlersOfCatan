/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.FinishTurnRequest;
import clientcommunicator.operations.RobPlayerRequest;
import clientcommunicator.operations.RollNumberRequest;

/**
 *
 * @author Michael
 */
public class TurnServerOperationsManager implements IServerOperationsManager
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
     * @pre It is the player's turn.  The status is rolling.
     * @post The turn status has changed.
     * @param request The request that represents what player rolled what number
     */
    public void rollNumber(RollNumberRequest request) throws ClientException
    { 
        this.currentServer.rollNumber(JSONParser.toJSON(request));
    }
    
    /**
     * @pre It is the player's turn.
     * @post It is not the player's turn anymore.
     * @param request The request that represents a player ending his/her turn.
     */
    public void finishTurn(FinishTurnRequest request) throws ClientException
    {
        this.currentServer.finishTurn(JSONParser.toJSON(request));
    }
    
    /**
     * @pre The player just rolled a 7.
     * @post The robbed player loses a resource (if they have any) to the robbing player.
     * @param request The request that represents the robbing and robbed players.
     */
    public void robPlayer(RobPlayerRequest request) throws ClientException
    {
        this.currentServer.robPlayer(JSONParser.toJSON(request));
    }
}
