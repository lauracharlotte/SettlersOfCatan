/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.player;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Michael
 */
@SuppressWarnings("serial")
public class User implements Serializable
{

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.playerId = -1;
    }

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

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + this.playerId;
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final User other = (User) obj;
        if (this.playerId != other.playerId)
        {
            return false;
        }
        if (!Objects.equals(this.username, other.username))
        {
            return false;
        }
        if (!Objects.equals(this.password, other.password))
        {
            return false;
        }
        return true;
    }
    
    
}
