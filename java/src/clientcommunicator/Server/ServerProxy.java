package clientcommunicator.Server;

<<<<<<< HEAD
=======
import clientcommunicator.modelserverfacade.ClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
public class ServerProxy implements IServerProxy
{

    @Override
    public int loginUser(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        String result = this.post("/user/login", body);
        Cookie cookie = new Cookie();
        try {
            return cookie.setUserCookieString(result);
        } catch (MalformedCookieException e) {
            throw new ClientException(String.format("cookie error: %s", e.getMessage()), e);
        }
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public int registerUser(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        String result = this.post("/user/register", body);
        Cookie cookie = new Cookie();
        try {
            return cookie.setUserCookieString(result);
        } catch (MalformedCookieException e) {
            throw new ClientException(String.format("cookie error: %s", e.getMessage()), e);
        }
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String listGames() throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.get("/games/list");
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String createGame(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/game/create", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String joinGame(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/games/join", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String saveGame(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/games/save", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String loadGame(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/games/load", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String getModel(int versionNumber) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        StringBuilder sb = new StringBuilder();
        sb.append("/games/model?version=");
        sb.append(versionNumber);
        return this.get(sb.toString());
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String resetGame() throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/game/reset", "");
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String getCommandsOfGame(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.get("/game/commands");
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String postCommandsToGame(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/game/commands", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String addAI(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/games/addAI", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String sendChat(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("moves/sendChat", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String rollNumber(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/rollNumber", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String robPlayer(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/robPlayer", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String finishTurn(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/finishTurn", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String buyDevCard(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/buyDevCard", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String yearOfPlenty(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/Year_of_Plenty", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String playRoadBuilding(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/Road_Building", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String playSoldier(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/Soldier", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String playMonopoly(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/Monopoly", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String playMonument(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/Monument", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String buildRoad(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/buildRoad", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String buildSettlement(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/buildSettlement", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String buildCity(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/buildCity", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String offerTrade(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/offerTrade", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String acceptTrade(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/acceptTrade", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String maritimeTrade(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        return this.post("/moves/maritimeTrade", body);
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
    }

    @Override
    public String discardCards(String body) throws ClientException
    {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

=======
        return this.post("/moves/discardCards", body);
    }
    
    // -------------------------------------------------------------------------
    // Below are all of the private variables/functions
    // They're down here at the bottom because they are only for making 
    // HTTP requests.
    // -------------------------------------------------------------------------
    private static String SERVER_HOST = "localhost";
    private static String SERVER_PORT = "8081";
    private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;

    /**
     * Performs the HTTP Get Request to the server.
     * Returns the data received from the server in a String JSON
     * @param path
     * @return output
     * @throws ClientException
     */
    private String get(String path) throws ClientException
    {
        try
        {
            Cookie cookie = new Cookie();
            String cookieString = cookie.getCompleteCookieString();
            // Connect with the server
            URL myURL = new URL(URL_PREFIX + path);
            HttpURLConnection connect = (HttpURLConnection) myURL.openConnection();
            connect.setRequestMethod("GET");
            if (cookieString != "")
            {
                connect.setRequestProperty("Cookie", cookieString);
            }
            
            // If the server sends back a response code that is not 200
            if (connect.getResponseCode() != HttpURLConnection.HTTP_OK) 
            {
                throw new ClientException(String.format("post failed: %s (http code %d)", path, connect.getResponseCode()));
            }
            
            // Buffer-read the result from the server
            BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) 
            {
               output.append(line);
            }
            
            // Close the buffer
            br.close();
            // Disconnect from the server
            connect.disconnect();
            
            return output.toString();
	}
        // If there is some kind of error with connecting to the server
        catch (IOException e) 
        {
            throw new ClientException(String.format("post failed: %s", e.getMessage()), e);
        }
    }
    
    /**
     * Performs the HTTP Post Request to the server.
     * Returns the data received from the server in a String JSON
     * @param path
     * @param body
     * @return output
     * @throws ClientException 
     */
    private String post(String path, String body) throws ClientException
    {
        try
        {
            Cookie cookie = new Cookie();
            String cookieString = cookie.getCompleteCookieString();
            // Connect to the server
            URL myURL = new URL(URL_PREFIX + path);
            HttpURLConnection connect = (HttpURLConnection) myURL.openConnection();
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("Content-Length", Integer.toString(body.length()));
            if (cookieString != "")
            {
                connect.setRequestProperty("Cookie", cookieString);
            }

            // Send the body if there is something to send
            if (body.length() > 1)
            {
                OutputStream os = connect.getOutputStream();
                os.write(body.getBytes());
                os.flush();
            }
            
            // If the server sends back a response code that is not 200
            if (connect.getResponseCode() != HttpURLConnection.HTTP_OK) 
            {
                throw new ClientException(String.format("post failed: %s (http code %d)", path, connect.getResponseCode()));
            }
            
            String result_cookie = connect.getHeaderField("Set-cookie");
            if (result_cookie != null)
            {
                return result_cookie;
            }
            
            // Buffer-read the result from the server
            BufferedReader br = new BufferedReader(new InputStreamReader(
                (connect.getInputStream())));
            
            // buffer into a string
            StringBuilder output = new StringBuilder();
            String line;
            while( (line = br.readLine()) != null) 
            {
               output.append(line);
            }
            
            // Close the buffer
            br.close();
            // disconnect from the server
            connect.disconnect();
            
            return output.toString();
	}
        // If there is some kind of error with connecting to the server
        catch (IOException e) 
        {
            throw new ClientException(String.format("post failed: %s", e.getMessage()), e);
        }
    }
>>>>>>> 600b981c7392c60b23722148464c943bf624d2ce
}
