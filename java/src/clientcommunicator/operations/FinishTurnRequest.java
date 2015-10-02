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
public class FinishTurnRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;

    /**
     *
     * @param playerIndex The index of the player who is finishing their turn
     */
    public FinishTurnRequest(PlayerIdx playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    /**
     *
     * @return The index of the player who is finishing their turn
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    @Override
    public String serialize()
    {
    	String serializing = "{type: \"finishTurn\", playerIndex: " + playerIndex.getPlayerIdx() + "}";
        return serializing;
    }
}
