package clientcommunicator.operations;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.player.PlayerIdx;
import org.json.JSONException;

/**
 *
 * @author Michael
 */
public class AcceptTradeRequest implements IJSONSerializable {

    /**
     *
     * @param acceptingPlayerIdx The index of the player that is accepting/declining the trade.
     * @param willAccept If the player will accept or decline the trade. True is accept.
     */
    public AcceptTradeRequest(PlayerIdx acceptingPlayerIdx, boolean willAccept)
    {
        this.acceptingPlayerIdx = acceptingPlayerIdx;
        this.willAccept = willAccept;
    }

    AcceptTradeRequest()
    {}

    /**
     *
     * @return The index of the player that is accepting/declining the trade.
     */
    public PlayerIdx getAcceptingPlayerIdx() 
    {
        return acceptingPlayerIdx;
    }

    /**
     *
     * @return If the player will accept or decline
     */
    public boolean willAccept() 
    {
        return willAccept;
    }


    private PlayerIdx acceptingPlayerIdx;
    private boolean willAccept;
    
    @Override
    public String serialize() 
    {
    	String serializing = "{type: \"acceptTrade\", "
    			+"playerIndex: " + acceptingPlayerIdx.getIndex() + ", "
    			+"willAccept: "+willAccept+"}";
    	return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonElement element = new JsonParser().parse(JSON);
        JsonObject obj = element.getAsJsonObject();
        this.acceptingPlayerIdx = new PlayerIdx(obj.get("playerIndex").getAsInt());
        this.willAccept = obj.get("willAccept").getAsBoolean();
    }
}
