/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.player.PlayerIdx;
import org.json.JSONException;
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

    public MaritimeTradeRequest()
    {
        
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
    	String rec1 = inputResource.toString();
    	String rec2 = outputResource.toString();
    	String rec1Lower = rec1.toLowerCase();
    	String rec2Lower = rec2.toLowerCase();
    	String serializing = "{type: \"maritimeTrade\", playerIndex: " + playerIndex.getIndex()
    						+ ", ratio: " + ratio + ", inputResource: \"" +rec1Lower + "\", "
    						+ "outputResource: \""+ rec2Lower + "\"}";
        return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
        this.ratio = obj.get("ratio").getAsInt();
        this.inputResource = ResourceType.valueOf(obj.get("inputResource").getAsString().toUpperCase());
        this.outputResource = ResourceType.valueOf(obj.get("outputResource").getAsString().toUpperCase());
    }
}
