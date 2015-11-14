package server.facade;

import java.util.Arrays;
import java.util.Collection;
import model.ClientModel;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.messages.MessageLine;
import model.messages.MessageList;
import model.player.NullablePlayerIdx;
import model.player.Player;
import model.player.PlayerIdx;
import model.player.User;
import server.model.GameManager;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 *
 * @author Scott
 */

// Function Checklist:
// x sendChat
// - rollNumber
// - robPlayer
// - finishTurn
// - buyDevCard
// - yearOfPlenty
// - roadBuilding
// - soldier
// - monument
// - monopoly
// - buildRoad
// - buildCity
// - buildSettlement
// - offerTrade
// - acceptTrade
// - maritimeTrade
// - discardCards

// TODO: 
// - Check that the user is able to make each move
// - Log moves (fill out the messages)
// - Change status
public class MovesFacade implements IMovesFacade { 
    
    GameManager manager;
    
    public MovesFacade(GameManager myManager)
    {
        this.manager = myManager;
    }
    
    /**
     * Sends a chat message from a player and logs it in the chat message box
     * 
     * @param playerIdx The turn tracker index of the player of the person who sent the message
     * @param message The message string sent by the player
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel sendChat(PlayerIdx playerIdx, String message, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        MessageLine messageLine = new MessageLine(message, user.getUsername());
        Collection<MessageLine> lines = model.getChat().getLines();
        lines.add(messageLine);
        model.getChat().setLines(lines);
        return model; 
    }
    
    /**
     * A player rolls a number and the necessary players receive their resources
     * 
     * @param playerIdx The turn tracker index of the player who rolled
     * @param number The number rolled
     * @return The new model information
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     */
    @Override
    public ClientModel rollNumber(PlayerIdx playerIdx, int number, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * A player moving the robber to a specific hex, and robbing a specific player
     * 
     * @param playerIdx The turn tracker index of the player of the player who robbed
     * @param victimIndex The turn tracker index of the player of the player being robbed
     * @param location The hex location of the placed robber
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel robPlayer(PlayerIdx playerIdx, NullablePlayerIdx victimIndex, HexLocation location, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * A player finishes their turn, and the turn tracker moves to the next player
     * 
     * @param playerIdx The turn tracker index of the player who is finishing their turn
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel finishTurn(PlayerIdx playerIdx, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        // change turn status
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * A player buys a development card and the next card is given
     * 
     * @param playerIdx The turn tracker index of the player who is buying the development card
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel buyDevCard(PlayerIdx playerIdx, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        Player player = getPlayerFromIdx(playerIdx, model);
        // check that there are dev cards left, and that player has enough resources
        ResourceCards resourceCards = player.getHand().getResourceCards();
        resourceCards = changeResource(resourceCards, ResourceType.ORE, -1);
        resourceCards = changeResource(resourceCards, ResourceType.SHEEP, -1);
        resourceCards = changeResource(resourceCards, ResourceType.WHEAT, -1);
        player.getHand().setResourceCards(resourceCards);
        // draw a random dev card from what is left
        model = setPlayerFromIdx(playerIdx, model, player);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * A player plays a development card and is given two requested resources
     * 
     * @param playerIdx The turn tracker index of the player who is playing the development card
     * @param resource1 The first resource chosen to collect
     * @param resource2 The second resource chosen to collect
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel yearOfPlenty(PlayerIdx playerIdx, ResourceType resource1, ResourceType resource2, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        Player player = getPlayerFromIdx(playerIdx, model);
        // can player do this?
        // take away dev card
        ResourceCards resourceCards = player.getHand().getResourceCards();
        resourceCards = changeResource(resourceCards, resource1, 1);
        resourceCards = changeResource(resourceCards, resource2, 1);
        player.getHand().setResourceCards(resourceCards);
        
        model = setPlayerFromIdx(playerIdx, model, player);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * The player plays a development card and is allowed to build two roads
     * 
     * @param playerIdx The turn tracker index of the player who is playing the development card
     * @param spot1 The first location chosen to build a road
     * @param spot2 The second location chosen to build a road
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel roadBuilding(PlayerIdx playerIdx, EdgeLocation spot1, EdgeLocation spot2, int game, User user)
    {
        // Check if they have the card
        // discard the card
        
        //Log the action
//        model.setLog(addLog(user, "message", model.getLog()));
        buildRoad(playerIdx, spot1, true, game, user);
        return buildRoad(playerIdx, spot2, true, game, user);
    }
    
    /**
     * The player plays the development card and is allowed to move the robber and rob
     * 
     * @param playerIdx The turn tracker index of the player who is playing the development card
     * @param victimIdx The turn tracker index of the player who is being robbed
     * @param location The hex location of the placed robber
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel soldier(PlayerIdx playerIdx, NullablePlayerIdx victimIdx, HexLocation location, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * The player plays the development card and is given a victory point
     * 
     * @param playerIdx The turn tracker index of the player who played the development card
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel monument(PlayerIdx playerIdx, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        Player player = getPlayerFromIdx(playerIdx, model);
        // can player do this?
        // take away dev card
        player.setVictoryPoints(player.getVictoryPoints() + 1);
        model = setPlayerFromIdx(playerIdx, model, player);
        
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * The player plays the development card and robs the chosen resource from every other player
     * 
     * @param resource The chosen resource to collect
     * @param playerIdx The turn tracker index of the player who played the development card
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel monopoly(ResourceType resource, PlayerIdx playerIdx, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * The player builds a road at a chosen location
     * 
     * @param playerIdx The turn tracker index of the player who is building a road
     * @param roadLocation The location of the road being built
     * @param free Whether the road was built during the game setup or if it was bought
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel buildRoad(PlayerIdx playerIdx, EdgeLocation roadLocation, boolean free, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        Player player = getPlayerFromIdx(playerIdx, model);
        if (!free)
        {
            // Check if you can
            ResourceCards resourceCards = player.getHand().getResourceCards();
            resourceCards = changeResource(resourceCards, ResourceType.BRICK, -1);
            resourceCards = changeResource(resourceCards, ResourceType.WOOD, -1);
            player.getHand().setResourceCards(resourceCards);
        }
        // Build the road 
        model = setPlayerFromIdx(playerIdx, model, player);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * The player builds a city at a chosen location
     * 
     * @param playerIdx The turn tracker index of the player building the city
     * @param vertexLocation The location of the city being built
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel buildCity(PlayerIdx playerIdx, VertexLocation vertexLocation, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        Player player = getPlayerFromIdx(playerIdx, model);
        // Check if you can
        ResourceCards resourceCards = player.getHand().getResourceCards();
        resourceCards = changeResource(resourceCards, ResourceType.WHEAT, -2);
        resourceCards = changeResource(resourceCards, ResourceType.ORE, -3);
        player.getHand().setResourceCards(resourceCards);
        // Build the city 
        model = setPlayerFromIdx(playerIdx, model, player);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * The player builds a settlement at a chosen location
     * 
     * @param playerIdx The turn tracker index of the player building the settlement
     * @param vertexLocation The location of the settlement being built
     * @param free Whether the settlement was built during the game setup or if it was bought
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel buildSettlement(PlayerIdx playerIdx, VertexLocation vertexLocation, boolean free, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        Player player = getPlayerFromIdx(playerIdx, model);
        if (!free)
        {
            // Check if you can
            ResourceCards resourceCards = player.getHand().getResourceCards();
            resourceCards = changeResource(resourceCards, ResourceType.BRICK, -1);
            resourceCards = changeResource(resourceCards, ResourceType.WOOD, -1);
            resourceCards = changeResource(resourceCards, ResourceType.WHEAT, -1);
            resourceCards = changeResource(resourceCards, ResourceType.SHEEP, -1);
            player.getHand().setResourceCards(resourceCards);
        }
        // Build the settlement 
        model = setPlayerFromIdx(playerIdx, model, player);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * One player offers a trade to another player
     * 
     * @param playerIdx The turn tracker index of the player offering the trade
     * @param offer The resource cards being offered
     * @param receiver The turn tracker index of the player being offered the trade
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel offerTrade(PlayerIdx playerIdx, ResourceCards offer, PlayerIdx receiver, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return null;
    }
    
    /**
     * The player accepts/denies the trade offered to them
     * 
     * @param playerIdx The turn tracker index of the player accepting the trade
     * @param willAccept Whether the player will accept the trade or not
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel acceptTrade(PlayerIdx playerIdx, boolean willAccept, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return null;
    }
    
    /**
     * The player trades in a number of their resources for one of another resource
     * 
     * @param playerIdx The turn tracker index of the player making the trade
     * @param ratio The ratio of the input resource to output resource
     * @param inputResource The resource the player is giving
     * @param outputResource The resource the player wants to receive
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel maritimeTrade(PlayerIdx playerIdx, int ratio, ResourceType inputResource, ResourceType outputResource, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        Player player = getPlayerFromIdx(playerIdx, model);
        Hand hand = player.getHand();
        ResourceCards resourceCards = hand.getResourceCards();
        resourceCards = changeResource(resourceCards, inputResource, 0 - ratio);
        resourceCards = changeResource(resourceCards, outputResource, 1);
        hand.setResourceCards(resourceCards);
        player.setHand(hand);
        model = setPlayerFromIdx(playerIdx, model, player);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    /**
     * The player discards certain cards from their hand
     * 
     * @param playerIdx The turn tracker index of the player discarding
     * @param discardedCards The cards the player is discarding
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel discardCards(PlayerIdx playerIdx, ResourceCards discardedCards, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        Player player = getPlayerFromIdx(playerIdx, model);
        Hand hand = player.getHand();
        ResourceCards resourceCards = hand.getResourceCards();
        resourceCards = changeResource(resourceCards, ResourceType.BRICK, discardedCards.getBrick());
        resourceCards = changeResource(resourceCards, ResourceType.ORE, discardedCards.getOre());
        resourceCards = changeResource(resourceCards, ResourceType.SHEEP, discardedCards.getWool());
        resourceCards = changeResource(resourceCards, ResourceType.WHEAT, discardedCards.getGrain());
        resourceCards = changeResource(resourceCards, ResourceType.WOOD, discardedCards.getLumber());
        hand.setResourceCards(resourceCards);
        player.setHand(hand);
        model = setPlayerFromIdx(playerIdx, model, player);
        
        //Log the action
        model.setLog(addLog(user, "message", model.getLog()));
        return model;
    }
    
    // **************************************************************************
    // All of the private functions
    // **************************************************************************
    
    /**
     * Get the player object from the model using the player index
     * @param playerIdx The index of the player
     * @param model The current game model
     * @return 
     */
    private Player getPlayerFromIdx(PlayerIdx playerIdx, ClientModel model)
    {
        Player[] players = model.getPlayers().toArray(new Player[4]);
        return players[playerIdx.getIndex()];
    }
    
    /**
     * Set the player object in the model using the player index
     * @param playerIdx The index of the player
     * @param model The current game model
     * @param player The new player object to be set
     * @return 
     */
    private ClientModel setPlayerFromIdx(PlayerIdx playerIdx, ClientModel model, Player player)
    {
        Player[] players = model.getPlayers().toArray(new Player[4]);
        players[playerIdx.getIndex()] = player;
        model.setPlayers(Arrays.asList(players));
        return model;
    }
    
    /**
     * Increase the amount of a specific resource by a given amount
     * @param resourceCards The current hand
     * @param resource The resource to be increased
     * @param amount The amount to add
     * @return 
     */
    private ResourceCards changeResource(ResourceCards resourceCards, ResourceType resource, int amount)
    {
        switch (resource)
        {
            case BRICK:
                resourceCards.setBrick(resourceCards.getBrick() + amount);
                break;
            case WOOD:
                resourceCards.setLumber(resourceCards.getLumber() + amount);
                break;
            case SHEEP:
                resourceCards.setWool(resourceCards.getWool() + amount);
                break;
            case WHEAT:
                resourceCards.setGrain(resourceCards.getGrain() + amount);
                break;
            case ORE:
                resourceCards.setOre(resourceCards.getOre() + amount);
                break;
        }
        return resourceCards;
    }
    
    private MessageList addLog(User user, String message, MessageList messageList)
    {
        
        return messageList;
    }
    
//    /**
//     * Decrease the amount of a specific resource by a given amount
//     * @param resourceCards The current hand
//     * @param resource The resource to be decreased
//     * @param decreaseBy The amount to decrease
//     * @return 
//     */
//    private ResourceCards decreaseResource(ResourceCards resourceCards, ResourceType resource, int decreaseBy)
//    {
//        switch (resource)
//        {
//            case BRICK:
//                resourceCards.setBrick(resourceCards.getBrick() - decreaseBy);
//                break;
//            case WOOD:
//                resourceCards.setLumber(resourceCards.getLumber() - decreaseBy);
//                break;
//            case SHEEP:
//                resourceCards.setWool(resourceCards.getWool() - decreaseBy);
//                break;
//            case WHEAT:
//                resourceCards.setGrain(resourceCards.getGrain() - decreaseBy);
//                break;
//            case ORE:
//                resourceCards.setOre(resourceCards.getOre() - decreaseBy);
//                break;
//        }
//        return resourceCards;
//    }
    
}
