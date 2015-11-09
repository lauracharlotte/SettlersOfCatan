package server.facade;

import model.ClientModel;
import model.cards.ResourceCards;
import model.player.NullablePlayerIdx;
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
     * Sends a chat message from a player and logs it in the chat message box
     * 
     * @param playerIdx The turn tracker index of the player of the person who sent the message
     * @param message The message string sent by the player
     * @return The new model information
     */
    @Override
    public ClientModel sendChat(PlayerIdx playerIdx, String message)
    {
        return null; 
    }
    
    /**
     * A player rolls a number and the necessary players receive their resources
     * 
     * @param playerIdx The turn tracker index of the player who rolled
     * @param number The number rolled
     * @return The new model information
     */
    @Override
    public ClientModel rollNumber(PlayerIdx playerIdx, int number)
    {
        return null;
    }
    
    /**
     * A player moving the robber to a specific hex, and robbing a specific player
     * 
     * @param playerIdx The turn tracker index of the player of the player who robbed
     * @param victimIndex The turn tracker index of the player of the player being robbed
     * @param location The hex location of the placed robber
     * @return The new model information
     */
    @Override
    public ClientModel robPlayer(PlayerIdx playerIdx, NullablePlayerIdx victimIndex, HexLocation location)
    {
        return null;
    }
    
    /**
     * A player finishes their turn, and the turn tracker moves to the next player
     * 
     * @param playerIdx The turn tracker index of the player who is finishing their turn
     * @return The new model information
     */
    @Override
    public ClientModel finishTurn(PlayerIdx playerIdx)
    {
        return null;
    }
    
    /**
     * A player buys a development card and the next card is given
     * 
     * @param playerIdx The turn tracker index of the player who is buying the development card
     * @return The new model information
     */
    @Override
    public ClientModel buyDevCard(PlayerIdx playerIdx)
    {
        return null;
    }
    
    /**
     * A player plays a development card and is given two requested resources
     * 
     * @param playerIdx The turn tracker index of the player who is playing the development card
     * @param resource1 The first resource chosen to collect
     * @param resource2 The second resource chosen to collect
     * @return The new model information
     */
    @Override
    public ClientModel yearOfPlenty(PlayerIdx playerIdx, ResourceType resource1, ResourceType resource2)
    {
        return null;
    }
    
    /**
     * The player plays a development card and is allowed to build two roads
     * 
     * @param playerIdx The turn tracker index of the player who is playing the development card
     * @param spot1 The first location chosen to build a road
     * @param spot2 The second location chosen to build a road
     * @return The new model information
     */
    @Override
    public ClientModel roadBuilding(PlayerIdx playerIdx, EdgeLocation spot1, EdgeLocation spot2)
    {
        return null;
    }
    
    /**
     * The player plays the development card and is allowed to move the robber and rob
     * 
     * @param playerIdx The turn tracker index of the player who is playing the development card
     * @param victimIdx The turn tracker index of the player who is being robbed
     * @param location The hex location of the placed robber
     * @return The new model information
     */
    @Override
    public ClientModel soldier(PlayerIdx playerIdx, NullablePlayerIdx victimIdx, HexLocation location)
    {
        return null;
    }
    
    /**
     * The player plays the development card and is given a victory point
     * 
     * @param playerIdx The turn tracker index of the player who played the development card
     * @return The new model information
     */
    @Override
    public ClientModel monument(PlayerIdx playerIdx)
    {
        return null;
    }
    
    /**
     * The player plays the development card and robs the chosen resource from every other player
     * 
     * @param resource The chosen resource to collect
     * @param playerIdx The turn tracker index of the player who played the development card
     * @return The new model information
     */
    @Override
    public ClientModel monopoly(ResourceType resource, PlayerIdx playerIdx)
    {
        return null;
    }
    
    /**
     * The player builds a road at a chosen location
     * 
     * @param playerIdx The turn tracker index of the player who is building a road
     * @param roadLocation The location of the road being built
     * @param free Whether the road was built during the game setup or if it was bought
     * @return The new model information
     */
    @Override
    public ClientModel buildRoad(PlayerIdx playerIdx, EdgeLocation roadLocation, boolean free)
    {
        return null;
    }
    
    /**
     * The player builds a city at a chosen location
     * 
     * @param playerIdx The turn tracker index of the player building the city
     * @param vertexLocation The location of the city being built
     * @return The new model information
     */
    @Override
    public ClientModel buildCity(PlayerIdx playerIdx, VertexLocation vertexLocation)
    {
        return null;
    }
    
    /**
     * The player builds a settlement at a chosen location
     * 
     * @param playerIdx The turn tracker index of the player building the settlement
     * @param vertexLocation The location of the settlement being built
     * @param free Whether the settlement was built during the game setup or if it was bought
     * @return The new model information
     */
    @Override
    public ClientModel buildSettlement(PlayerIdx playerIdx, VertexLocation vertexLocation, boolean free)
    {
        return null;
    }
    
    /**
     * One player offers a trade to another player
     * 
     * @param playerIdx The turn tracker index of the player offering the trade
     * @param offer The resource cards being offered
     * @param receiver The turn tracker index of the player being offered the trade
     * @return The new model information
     */
    @Override
    public ClientModel offerTrade(PlayerIdx playerIdx, ResourceCards offer, PlayerIdx receiver)
    {
        return null;
    }
    
    /**
     * The player accepts/denies the trade offered to them
     * 
     * @param playerIdx The turn tracker index of the player accepting the trade
     * @param willAccept Whether the player will accept the trade or not
     * @return The new model information
     */
    @Override
    public ClientModel acceptTrade(PlayerIdx playerIdx, boolean willAccept)
    {
        return null;
    }
    
    /**
     * The player trades in a number of their resources for one of another resource
     * 
     * @param playerIdx The turn tracker index of the player making the trade
     * @param ratio The ratio of the input resource to output resource
     * @param inputResource The resource the player is giving
     * @param outputResource The resource the player wants to receive
     * @return The new model information
     */
    @Override
    public ClientModel maritimeTrade(PlayerIdx playerIdx, int ratio, ResourceType inputResource, ResourceType outputResource)
    {
        return null;
    }
    
    /**
     * The player discards certain cards from their hand
     * 
     * @param playerIdx The turn tracker index of the player discarding
     * @param discardedCards The cards the player is discarding
     * @return The new model information
     */
    @Override
    public ClientModel discardCards(PlayerIdx playerIdx, ResourceCards discardedCards)
    {
        return null;
    }
    
}
