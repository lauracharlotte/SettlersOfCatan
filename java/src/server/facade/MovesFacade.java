package server.facade;

import model.cards.ResourceCards;
import model.player.PlayerIdx;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 *
 * @author Scott
 */
public class MovesFacade implements IMovesFacade {
    
    /**
     * The constructor for the Moves Facade
     */
    public MovesFacade() {}    
    
    /**
     * 
     * @param playerIdx
     * @param message 
     */
    public void sendChat(PlayerIdx playerIdx, String message)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param number 
     */
    public void rollNumber(PlayerIdx playerIdx, int number)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param victimIndex
     * @param location 
     */
    public void robPlayer(PlayerIdx playerIdx, PlayerIdx victimIndex, HexLocation location)
    {
        
    }
    
    /**
     * 
     * @param playerIdx 
     */
    public void finishTurn(PlayerIdx playerIdx)
    {
        
    }
    
    /**
     * 
     * @param playerIdx 
     */
    public void buyDevCard(PlayerIdx playerIdx)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param resource1
     * @param resource2 
     */
    public void yearOfPlenty(PlayerIdx playerIdx, ResourceType resource1, ResourceType resource2)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param spot1
     * @param spot2 
     */
    public void roadBuilding(PlayerIdx playerIdx, EdgeLocation spot1, EdgeLocation spot2)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param victimIdx
     * @param location 
     */
    public void soldier(PlayerIdx playerIdx, PlayerIdx victimIdx, HexLocation location)
    {
        
    }
    
    /**
     * 
     * @param resource
     * @param playerIdx 
     */
    public void monument(ResourceType resource, PlayerIdx playerIdx)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param roadLocation
     * @param free 
     */
    public void buildRoad(PlayerIdx playerIdx, EdgeLocation roadLocation, boolean free)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param vertexLocation 
     */
    public void buildCity(PlayerIdx playerIdx, VertexLocation vertexLocation)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param vertexLocation
     * @param free 
     */
    public void buildSettlement(PlayerIdx playerIdx, VertexLocation vertexLocation, boolean free)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param offer
     * @param receiver 
     */
    public void offerTrade(PlayerIdx playerIdx, ResourceCards offer, PlayerIdx receiver)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param willAccept 
     */
    public void acceptTrade(PlayerIdx playerIdx, boolean willAccept)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param ratio
     * @param inputResource
     * @param outputResource 
     */
    public void maritimeTrade(PlayerIdx playerIdx, int ratio, ResourceType inputResource, ResourceType outputResource)
    {
        
    }
    
    /**
     * 
     * @param playerIdx
     * @param discardedCards 
     */
    public void discardCards(PlayerIdx playerIdx, ResourceCards discardedCards)
    {
        
    }
    
}
