/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModel;
import org.json.JSONException;
import server.IGameAccess;
import server.IPersistenceFactory;
import server.ServerException;
import server.command.ICommand;
import server.facade.IMovesFacade;
import server.facade.MovesFacade;

/**
 *
 * @author Michael
 */
public class GameManager
{
    private Collection<ClientModel> gameList = Collections.synchronizedList(new ArrayList<ClientModel>());

    private IPersistenceFactory persistence;
    
    private int interval;
    
    public GameManager()
    {
        
    }
    
    public GameManager(IPersistenceFactory persistence, int interval)
    {
        this.persistence = persistence;
        this.interval = interval;
        
        // Get the list of games from the persistence
        this.persistence.beginTransaction();
        IGameAccess gameAccessObject = this.persistence.getGameAccessObject();
        gameList = Collections.synchronizedList(new ArrayList<ClientModel>(gameAccessObject.getGames()));
        Collection<Collection<ICommand>> allCommands = new ArrayList<>();
        for (int i = 0; i < gameList.size(); i++)
        {
            Collection<ICommand> commands = gameAccessObject.getAllCommands(i);
            allCommands.add(commands);
        }
        this.persistence.endTransaction();
        // Execute all of the commands
        int i = 0;
        for (Collection<ICommand> commands : allCommands)
        {
            this.persistence.beginTransaction();
            gameAccessObject.deleteGameCommands(i);
            this.persistence.endTransaction();
            executeCommands(commands, i);
            i++;
        }
    }
    
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
    	ArrayList<ClientModel> games = new ArrayList<>(gameList);
    	games.remove(index);
    	games.add(index, game);
    	gameList = Collections.synchronizedList(games);
    }
    
    public void saveCommand(ICommand command, int gameId)
    {
        this.persistence.beginTransaction();
        IGameAccess gameAccessObject = this.persistence.getGameAccessObject();
        gameAccessObject.saveCommand(command, gameId);
        this.persistence.endTransaction();
        this.persistence.beginTransaction();
        if (gameAccessObject.getCommandAmount(gameId) == interval)
        {
            gameAccessObject.saveGame(this.getGameWithNumber(gameId), gameId);
            this.persistence.endTransaction();
            this.persistence.beginTransaction();
            gameAccessObject.deleteGameCommands(gameId);
            this.persistence.endTransaction();
        }
        else
            this.persistence.endTransaction();
    }
    
    public void saveGame(int gameId)
    {
        this.persistence.beginTransaction();
        IGameAccess gameAccessObject = this.persistence.getGameAccessObject();
        gameAccessObject.saveGame(this.getGameWithNumber(gameId), gameId);
        gameAccessObject.deleteGameCommands(gameId);
        this.persistence.endTransaction();
    }
    
    private void executeCommands(Collection<ICommand> commands, int gameId)
    {
        IMovesFacade movesFacade = new MovesFacade(this);
        for (ICommand command : commands)
        {
            try {
                command.execute(movesFacade, command.getRequestBody(), command.getCurrentCookie());
            } catch (ServerException | JSONException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
