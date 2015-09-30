/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.modelserverfacade;

import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.IJSONSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.google.gson.Gson;

import model.ClientModel;

/**
 *
 * @author Michael
 */
public class JSONParser 
{

    /**
     *
     * @param serializable An object that needs to be serialized
     * @return The given object as a JSON string
     */
    public static String toJSON(IJSONSerializable serializable)
    {
        return serializable.serialize();
    }
    
    /**
     * @pre modelJSon is valid JSON for ClientModel
     * @param modelJSON A string that contains a ClientModel JSON object
     * @return The clientModel that the model JSON represented
     */
    public static ClientModel fromJSONToModel(String modelJSON)
    {
    	Gson gson = new Gson();
    	ClientModel newModel = gson.fromJson(modelJSON, ClientModel.class);
    	return newModel;
    	
        //throw new UnsupportedOperationException("Not Supported Yet");
    }
    
    /**
     * @pre gameListJSON is valid JSON for a game list.
     * @param gameListJSON Represents a list of games -- as in response to listGames API.
     * @return Collection of GameJSONResponses which holds all games that were specified in the JSON
     */
    public static Collection<GameJSONResponse> fromJSONToGameCollection(String gameListJSON)
    {
    	Gson gson = new Gson();
    	GameJSONResponse[] games = gson.fromJson(gameListJSON, GameJSONResponse[].class);
    	Collection<GameJSONResponse> gameCollection = new ArrayList<GameJSONResponse>(Arrays.asList(games));
    	return gameCollection;
    	
        //throw new UnsupportedOperationException("Not Supported Yet");
    }
    
    /**
     * @pre gameJSON is valid JSON for a game.
     * @param gameJSON Represents valid JSON for a game -- as in listGames.
     * @return The information extracted from the JSON string
     */
    public static GameJSONResponse fromJSONToGame(String gameJSON)
    {
    	Gson gson = new Gson();
    	GameJSONResponse game = gson.fromJson(gameJSON, GameJSONResponse.class);
    	return game;
    	
        //throw new UnsupportedOperationException("Not Supported Yet");
    }
}
