/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.Server;

/**
 *
 * @author Michael
 */
public interface IServerProxy 
{

    /**
     *
     * @param body
     * @return
     */
    String loginUser(String body);

    /**
     *
     * @param body
     * @return
     */
    String registerUser(String body);

    /**
     *
     * @return
     */
    String listGames();

    /**
     *
     * @param body
     * @return
     */
    String createGame(String body);

    /**
     *
     * @param body
     * @return
     */
    String joinGame(String body);

    /**
     *
     * @param body
     * @return
     */
    String saveGame(String body);

    /**
     *
     * @param body
     * @return
     */
    String loadGame(String body);

    /**
     *
     * @return
     */
    String getModel();

    /**
     *
     * @return
     */
    String resetGame();

    /**
     *
     * @param body
     * @return
     */
    String getCommandsOfGame(String body);

    /**
     *
     * @param body
     * @return
     */
    String postCommandsToGame(String body);

    /**
     *
     * @param body
     * @return
     */
    String addAI(String body);

    /**
     *
     * @param body
     * @return
     */
    String sendChat(String body);

    /**
     *
     * @param body
     * @return
     */
    String rollNumber(String body);

    /**
     *
     * @param body
     * @return
     */
    String robPlayer(String body);

    /**
     *
     * @param body
     * @return
     */
    String finishTurn(String body);

    /**
     *
     * @param body
     * @return
     */
    String buyDevCard(String body);

    /**
     *
     * @param body
     * @return
     */
    String yearOfPlenty(String body);

    /**
     *
     * @param body
     * @return
     */
    String playRoadBuilding(String body);

    /**
     *
     * @param body
     * @return
     */
    String playSoldier(String body);

    /**
     *
     * @param body
     * @return
     */
    String playMonopoly(String body);

    /**
     *
     * @param body
     * @return
     */
    String playMonument(String body);

    /**
     *
     * @param body
     * @return
     */
    String buildRoad(String body);

    /**
     *
     * @param body
     * @return
     */
    String buildSettlement(String body);

    /**
     *
     * @param body
     * @return
     */
    String buildCity(String body);

    /**
     *
     * @param body
     * @return
     */
    String offerTrade(String body);

    /**
     *
     * @param body
     * @return
     */
    String acceptTrade(String body);

    /**
     *
     * @param body
     * @return
     */
    String maritimeTrade(String body);

    /**
     *
     * @param body
     * @return
     */
    String discardCards(String body); 
}
