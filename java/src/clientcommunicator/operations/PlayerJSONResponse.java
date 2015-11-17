/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import org.json.JSONException;
import org.json.JSONObject;

import shared.definitions.CatanColor;

/**
 *
 * @author Michael
 */
public class PlayerJSONResponse implements IJSONSerializable
{

    private CatanColor color;
    private String name;
    private int id;

    /**
     *
     * @param color The color of this player
     * @param name The name of this player
     * @param id The id of this player
     */
    public PlayerJSONResponse(CatanColor color, String name, int id)
    {
        this.color = color;
        this.name = name;
        this.id = id;
    }
    
    /**
     *
     * @return The color of this player
     */
    public CatanColor getColor()
    {
        return color;
    }

    /**
     *
     * @return The name of this player
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return The id of this player
     */ 
    public int getId()
    {
        return id;
    }

    /**
     *
     * @return
     */
    public boolean isEmptyPlayer()
    {
        throw new UnsupportedOperationException();
    }

	@Override
	public String serialize()
	{
		
		return this.serializeToObject().toString();
	}
        
        public JSONObject serializeToObject()
        {
            JSONObject player = new JSONObject();
            try 
            {
                    player.put("color", color.toString());
                    player.put("name", name);
                    player.put("playerID", id);
            } 
            catch (JSONException e) 
            {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            return player;
        }

	@Override
	public void deserialize(String JSON) throws JSONException 
	{
		JSONObject player = new JSONObject(JSON);
		color = CatanColor.valueOf(player.getString("color"));
		name = player.getString("name");
		id = player.getInt("playerID");	
	}
}
