/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.*;

import model.ClientModel;
import model.player.Player;

/**
 *
 * @author Michael
 */
public class GameJSONResponse implements IJSONSerializable
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
    
    public GameJSONResponse(ClientModel model, int gameId)
    {
    	ArrayList<Player> allPlayers = new ArrayList<Player>(model.getPlayers());
		ArrayList<PlayerJSONResponse> players = new ArrayList<PlayerJSONResponse>();
		for (int i = 0; i < allPlayers.size(); i++)
		{
			Player p = allPlayers.get(i);
			PlayerJSONResponse player = new PlayerJSONResponse(p.getColor(), p.getName(), p.getPlayerId());
			players.add(player);
		}
		this.title = model.getTitle();
		this.gameId = gameId; 
		this.players = players;
    }

	@Override
	public String serialize() 
	{
		return this.serializeToObject().toString();
	}
        
        public JSONObject serializeToObject()
        {
            JSONObject game = new JSONObject();
            try 
            {
                    game.put("title", title);
                    game.put("id", gameId);
                    JSONArray playersJSON = new JSONArray();
                    Iterator<PlayerJSONResponse> i = players.iterator();
                    while (i.hasNext())
                    {
                            playersJSON.put(i.next().serializeToObject());
                    }
                    game.put("players", playersJSON);
            } 
            catch (JSONException e) 
            {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            return game;
        }

	@Override
	public void deserialize(String JSON) throws JSONException {
		JSONObject game = new JSONObject(JSON);
		title = game.getString("title");
		gameId = game.getInt("gameId");
		players.clear();
		JSONArray playersJSON = new JSONArray(game.getJSONArray("players"));
		for (int i = 0; i < playersJSON.length(); i++)
		{
			PlayerJSONResponse player = new PlayerJSONResponse(null, null, 0);
			player.deserialize(playersJSON.get(i).toString());
			players.add(player);
		}
	}

    
}
