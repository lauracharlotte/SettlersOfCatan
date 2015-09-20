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

    /**
     *
     * @param playerIndex The index of the player that rolled the number
     * @param numberRolled The number that was rolled on the dice
     */
    public RollNumberRequest(PlayerIdx playerIndex, int numberRolled)
    {
        this.playerIndex = playerIndex;
        this.numberRolled = numberRolled;
    }

    /**
     *
     * @return The index of the player that rolled the number
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The number that was rolled on the dice
     */
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
