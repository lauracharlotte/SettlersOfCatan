/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.NullablePlayerIdx;
import model.player.PlayerIdx;
import org.json.JSONException;
import shared.locations.HexLocation;

/**
 *
 * @author Michael
 */
public class PlaySoldierRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private NullablePlayerIdx victimIndex;
    private HexLocation newLocation;

    /**
     *
     * @param playerIndex The index of the player playing the card
     * @param victimIndex The index of the player being robbed
     * @param newLocation The location where the robber should be placed
     */
    public PlaySoldierRequest(PlayerIdx playerIndex, NullablePlayerIdx victimIndex, HexLocation newLocation)
    {
        this.playerIndex = playerIndex;
        this.victimIndex = victimIndex;
        this.newLocation = newLocation;
    }
  
    /**
     *
     * @return The index of the player playing the card
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The index of the player being robbed
     */
    public NullablePlayerIdx getVictimIndex()
    {
        return victimIndex;
    }

    /**
     *
     * @return The location where the robber should be placed
     */
    public HexLocation getNewLocation()
    {
        return newLocation;
    }
      
    
    @Override
    public String serialize()//???
    {
    	String serializing = "{type: \"Soldier\", playerIndex: "+ playerIndex.getIndex() +
    			", victimIndex: "+ victimIndex.getIndex() + ", location: {x: "+ newLocation.getX() + ", y: " + newLocation.getY()+"}}";
    	return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
