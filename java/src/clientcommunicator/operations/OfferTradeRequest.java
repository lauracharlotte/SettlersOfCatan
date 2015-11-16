package clientcommunicator.operations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.cards.ResourceCards;
import model.player.PlayerIdx;
import org.json.JSONException;

public class OfferTradeRequest implements IJSONSerializable {
	
    public OfferTradeRequest(PlayerIdx PlayerIdx, ResourceCards offer, int receiver)
    {
        this.PlayerIdx = PlayerIdx;
        this.offer = offer;
        this.receiver = receiver;
    }
    
    public OfferTradeRequest()
    {
        
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
    			+"{brick: "+offer.getBrick()+", ore: "+offer.getOre()+", sheep: "+offer.getWool()
    			+", wheat: "+offer.getGrain()+ ", wood: "+offer.getLumber()+"}, receiver: "+receiver+"}";
    	return serializing;
     }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.PlayerIdx = new PlayerIdx(obj.get("playerIndex").getAsInt());
        this.receiver = obj.get("receiver").getAsInt();
        JsonObject offerInJSON = obj.getAsJsonObject("offer");
        int brick = offerInJSON.get("brick").getAsInt();
        int ore = offerInJSON.get("ore").getAsInt();
        int sheep = offerInJSON.get("sheep").getAsInt();
        int wheat = offerInJSON.get("wheat").getAsInt();
        int wood = offerInJSON.get("wood").getAsInt();
        this.offer = new ResourceCards(brick, wheat, wood, ore, sheep);
    }

}
