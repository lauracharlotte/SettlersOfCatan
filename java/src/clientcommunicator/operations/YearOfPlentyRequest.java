/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.cards.ResourceCards;
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
    public String serialize()//good, just need Enum
    {
    	String rec1 = resource1.toString();
    	String recLower1 = rec1.toLowerCase();
    	String rec2 = resource2.toString();
    	String recLower2 = rec2.toLowerCase();
    	String serializing = "{type: \"Year_of_Plenty\", playerIndex: " + playerIndex.getIndex()
    	+ ", resource1: \"" + recLower1 + "\", resource2: \""+ recLower2 + "\"}";
    	return serializing;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(final String[] args)
    {
    	
    	PlayerIdx index = new PlayerIdx(2);
    	ResourceType firstResource = ResourceType.BRICK;
    	ResourceType secondResource = ResourceType.ORE;
    	YearOfPlentyRequest yearOfPlentReq = new YearOfPlentyRequest(index, firstResource, secondResource);
    	String work = yearOfPlentReq.serialize();
    	System.out.println(work);
    }
    
}
