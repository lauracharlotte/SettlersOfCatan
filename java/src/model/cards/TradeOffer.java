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
     * The ResourceCards of the offer
     * Positive numbers are resources being offered. Negative are resources being asked for.
     */
    private ResourceCards resourceCards;

    /**
     * The Constructor of a TradeOffer
     */
    public TradeOffer(PlayerIdx senderNumber, PlayerIdx receiverNumber, ResourceCards resourceCards)
    {
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
        this.resourceCards = resourceCards;
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
     * @return ResourceCards
     */
    public ResourceCards getResourceCards()
    {
        return resourceCards;
    }

    /**
     * Obvious
     * @param ResourceCards
     */
    public void setReceiverNumber(ResourceCards resourceCards)
    {
        this.resourceCards = resourceCards;
    }
    
    @Override
    public String toString()
    {
    	StringBuilder str = new StringBuilder();
    	str.append("Sender Number: ");
    	str.append(senderNumber.toString());
    	str.append("\nReceiver Number: ");
    	str.append(receiverNumber.toString());
    	str.append("\nOffer: \n");
    	str.append(resourceCards.toString());
    	return str.toString();
    }
}