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
     * @param playerThatsRobbingIndex
     * @param victimIndex
     * @param location
     */
    public RobPlayerRequest(PlayerIdx playerThatsRobbingIndex, PlayerIdx victimIndex, HexLocation location)
    {
        this.playerThatsRobbingIndex = playerThatsRobbingIndex;
        this.victimIndex = victimIndex;
        this.location = location;
    }
    
    /**
     *
     * @return
     */
    public PlayerIdx getPlayerThatsRobbingIndex()
    {
        return playerThatsRobbingIndex;
    }

    /**
     *
     * @return
     */
    public PlayerIdx getVictimIndex()
    {
        return victimIndex;
    }

    /**
     *
     * @return
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
