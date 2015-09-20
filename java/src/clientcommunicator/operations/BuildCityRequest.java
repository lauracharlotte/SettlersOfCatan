/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
public class BuildCityRequest implements IJSONSerializable
{

    /**
     *
     * @param playerIndex The index of the player that wants to build the city
     * @param location The vertex location that the player wants to put the city at
     */
    public BuildCityRequest(PlayerIdx playerIndex, VertexLocation location)
    {
        this.playerIndex = playerIndex;
        this.location = location;
    }

    /**
     *
     * @return The index of the player that wants to build the city
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The vertex location that the player wants to put the city at
     */
    public VertexLocation getLocation()
    {
        return location;
    }
    
    private PlayerIdx playerIndex;
    private VertexLocation location;

    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
