package server.handlers;

import clientcommunicator.Server.Cookie;
import model.ClientModel;
import model.player.User;
import server.model.GameManager;
import model.player.Player;
import server.model.UserManager;

/**
 * Checks validity of cookies coming from the client
 * @author Madison Brooks
 *
 */
public class CookieVerifier 
{
    private UserManager uManager;
    private GameManager gManager;
    
    public CookieVerifier(UserManager userManager, GameManager gameManager)
    {
        this.uManager = userManager;
        this.gManager = gameManager;
    }
    
    /**
     * Verifies a cookie
     * @param cookie the cookie to be checked
     * @return true if valid, false otherwise
     */
    public boolean isVerified(Cookie cookie)
    {
        if(cookie.getCompleteCookieString().equals(""))
            return true;
        User cookiedUser = cookie.getUser();
        if(!uManager.verifyUser(cookiedUser))
            return false;
        int gameNumber = cookie.getGameNumber();
        if (gameNumber == -1)
            return true;
        ClientModel currentModel;
        try
        {
            currentModel = gManager.getGameWithNumber(gameNumber);
        }
        catch(IllegalArgumentException e)
        {
            return false;
        }
        for(Player p: currentModel.getPlayers())
        {
            if(p.getPlayerId() == cookiedUser.getPlayerId() && p.getName().equals(cookiedUser.getUsername()))
            {
                return true;
            }
        }
        return false;
    }
}
