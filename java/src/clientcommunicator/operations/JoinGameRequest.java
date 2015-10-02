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

    private int gameId;
    private CatanColor playerColor;

    /**
     *
     * @param playerIndex The player index the user would like to join at
     * @param playerColor The color the player would like to use
     */
    public JoinGameRequest(int gameId, CatanColor playerColor)
    {
        this.gameId = gameId;
        this.playerColor = playerColor;
    }

    /**
     *
     * @return The player index the user would like to join at
     */
    public int getPlayerIndex()
    {
        return gameId;
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
    	String stringColor = playerColor.toString();
    	String lowerCaseColor = stringColor.toLowerCase();
    	String serializing = "{id: "+ gameId + ", color: \""+ lowerCaseColor+"\"}";
    	return serializing;
    }    
}
