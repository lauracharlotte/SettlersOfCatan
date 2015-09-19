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
public class RollNumberRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private int numberRolled;

    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    public int getNumberRolled()
    {
        return numberRolled;
    }
    
    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
