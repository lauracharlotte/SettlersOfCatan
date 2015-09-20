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
public class YearOfPlentyRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private ResourceType resource1;
    private ResourceType resource2;

    /**
     *
     * @param playerIndex The index of the player who is playing the card
     * @param resource1 The first resource desired
     * @param resource2 The second resource desired
     */
    public YearOfPlentyRequest(PlayerIdx playerIndex, ResourceType resource1, ResourceType resource2)
    {
        this.playerIndex = playerIndex;
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    /**
     *
     * @return The index of the player who is playing the card
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The first resource desired
     */
    public ResourceType getResource1()
    {
        return resource1;
    }

    /**
     *
     * @return The second resource desired
     */
    public ResourceType getResource2()
    {
        return resource2;
    }
    
    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
