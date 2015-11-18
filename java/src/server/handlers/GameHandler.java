package server.handlers;

import clientcommunicator.Server.Cookie;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

import server.ServerException;
import server.command.ICommand;
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
        ICommand currentCommand;
        if(currentCookie.getGameNumber()<0 || currentCookie.getUser() == null)
            this.sendQuickResponse(he, "Invalid cookie", 400);
        try
        {
            currentCommand = this.getCommand(he);
            
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
            Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
            this.sendQuickResponse(he, "Invalid request", 400);
            return;
        }
        String response;
        try
        {
            response = currentCommand.execute(this.currentFacade, this.getRequestBody(he), currentCookie);
        }
        catch (JSONException | ServerException ex)
        {
            Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
            this.sendQuickResponse(he, "Invalid operation", 400);
            return;
        }
        this.sendQuickResponse(he, response, 200);
    }
}
