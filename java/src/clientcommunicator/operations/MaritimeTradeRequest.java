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
    
    
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    public int getRatio()
    {
        return ratio;
    }

    public ResourceType getInputResource()
    {
        return inputResource;
    }

    public ResourceType getOutputResource()
    {
        return outputResource;
    }

    public MaritimeTradeRequest(PlayerIdx playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource)
    {
        this.playerIndex = playerIndex;
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    }

    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
