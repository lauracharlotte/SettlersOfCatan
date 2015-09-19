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
public class PlaySoldierRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private int victimIndex;
    private HexLocation newLocation;

    public PlaySoldierRequest(PlayerIdx playerIndex, int victimIndex, HexLocation newLocation)
    {
        this.playerIndex = playerIndex;
        this.victimIndex = victimIndex;
        this.newLocation = newLocation;
    }
  
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    public int getVictimIndex()
    {
        return victimIndex;
    }

    public HexLocation getNewLocation()
    {
        return newLocation;
    }
    
    
    
    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
