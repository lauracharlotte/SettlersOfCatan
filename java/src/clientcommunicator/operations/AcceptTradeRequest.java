package clientcommunicator.operations;
import model.player.PlayerIdx;

/**
 *
 * @author Michael
 */
public class AcceptTradeRequest implements IJSONSerializable {

    /**
     *
     * @param acceptingPlayerIdx
     * @param willAccept
     */
    public AcceptTradeRequest(PlayerIdx acceptingPlayerIdx, boolean willAccept)
    {
        this.acceptingPlayerIdx = acceptingPlayerIdx;
        this.willAccept = willAccept;
    }

    /**
     *
     * @return
     */
    public PlayerIdx getAcceptingPlayerIdx() 
    {
        return acceptingPlayerIdx;
    }

    /**
     *
     * @return
     */
    public boolean willAccept() 
    {
        return willAccept;
    }


    private PlayerIdx acceptingPlayerIdx;
    private boolean willAccept;
    
    @Override
    public String Serialize() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
