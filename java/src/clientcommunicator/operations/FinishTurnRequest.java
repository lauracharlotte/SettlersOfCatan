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
public class FinishTurnRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;

    /**
     *
     * @param playerIndex The index of the player who is finishing their turn
     */
    public FinishTurnRequest(PlayerIdx playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    public FinishTurnRequest()
    {
        
    }
    
    /**
     *
     * @return The index of the player who is finishing their turn
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    @Override
    public String serialize()
    {
    	String serializing = "{type: \"finishTurn\", playerIndex: " + playerIndex.getIndex() + "}";
        return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
    }
}
