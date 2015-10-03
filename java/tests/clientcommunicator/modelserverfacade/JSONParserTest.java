package clientcommunicator.modelserverfacade;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.json.JSONException;

import clientcommunicator.Server.MockServerProxy;
import model.ClientModel;
import model.messages.MessageLine;
import model.player.Player;

public class JSONParserTest {
	
	String sampleJSON1;
	String sampleJSON2;
	
	public JSONParserTest()
	{
		
	}
	
	@Before
	public void setUp()
	{
		sampleJSON1 = "{\n" +
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
				"    \"lines\": [\n" +
				"      {\n" +
				"        \"source\": \"server\",\n" +
				"        \"message\": \"invalid operation\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"client\",\n" +
				"        \"message\": \"game starting\"\n" +
				"      }\n" +
				"    ]\n" +
				"  },\n" +
				"  \"chat\": {\n" +
				"    \"lines\": [\n" +
				"      {\n" +
				"        \"source\": \"player 0\",\n" +
				"        \"message\": \"Hello!\"\n" +
				"      }\n" +
				"    ]\n" +
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
		
		sampleJSON2 = "{\n" +
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
				"  \"tradeOffer\": {\n" +
				"    \"sender\": 0,\n" +
				"    \"receiver\": 1,\n" +
				"    \"offer\": {\n" +
				"      \"brick\": 2,\n" +
				"      \"wood\": 0,\n" +
				"      \"sheep\": 0,\n" +
				"      \"wheat\": 0,\n" +
				"      \"ore\": -1\n" +
				"     }\n" +
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
	
	@Test
	public void testCreateModels() throws JSONException
	{
		System.out.println("Setting up MockServerProxy for create model test...");
		MockServerProxy server = new MockServerProxy();
		String modelJSON = server.getModel(0);
		System.out.println("JSONParser parsing model from MockServerProxy...");
		ClientModel model = JSONParser.fromJSONToModel(modelJSON);
		System.out.println("Testing sample of items in model...");
		int expResult = 14;
		int result = model.getBank().getDevelopmentCards().getSoldier();
		assertEquals(expResult, result);
		expResult = 3;
		result = model.getMap().getRadius();
		assertEquals(expResult, result);
		expResult = 37;
		result = model.getMap().getHexes().size();
		assertEquals(expResult, result);
		expResult = 12;
		ArrayList<Player> players = (ArrayList<Player>) model.getPlayers();
		result = players.get(0).getPlayerId();
		assertEquals(expResult, result);
		System.out.println("Test create model from MockServerProxy passed.");
	}
	
	@Test
	public void testCreateModelWithMessages() throws JSONException
	{
		System.out.println("JSONParser parsing model with chat and log...");
		ClientModel model = JSONParser.fromJSONToModel(sampleJSON1);
		System.out.println("Testing sample of items in model...");
		String expected = "Hello!";
		ArrayList<MessageLine> lines = (ArrayList<MessageLine>) model.getChat().getLines();
		String actual = lines.get(0).getMessage();
		assertEquals(expected, actual);
		int expResult = 2;
		int result = model.getLog().getLines().size();
		assertEquals(expResult, result);
		expected = "firstround";
		actual = model.getTurnTracker().getStatus().toString();
		assertEquals(expected, actual);
		expected = "WHITE";
		ArrayList<Player> players = (ArrayList<Player>) model.getPlayers();
		actual = players.get(0).getColor().toString();
		assertEquals(expected, actual);
		System.out.println("Test create model with chat and log passed.");
	}
	
	@Test
	public void testCreateModelWithTradeOffer() throws JSONException
	{
		System.out.println("JSONParser parsing model with TradeOffer...");
		ClientModel model = JSONParser.fromJSONToModel(sampleJSON2);
		System.out.println("Testing sample of items in model...");
		int expResult = 24;
		int result = model.getBank().getResourceCards().getBrick();
		assertEquals(expResult, result);
		expResult = 1;
		result = model.getTradeOffer().getReceiverNumber().getIndex();
		assertEquals(expResult, result);
		expResult = -1;
		result = model.getTradeOffer().getResourceCards().getOre();
		assertEquals(expResult, result);
		expResult = 9;
		result = model.getMap().getPorts().size();
		assertEquals(expResult, result);
		System.out.println("Test create model with TradeOffer passed.");
	}
}