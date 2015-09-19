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
    String loginUser(String body);
    String registerUser(String body);
    String listGames();
    String createGame(String body);
    String joinGame(String body);
    String saveGame(String body);
    String loadGame(String body);
    String getModel();
    String resetGame();
    String getCommandsOfGame(String body);
    String postCommandsToGame(String body);
    String addAI(String body);
    String sendChat(String body);
    String rollNumber(String body);
    String robPlayer(String body);
    String finishTurn(String body);
    String buyDevCard(String body);
    String yearOfPlenty(String body);
    String playRoadBuilding(String body);
    String playSoldier(String body);
    String playMonopoly(String body);
    String playMonument(String body);
    String buildRoad(String body);
    String buildSettlement(String body);
    String buildCity(String body);
    String offerTrade(String body);
    String acceptTrade(String body);
    String maritimeTrade(String body);
    String discardCards(String body); 
}
