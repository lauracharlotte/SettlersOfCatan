package clientcommunicator.operations;

import model.cards.ResourceCards;
import model.player.PlayerIdx;

public class OfferTradeRequest implements IJSONSerializable {
	
    public OfferTradeRequest(PlayerIdx PlayerIdx, ResourceCards offer, int receiver)
    {
        this.PlayerIdx = PlayerIdx;
        this.offer = offer;
        this.receiver = receiver;
    }
    
    private PlayerIdx PlayerIdx;
    private ResourceCards offer;
    private int receiver;
    
	public PlayerIdx getPlayerIdx() 
	{
		return PlayerIdx;
	}
	public ResourceCards getOffer() 
	{
		return offer;
	}
	public int getReceiver() 
	{
		return receiver;
	}
    
    @Override
    public String serialize()
    {	
    	String serializing = "{type: \"offerTrade\", playerIndex: " + PlayerIdx+", offer: "
    			+" {brick: "+offer.getBrick()+", ore: "+offer.getOre()+", sheep: "+offer.getWool()
    			+", wheat: "+offer.getGrain()+ ", wood: "+offer.getLumber()+"}, receiver: "+receiver+"}";
    	return serializing;
     }

}
