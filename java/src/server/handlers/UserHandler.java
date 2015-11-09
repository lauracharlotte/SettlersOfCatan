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
    public void reallyHandle(HttpExchange he, Cookie currentCookie)
    {
        if (!currentCookie.getCompleteCookieString().equals(""))
        {
            try
            {
                he.sendResponseHeaders(400, 0);
            }
            catch (IOException ex)
            {
                Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            OutputStream response = he.getResponseBody();
            PrintWriter pw = new PrintWriter(response);
            pw.print("Invalid cookie state");
            pw.close();
            try
            {
                response.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            he.close();
            return;
        }
        //Get right command class
    }
    
}
