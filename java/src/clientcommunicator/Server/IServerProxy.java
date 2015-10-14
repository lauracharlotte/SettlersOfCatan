/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.Server;

import clientcommunicator.modelserverfacade.ClientException;

/**
 *
 * @author Michael
 */
public interface IServerProxy 
{

    /**
     * @pre The username and password in the body have been registered. The username and password are not empty. No player is logged in.
     * @post The player is logged in.
     * @param body A valid JSON body for a loginUser http request
     * @return The unique player id
     * @throws ClientException
     */
    int loginUser(String body) throws ClientException;

    /**
     * @pre The username is not associated with an existing player. The username and password are not null.  No player is logged in.
     * @post The player is logged in. The username and password are valid for login.
     * @param body A valid JSON body for a register user request.
     * @return The unique player id
     * @throws ClientException
     */
    int registerUser(String body) throws ClientException;

    /**
     * @pre The player has been logged in.
     * @return The body of the message that is in response to the list games request.
     * @throws ClientException
     */
    String listGames() throws ClientException;

    /**
     * @pre The player has been logged in and is not currently in a game.
     * @post A new game has been created on the server.
     * @param body The JSON representation of the required http request body for creating a game.
     * @return The body of the message that is in response to the create games request.
     * @throws ClientException
     */
    String createGame(String body)throws ClientException;

    /**
     * @pre The player has been logged in and is not currently in a game.
     * @post The player is participating in the game with the specified color and cannot join another one. The catan.game cookie is set.
     * @param body The JSON representation of the required http request body for joining a game.
     * @return The body of the message that is in response to the join game request.
     * @throws ClientException
     */
    String joinGame(String body, boolean keepCookie) throws ClientException;

    /**
     * @pre The user is logged in and is currently in a game.
     * @param versionNumber The version number of the current working model. -1 if current version number is not known.
     * @return The body of the message that is in response to the get model request. Returns "true" if given versionNumber matches current number. Else returns newest model.
     * @throws ClientException
     */
    String getModel(int versionNumber) throws ClientException;

    /**
     * @pre The user is logged in and in a game.  The current game has space for another player. The AI Type in the body is valid.
     * @post A new AI player has been added. 
     * @param body The JSON representation of the required request body for adding an AI.
     * @return The body of the message that is in response to the add AI request.
     * @throws ClientException
     */
    String addAI(String body) throws ClientException;

    /**
     * @pre None
     * @post The chat contains the message specified in the body parameter
     * @param body The JSON representation of the required request body for adding a chat message.
     * @return The body of the message that is in response to the send chat request.
     * @throws ClientException
     */
    String sendChat(String body) throws ClientException;

    /**
     * @pre It is the client's turn. The client model's status is "rolling".
     * @post The client model's status will no longer show as "rolling."
     * @param body The JSON representation of the required request body for rolling.
     * @return The body of the message that is in response to the roll number request.
     * @throws ClientException
     */
    String rollNumber(String body) throws ClientException;

    /**
     * @pre The robber is being moved. The player(if any) being robbed can give you a resource card.
     * @post The robber moved.  The player that was robbed gives the client a resource card.
     * @param body The JSON representation of the required request body for robbing.
     * @return The body of the message that is in response to the rob player request.
     * @throws ClientException
     */
    String robPlayer(String body) throws ClientException;

    /**
     * @pre It is your turn.  You are logged in, in a game.
     * @post The cards in your new dev card hand are now in the old dev card hand.  It is the next player's turn.
     * @param body The JSON representation of the required request body for finishing a turn.
     * @return The body of the message that is in response to the finish turn request.
     * @throws ClientException
     */
    String finishTurn(String body) throws ClientException;

    /**
     * @pre You have the required resources.  There are dev cards to buy.
     * @post You have a new dev card. You have paid the resource cost.
     * @param body The JSON representation of the required request body for buying a dev card.
     * @return The body of the message that is in response to the buy dev card request.
     * @throws ClientException
     */
    String buyDevCard(String body) throws ClientException;

