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

    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    public EdgeLocation getLocation()
    {
        return location;
    }

    public boolean isFree()
    {
        return free;
    }
    
    private PlayerIdx playerIndex;
    private EdgeLocation location;
    private boolean free;

    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
