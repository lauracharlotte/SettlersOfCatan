/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import model.ClientModel;

/**
 *
 * @author Michael
 */
public class GameManager
{
    private Collection<ClientModel> gameList = Collections.synchronizedList(new ArrayList<ClientModel>());

    public ClientModel getGameWithNumber(int gameId)
    {
        if(gameId >= gameList.size() || gameId<0)
            throw new IllegalArgumentException();
        return (ClientModel)(gameList.toArray()[gameId]);
    }
    
    public ClientModel getGameWithName(String name)
    {
        throw new UnsupportedOperationException();
    }
    
    public Collection<ClientModel> getAllGames()
    {
    	return gameList;
    }
    
    public void addNewGame(ClientModel game)
    {
    	gameList.add(game);
    }
    
    public void replaceGame(int index, ClientModel game)
    {
    	ArrayList<ClientModel> games = (ArrayList<ClientModel>)gameList;
    	games.remove(index);
    	games.add(index, game);
    	gameList = Collections.synchronizedList(games);
    }
}
