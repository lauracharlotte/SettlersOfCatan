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
     * @param playerIndex
     * @param location
     * @param free
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
    public EdgeLocation getLocation()
    {
        return location;
    }

    /**
     *
     * @return
     */
    public boolean isFree()
    {
        return free;
    }

    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
