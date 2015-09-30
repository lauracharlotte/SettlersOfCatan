/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.cards.ResourceCards;
import model.player.PlayerIdx;

/**
 *
 * @author Michael
 */
public class BuyDevCardRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;

    
    
    /**
     *
     * @param playerIndex The index of the player that wants to build a development card.
     */
    public BuyDevCardRequest(PlayerIdx playerIndex)
    {
        this.playerIndex = playerIndex;
    }
    
    
    
    @Override
    public String serialize()
    {
    	String serializing = "{type: \"buyDevCard\", playerIndex:" + playerIndex.getPlayerIdx()+"}";
        return serializing;
    	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return The index of the player that wants to build a development card.
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }
    
    public static void main(final String[] args)
    {
    	PlayerIdx index = new PlayerIdx(2);
    	BuyDevCardRequest thisDiscard = new BuyDevCardRequest(index);
    	String work = thisDiscard.serialize();
    	System.out.println(work);
    }
    
}
