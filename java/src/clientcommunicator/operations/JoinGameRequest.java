/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.definitions.CatanColor;

/**
 *
 * @author Michael
 */
public class JoinGameRequest implements IJSONSerializable
{

    private int gameId;
    private CatanColor playerColor;

    /**
     *
     * @param playerIndex The player index the user would like to join at
     * @param playerColor The color the player would like to use
     */
    public JoinGameRequest(int gameId, CatanColor playerColor)
    {
        this.gameId = gameId;
        this.playerColor = playerColor;
    }

    /**
     *
     * @return The player index the user would like to join at
     */
    public int getPlayerIndex()
    {
        return gameId;
    }

    /**
     *
     * @return The color the player would like to use
     */
    public CatanColor getPlayerColor()
    {
        return playerColor;
    }
    
    @Override
    public String serialize()//NEED TO CHANGE PERAMETERS, ASK IF IT AFFECTS ANYTHING ALSO ???
    {
    	String stringColor = playerColor.toString();
    	String lowerCaseColor = stringColor.toLowerCase();
    	String serializing = "{id: "+ gameId + ", color: \""+ lowerCaseColor+"\"}";
    	return serializing;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(final String[] args)
    {
    	int theGameId = 5;
    	CatanColor theColor = CatanColor.BLUE;
    	JoinGameRequest joinGameReq = new JoinGameRequest(theGameId, theColor);
    	String work = joinGameReq.serialize();
    	System.out.println(work);
    }
    
}
