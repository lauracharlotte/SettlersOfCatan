package clientcommunicator.operations;
import model.cards.ResourceCards;
import model.player.PlayerIdx;

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
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void main(final String[] args)
    {
    	PlayerIdx index = new PlayerIdx(2);
    	Boolean accepting = false;
    	AcceptTradeRequest thisTrade = new AcceptTradeRequest(index, accepting);
    	String work = thisTrade.serialize();
    	System.out.println(work);
    }

}
