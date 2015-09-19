package clientcommunicator.operations;

public class AcceptTradeRequest implements IJSONSerializable {

    public PlayerIdx getAcceptingPlayerIdx() {
        return acceptingPlayerIdx;
    }

    public void setAcceptingPlayerIdx(PlayerIdx acceptingPlayerIdx) {
        this.acceptingPlayerIdx = acceptingPlayerIdx;
    }

    public boolean isWillAccept() {
        return willAccept;
    }

    public void setWillAccept(boolean willAccept) {
        this.willAccept = willAccept;
    }

    private PlayerIdx acceptingPlayerIdx;
    private boolean willAccept;
    
    @Override
    public String Serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
