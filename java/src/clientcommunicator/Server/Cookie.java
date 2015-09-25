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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Michael
 */
public class Cookie 
{
    
    private String userInformationString;
    private int gameNumber;
    
    /**
     * @pre setUserCookieString has been called with a valid cookie--and may or may not be in a game
     * @return String that should follow the "Cookie:" header in an http request 
     */
    public String getCompleteCookieString()
    {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * @pre The user has been logged in and this comes from the response
     * for a successful games/join request
     * @post getCompleteCookieString includes the Catan game number
     * @param gameCookieString The whole value of the "Set-cookie:" header.  
     * Should be in the form "catan.game=NN;Path=/;" where NN is an integer
     */
    public void setGameNumberFromCookie(String gameCookieString) throws MalformedCookieException
    {
        
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
        userInformationCookieString = userInformationCookieString.replaceFirst("Path=/;", "");
        String urlDecoded = userInformationCookieString.substring(0, userInformationCookieString.length() - 1);
        try
        {
            urlDecoded = java.net.URLDecoder.decode(urlDecoded, "UTF-8");
        }
        catch (UnsupportedEncodingException ex)
        {
            throw new MalformedCookieException();
        }
        JsonElement element = new JsonParser().parse(urlDecoded);
        if(element.isJsonObject())
        {
            JsonObject jobject = element.getAsJsonObject();
            if(jobject.has("playerID"))
            {
                return jobject.get("playerID").getAsInt();
            }
            else
            {
                throw new MalformedCookieException();
            }
        }
        else
        {
            throw new MalformedCookieException();
        }
    }
    
    public static void main(String[] args)
    {
        try
        {
            int playerIdx = new Cookie().setUserCookieString("catan.user=%7B%22name%22%3A%22Sam%22%2C%22password%22%3A%22sam%22%2C%22playerID%22%3A0%7D;Path=/;");
        } 
        catch (MalformedCookieException e)
        {
            
        }
    }
}
