/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import java.util.Collection;

/**
 *
 * @author Michael
 */
public class GameJSONResponse
{
    private String title;
    private int gameId;
    private Collection<PlayerJSONResponse> players;

    public String getTitle()
    {
        return title;
    }

    public int getGameId()
    {
        return gameId;
    }

    public Collection<PlayerJSONResponse> getPlayers()
    {
        return players;
    }

    public void setPlayers(Collection<PlayerJSONResponse> players)
    {
        this.players = players;
    }

    public GameJSONResponse(String title, int gameId)
    {
        this.title = title;
        this.gameId = gameId;
    }

    public GameJSONResponse(String title, int gameId, Collection<PlayerJSONResponse> players)
    {
        this(title, gameId);
        this.players = players;
    }

    
}
