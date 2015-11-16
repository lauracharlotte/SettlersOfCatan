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
public class CreateGameRequest implements IJSONSerializable
{
    private boolean randomTiles;
    private boolean randomNumbers;
    private boolean randomPorts;
    private String name;
    
    /**
     *
     * @param randomTiles If the tiles should be placed randomly
     * @param randomNumbers If the numbers should be placed on the tiles randomly
     * @param randomPorts If the ports should be placed randomly
     * @param name The name of the game
     */
    public CreateGameRequest(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        this.randomTiles = randomTiles;
        this.randomNumbers = randomNumbers;
        this.randomPorts = randomPorts;
        this.name = name;
    }
    
    public CreateGameRequest()
    {
        
    }
    
    /**
     *
     * @return If the tiles should be placed randomly
     */
    public boolean isRandomTiles()
    {
        return randomTiles;
    }

    /**
     *
     * @return If the numbers should be placed on the tiles randomly
     */
    public boolean isRandomNumbers()
    {
        return randomNumbers;
    }

    /**
     *
     * @return If the ports should be placed randomly
     */
    public boolean isRandomPorts()
    {
        return randomPorts;
    }

    /**
     *
     * @return The name of the game
     */
    public String getName()
    {
        return name;
    }
    
    @Override
    public String serialize()
    {
    	String serializing = "{randomTiles: "+randomTiles +", randomNumbers: "+ randomNumbers+", "
    						+"randomPorts: "+randomPorts+", name: \""+ name +"\"}";
		return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.randomTiles = obj.get("randomTiles").getAsBoolean();
        this.randomNumbers = obj.get("randomNumbers").getAsBoolean();
        this.randomPorts = obj.get("randomPorts").getAsBoolean();
        this.name = obj.get("name").getAsString();
    }
}
