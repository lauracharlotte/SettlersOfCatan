/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.player;

/**
 *
 * @author Michael
 */
public class User
{

    /**
     *
     * @return the unique id associated with the user
     */
    public int getPlayerId()
    {
        return playerId;
    }

    /**
     *
     * @param playerId the unique id that should be associated with this user
     */
    public void setPlayerId(int playerId)
    {
        this.playerId = playerId;
    }

    /**
     *
     * @return the username of the player
     */
    public String getUsername()
    {
        return username;
    }

    /**
     *
     * @param username The username that should be associated with this user
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     *
     * @return the password that is associated with this user
     */
    public String getPassword()
    {
        return password;
    }

    /**
     *
     * @param password the password that should be associated with this player
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    private int playerId;
    private String username;
    private String password;
}
