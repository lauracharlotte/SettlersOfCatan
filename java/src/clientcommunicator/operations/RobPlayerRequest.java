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
    
    
    
    public PlayerIdx getPlayerThatsRobbingIndex()
    {
        return playerThatsRobbingIndex;
    }

    public PlayerIdx getVictimIndex()
    {
        return victimIndex;
    }

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
