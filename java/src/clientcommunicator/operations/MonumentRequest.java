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
public class MonumentRequest implements IJSONSerializable
{
    
    
    private PlayerIdx playerIndex;
    
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
     * @param playerIndex The index of the player playing the card
     */
    public MonumentRequest(PlayerIdx playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    
    @Override
    public String serialize()
    {
    	String serializing = "{type: \"Monument\", playerIndex: "+ playerIndex.getPlayerIdx()+ "}";
    	return serializing;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(final String[] args)
    {
    	PlayerIdx index = new PlayerIdx(2);
    	MonumentRequest monuReq = new MonumentRequest(index);
    	String work = monuReq.serialize();
    	System.out.println(work);
    }
    
}
