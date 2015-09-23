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
        return "{\n" +
"  \"deck\": {\n" +
"    \"yearOfPlenty\": 2,\n" +
"    \"monopoly\": 2,\n" +
"    \"soldier\": 14,\n" +
"    \"roadBuilding\": 2,\n" +
"    \"monument\": 5\n" +
"  },\n" +
"  \"map\": {\n" +
"    \"hexes\": [\n" +
"      {\n" +
"        \"resource\": \"brick\",\n" +
"        \"location\": {\n" +
"          \"x\": 0,\n" +
"          \"y\": -2\n" +
"        },\n" +
"        \"number\": 4\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"sheep\",\n" +
"        \"location\": {\n" +
"          \"x\": 1,\n" +
"          \"y\": -2\n" +
"        },\n" +
"        \"number\": 9\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"wood\",\n" +
"        \"location\": {\n" +
"          \"x\": 2,\n" +
"          \"y\": -2\n" +
"        },\n" +
"        \"number\": 4\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"ore\",\n" +
"        \"location\": {\n" +
"          \"x\": -1,\n" +
"          \"y\": -1\n" +
"        },\n" +
"        \"number\": 9\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"wood\",\n" +
"        \"location\": {\n" +
"          \"x\": 0,\n" +
"          \"y\": -1\n" +
"        },\n" +
"        \"number\": 11\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"wheat\",\n" +
"        \"location\": {\n" +
"          \"x\": 1,\n" +
"          \"y\": -1\n" +
"        },\n" +
"        \"number\": 8\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"sheep\",\n" +
"        \"location\": {\n" +
"          \"x\": 2,\n" +
"          \"y\": -1\n" +
"        },\n" +
"        \"number\": 10\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"wood\",\n" +
"        \"location\": {\n" +
"          \"x\": -2,\n" +
"          \"y\": 0\n" +
"        },\n" +
"        \"number\": 3\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"wheat\",\n" +
"        \"location\": {\n" +
"          \"x\": -1,\n" +
"          \"y\": 0\n" +
"        },\n" +
"        \"number\": 2\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"wood\",\n" +
"        \"location\": {\n" +
"          \"x\": 0,\n" +
"          \"y\": 0\n" +
"        },\n" +
"        \"number\": 6\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"sheep\",\n" +
"        \"location\": {\n" +
"          \"x\": 1,\n" +
"          \"y\": 0\n" +
"        },\n" +
"        \"number\": 12\n" +
"      },\n" +
"      {\n" +
"        \"location\": {\n" +
"          \"x\": 2,\n" +
"          \"y\": 0\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"brick\",\n" +
"        \"location\": {\n" +
"          \"x\": -2,\n" +
"          \"y\": 1\n" +
"        },\n" +
"        \"number\": 8\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"wheat\",\n" +
"        \"location\": {\n" +
"          \"x\": -1,\n" +
"          \"y\": 1\n" +
"        },\n" +
"        \"number\": 11\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"sheep\",\n" +
"        \"location\": {\n" +
"          \"x\": 0,\n" +
"          \"y\": 1\n" +
"        },\n" +
"        \"number\": 10\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"wheat\",\n" +
"        \"location\": {\n" +
"          \"x\": 1,\n" +
"          \"y\": 1\n" +
"        },\n" +
"        \"number\": 6\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"ore\",\n" +
"        \"location\": {\n" +
"          \"x\": -2,\n" +
"          \"y\": 2\n" +
"        },\n" +
"        \"number\": 3\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"brick\",\n" +
"        \"location\": {\n" +
"          \"x\": -1,\n" +
"          \"y\": 2\n" +
"        },\n" +
"        \"number\": 5\n" +
"      },\n" +
"      {\n" +
"        \"resource\": \"ore\",\n" +
"        \"location\": {\n" +
"          \"x\": 0,\n" +
"          \"y\": 2\n" +
"        },\n" +
"        \"number\": 5\n" +
"      }\n" +
"    ],\n" +
"    \"roads\": [],\n" +
"    \"cities\": [],\n" +
"    \"settlements\": [],\n" +
"    \"radius\": 3,\n" +
"    \"ports\": [\n" +
"      {\n" +
"        \"ratio\": 3,\n" +
"        \"direction\": \"S\",\n" +
"        \"location\": {\n" +
"          \"x\": 1,\n" +
"          \"y\": -3\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"ratio\": 2,\n" +
"        \"resource\": \"brick\",\n" +
"        \"direction\": \"NE\",\n" +
"        \"location\": {\n" +
"          \"x\": -2,\n" +
"          \"y\": 3\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"ratio\": 2,\n" +
"        \"resource\": \"sheep\",\n" +
"        \"direction\": \"NW\",\n" +
"        \"location\": {\n" +
"          \"x\": 2,\n" +
"          \"y\": 1\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"ratio\": 2,\n" +
"        \"resource\": \"ore\",\n" +
"        \"direction\": \"N\",\n" +
"        \"location\": {\n" +
"          \"x\": 0,\n" +
"          \"y\": 3\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"ratio\": 3,\n" +
"        \"direction\": \"NE\",\n" +
"        \"location\": {\n" +
"          \"x\": -3,\n" +
"          \"y\": 2\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"ratio\": 2,\n" +
"        \"resource\": \"wood\",\n" +
"        \"direction\": \"SW\",\n" +
"        \"location\": {\n" +
"          \"x\": 3,\n" +
"          \"y\": -3\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"ratio\": 2,\n" +
"        \"resource\": \"wheat\",\n" +
"        \"direction\": \"SE\",\n" +
"        \"location\": {\n" +
"          \"x\": -3,\n" +
"          \"y\": 0\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"ratio\": 3,\n" +
"        \"direction\": \"NW\",\n" +
"        \"location\": {\n" +
"          \"x\": 3,\n" +
"          \"y\": -1\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"ratio\": 3,\n" +
"        \"direction\": \"S\",\n" +
"        \"location\": {\n" +
"          \"x\": -1,\n" +
"          \"y\": -2\n" +
"        }\n" +
"      }\n" +
"    ],\n" +
"    \"robber\": {\n" +
"      \"x\": 2,\n" +
"      \"y\": 0\n" +
"    }\n" +
"  },\n" +
"  \"players\": [\n" +
"    {\n" +
"      \"resources\": {\n" +
"        \"brick\": 0,\n" +
"        \"wood\": 0,\n" +
"        \"sheep\": 0,\n" +
"        \"wheat\": 0,\n" +
"        \"ore\": 0\n" +
"      },\n" +
"      \"oldDevCards\": {\n" +
"        \"yearOfPlenty\": 0,\n" +
"        \"monopoly\": 0,\n" +
"        \"soldier\": 0,\n" +
"        \"roadBuilding\": 0,\n" +
"        \"monument\": 0\n" +
"      },\n" +
"      \"newDevCards\": {\n" +
"        \"yearOfPlenty\": 0,\n" +
"        \"monopoly\": 0,\n" +
"        \"soldier\": 0,\n" +
"        \"roadBuilding\": 0,\n" +
"        \"monument\": 0\n" +
"      },\n" +
"      \"roads\": 15,\n" +
"      \"cities\": 4,\n" +
"      \"settlements\": 5,\n" +
"      \"soldiers\": 0,\n" +
"      \"victoryPoints\": 0,\n" +
"      \"monuments\": 0,\n" +
"      \"playedDevCard\": false,\n" +
"      \"discarded\": false,\n" +
"      \"playerID\": 12,\n" +
"      \"playerIndex\": 0,\n" +
"      \"name\": \"Michael\",\n" +
"      \"color\": \"white\"\n" +
"    },\n" +
"    null,\n" +
"    null,\n" +
"    null\n" +
"  ],\n" +
"  \"log\": {\n" +
"    \"lines\": []\n" +
"  },\n" +
"  \"chat\": {\n" +
"    \"lines\": []\n" +
"  },\n" +
"  \"bank\": {\n" +
"    \"brick\": 24,\n" +
"    \"wood\": 24,\n" +
"    \"sheep\": 24,\n" +
"    \"wheat\": 24,\n" +
"    \"ore\": 24\n" +
"  },\n" +
"  \"turnTracker\": {\n" +
"    \"status\": \"FirstRound\",\n" +
"    \"currentTurn\": 0,\n" +
"    \"longestRoad\": -1,\n" +
"    \"largestArmy\": -1\n" +
"  },\n" +
"  \"winner\": -1,\n" +
"  \"version\": 0\n" +
"}";
    }

    @Override
    public String resetGame()
    {
        return this.getModel(-1);
    }

    @Override
    public String getCommandsOfGame(String body)
    {
        return "[]";
    }

    @Override
    public String postCommandsToGame(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String addAI(String body)
    {
        return "{\n" +
"AIType: 'LARGEST_ARMY'\n" +
"}";
    }

    @Override
    public String sendChat(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String rollNumber(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String robPlayer(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String finishTurn(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String buyDevCard(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String yearOfPlenty(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String playRoadBuilding(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String playSoldier(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String playMonopoly(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String playMonument(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String buildRoad(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String buildSettlement(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String buildCity(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String offerTrade(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String acceptTrade(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String maritimeTrade(String body)
    {
        return this.getModel(-1);
    }

    @Override
    public String discardCards(String body)
    {
        return this.getModel(-1);
    }
    
}
