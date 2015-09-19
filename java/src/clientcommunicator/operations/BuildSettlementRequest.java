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
public class BuildSettlementRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private VertexLocation location;
    private boolean free;

    public BuildSettlementRequest(PlayerIdx playerIndex, VertexLocation location, boolean free)
    {
        this.playerIndex = playerIndex;
        this.location = location;
        this.free = free;
    }
    
    
    
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    public VertexLocation getLocation()
    {
        return location;
    }

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
