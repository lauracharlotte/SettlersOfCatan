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
import shared.definitions.CatanColor;

/**
 *
 * @author Michael
 */
public class JoinGameRequest implements IJSONSerializable
{

    private int gameId;
    private CatanColor playerColor;

    /**
     *
     * @param gameId The gameId that the player would like to join
     * @param playerColor The color the player would like to use
     */
    public JoinGameRequest(int gameId, CatanColor playerColor)
    {
        this.gameId = gameId;
        this.playerColor = playerColor;
    }

    public JoinGameRequest()
    {
        
    }
    
    /**
     *
     * @return The gameId number that the player would like to join
     */
    public int getGameId()
    {
        return gameId;
    }

    /**
     *
     * @return The color the player would like to use
     */
    public CatanColor getPlayerColor()
    {
        return playerColor;
    }
    
    @Override
    public String serialize()
    {
    	String stringColor = playerColor.toString();
    	String lowerCaseColor = stringColor.toLowerCase();
    	String serializing = "{id: "+ gameId + ", color: \""+ lowerCaseColor+"\"}";
    	return serializing;
    }    

    @Override
    public void deserialize(String JSON) throws JSONException
    {
            JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
            this.gameId = obj.get("id").getAsInt();
            this.playerColor = CatanColor.valueOf(obj.get("color").getAsString().toUpperCase());
    }
}
