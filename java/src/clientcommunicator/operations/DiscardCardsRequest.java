/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.cards.ResourceCardList;
import model.player.PlayerIdx;

/**
 *
 * @author Michael
 */
public class DiscardCardsRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private ResourceCardList discardedCards;

    /**
     *
     * @return The index of the player that is discarding
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The resources that the player is discarding
     */
    public ResourceCardList getDiscardedCards()
    {
        return discardedCards;
    }

    /**
     *
     * @param playerIndex The index of the player that is discarding
     * @param discardedCards The resources that the player is discarding
     */
    public DiscardCardsRequest(PlayerIdx playerIndex, ResourceCardList discardedCards)
    {
        this.playerIndex = playerIndex;
        this.discardedCards = discardedCards;
    }
    
    @Override
    public String Serialize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
