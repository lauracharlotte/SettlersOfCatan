package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handler for games requests
 */
public class GamesHandler implements HttpHandler
{
	private CookieVerifier verifier;
	
	/**
    *
    * @param he The current httpExchange -- should only correspond to a join, create, or list request
    * @throws IOException When the connection is bad in some way
    */
   @Override
   public void handle(HttpExchange he) throws IOException
   {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }
}
