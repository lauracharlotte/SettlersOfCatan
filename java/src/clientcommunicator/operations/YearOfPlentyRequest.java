/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.cards.ResourceCards;
import model.player.PlayerIdx;
import org.json.JSONException;
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
    
    public YearOfPlentyRequest()
    {
        
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
    public String serialize()
    {
    	String rec1 = resource1.toString();
    	String recLower1 = rec1.toLowerCase();
    	String rec2 = resource2.toString();
    	String recLower2 = rec2.toLowerCase();
    	String serializing = "{type: \"Year_of_Plenty\", playerIndex: " + playerIndex.getIndex()
    	+ ", resource1: \"" + recLower1 + "\", resource2: \""+ recLower2 + "\"}";
    	return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
        this.resource1 = ResourceType.valueOf(obj.get("resource1").getAsString().toUpperCase());
        this.resource2 = ResourceType.valueOf(obj.get("resource2").getAsString().toUpperCase());
    }
}
