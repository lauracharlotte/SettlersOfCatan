/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Scott
 */
public class MovesHandler extends AbstractHandler 
{
    public MovesHandler(CookieVerifier cv, IModelFacade facade)
    {
        super(cv, facade);
    }
    
    @Override
    public void reallyHandle(HttpExchange he, Cookie currentCookie) throws IOException
    {
        if (currentCookie.getGameNumber() < 0 || currentCookie.getUser() == null)
        {
            this.sendQuickResponse(he, "Missing necessary cookie.", 400);
            return;
        }
        ICommand command;
        try
        {
            command = this.getCommand(he);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
            Logger.getLogger(MovesHandler.class.getName()).log(Level.SEVERE, null, ex);
            this.sendQuickResponse(he, "Something died", 400);
            return;
        }
        String result;
        try
        {
            result = command.execute(currentFacade, this.getRequestBody(he), currentCookie);
        }
        catch (JSONException | ServerException ex)
        {
            Logger.getLogger(MovesHandler.class.getName()).log(Level.SEVERE, null, ex);
            this.sendQuickResponse(he, "Something died", 400);
            return;
        }
        this.sendQuickResponse(he, result, 200);
    }
    
}
