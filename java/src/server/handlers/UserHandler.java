/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

/**
 *
 * @author Michael
 */
public class UserHandler implements HttpHandler
{

    /**
     *
     * @param he The current httpExchange -- should only correspond to a login or register request
     * @throws IOException When the connection is bad in some way
     */
    @Override
    public void handle(HttpExchange he) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
