/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.definitions.CatanColor;

/**
 *
 * @author Michael
 */
public class JoinGameRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private CatanColor playerColor;

    /**
     *
     * @param playerIndex The player index the user would like to join at
     * @param playerColor The color the player would like to use
     */
    public JoinGameRequest(PlayerIdx playerIndex, CatanColor playerColor)
    {
        this.playerIndex = playerIndex;
        this.playerColor = playerColor;
    }

    /**
     *
     * @return The player index the user would like to join at
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The color the player would like to use
     */
    public CatanColor getPlayerColor()
    {
        return playerColor;
    }
    
    @Override
    public String serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
