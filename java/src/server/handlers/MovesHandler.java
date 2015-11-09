/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import clientcommunicator.Server.Cookie;
import com.sun.net.httpserver.HttpExchange;
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
    public void reallyHandle(HttpExchange he, Cookie currentCookie)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
