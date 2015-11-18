package server.handlers;

import clientcommunicator.Server.Cookie;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

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
        ICommand command;
        try
        {
            command = this.getCommand(he);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
            Logger.getLogger(GamesHandler.class.getName()).log(Level.SEVERE, null, ex);
            this.sendQuickResponse(he, ex.getMessage(), 400);
            return;
        }
        String response;
        String oldCookieString = currentCookie.getCompleteCookieString();
        try
        {
            response = command.execute(currentFacade, this.getRequestBody(he), currentCookie);
        }
        catch (JSONException | ServerException ex)
        {
            Logger.getLogger(GamesHandler.class.getName()).log(Level.SEVERE, null, ex);
            this.sendQuickResponse(he, ex.getMessage(), 400);
            return;
        }
        if(currentCookie.getGameNumber() >= 0)
        {
            String cookieString = "catan.game="+currentCookie.getGameNumber()+";Path=/;";
            he.getResponseHeaders().add("Set-cookie", cookieString);
        }
        this.sendQuickResponse(he, response, 200);
    }

}
