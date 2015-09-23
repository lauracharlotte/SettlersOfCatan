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
public class MaritimeTradeRequest implements IJSONSerializable
{
    
        
    private PlayerIdx playerIndex;
    private int ratio;
    private ResourceType inputResource;
    private ResourceType outputResource;
    
    /**
     *
     * @return the index of the player trading
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return the ratio of the trade
     */
    public int getRatio()
    {
        return ratio;
    }

    /**
     *
     * @return the type of resource the player is giving
     */
    public ResourceType getInputResource()
    {
        return inputResource;
    }

    /**
     *
     * @return the type of the resource the player is getting
     */
    public ResourceType getOutputResource()
    {
        return outputResource;
    }

    /**
     *
     * @param playerIndex the index of the player trading
     * @param ratio the ratio of the trade
     * @param inputResource the type of resource the player is giving
     * @param outputResource the type of the resource the player is getting
     */
    public MaritimeTradeRequest(PlayerIdx playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource)
    {
        this.playerIndex = playerIndex;
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    }

    @Override
    public String serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
