package server.handlers;

import clientcommunicator.Server.Cookie;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handler for games requests
 */
public class GamesHandler extends AbstractHandler
{
    @Override
    public void reallyHandle(HttpExchange he, Cookie currentCookie)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
