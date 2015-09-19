package clientcommunicator.operations;
import model.player.PlayerIdx;

public class AcceptTradeRequest implements IJSONSerializable {

    public PlayerIdx getAcceptingPlayerIdx() 
    {
        return acceptingPlayerIdx;
    }

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
