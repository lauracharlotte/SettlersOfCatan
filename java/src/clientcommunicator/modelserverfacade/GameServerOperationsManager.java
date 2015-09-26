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
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Michael
 */
public class GameServerOperationsManager implements IServerOperationsManager 
{

    private IServerProxy currentServer;
    
    /**
     * 
     * @return a collection of games that are on the server
     */
    public Collection<GameJSONResponse> listGames()
    {
        String jsonResponse = this.currentServer.listGames();
        return JSONParser.fromJSONToGameCollection(jsonResponse);
    }

    /**
     * @pre The user should not have entered into a game yet but should be logged in.
     * @param request a createGameRequest that should be performed
     * @return A game object that represents the game that was just created
     */
    public GameJSONResponse createGame(CreateGameRequest request)
    {
        String response = this.currentServer.createGame(JSONParser.toJSON(request));
        return JSONParser.fromJSONToGame(response);
    }
    
    /**
     * @pre The user should not have previously joined a game
     * @post The user joins the specified game
     * @param request A join game request that should be performed
     */
    public void joinGame(JoinGameRequest request)
    {
       this.currentServer.joinGame(JSONParser.toJSON(request));
    }
    
    /**
     *  @pre The user is associated with a game
     *  @post The game the user is in is set to the beginning or just after the setup phase (see the IServerProxy documentation)
     */
    public void resetGame()
    {
        String response = this.currentServer.resetGame();
        //check response here
    }
    
    /**
     * @pre The user is logged in and in a game with an empty seat
     * @post The game the user is in has an AI player added
     */
    public void addAI()
    {
        String addAiRequest = "{\n"
                + "\"AIType\": \"LARGEST_ARMY\""
                + "\n}";
        this.currentServer.addAI(addAiRequest);
    }

    /**
     *  
     * @return List of all possible types of AIs that can be added
     */
    public Collection<String> listAI()
    {
        Collection<String> aiTypes = new ArrayList<>();
        aiTypes.add("LARGEST_ARMY"); //only AIType supported
        return aiTypes;
    }

    /**
     *
     * @param serverToUse The server this manager should start using
     */    
    @Override
    public void setServer(IServerProxy serverToUse) 
    {
        if(serverToUse == null)
            throw new IllegalArgumentException("Cannot set server to null.");
        this.currentServer = serverToUse;
    }
    
    
    
}
