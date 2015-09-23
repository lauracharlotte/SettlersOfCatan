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
public class MockServerProxy implements IServerProxy
{

    @Override
    public int loginUser(String body)
    {
        return 55;
    }

    @Override
    public int registerUser(String body)
    {
        return 55;
    }

    @Override
    public String listGames()
    {
       return "[\n" +
"  {\n" +
"    \"title\": \"Default Game\",\n" +
"    \"id\": 0,\n" +
"    \"players\": [\n" +
"      {\n" +
"        \"color\": \"orange\",\n" +
"        \"name\": \"Sam\",\n" +
"        \"id\": 0\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"blue\",\n" +
"        \"name\": \"Brooke\",\n" +
"        \"id\": 1\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"red\",\n" +
"        \"name\": \"Pete\",\n" +
"        \"id\": 10\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"green\",\n" +
"        \"name\": \"Mark\",\n" +
"        \"id\": 11\n" +
"      }\n" +
"    ]\n" +
"  },\n" +
"  {\n" +
"    \"title\": \"AI Game\",\n" +
"    \"id\": 1,\n" +
"    \"players\": [\n" +
"      {\n" +
"        \"color\": \"orange\",\n" +
"        \"name\": \"Pete\",\n" +
"        \"id\": 10\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"yellow\",\n" +
"        \"name\": \"Ken\",\n" +
"        \"id\": -2\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"puce\",\n" +
"        \"name\": \"Miguel\",\n" +
"        \"id\": -3\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"blue\",\n" +
"        \"name\": \"Scott\",\n" +
"        \"id\": -4\n" +
"      }\n" +
"    ]\n" +
"  },\n" +
"  {\n" +
"    \"title\": \"Empty Game\",\n" +
"    \"id\": 2,\n" +
"    \"players\": [\n" +
"      {\n" +
"        \"color\": \"orange\",\n" +
"        \"name\": \"Sam\",\n" +
"        \"id\": 0\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"blue\",\n" +
"        \"name\": \"Brooke\",\n" +
"        \"id\": 1\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"red\",\n" +
"        \"name\": \"Pete\",\n" +
"        \"id\": 10\n" +
"      },\n" +
"      {\n" +
"        \"color\": \"green\",\n" +
"        \"name\": \"Mark\",\n" +
"        \"id\": 11\n" +
"      }\n" +
"    ]\n" +
"  }\n" +
"]";
    }

    @Override
    public String createGame(String body)
    {
        return "{\n" +
"  \"title\": \"new game1\",\n" +
"  \"id\": 3,\n" +
"  \"players\": [\n" +
"    {},\n" +
"    {},\n" +
"    {},\n" +
"    {}\n" +
"  ]\n" +
"}";
    }

    @Override
    public String joinGame(String body)
    {
        return "Success";
    }

    @Override
    public String saveGame(String body)
    {
        return "Success";
    }

    @Override
    public String loadGame(String body)
    {
       return "Success";
    }

    @Override
    public String getModel(int versionNumber)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String resetGame()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCommandsOfGame(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String postCommandsToGame(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String addAI(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String sendChat(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String rollNumber(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String robPlayer(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String finishTurn(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buyDevCard(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String yearOfPlenty(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playRoadBuilding(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playSoldier(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playMonopoly(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playMonument(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buildRoad(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buildSettlement(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buildCity(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String offerTrade(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String acceptTrade(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String maritimeTrade(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String discardCards(String body)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
