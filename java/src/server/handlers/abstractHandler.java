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

/**
 *
 * @author Michael
 */
public abstract class AbstractHandler implements HttpHandler
{
    
    /**
     * This method grabs the cookie information sent by the client. 
     * Then, checks the cookie against the model for validity, calls reallyHandle if succeeds.
     * @param he The httpExchange initiated by the server
     * @throws IOException Something goes bad in the exchange
     */
    @Override
    public void handle(HttpExchange he) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method completes the httpExchange initiated by the handle method.
     * @param he The httpExchange initiated by the server
     * @param currentCookie The cookie information parsed by handle.
     */
    public abstract void reallyHandle(HttpExchange he, Cookie currentCookie);
    
}
