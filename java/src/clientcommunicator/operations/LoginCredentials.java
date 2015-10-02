/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
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
}
