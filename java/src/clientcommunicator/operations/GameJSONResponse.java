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
     * @return the title of the game
     */
    public String getTitle()
    {
        return title;
    }

    /**
     *
     * @return the unique id of the game
     */
    public int getGameId()
    {
        return gameId;
    }

    /**
     *
     * @return the players that are associated with the game
     */
    public Collection<PlayerJSONResponse> getPlayers()
    {
        return players;
    }

    /**
     *
     * @param players the players that are associated with the game
     */
    public void setPlayers(Collection<PlayerJSONResponse> players)
    {
        this.players = players;
    }

    /**
     *
     * @param title the title of the game
     * @param gameId the unique id of the game
     */
    public GameJSONResponse(String title, int gameId)
    {
        this.title = title;
        this.gameId = gameId;
    }

    /**
     *
     * @param title the title of the game
     * @param gameId the unique id of the game
     * @param players the players that are associated with the game
     */
    public GameJSONResponse(String title, int gameId, Collection<PlayerJSONResponse> players)
    {
        this(title, gameId);
        this.players = players;
    }

    
}
