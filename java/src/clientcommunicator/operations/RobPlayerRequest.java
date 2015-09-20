/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.locations.HexLocation;

/**
 *
 * @author Michael
 */
public class RobPlayerRequest implements IJSONSerializable
{

    private PlayerIdx playerThatsRobbingIndex;
    private PlayerIdx victimIndex;
    private HexLocation location;

    /**
     *
     * @param playerThatsRobbingIndex The player that is robbing.
     * @param victimIndex The player that is being robbed.
     * @param location The new robber location.
     */
    public RobPlayerRequest(PlayerIdx playerThatsRobbingIndex, PlayerIdx victimIndex, HexLocation location)
    {
        this.playerThatsRobbingIndex = playerThatsRobbingIndex;
        this.victimIndex = victimIndex;
        this.location = location;
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
    public PlayerIdx getVictimIndex()
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
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
