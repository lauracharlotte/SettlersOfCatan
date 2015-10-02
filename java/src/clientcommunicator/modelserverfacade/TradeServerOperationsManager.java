package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.AcceptTradeRequest;
import clientcommunicator.operations.MaritimeTradeRequest;

public class TradeServerOperationsManager implements IServerOperationsManager {
	
	private IServerProxy currentServer;
    
    @Override
    public void setServer(IServerProxy serverToUse) 
    {
        if (serverToUse == null)
            throw new IllegalArgumentException("Cannot set server to null.");
        this.currentServer = serverToUse;
    }
    
    /**
     * @pre The OfferTradeRequest is valid--the sender has the correct amount of resource cards and either the sender
     * or the receiver has the current turn.
     * @post The user's trade request has been posted
     * @param request Request object to offer a trade
     */
    public void offerTrade(OfferTradeRequest request) throws ClientException
    {
        this.currentServer.offerTrade(JSONParser.toJSON(request));
    }
    
    /**
     * @pre The AcceptTradeRequest is valid--the receiver has the right number of resources.
     * @post The sender and receiver each receive the proper resource cards
     * @param request Request object to accept a trade
     */
    public void acceptTrade(AcceptTradeRequest request) throws ClientException
    {
        this.currentServer.acceptTrade(JSONParser.toJSON(request));
    }
    
    /**
     * @pre The MaritimeTradeRequest is valid--the user has a settlement on the port. It's the user's turn.
     * The user has the correct resource cards.
     * @post The user loses and receives the proper resource cards.
     * @param request Request object to trade through a port
     */
    public void maritimeTrade(MaritimeTradeRequest request) throws ClientException
    {
        this.currentServer.maritimeTrade(JSONParser.toJSON(request));
    }

}
