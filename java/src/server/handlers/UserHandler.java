/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import clientcommunicator.Server.Cookie;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerException;
import server.command.ICommand;
import server.facade.IModelFacade;
import server.facade.IUserFacade;

/**
 * Handles login and user
 * @author Michael
 */
public class UserHandler extends AbstractHandler
{   
    public UserHandler(CookieVerifier cv, IModelFacade facade)
    {
        super(cv, facade);
    }
    
    @Override
    public void reallyHandle(HttpExchange he, Cookie currentCookie) throws IOException
    {
        if (!currentCookie.getCompleteCookieString().equals(""))
        {
            this.sendQuickResponse(he, "Cannot be already in logged in.", 400);
            return;
        }
        ICommand command;
        String result = "";
        try
        {
            command = this.getCommand(he);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        try
        {
            result = command.execute(currentFacade, this.getRequestBody(he), currentCookie);
        }
        catch (ServerException ex)
        {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(result);
    }
    
}
