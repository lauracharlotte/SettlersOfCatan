package server.facade;

import guicommunicator.MapModelFacade;
import guicommunicator.ResourceModelFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.cards.TradeOffer;
import model.map.CatanMap;
import model.map.EdgeObject;
import model.map.Hex;
import model.map.VertexObject;
import model.messages.MessageLine;
import model.messages.MessageList;
import model.player.NullablePlayerIdx;
import model.player.Player;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import model.player.User;
import server.model.GameManager;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

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
        
        // Create a new message
        MessageLine messageLine = new MessageLine(message, user.getUsername());
        Collection<MessageLine> lines = model.getChat().getLines();
        lines.add(messageLine);
        
        // Set the new list of messages
        model.getChat().setLines(lines);
        
        // Update version number and model
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
        return model; 
    }
    
    /**
     * A player rolls a number and the necessary players receive their resources
     * 
     * @param playerIdx The turn tracker index of the player who rolled
     * @param number The number rolled
     * @param game The game number of the game being modified
     * @param user The user ID of the player making the request
     * @return The new model information
     */
    @Override
    public ClientModel rollNumber(PlayerIdx playerIdx, int number, int game, User user)
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getCurrentTurn().equals(playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.rolling))
            return model;
        // give every player their respective resources
        Collection<Hex> hexes = model.getMap().getHexes();
        //take out of the bank resources and make sure there's enough
        model = safeGiveRolledResource(ResourceType.BRICK, model, model, number, hexes);
        model = safeGiveRolledResource(ResourceType.WOOD, model, model, number, hexes);
        model = safeGiveRolledResource(ResourceType.SHEEP, model, model, number, hexes);
        model = safeGiveRolledResource(ResourceType.WHEAT, model, model, number, hexes);
        model = safeGiveRolledResource(ResourceType.ORE, model, model, number, hexes);
        
        // Update the log, version number, and model
        model.setLog(addLog(user, user.getUsername() + " rolled a " + Integer.toString(number), model.getLog()));
        
        if(number == 7)
        {
            model = playersDiscard(model);
            //if somebody needs to discard
            if (!playersHaveDiscarded(model))
                model.getTurnTracker().setStatus(TurnStatusEnumeration.discarding);
            else
                model.getTurnTracker().setStatus(TurnStatusEnumeration.robbing);
        }
        else
        {
            model.getTurnTracker().setStatus(TurnStatusEnumeration.playing);
        }
  
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.robbing))
            return model;
        MapModelFacade mapFacade = new MapModelFacade();
        mapFacade.configureFacade(model.getMap(), null, model);
        if(!mapFacade.canPlaceRobber(location))
            return model;
        CatanMap map = model.getMap();
        map.setRobber(location);
        model.setMap(map);
        if (victimIndex.getIndex() < 0)
        {
            // no one lives on that hex. Rob no one.
            model.setLog(addLog(user, user.getUsername() + " moved the robbed but couldn't rob anyone!" , model.getLog()));
        }
        else
        {
            // Rob the victim of their precious jewels.
            Player victim = getPlayerFromIdx(new PlayerIdx(victimIndex.getIndex()), model); // need to rob
            int cardNumber = new Random().nextInt(victim.getHand().getResourceCards().getTotal());
            ResourceType type;
            ResourceCards victimsHand = victim.getHand().getResourceCards();
            if(victimsHand.getTotal() == 0)
                return model;
            if(victimsHand.getBrick() > cardNumber)
                type = ResourceType.BRICK;
            else if(victimsHand.getBrick() + victimsHand.getGrain() > cardNumber)
                type = ResourceType.WHEAT;
            else if(victimsHand.getBrick() + victimsHand.getGrain() + victimsHand.getLumber() > cardNumber)
                type = ResourceType.BRICK;
            else if(victimsHand.getTotal() - victimsHand.getOre() > cardNumber)
                type = ResourceType.SHEEP;
            else
                type = ResourceType.ORE;
            this.changeResource(victimsHand, type, -1);
            this.changeResource(this.getPlayerFromIdx(playerIdx, model).getHand().getResourceCards(), type, 1);
            model.setLog(addLog(user, user.getUsername() + " moved the robbed and robbed " + victim.getName(), model.getLog()));
        }
        model.getTurnTracker().setStatus(TurnStatusEnumeration.playing);
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        //make sure it was their turn
        ClientModel model = this.manager.getGameWithNumber(game);
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(model.getTurnTracker().getStatus() == TurnStatusEnumeration.firstround)
        {
            if(model.getTurnTracker().getCurrentTurn().getIndex() == 3)
                model.getTurnTracker().setStatus(TurnStatusEnumeration.secondround);
            else
                model.getTurnTracker().setCurrentTurn(new PlayerIdx(model.getTurnTracker().getCurrentTurn().getIndex() +1));
        }
        else if(model.getTurnTracker().getStatus() == TurnStatusEnumeration.secondround)
        {
            if(model.getTurnTracker().getCurrentTurn().getIndex() == 0)
                model.getTurnTracker().setStatus(TurnStatusEnumeration.rolling);
            else
                model.getTurnTracker().setCurrentTurn(new PlayerIdx(model.getTurnTracker().getCurrentTurn().getIndex() - 1));
        }
        else
        {
            // get the next player by adding one with wrap around.
            PlayerIdx nextPlayer = new PlayerIdx((playerIdx.getIndex() + 1) % 4);
            // change turn status
            model.getTurnTracker().setCurrentTurn(nextPlayer);
            model.getTurnTracker().setStatus(TurnStatusEnumeration.rolling);
            // Update the log, version number, and model
            Player p = this.getPlayerFromIdx(playerIdx, model);
            p.setPlayedDevCard(false);
            DevelopmentCards old = p.getHand().getDevelopmentCards();
            DevelopmentCards newDevCards = p.getNewDevCards();
            old.setMonopoly(old.getMonopoly() + newDevCards.getMonopoly());
            old.setRoadBuilding(old.getRoadBuilding() + newDevCards.getRoadBuilding());
            old.setSoldier(old.getSoldier() + newDevCards.getSoldier());
            old.setYearOfPlenty(old.getYearOfPlenty() + newDevCards.getYearOfPlenty());
            p.setNewDevCards(new DevelopmentCards());
            model.setLog(addLog(user, user.getUsername() + "'s turn just ended", model.getLog()));
        }
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing))
            return model;
        if(model.getBank().getDevelopmentCards().getTotal() <= 0)
            return model;
        Player player = getPlayerFromIdx(playerIdx, model);
        ResourceModelFacade resourceFacade = new ResourceModelFacade();
        if(!resourceFacade.canBuyDevCardAsPlayer(player))
            return model;
        ResourceCards resourceCards = player.getHand().getResourceCards();
        resourceCards = changeResource(resourceCards, ResourceType.ORE, -1);
        resourceCards = changeResource(resourceCards, ResourceType.SHEEP, -1);
        resourceCards = changeResource(resourceCards, ResourceType.WHEAT, -1);
        player.getHand().setResourceCards(resourceCards);
        int cardNumber = new Random().nextInt(model.getBank().getDevelopmentCards().getTotal());
        DevelopmentCards card = model.getBank().getDevelopmentCards();
        boolean picked = false;
        int total = card.getMonopoly();
        DevelopmentCards players = player.getNewDevCards();
        DevelopmentCards oldDevCardHand = player.getHand().getDevelopmentCards();
        if(cardNumber<total)
        {
            players.setMonopoly(players.getMonopoly() + 1);
            card.setMonopoly(card.getMonopoly() - 1);
            picked = true;
        }   
        total+=card.getMonument();
        if(cardNumber<total && !picked)
        {
            oldDevCardHand.setMonument(oldDevCardHand.getMonument() + 1);
            card.setMonument(card.getMonument() - 1);
            picked = true;
        }
        total+=card.getRoadBuilding();
        if(cardNumber<total && !picked)
        {
            players.setRoadBuilding(players.getRoadBuilding() + 1);
            card.setRoadBuilding(card.getRoadBuilding() - 1);
            picked = true;
        }
        total+=card.getSoldier();
        if(cardNumber<total && !picked)
        {
            players.setSoldier(players.getSoldier() + 1);
            card.setSoldier(card.getSoldier() - 1);
            picked = true;
        }
        if(!picked)
        {
            players.setYearOfPlenty(players.getYearOfPlenty() + 1);
            card.setYearOfPlenty(card.getYearOfPlenty() - 1);
        }
        // Update the log, version number, and model
        model = setPlayerFromIdx(playerIdx, model, player);
        model.setLog(addLog(user, user.getUsername() + " bought a Development Card", model.getLog()));
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing))
            return model;
        Player player = getPlayerFromIdx(playerIdx, model);
        int numberOfCards = player.getHand().getDevelopmentCards().getYearOfPlenty();
        if(numberOfCards <= 0)
            return model;
        if(!bankHasYearOfPlenty(resource1, resource2, model.getBank().getResourceCards()))
            return model;
        // take away dev card
        player.getHand().getDevelopmentCards().setYearOfPlenty(numberOfCards - 1);
        ResourceCards resourceCards = player.getHand().getResourceCards();
        resourceCards = changeResource(resourceCards, resource1, 1);
        resourceCards = changeResource(resourceCards, resource2, 1);
        player.getHand().setResourceCards(resourceCards);
        model.getBank().setResourceCards(changeResource(model.getBank().getResourceCards(), resource1, -1));
        model.getBank().setResourceCards(changeResource(model.getBank().getResourceCards(), resource2, -1));
        player.setPlayedDevCard(true);
        model = setPlayerFromIdx(playerIdx, model, player);
        
        // Update the log, version number, and model
        model.setLog(addLog(user, user.getUsername() + " used a Year of Plenty", model.getLog()));
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        // need to make sure it's their turn
        ClientModel model = this.manager.getGameWithNumber(game);
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        Player player = this.getPlayerFromIdx(playerIdx, model);
        int numberOfCards = player.getHand().getDevelopmentCards().getRoadBuilding();
        if(numberOfCards <= 0)
            return model;
        // take away dev card
        MapModelFacade mapFacade = new MapModelFacade();
        mapFacade.configureFacade(model.getMap(), player, model);
        if(!mapFacade.canPlaceRoad(spot1))
            return model;
        player.getHand().getDevelopmentCards().setRoadBuilding(numberOfCards - 1);
        model = actuallyBuildRoad(playerIdx, spot1, model);
        if(!mapFacade.canPlaceRoad(spot2))
            return model;
        player.setPlayedDevCard(true);
        model = actuallyBuildRoad(playerIdx, spot2, model);
        
        // Update the log, version number, and model
        model.setLog(addLog(user, user.getUsername() + " used a Road Building", model.getLog()));
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
        return model;
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
        //need to make sure they have the card
        //need to discard the card
        //check if they have the largest army
        //see if they won
        ClientModel model = this.manager.getGameWithNumber(game);
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        Player player = this.getPlayerFromIdx(playerIdx, model);
        int numberOfCards = player.getHand().getDevelopmentCards().getSoldier();
        if(numberOfCards <= 0)
            return model;
        // take away dev card
        MapModelFacade mapFacade = new MapModelFacade();
        mapFacade.configureFacade(model.getMap(), player, model);
        if(!mapFacade.canPlaceRobber(location))
            return model;
        player.getHand().getDevelopmentCards().setSoldier(numberOfCards - 1);
        // Update the log
        model.setLog(addLog(user, user.getUsername() + " used a soldier", model.getLog()));
        model.getTurnTracker().setStatus(TurnStatusEnumeration.robbing);
        // Rob the player
        model = robPlayer(playerIdx, victimIdx, location, game, user);
        player.setSoldiers(player.getSoldiers() + 1);
        if(player.getSoldiers() >= 3)
            if(model.getTurnTracker().getLargestArmy().isNull())
            {
                model.getTurnTracker().setLargestArmy(new NullablePlayerIdx(playerIdx.getIndex()));
                player.setVictoryPoints(player.getVictoryPoints()+2);
            }
            else if(model.getTurnTracker().getLargestArmy().getIndex() != playerIdx.getIndex())
            {
                Player otherPlayer = this.getPlayerFromIdx(new PlayerIdx(model.getTurnTracker().getLargestArmy().getIndex()), model);
                if(player.getSoldiers() > otherPlayer.getSoldiers())
                {
                    model.getTurnTracker().setLargestArmy(new NullablePlayerIdx(playerIdx.getIndex()));
                    player.setVictoryPoints(player.getVictoryPoints()+2);
                    otherPlayer.setVictoryPoints(otherPlayer.getVictoryPoints() - 2);
                }
            }
        player.setPlayedDevCard(true);
        model.setVersion(model.getVersion() + 1);
        this.setWinner(model, playerIdx);
        // Update the model (the version number is updated in the robPlayer function)
        this.manager.replaceGame(game, model);
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
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing))
            return model;
        int numberOfCards = player.getHand().getDevelopmentCards().getMonument();
        if(numberOfCards <= 0)
            return model;
        // take away dev card
        player.getHand().getDevelopmentCards().setMonument(numberOfCards - 1);
        player.setVictoryPoints(player.getVictoryPoints() + 1);
        model = setPlayerFromIdx(playerIdx, model, player);
        
        // Update the log, version number, and model
        model.setLog(addLog(user, user.getUsername() + " used a Monument", model.getLog()));
        model.setVersion(model.getVersion() + 1);
        this.setWinner(model, playerIdx);
        this.manager.replaceGame(game, model);
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
        //discard the card, make sure it's their turn. Not in that order
        ClientModel model = this.manager.getGameWithNumber(game);
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing))
            return model;
        Player player = this.getPlayerFromIdx(playerIdx, model);
        int numberOfCards = player.getHand().getDevelopmentCards().getMonopoly();
        if(numberOfCards <= 0)
            return model;
        // take away dev card
        player.getHand().getDevelopmentCards().setMonopoly(numberOfCards - 1);
        Collection<Player> players = model.getPlayers();
        int amount = 0; // this is the amount of cards the player steals from everyone else
        for (Player currentPlayer : players)
        {
            Hand hand = currentPlayer.getHand();
            ResourceCards cards = hand.getResourceCards();
            switch (resource)
            {
                case BRICK:
                    amount += cards.getBrick();
                    cards.setBrick(0);
                    break;
                case WOOD:
                    amount += cards.getLumber();
                    cards.setLumber(0);
                    break;
                case ORE:
                    amount += cards.getOre();
                    cards.setOre(0);
                    break;
                case WHEAT:
                    amount += cards.getGrain();
                    cards.setGrain(0);
                    break;
                case SHEEP:
                    amount += cards.getWool();
                    cards.setWool(0);
                    break;
            }
            hand.setResourceCards(cards);
            currentPlayer.setHand(hand);
            model = setPlayerFromIdx(currentPlayer.getPlayerIndex(), model, currentPlayer);
        }
        // Increase the player's resource by the stolen amount
        Hand hand = player.getHand();
        hand.setResourceCards(changeResource(hand.getResourceCards(), resource, amount));
        player.setHand(hand);
        player.setPlayedDevCard(true);
        model = setPlayerFromIdx(playerIdx, model, player);
        
        // Update the log, version number, and model
        model.setLog(addLog(user, user.getUsername() + " used a Monopoly", model.getLog()));
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing) 
                && !model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.firstround)
                && !model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.secondround))
            return model;
        Player player = getPlayerFromIdx(playerIdx, model);
        //System.out.println(player.getRoads());
        if(player.getRoads() <= 0)
            return model;
        MapModelFacade mapFacade = new MapModelFacade();
        mapFacade.configureFacade(model.getMap(), player, model);
        VertexDirection[] vertDir = roadLocation.getDir().convertEdgeDirToVertexDir();
        VertexLocation[] vertLocations = new VertexLocation[2];
        vertLocations[0] = new VertexLocation(roadLocation.getHexLoc(), vertDir[0]).getNormalizedLocation();
        vertLocations[1] = new VertexLocation(roadLocation.getHexLoc(), vertDir[1]).getNormalizedLocation();
        if (!free)
        {
            if(!mapFacade.canPlaceRoad(roadLocation))
                return model;
            ResourceModelFacade resourceFacade = new ResourceModelFacade();
            if(!resourceFacade.canBuildRoadAsPlayer(player))
                return model;
            ResourceCards resourceCards = player.getHand().getResourceCards();
            resourceCards = changeResource(resourceCards, ResourceType.BRICK, -1);
            resourceCards = changeResource(resourceCards, ResourceType.WOOD, -1);
            player.getHand().setResourceCards(resourceCards);
            model.getBank().getResourceCards().setBrick(model.getBank().getResourceCards().getBrick() + 1);
            model.getBank().getResourceCards().setLumber(model.getBank().getResourceCards().getLumber() + 1);
        }
        else if(!(mapFacade.onLand(roadLocation) && (mapFacade.spaceForSettlement(vertLocations[0]) || mapFacade.spaceForSettlement(vertLocations[1]))))
            return model;
        // Build the road 
        model = actuallyBuildRoad(playerIdx, roadLocation, model);
        model = setPlayerFromIdx(playerIdx, model, player);
        
        // Update the log, version number, and model
        model.setLog(addLog(user, user.getUsername() + " built a road", model.getLog()));
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        //is it their turn?
        ClientModel model = this.manager.getGameWithNumber(game);
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing))
            return model;
        Player player = getPlayerFromIdx(playerIdx, model);
        if(player.getCities() <= 0)
            return model;
        MapModelFacade mapFacade = new MapModelFacade();
        mapFacade.configureFacade(model.getMap(), player, model);
        if(!mapFacade.canPlaceCity(vertexLocation))
            return model;
        ResourceModelFacade resourceFacade = new ResourceModelFacade();
        if(!resourceFacade.canBuildCityAsPlayer(player))
            return model;
        ResourceCards resourceCards = player.getHand().getResourceCards();
        resourceCards = changeResource(resourceCards, ResourceType.WHEAT, -2);
        resourceCards = changeResource(resourceCards, ResourceType.ORE, -3);
        player.getHand().setResourceCards(resourceCards);
        model.getBank().getResourceCards().setOre(model.getBank().getResourceCards().getOre() + 3);
        model.getBank().getResourceCards().setGrain(model.getBank().getResourceCards().getGrain() + 2);
        model.getMap().getCities().add(new VertexObject(vertexLocation, playerIdx));
        player.setCities(player.getCities()-1);
        player.setSettlements(player.getSettlements() + 1);
        model = setPlayerFromIdx(playerIdx, model, player);
        
        // Update the log, version number, and model
        model.setLog(addLog(user, user.getUsername() + " built a city", model.getLog()));
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        if(player.getSettlements() <= 0)
            return model;
        MapModelFacade mapFacade = new MapModelFacade();
        mapFacade.configureFacade(model.getMap(), player, model);
        if(!mapFacade.canPlaceSettlement(vertexLocation))
            return model;
        if(model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.secondround))
        {
            ArrayList<HexLocation> hexLocations = new ArrayList<>();
            vertexLocation = vertexLocation.getNormalizedLocation();
            hexLocations.add(vertexLocation.getHexLoc());
            hexLocations.add(vertexLocation.getHexLoc().getNeighborLoc(EdgeDirection.North));
            if(vertexLocation.getDir() == VertexDirection.NorthEast)
                hexLocations.add(vertexLocation.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast));
            else if(vertexLocation.getDir() == VertexDirection.NorthWest)
                hexLocations.add(vertexLocation.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest));
            ArrayList<HexType> rTypes = new ArrayList<>();
            for(HexLocation location: hexLocations)
            {
                for(Hex h: model.getMap().getHexes())
                    if(h.getLocation().equals(location))
                        rTypes.add(h.getType());
            }
            for(HexType type: rTypes)
            {
                switch(type)
                {
                    case WOOD:
                        player.getHand().getResourceCards().setLumber(player.getHand().getResourceCards().getLumber() + 1);
                        break;
                    case SHEEP: 
                        player.getHand().getResourceCards().setWool(player.getHand().getResourceCards().getWool() + 1);
                        break;
                    case BRICK: 
                        player.getHand().getResourceCards().setBrick(player.getHand().getResourceCards().getBrick() + 1);
                        break;
                    case ORE: 
                        player.getHand().getResourceCards().setOre(player.getHand().getResourceCards().getOre() + 1);
                        break;
                    case WHEAT: 
                        player.getHand().getResourceCards().setGrain(player.getHand().getResourceCards().getGrain() + 1);
                        break;
                }
            }
        }
        else if(model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.firstround))
        {}
        else if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing))
            return model;
        if (!free)
        {
            ResourceModelFacade resourceFacade = new ResourceModelFacade();
            if(!resourceFacade.canBuildSettlementAsPlayer(player))
                return model;
            // Check if you have enough resources
            ResourceCards resourceCards = player.getHand().getResourceCards();
            resourceCards = changeResource(resourceCards, ResourceType.BRICK, -1);
            resourceCards = changeResource(resourceCards, ResourceType.WOOD, -1);
            resourceCards = changeResource(resourceCards, ResourceType.WHEAT, -1);
            resourceCards = changeResource(resourceCards, ResourceType.SHEEP, -1);
            player.getHand().setResourceCards(resourceCards);
            model.getBank().getResourceCards().setBrick(model.getBank().getResourceCards().getBrick() + 1);
            model.getBank().getResourceCards().setLumber(model.getBank().getResourceCards().getLumber() + 1);
            model.getBank().getResourceCards().setWool(model.getBank().getResourceCards().getWool() + 1);
            model.getBank().getResourceCards().setGrain(model.getBank().getResourceCards().getGrain() + 1);
        }
        CatanMap map = model.getMap();
        Collection<VertexObject> settlements = map.getSettlements();
        settlements.add(new VertexObject(vertexLocation, playerIdx));
        map.setSettlements(settlements);
        model.setMap(map);
        // up their victory points
        player.setSettlements(player.getSettlements() - 1);
        player.setVictoryPoints(player.getVictoryPoints() + 1);
        model = setPlayerFromIdx(playerIdx, model, player);
        // Update the log, version number, and model
        model.setLog(addLog(user, user.getUsername() + " built a settlement", model.getLog()));
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        Player player = getPlayerFromIdx(playerIdx, model);
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing))
            return model;
        if(!player.hasEnoughResources(offer))
            return model;
        // Create and set the trade
        TradeOffer trade = new TradeOffer(playerIdx, receiver, offer);
        model.setTradeOffer(trade);
        
        // Update the log, version number, and model
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
        return model;
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
    public ClientModel acceptTrade(PlayerIdx playerIdx, boolean willAccept, int game, User user) //user, game is from the cookie
    {
        ClientModel model = this.manager.getGameWithNumber(game);
        // check if they can accept
        if(model.getTradeOffer() == null)
            return model;
        ResourceCards trade = model.getTradeOffer().getResourceCards();
        Player accepter = getPlayerFromIdx(model.getTradeOffer().getReceiverNumber(), model);
        if(!accepter.getPlayerIndex().equals(playerIdx))
            return model;
        if(willAccept)
        {
            if(!accepter.hasEnoughResources(new ResourceCards(trade.getBrick()<0?-trade.getBrick():0, 
                    trade.getGrain()<0?-trade.getGrain():0, 
                    trade.getLumber()<0?-trade.getLumber():0, 
                    trade.getOre()<0?-trade.getOre():0, trade.getWool()<0?-trade.getWool():0)))
                    return model;
            Hand accepterHand = accepter.getHand();
            ResourceCards accepterCards = accepterHand.getResourceCards();
            //does acceptor have enough cards?
            accepterCards = changeResource(accepterCards, ResourceType.BRICK, trade.getBrick());
            accepterCards = changeResource(accepterCards, ResourceType.ORE, trade.getOre());
            accepterCards = changeResource(accepterCards, ResourceType.SHEEP, trade.getWool());
            accepterCards = changeResource(accepterCards, ResourceType.WHEAT, trade.getGrain());
            accepterCards = changeResource(accepterCards, ResourceType.WOOD, trade.getLumber());
            accepterHand.setResourceCards(accepterCards);
            accepter.setHand(accepterHand);
            model = setPlayerFromIdx(model.getTradeOffer().getReceiverNumber(), model, accepter);

            Player trader = getPlayerFromIdx(model.getTradeOffer().getSenderNumber(), model);
            Hand traderHand = trader.getHand();
            ResourceCards traderCards = traderHand.getResourceCards();
            traderCards = changeResource(traderCards, ResourceType.BRICK, -trade.getBrick());
            traderCards = changeResource(traderCards, ResourceType.ORE, -trade.getOre());
            traderCards = changeResource(traderCards, ResourceType.SHEEP, -trade.getWool());
            traderCards = changeResource(traderCards, ResourceType.WHEAT, -trade.getGrain());
            traderCards = changeResource(traderCards, ResourceType.WOOD, -trade.getLumber());
            traderHand.setResourceCards(traderCards);
            trader.setHand(traderHand);
            model = setPlayerFromIdx(model.getTradeOffer().getSenderNumber(), model, trader);
            model.setLog(addLog(user, user.getUsername()+" accepted the trade.", model.getLog()));
        }
        else
            model.setLog(addLog(user, user.getUsername()+" declined the trade.", model.getLog()));
        model.setTradeOffer(null);
        // Update the log, version number, and model
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
        return model;
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
        //is it their turn?
        ClientModel model = this.manager.getGameWithNumber(game);
        if(!this.isTheirTurn(model, playerIdx))
            return model;
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.playing))
            return model;
        Player player = getPlayerFromIdx(playerIdx, model);
        Hand hand = player.getHand();
        ResourceCards resourceCards = hand.getResourceCards();
        int currentAmount = -1;
        int bankAmount = -1;
        switch(inputResource)
        {
            case WOOD:
                currentAmount = resourceCards.getLumber();
                bankAmount = model.getBank().getResourceCards().getLumber();
                break;
            case SHEEP:
                currentAmount = resourceCards.getWool();
                bankAmount = model.getBank().getResourceCards().getWool();
                break;
            case BRICK:
                currentAmount = resourceCards.getBrick();
                bankAmount = model.getBank().getResourceCards().getBrick();
                break;
            case ORE:
                currentAmount = resourceCards.getOre();
                bankAmount = model.getBank().getResourceCards().getOre();
                break;
            case WHEAT:
                currentAmount = resourceCards.getGrain();
                bankAmount = model.getBank().getResourceCards().getGrain();
                break;
        }
        if(currentAmount <= 0)
            return model;
        if(currentAmount - ratio < 0)
            return model;
        if(bankAmount < 1 && inputResource != outputResource)
            return model;
        resourceCards = changeResource(resourceCards, inputResource, -ratio);
        resourceCards = changeResource(resourceCards, outputResource, 1);
        hand.setResourceCards(resourceCards);
        player.setHand(hand);
        model = setPlayerFromIdx(playerIdx, model, player);
        changeResource(model.getBank().getResourceCards(), inputResource, ratio);
        changeResource(model.getBank().getResourceCards(), inputResource, -1);
        // Maritime trade doesn't need to log - according to what is being done on the TA server.
        // Update the version number and model
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
        if (!player.needtoDiscard())
            return model;
        Hand hand = player.getHand();
        if(!model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.discarding))
            return model;
        if(!new ResourceModelFacade().hasEnoughResourcesAsPlayer(player, discardedCards))
            return model;
        // all 4 players have to discard -- if you're the last one to discard, the status changes to robbing
        ResourceCards resourceCards = hand.getResourceCards();
        resourceCards = changeResource(resourceCards, ResourceType.BRICK, -discardedCards.getBrick());
        resourceCards = changeResource(resourceCards, ResourceType.ORE, -discardedCards.getOre());
        resourceCards = changeResource(resourceCards, ResourceType.SHEEP, -discardedCards.getWool());
        resourceCards = changeResource(resourceCards, ResourceType.WHEAT, -discardedCards.getGrain());
        resourceCards = changeResource(resourceCards, ResourceType.WOOD, -discardedCards.getLumber());
        hand.setResourceCards(resourceCards);
        player.setDiscarded(true);
        player.setHand(hand);
        model = setPlayerFromIdx(playerIdx, model, player);
        model.getBank().getResourceCards().setBrick(model.getBank().getResourceCards().getBrick() + discardedCards.getBrick());
        model.getBank().getResourceCards().setBrick(model.getBank().getResourceCards().getBrick() + discardedCards.getOre());
        model.getBank().getResourceCards().setBrick(model.getBank().getResourceCards().getBrick() + discardedCards.getWool());
        model.getBank().getResourceCards().setBrick(model.getBank().getResourceCards().getBrick() + discardedCards.getGrain());
        model.getBank().getResourceCards().setBrick(model.getBank().getResourceCards().getBrick() + discardedCards.getLumber());
        
        // if all players have finished discarding
        if (playersHaveDiscarded(model))
        {
            model.getTurnTracker().setStatus(TurnStatusEnumeration.robbing);
            for(Player p: model.getPlayers())
                p.setDiscarded(false);
        }
        // Discard doesn't need to log - according to what is being done on the TA server.
        // Update the version number and model
        model.setVersion(model.getVersion() + 1);
        this.manager.replaceGame(game, model);
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
    
    /**
     * Adds the given log line to the model log
     * @param user
     * @param message
     * @param messageList
     * @return A MessageList to be set into the model
     */
    private MessageList addLog(User user, String message, MessageList messageList)
    {
        MessageLine line = new MessageLine(message, user.getUsername());
        Collection<MessageLine> lines = messageList.getLines();
        lines.add(line);
        messageList.setLines(lines);
        return messageList;
    }
    
    /**
     * Used by roadBuilding and buildRoad, since they both build roads but in different ways
     * @param playerIdx The index of the player building the road
     * @param location The location of the road being built
     * @param model The current game model
     * @return The modified game model
     */
    private ClientModel actuallyBuildRoad(PlayerIdx playerIdx, EdgeLocation location, ClientModel model)
    {
        MapModelFacade modelFacade = new MapModelFacade();
        modelFacade.configureFacade(model.getMap(), this.getPlayerFromIdx(playerIdx, model), model);
        Player player = getPlayerFromIdx(playerIdx, model);
        if(player.getRoads() <= 0)
            return model;
        // check to see if they now have the longest road and didn't before
        CatanMap map = model.getMap();
        Collection<EdgeObject> roads = map.getRoads();
        roads.add(new EdgeObject(location, playerIdx));
        map.setRoads(roads);
        model.setMap(map);
        player.setRoads(player.getRoads() - 1);
        model = setPlayerFromIdx(playerIdx, model, player);
        model = this.checkLongestRoad(model, playerIdx);
        this.setWinner(model, playerIdx);
        return model;
    }
    
    /**
     * Maps the hex location to all of the players that have a settlement on that hex
     * @param hexLocation
     * @param model
     * @return A list of Player objects
     */
    private Collection<Player> getSettlementsByHexLocation(HexLocation hexLocation, ClientModel model)
    {
        MapModelFacade mapFacade = new MapModelFacade();
        mapFacade.configureFacade(model.getMap(), null, model);
        return mapFacade.playersWithSettlementsByHex(hexLocation);
    }
    
    /**
     * Maps the hex location to all of the players that have a city on that hex
     * @param hexLocation
     * @param model
     * @return A list of Player objects
     */
    private Collection<Player> getCitiesByHexLocation(HexLocation hexLocation, ClientModel model)
    {
        MapModelFacade mapFacade = new MapModelFacade();
        mapFacade.configureFacade(model.getMap(), null, model);
        return mapFacade.playersWithCitiesByHex(hexLocation);
    }
    
    /**
     * Converts a hexType to a ResourceType
     * @param hex
     * @return A ResourceType
     */
    private ResourceType hexToResource(Hex hex)
    {
        ResourceType resource = null;
        switch (hex.getType())
        {
            case BRICK:
                resource = ResourceType.BRICK;
                break;
            case WOOD:
                resource = ResourceType.WOOD;
                break;
            case SHEEP:
                resource = ResourceType.SHEEP;
                break;
            case ORE:
                resource = ResourceType.ORE;
                break;
            case WHEAT:
                resource = ResourceType.WHEAT;
                break;
        }
        return resource;
    }
    
    private boolean isTheirTurn(ClientModel model, PlayerIdx idx)
    {
        return model.getTurnTracker().getCurrentTurn().equals(idx);
    }
    
    private void setWinner(ClientModel model, PlayerIdx idx)
    {
        Player currentPlayer = this.getPlayerFromIdx(idx, model);
        if(currentPlayer.getVictoryPoints() >= 10)
            model.setWinner(new NullablePlayerIdx(idx.getIndex()));
    }
    
    private boolean bankHasYearOfPlenty(ResourceType resource1, ResourceType resource2, ResourceCards cards)
    {
        ResourceCards yopCards = changeResource(new ResourceCards(0,0,0,0,0), resource1, 1);
        yopCards = changeResource(yopCards, resource2, 1);
        boolean hasEnough = true;
        hasEnough = hasEnough && (yopCards.getBrick() <= cards.getBrick());
        hasEnough = hasEnough && (yopCards.getGrain() <= cards.getGrain());
        hasEnough = hasEnough && (yopCards.getLumber() <= cards.getLumber());
        hasEnough = hasEnough && (yopCards.getOre() <= cards.getOre());
        hasEnough = hasEnough && (yopCards.getWool() <= cards.getWool());
        return hasEnough;
    }

    private ClientModel checkLongestRoad(ClientModel model, PlayerIdx idx)
    {
        Player currentPlayer = this.getPlayerFromIdx(idx, model);
        if(currentPlayer.getRoads() <= 10)
        {
            NullablePlayerIdx currentHolder = model.getTurnTracker().getLongestRoad();
            if(currentHolder.getIndex() == currentPlayer.getPlayerId())
                return model;
            if(currentHolder.isNull() || this.getPlayerFromIdx(new PlayerIdx(currentHolder.getIndex()), model).getRoads() > currentPlayer.getRoads())
            {
                model.getTurnTracker().setLongestRoad(new NullablePlayerIdx(idx.getIndex()));
                currentPlayer.setVictoryPoints(currentPlayer.getVictoryPoints() + 2);
                model = this.setPlayerFromIdx(idx, model, currentPlayer);
                if(currentHolder.isNotNull())
                {
                    Player oldPlayer = this.getPlayerFromIdx(new PlayerIdx(currentHolder.getIndex()), model);
                    oldPlayer.setVictoryPoints(oldPlayer.getVictoryPoints() - 2);
                    model = this.setPlayerFromIdx(oldPlayer.getPlayerIndex(), model, oldPlayer);
                }
            }
        }
        return model;
    }
    
    /**
     * Sets all players haveDiscarded
     * @param model The current game we're working on.
     * @return The new game with the modified players
     */
    private ClientModel playersDiscard(ClientModel model)
    {
        Collection<Player> players = model.getPlayers();
        for (Player player : players)
        {
            player.setDiscarded(!player.needtoDiscard());
            model = setPlayerFromIdx(player.getPlayerIndex(), model, player);
        }
        return model;
    }
    
    /**
     * Checks if there is a player that still hasn't discarded.
     * @param model The current game we are working with
     * @return Boolean (true if all players have discarded)
     */
    private boolean playersHaveDiscarded(ClientModel model)
    {
        Collection<Player> players = model.getPlayers();
        for (Player player : players)
        {
            if (!player.getDiscarded())
                return false;
        }
        return true;
    }
    
    /**
     * Safely gives the players the resources they deserve due to the roll. If there aren't enough resources
     * for everyone then nobody gets any cards.
     * @param resource
     * @param model
     * @param original
     * @param number
     * @param hexes
     * @return 
     */
    private ClientModel safeGiveRolledResource(ResourceType resource, ClientModel model, ClientModel original, int number, Collection<Hex> hexes)
    {
        for (Hex hex : hexes) 
        {
            if (hex.getNumber() == number && hexToResource(hex) == resource)
            {
                Hand bank = model.getBank();
                ResourceCards bankCards = bank.getResourceCards();
                Collection<Player> players = getSettlementsByHexLocation(hex.getLocation(), model);
                for (Player player : players)
                {
                    if (!bankHasResource(bankCards, resource, 1))
                        return original;
                    Hand hand = player.getHand();
                    ResourceCards cards = changeResource(hand.getResourceCards(), resource, 1);
                    hand.setResourceCards(cards);
                    player.setHand(hand);
                    model = setPlayerFromIdx(player.getPlayerIndex(), model, player);
                    
                    bankCards = changeResource(bankCards, resource, -1);
                    bank.setResourceCards(bankCards);
                    model.setBank(bank);
                }
                players = getCitiesByHexLocation(hex.getLocation(), model);
                for (Player player : players)
                {
                    if (!bankHasResource(bankCards, resource, 2))
                        return original;
                    Hand hand = player.getHand();
                    ResourceCards cards = changeResource(hand.getResourceCards(), resource, 2);
                    hand.setResourceCards(cards);
                    player.setHand(hand);
                    model = setPlayerFromIdx(player.getPlayerIndex(), model, player);
                    
                    bankCards = changeResource(bankCards, resource, -2);
                    bank.setResourceCards(bankCards);
                    model.setBank(bank);
                }
            }
        }
        return model;
    }
    
    /**
     * Check if the bank has enough of that resource
     * @param bank
     * @param resource
     * @param amount
     * @return 
     */
    private boolean bankHasResource(ResourceCards bank, ResourceType resource, int amount)
    {
        switch (resource)
        {
            case BRICK:
                return bank.getBrick() >= amount;
            case WOOD:
                return bank.getLumber() >= amount;
            case ORE:
                return bank.getOre() >= amount;
            case WHEAT:
                return bank.getGrain() >= amount;
            case SHEEP:
                return bank.getWool() >= amount;
        }
        return false;
    }
}
