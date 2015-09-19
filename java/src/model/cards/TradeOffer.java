package model.cards;

import model.player.PlayerIdx;

/**
 * The trade offer between two players
 * @author Scott Hampton
 */
public class TradeOffer
{
    /**
     * The PlayerIdx of the sender
     */
    private PlayerIdx senderNumber;

    /**
     * The PlayerIdx of the receiver
     */
    private PlayerIdx receiverNumber;

    /**
     * The ResourceCardList of the offer
     * Positive numbers are resources being offered. Negative are resources being asked for.
     */
    private ResourceCardList resourceCardList;

    /**
     * The Constructor of a TradeOffer
     */
    public TradeOffer(senderNumber, receiverNumber, resourceCardList)
    {
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
        this.resourceCardList = resourceCardList;
    }

    /**
     * Obvious
     * @return senderNumber
     */
    public PlayerIdx getSenderNumber()
    {
        return senderNumber;
    }

    /**
     * Obvious
     * @param senderNumber
     */
    public void setSenderNumber(PlayerIdx senderNumber)
    {
        this.senderNumber = senderNumber;
    }

    /**
     * Obvious
     * @return receiverNumber
     */
    public PlayerIdx getReceiverNumber()
    {
        return receiverNumber;
    }

    /**
     * Obvious
     * @param receiverNumber
     */
    public void setReceiverNumber(PlayerIdx receiverNumber)
    {
        this.receiverNumber = receiverNumber;
    }

    /**
     * Obvious
     * @return resourceCardList
     */
    public ResourceCardList getReceiverNumber()
    {
        return resourceCardList;
    }

    /**
     * Obvious
     * @param resourceCardList
     */
    public void setReceiverNumber(ResourceCardList resourceCardList)
    {
        this.resourceCardList = resourceCardList;
    }
}