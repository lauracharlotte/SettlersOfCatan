/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.player.NullablePlayerIdx;
import model.player.PlayerIdx;
import org.json.JSONException;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

/**
 *
 * @author Michael
 */
public class RobPlayerRequest implements IJSONSerializable
{

    private PlayerIdx playerThatsRobbingIndex;
    private NullablePlayerIdx victimIndex;
    private HexLocation location;

    /**
     *
     * @param playerThatsRobbingIndex The player that is robbing.
     * @param victimIndex The player that is being robbed.
     * @param location The new robber location.
     */
    public RobPlayerRequest(PlayerIdx playerThatsRobbingIndex, NullablePlayerIdx victimIndex, HexLocation location)
    {
        this.playerThatsRobbingIndex = playerThatsRobbingIndex;
        this.victimIndex = victimIndex;
        this.location = location;
    }
    
    public RobPlayerRequest()
    {
        
    }
    
    /**
     *
     * @return The player that is robbing.
     */
    public PlayerIdx getPlayerThatsRobbingIndex()
    {
        return playerThatsRobbingIndex;
    }

    /**
     *
     * @return The player that is being robbed.
     */
    public NullablePlayerIdx getVictimIndex()
    {
        return victimIndex;
    }

    /**
     *
     * @return The new robber location.
     */
    public HexLocation getLocation()
    {
        return location;
    }
    
    @Override
    public String serialize()
    {
    	String serializing = "{type: \"robPlayer\", playerIndex: "+ playerThatsRobbingIndex.getIndex() +", victimIndex: "+victimIndex.getIndex() + ", location: {x: "+location.getX()+ ", y: " + location.getY()+ "}}";
        return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerThatsRobbingIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
        this.victimIndex = new NullablePlayerIdx(obj.get("victimIndex").getAsInt());
        JsonObject locationObj = obj.getAsJsonObject("location");
        int x = locationObj.get("x").getAsInt();
        int y = locationObj.get("y").getAsInt();
        this.location = new HexLocation(x,y);
    }
}
