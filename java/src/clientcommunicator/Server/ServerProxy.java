package clientcommunicator.Server;

import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 * 
 * @author Scott
 */
public class ServerProxy implements IServerProxy
{

    @Override
    public int loginUser(String body) throws ClientException
    {
        String result = this.post("/user/login", body);
        try {
            return cookie.setUserCookieString(result);
        } catch (MalformedCookieException e) {
            throw new ClientException(String.format("cookie error: %s", e.getMessage()), e);
        }
    }

    @Override
    public int registerUser(String body) throws ClientException
    {
        String result = this.post("/user/register", body);
        try {
            return cookie.setUserCookieString(result);
        } catch (MalformedCookieException e) {
            throw new ClientException(String.format("cookie error: %s", e.getMessage()), e);
        }
    }

    @Override
    public String listGames() throws ClientException
    {
        return this.get("/games/list");
    }

    @Override
    public String createGame(String body) throws ClientException
    {
        return this.post("/games/create", body);
    }

    @Override
    public String joinGame(String body, boolean keepCookie) throws ClientException
    {
        String gameCookie = this.post("/games/join", body);
        try
        {
            if(keepCookie)
                this.cookie.setGameNumberFromCookie(gameCookie);
        }
        catch (MalformedCookieException ex)
        {
            Logger.getLogger(ServerProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Success";
    }

    @Override
    public String getModel(int versionNumber) throws ClientException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("/game/model");
        if(versionNumber != -1)
            sb.append("?version=").append(versionNumber);
        return this.get(sb.toString());
    }

    @Override
    public String addAI(String body) throws ClientException
    {
        return this.post("/game/addAI", body);
    }

    @Override
    public String sendChat(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/sendChat", body));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String rollNumber(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/rollNumber", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String robPlayer(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/robPlayer", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String finishTurn(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/finishTurn", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String buyDevCard(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/buyDevCard", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String yearOfPlenty(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/Year_of_Plenty", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String playRoadBuilding(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/Road_Building", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String playSoldier(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/Soldier", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String playMonopoly(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/Monopoly", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String playMonument(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/Monument", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String buildRoad(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/buildRoad", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String buildSettlement(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/buildSettlement", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String buildCity(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/buildCity", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String offerTrade(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/offerTrade", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String acceptTrade(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/acceptTrade", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String maritimeTrade(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/maritimeTrade", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @Override
    public String discardCards(String body) throws ClientException
    {
        // update model
        ModelServerFacadeFactory model = ModelServerFacadeFactory.getInstance();
        try {
            model.updateModel(this.post("/moves/discardCards", body));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "Success";
    }
    
    public static void setSERVER_HOST(String SERVER_HOST)
    {
        ServerProxy.SERVER_HOST = SERVER_HOST;
        ServerProxy.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }

    // -------------------------------------------------------------------------
    // Below are all of the private variables/functions
    // They're down here at the bottom because they are only for making 
    // HTTP requests.
    // -------------------------------------------------------------------------
    public static void setSERVER_PORT(String SERVER_PORT)
    {
        ServerProxy.SERVER_PORT = SERVER_PORT;
        ServerProxy.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }
    
    
    private static String SERVER_HOST="";
    private static String SERVER_PORT="";
    private static String URL_PREFIX;
    private Cookie cookie = new Cookie();
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
            String cookieString = cookie.getCompleteCookieString();
            // Connect with the server
            URL myURL = new URL(URL_PREFIX + path);
            HttpURLConnection connect = (HttpURLConnection) myURL.openConnection();
            connect.setRequestMethod("GET");
            if (!"".equals(cookieString))
            {
                connect.setRequestProperty("Cookie", cookieString);
            }
            
            // If the server sends back a response code that is not 200
            if (connect.getResponseCode() != HttpURLConnection.HTTP_OK) 
            {
                throw new ClientException(getErrorResponse(connect));
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
            String cookieString = cookie.getCompleteCookieString();
            // Connect to the server
            URL myURL = new URL(URL_PREFIX + path);
            HttpURLConnection connect = (HttpURLConnection) myURL.openConnection();
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("Content-Length", Integer.toString(body.length()));
            if (!cookieString.equals(""))
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
                throw new ClientException(getErrorResponse(connect));
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
    
    private String getErrorResponse(HttpURLConnection connect)
    {
        try {
            // Buffer-read the result from the server
            BufferedReader br;
            if(connect.getErrorStream() != null)
                br = new BufferedReader(new InputStreamReader(
                (connect.getErrorStream())));
            else
                return "";
            // buffer into a string
            StringBuilder output = new StringBuilder();
            String line;
            while( (line = br.readLine()) != null) 
            {
               output.append(line);
            }

            // Close the buffer
            br.close();
            
            return output.toString();
        } 
        catch (IOException e)
        {
            return "400 Response from server";
        }
        
    }
}
