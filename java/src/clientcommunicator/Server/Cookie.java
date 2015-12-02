/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.Server;

import com.google.gson.*;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.player.User;

/**
 *
 * @author Michael
 */
public class Cookie 
{
    
    private String userInformationString = "";
    private int gameNumber = Integer.MIN_VALUE;
    
    /**
     * @pre setUserCookieString has been called with a valid cookie--and may or may not be in a game
     * @return String that should follow the "Cookie:" header in an http request 
     */
    public String getCompleteCookieString()
    {
        if(userInformationString.isEmpty())
        {
            return "";
        }
        else
        {
            StringBuilder cookie = new StringBuilder("catan.user=");
            cookie.append(this.userInformationString);
            if(this.gameNumber != Integer.MIN_VALUE)
            {
                cookie.append(";");
                cookie.append("catan.game=").append(this.gameNumber);
            }
            return cookie.toString();
        }
    }
    
    public int getGameNumber()
    {
        return this.gameNumber == Integer.MIN_VALUE? -1: this.gameNumber;
    }
    
    /**
     * @pre The user has been logged in and this comes from the response
     * for a successful games/join request
     * @post getCompleteCookieString includes the Catan game number
     * @param gameCookieString The whole value of the "Set-cookie:" header.  
     * Should be in the form "catan.game=NN;Path=/;" where NN is an integer
     */
    public int setGameNumberFromCookie(String gameCookieString) throws MalformedCookieException
    {
        if(gameCookieString.substring(0, 11).equals("catan.game="))
        {
            try
            {
                int end = 11;
                while(Character.isDigit(gameCookieString.charAt(end)))
                    end++;
                this.gameNumber = Integer.parseInt(gameCookieString.substring(11,end));
            } 
            catch(NumberFormatException e)
            {
                throw new MalformedCookieException();
            }
        }
        else
        {
            throw new MalformedCookieException();
        }
        return this.gameNumber;
    }
    
    public void setGameNumber(int gameNumber)
    {
    	StringBuilder gameString = new StringBuilder();
    	gameString.append("catan.game=");
    	gameString.append(gameNumber);
    	gameString.append(";Path=/");
    	try 
    	{
			setGameNumberFromCookie(gameString.toString());
        } 
    	catch (MalformedCookieException e) 
    	{
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }
    
    /**
     * @throws MalformedCookieException The Cookie was not of the proper form
     * @pre The server has just successfully called login/register and this is the cookie response
     * @post getCompleteCookieString includes user information
     * @param userInformationCookieString The whole value of the "Set-cookie:" header.
     * Should be in the form of "catan.user=%7B%22name%22%3A%22Sam%22%2C%22password%22%3A%22sam%22%2C%
22playerID%22%3A0%7D;Path=/;"
     */
    public int setUserCookieString(String userInformationCookieString) throws MalformedCookieException
    {
        userInformationCookieString = userInformationCookieString.trim();
        userInformationCookieString = userInformationCookieString.replaceFirst("catan.user=", "");
        userInformationCookieString = userInformationCookieString.substring(0, userInformationCookieString.length() - 7);
        String userPart = userInformationCookieString.substring(0, userInformationCookieString.length() - 1);
        //this.userInformationString = urlDecoded;
        JsonElement element = this.decodeJSON(userPart);
        if(element.isJsonObject())
        {
            JsonObject jobject = this.getUserJsonObject(element, true);
            return jobject.get("playerID").getAsInt();
        }
        else
        {
            throw new MalformedCookieException();
        }
    }
    
    private JsonElement decodeJSON(String urlDecoded) throws MalformedCookieException
    {
        try
        {
            urlDecoded = java.net.URLDecoder.decode(urlDecoded, "UTF-8");
            return new JsonParser().parse(urlDecoded);
        }
        catch (UnsupportedEncodingException | JsonSyntaxException ex)
        {
            throw new MalformedCookieException();
        }
    }
    
    private JsonObject getUserJsonObject(JsonElement element, boolean setNew) throws MalformedCookieException
    {
        JsonObject jobject = element.getAsJsonObject();
        if(!jobject.has("playerID"))
        {
            throw new MalformedCookieException();
        }
        JsonObject resultingCookie = new JsonObject();
        resultingCookie.add("name", jobject.get("name"));
        resultingCookie.add("password", jobject.get("password"));
        resultingCookie.add("playerID", jobject.get("playerID"));
        if(setNew)
        {
            try
            {
                String realCookie = resultingCookie.toString();
                realCookie = java.net.URLEncoder.encode(realCookie, "UTF-8");
                this.userInformationString = realCookie;
            }
            catch (UnsupportedEncodingException ex)
            {
                Logger.getLogger(Cookie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jobject;
    }
    
    /**
     *
     * @param incomingCookie The cookie value that is coming into the server
     * @return The user information contained within the cookie, null otherwise
     */
    public User getUser()
    {
        if(this.userInformationString == null)
            return null;
        try
        {
            JsonObject userJSONObject = this.getUserJsonObject(this.decodeJSON(this.userInformationString), false);
            User myUser = new User(userJSONObject.get("name").getAsString(), userJSONObject.get("password").getAsString());
            myUser.setPlayerId(userJSONObject.get("playerID").getAsInt());
            return myUser;
        } 
        catch(MalformedCookieException e)
        {
            return null;
        }
    }
    
    public void setUser(User userToBeSet)
    {
        JsonObject resultingCookie = new JsonObject();
        resultingCookie.addProperty("name", userToBeSet.getUsername());
        resultingCookie.addProperty("password", userToBeSet.getPassword());
        resultingCookie.addProperty("playerID", userToBeSet.getPlayerId());
        try
        {
            this.getUserJsonObject(resultingCookie, true);
        }
        catch (MalformedCookieException ex)
        {
            Logger.getLogger(Cookie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void parseCookieString(String cookieString) throws MalformedCookieException
    {
        if(cookieString == null)
            return;
        cookieString = cookieString.trim();
        String[] allCookies = cookieString.split(";");
        for(String currentCookie : allCookies)
        {
            currentCookie = currentCookie.trim();
            if(currentCookie.contains("catan.game=null"))
                continue;
            else if(currentCookie.startsWith("catan.game="))
            {
                currentCookie = currentCookie.concat(";");
                this.setGameNumberFromCookie(currentCookie);
            }
            else if(currentCookie.startsWith("catan.user="))
            {
                StringBuilder sb = new StringBuilder(currentCookie);
                sb.append(";1234567");
                this.setUserCookieString(sb.toString());
            }
            else
                continue;
        }
    }
}