    /**
     * @pre The bank has the two specified resources.
     * @post You gain the resources.
     * @param body The JSON representation of the required request body for playing year of plenty.
     * @return The body of the message that is in response to the year of plenty request.
     * @throws ClientException
     */
    String yearOfPlenty(String body) throws ClientException;

    /**
     * @pre The locations specified in the body are connected to your roads.  Neither is on water, and you have two unused roads.
     * @post You use up two roads.  Two new roads show in map.  Longest road may have changed players.
     * @param body The JSON representation of the required request body for playing road building card.
     * @return The body of the message that is in response to the road building card request.
     * @throws ClientException
     */
    String playRoadBuilding(String body) throws ClientException;

    /**
     * @pre The robber is not being kept in same location.  The player being robbed has a resource card.
     * @post The robber has moved.  The player that was robbed has given you one of his resources.  Largest army may have been moved.
     * @param body The JSON representation of the required request body for playing a soldier card.
     * @return The body of the message that is in response to the play soldier request.
     * @throws ClientException
     */
    String playSoldier(String body) throws ClientException;

    /**
     * @pre You are logged in and in a game--haven't played a dev card this turn.
     * @post You have all of the other player's resource cards of the given type.
     * @param body The JSON representation of the required request body for playing a monopoly card.
     * @return The body of the message that is in response to the play monopoly request.
     * @throws ClientException
     */
    String playMonopoly(String body) throws ClientException;

    /**
     * @pre You have enough monument cards to win the game
     * @post You gained a victory point.
     * @param body The JSON representation of the required request body for playing a monument card.
     * @return The body of the message that is in response to the play monument request.
     * @throws ClientException
     */
    String playMonument(String body) throws ClientException;

    /**
     * @pre Location is open.  It is connected to another one of your roads.  It is not on water.  You have required resources.
     * In settlement, must be connected to settlement.
     * @post You lose resources required if it is not setup.  The road is on the map.  Longest road may change players.
     * @param body The JSON representation of the required request body for building a road.
     * @return The body of the message that is in response to the build road request.
     * @throws ClientException
     */
    String buildRoad(String body) throws ClientException;

    /**
     * @pre You have required resources and the space is open, connected to one of your roads (if not setup), and not adjacent to another settlement
     * @post The settlement is places.  You lose resources if not setup.
     * @param body The JSON representation of the required request body for building a settlement.
     * @return The body of the message that is in response to the build settlement request.
     * @throws ClientException
     */
    String buildSettlement(String body) throws ClientException;

    /**
     * @pre You have a settlement at location and required resources.
     * @post You lose resources.  The city is on the map, you get a settlement back.
     * @param body The JSON representation of the required request body for building a city.
     * @return The body of the message that is in response to the build city request.
     * @throws ClientException
     */
    String buildCity(String body) throws ClientException;

    /**
     * @pre You have resources you are offering
     * @post The trade is offered to other player.
     * @param body The JSON representation of the required request body for offering a trade.
     * @return The body of the message that is in response to the offer trade request.
     * @throws ClientException
     */
    String offerTrade(String body) throws ClientException;

    /**
     * @pre A trade has been offered to you
     * @post Resources trade hands
     * @param body The JSON representation of the required request body for accepting a trade.
     * @return The body of the message that is in response to the accept trade request.
     * @throws ClientException
     */
    String acceptTrade(String body) throws ClientException;

    /**
     * @pre You have specified resources--you have port for ratios less than 4.  The bank has what you want.
     * @post The trade is executed
     * @param body The JSON representation of the required request body for maritime trade.
     * @return The body of the message that is in response to the maritime trade request.
     * @throws ClientException
     */
    String maritimeTrade(String body) throws ClientException;

    /**
     * @pre The turn status is "Discarding".  You have over 7 cards.  You have the cards you are discarding.
     * @post You give up the specified resources.  The turn status may change.
     * @param body The JSON representation of the required request body for discarding a card.
     * @return The body of the message that is in response to the discard cards request.
     * @throws ClientException
     */
    String discardCards(String body) throws ClientException; 
}
