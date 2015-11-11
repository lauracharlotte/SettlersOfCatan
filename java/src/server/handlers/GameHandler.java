package server.handlers;

import clientcommunicator.Server.Cookie;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import server.facade.IModelFacade;

/**
 * 
 * @author LaurasAdventurePC
 *
 * Handler for Game.
 */
public class GameHandler extends AbstractHandler
{
    public GameHandler(CookieVerifier cv, IModelFacade facade)
    {
        super(cv, facade);
    }
    
    @Override
    public void reallyHandle(HttpExchange he, Cookie currentCookie) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
