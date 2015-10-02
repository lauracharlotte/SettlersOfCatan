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
     * @param playerIndex The index of the player playing the card
     * @param resource The resource type desired
     */
    public MonopolyRequest(PlayerIdx playerIndex, ResourceType resource)
    {
        this.playerIndex = playerIndex;
        this.resource = resource;
    }
    
    /**
     *
     * @return The index of the player playing the card
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The resource type desired
     */
    public ResourceType getResource()
    {
        return resource;
    }

    
    @Override
    public String serialize()
    {
    	String rec = resource.toString();
    	String recLower = rec.toLowerCase();
    	String serializing = "{type: \"Monopoly\", resource: \"" + recLower
    						+ "\", playerIndex: "+ playerIndex.getIndex() + "}";
        return serializing;
    }
}
