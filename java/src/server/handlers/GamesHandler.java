package server.handlers;

import clientcommunicator.Server.Cookie;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerException;
import server.command.ICommand;
import server.facade.IModelFacade;

/**
 * Handler for games requests
 */
public class GamesHandler extends AbstractHandler
{
    public GamesHandler(CookieVerifier cv, IModelFacade facade)
    {
        super(cv, facade);
    }
    
    @Override
    public void reallyHandle(HttpExchange he, Cookie currentCookie) throws IOException
    {
        if("".equals(currentCookie.getCompleteCookieString()))
        {
            this.sendQuickResponse(he, "Cookie error", 400);
            return;
        }
        ICommand command;
        try
        {
            command = this.getCommand(he);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
            Logger.getLogger(GamesHandler.class.getName()).log(Level.SEVERE, null, ex);
            this.sendQuickResponse(he, "Error", 400);
            return;
        }
        String response;
        String oldCookieString = currentCookie.getCompleteCookieString();
        try
        {
            response = command.execute(currentFacade, this.getRequestBody(he), currentCookie);
        }
        catch (ServerException ex)
        {
            Logger.getLogger(GamesHandler.class.getName()).log(Level.SEVERE, null, ex);
            this.sendQuickResponse(he, "Error", 400);
            return;
        }
        if(!currentCookie.getCompleteCookieString().equals(oldCookieString))
        {
            String cookieString = currentCookie.getCompleteCookieString()+";Path=/;";
            he.getResponseHeaders().add("Set-cookie", cookieString);
        }
        this.sendQuickResponse(he, response, 200);
    }

}
