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

    /**
     *
     * @return
     */
    public String getTitle()
    {
        return title;
    }

    /**
     *
     * @return
     */
    public int getGameId()
    {
        return gameId;
    }

    /**
     *
     * @return
     */
    public Collection<PlayerJSONResponse> getPlayers()
    {
        return players;
    }

    /**
     *
     * @param players
     */
    public void setPlayers(Collection<PlayerJSONResponse> players)
    {
        this.players = players;
    }

    /**
     *
     * @param title
     * @param gameId
     */
    public GameJSONResponse(String title, int gameId)
    {
        this.title = title;
        this.gameId = gameId;
    }

    /**
     *
     * @param title
     * @param gameId
     * @param players
     */
    public GameJSONResponse(String title, int gameId, Collection<PlayerJSONResponse> players)
    {
        this(title, gameId);
        this.players = players;
    }

    
}
