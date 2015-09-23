package clientcommunicator.Server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerProxy implements IServerProxy
{

    @Override
    public int loginUser(String body)
    {
        this.post("/user/login");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int registerUser(String body)
    {
        this.post("/user/register");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String listGames()
    {
        this.post("/games/list");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String createGame(String body)
    {
        this.post("/game/create");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String joinGame(String body)
    {
        this.post("/games/join");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String saveGame(String body)
    {
        this.post("/games/save");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String loadGame(String body)
    {
        this.post("/games/load");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getModel(int versionNumber)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("/games/model?version=");
        sb.append(versionNumber);
        this.get(sb.toString());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String resetGame()
    {
        this.post("/game/reset");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCommandsOfGame(String body)
    {
        this.get("/game/commands");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String postCommandsToGame(String body)
    {
        this.post("/game/commands");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String addAI(String body)
    {
        this.post("/games/addAI");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String sendChat(String body)
    {
        this.post("moves/sendChat");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String rollNumber(String body)
    {
        this.post("/moves/rollNumber");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String robPlayer(String body)
    {
        this.post("/moves/robPlayer");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String finishTurn(String body)
    {
        this.post("/moves/finishTurn");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buyDevCard(String body)
    {
        this.post("/moves/buyDevCard");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String yearOfPlenty(String body)
    {
        this.post("/moves/Year_of_Plenty");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playRoadBuilding(String body)
    {
        this.post("/moves/Road_Building");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playSoldier(String body)
    {
        this.post("/moves/Soldier");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playMonopoly(String body)
    {
        this.post("/moves/Monopoly");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playMonument(String body)
    {
        this.post("/moves/Monument");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buildRoad(String body)
    {
        this.post("/moves/buildRoad");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buildSettlement(String body)
    {
        this.post("/moves/buildSettlement");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buildCity(String body)
    {
        this.post("/moves/buildCity");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String offerTrade(String body)
    {
        this.post("/moves/offerTrade");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String acceptTrade(String body)
    {
        this.post("/moves/acceptTrade");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String maritimeTrade(String body)
    {
        this.post("/moves/maritimeTrade");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String discardCards(String body)
    {
        this.post("/moves/discardCards");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // -------------------------------------------------------------------------
    // Below are all of the private variables/functions
    // They're down here at the bottom because they are only for making 
    // HTTP requests.
    // -------------------------------------------------------------------------
    private static String SERVER_HOST = "localhost";
    private static String SERVER_PORT = "8080";
    private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;

    private void get(String path)
    {
        try
        {
            URL myURL = new URL(URL_PREFIX + path);
            HttpURLConnection connect = (HttpURLConnection) myURL.openConnection();
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Accept", "text/html");
            connect.connect();
            // do something
	}
        catch (IOException e) 
        {
            // return a failed status or something
        }
    }
    
    private void post(String path)
    {
        try
        {
            URL myURL = new URL(URL_PREFIX + path);
            HttpURLConnection connect = (HttpURLConnection) myURL.openConnection();
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Accept", "text/html");
            connect.connect();
            // do something
	}
        catch (IOException e) 
        {
            // return a failed status or something
        }
    }
}
