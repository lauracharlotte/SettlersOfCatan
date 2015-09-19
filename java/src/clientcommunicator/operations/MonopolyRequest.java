/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.definitions.ResourceType;

/**
 *
 * @author Michael
 */
public class MonopolyRequest implements IJSONSerializable
{
    
    
    private PlayerIdx playerIndex;
    private ResourceType resource;

    /**
     *
     * @param playerIndex
     * @param resource
     */
    public MonopolyRequest(PlayerIdx playerIndex, ResourceType resource)
    {
        this.playerIndex = playerIndex;
        this.resource = resource;
    }
    
    /**
     *
     * @return
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return
     */
    public ResourceType getResource()
    {
        return resource;
    }

    
    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
