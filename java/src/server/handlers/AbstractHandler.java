/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import clientcommunicator.Server.Cookie;
import clientcommunicator.Server.MalformedCookieException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.command.ICommand;
import server.facade.IModelFacade;

/**
 *
 * @author Michael
 */
public abstract class AbstractHandler implements HttpHandler
{
    protected final IModelFacade currentFacade;
    private final CookieVerifier cookieVerifier;
    
    public AbstractHandler(CookieVerifier cv, IModelFacade facade)
    {
        this.cookieVerifier = cv;
        this.currentFacade = facade;
    }
    
    /**
     * This method grabs the cookie information sent by the client. 
     * Then, checks the cookie against the model for validity, calls reallyHandle if succeeds.
     * @param he The httpExchange initiated by the server
     * @throws IOException Something goes bad in the exchange
     */
    @Override
    public void handle(HttpExchange he) throws IOException
    {
        //grab cookie information
        String cookieParam = (String) he.getAttribute("Cookie");
        //create new cookie object
        Cookie cookie = new Cookie();
        try 
        {
            cookie.parseCookieString(cookieParam);
        } 
        catch (MalformedCookieException ex) 
        {
            this.sendQuickResponse(he, String.format("cookie error: %s", ex.getMessage()), 400);
            return;
        }
        //call the cookie verifer to make sure the data is valid
        //if it's not valid, return an error message to client
        if (!cookieVerifier.isVerified(cookie))
        {
            this.sendQuickResponse(he, String.format("cookie error"), 400);
            return;
        }
        else
        {
            //else call reallyHandle with he and the cookie you created
            reallyHandle(he, cookie);
        }
    }
    
    public void sendQuickResponse(HttpExchange he, String responseBodyText, int responseCode) throws IOException
    {
        he.sendResponseHeaders(responseCode, 0);
        OutputStream response = he.getResponseBody();
        try (PrintWriter pw = new PrintWriter(response))
        {
            pw.print(responseBodyText);
        }
        try
        {
            response.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        he.close();
    }
    
    public ICommand getCommand(HttpExchange he) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        URI currentURI = he.getRequestURI();
        String path = currentURI.getPath();
        String commandPath = path.substring(path.lastIndexOf('/') + 1);
        StringBuilder sb = new StringBuilder(commandPath);
        while(sb.lastIndexOf("_") != -1)
        {
            sb.deleteCharAt(sb.lastIndexOf("_"));
        }
        sb.setCharAt(0, Character.toUpperCase(commandPath.charAt(0)));
        sb.insert(0, "server.command.");
        sb.append("Command");
        Class commandClass = Class.forName(sb.toString());
        return (ICommand)commandClass.newInstance();
    }
    
    public String getRequestBody(HttpExchange he) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(he.getRequestBody()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) 
        {
            output.append(line);
        }    
        // Close the buffer
        br.close();
        return output.toString();
        // Disconnect from the server
    }
    
    /**
     * This method completes the httpExchange initiated by the handle method.
     * @param he The httpExchange initiated by the server
     * @param currentCookie The cookie information parsed by handle.
     */
    public abstract void reallyHandle(HttpExchange he, Cookie currentCookie) throws IOException;
    
}