package server.handlers;

import clientcommunicator.Server.Cookie;
import server.model.GameManager;
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
            return false;
    }
}
