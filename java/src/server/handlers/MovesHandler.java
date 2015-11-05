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
 * @author Scott
 */
public class MovesHandler implements HttpHandler {
    
    /**
     * 
     * @param he HTTP Exchange, corresponds to each of the moves methods
     * @throws IOException 
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
