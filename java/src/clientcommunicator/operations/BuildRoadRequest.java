/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.locations.EdgeLocation;

/**
 *
 * @author Michael
 */
public class BuildRoadRequest implements IJSONSerializable
{

    /**
     *
     * @param playerIndex The index of the player that wants to build the road
     * @param location Where the player wants to build the road
     * @param free True if in setup phase, false otherwise
     */
    public BuildRoadRequest(PlayerIdx playerIndex, EdgeLocation location, boolean free)
    {
        this.playerIndex = playerIndex;
        this.location = location;
        this.free = free;
    }

    private PlayerIdx playerIndex;
    private EdgeLocation location;
    private boolean free;
    
    /**
     *
     * @return The index of the player that wants to build the road
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return Where the player wants to build the road
     */
    public EdgeLocation getLocation()
    {
        return location;
    }

    /**
     *
     * @return True if in setup phase, false otherwise
     */
    public boolean isFree()
    {
        return free;
    }

    @Override
    public String serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
