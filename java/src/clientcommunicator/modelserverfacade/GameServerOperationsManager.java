/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.Server.IServerProxy;
import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import java.util.Collection;

/**
 *
 * @author Michael
 */
public class GameServerOperationsManager implements IServerOperationsManager 
{

    /**
     * 
     * @return a collection of games that are on the server
     */
    public Collection<GameJSONResponse> listGames()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @pre The user should not have entered into a game yet but should be logged in.
     * @param request a createGameRequest that should be performed
     * @return A game object that represents the game that was just created
     */
    public GameJSONResponse createGame(CreateGameRequest request)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * @pre The user should not have previously joined a game
     * @post The user joins the specified game
     * @param request A join game request that should be performed
     */
    public void joinGame(JoinGameRequest request)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *  @pre The user is associated with a game
     *  @post The game the user is in is set to the beginning or just after the setup phase (see the IServerProxy documentation)
     */
    public void resetGame()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * @pre The user is logged in and in a game with an empty seat
     * @post The game the user is in has an AI player added
     * @param AIType A string that represents the AI Type that should be used when adding an AI to the game
     */
    public void addAI(String AIType)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *  
     * @return List of all possible types of AIs that can be added
     */
    public Collection<String> listAI()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param serverToUse The server this manager should start using
     */
    @Override
    public void setServer(IServerProxy serverToUse)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
