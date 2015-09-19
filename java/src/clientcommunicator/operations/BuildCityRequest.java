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
     * @param playerIndex
     * @param location
     */
    public BuildCityRequest(PlayerIdx playerIndex, VertexLocation location)
    {
        this.playerIndex = playerIndex;
        this.location = location;
    }

    /**
     *
     * @return
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return
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
