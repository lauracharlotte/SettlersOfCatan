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

/**
 *
 * @author Michael
 */
public class SendChatRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private String content;

    public SendChatRequest()
    {
        
    }
    
    /**
     *
     * @return The index of the player sending the message
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The message being sent
     */
    public String getContent()
    {
        return content;
    }

    /**
     *
     * @param playerIndex The index of the player sending the message
     * @param content The message being sent
     */
    public SendChatRequest(PlayerIdx playerIndex, String content)
    {
        this.playerIndex = playerIndex;
        this.content = content;
    }
       
    
    @Override
    public String serialize()
    {
    	String serializing = "{type: \"sendChat\", playerIndex: " + playerIndex.getIndex() + ", content: \"" + content +"\"}";
        return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
        this.content = obj.get("content").getAsString();
    }
}
