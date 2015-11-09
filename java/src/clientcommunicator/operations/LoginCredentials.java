/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.player.PlayerIdx;
import org.json.JSONException;
import org.json.JSONObject;
import shared.definitions.CatanColor;

/**
 *
 * @author Michael
 */
public class LoginCredentials implements IJSONSerializable
{

    private String username;
    private String password;

    /**
     *
     * @param username The user's username
     * @param password The user's password
     */
    public LoginCredentials(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public LoginCredentials()
    {}

    /**
     *
     * @return The user's username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     *
     * @return The user's password
     */
    public String getPassword()
    {
        return password;
    }
    
    @Override
    public String serialize()
    {
    	String serializing = "{username: \""+username+"\", password: \""+password+"\"}";
        return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonElement obj = new JsonParser().parse(JSON);
        this.username = obj.getAsJsonObject().get("username").getAsString();
        this.password = obj.getAsJsonObject().get("password").getAsString();
    }
}
