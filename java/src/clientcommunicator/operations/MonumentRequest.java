/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;

/**
 *
 * @author Michael
 */
public class MonumentRequest implements IJSONSerializable
{
    
    
    private PlayerIdx playerIndex;
    
    
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    public MonumentRequest(PlayerIdx playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    
    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
