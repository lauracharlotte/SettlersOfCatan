/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.facade.GameFacade;
import server.facade.GamesFacade;
import server.facade.IGameFacade;
import server.facade.IGamesFacade;
import server.facade.IMovesFacade;
import server.facade.IUserFacade;
import server.facade.MovesFacade;
import server.facade.UserFacade;
import server.handlers.CookieVerifier;
import server.handlers.GameHandler;
import server.handlers.GamesHandler;
import server.handlers.Handlers;
import server.handlers.MovesHandler;
import server.handlers.UserHandler;
import server.model.GameManager;
import server.model.UserManager;

/**
 * Java Server that can handle httpRequests dealing with Settlers of Catan
 * @author Michael
 */
public class Server
{
    public Server(int portNumber)
    {
        this.port = portNumber;
    }
    
    /**
     * Starts a Java Server that can handle httpRequests dealing with Settlers of Catan.
     * @param args args[0] must be the port number the server should be ran on
     */
    public static void main(String[] args) throws Exception
    {
        if(args.length < 2)
        {
            System.err.println("Usage: our-server <persistence-provider> <numberofdiffs>");
            return;
        }
        String persistenceProvider = args[0];
        int numberOfDiffs = Integer.parseInt(args[1]);
        int portNumber = 8081;
        new Server(portNumber).run(persistenceProvider, numberOfDiffs);
    }
    
    public void stop()
    {
        server.stop(0);
    }
    
    private HttpServer server;
    
    public void run(GameManager myGameManager, UserManager myUserManager)
    {
        if(myGameManager==null)
            myGameManager = new GameManager();
        if(myUserManager == null)
            myUserManager = new UserManager();
        CookieVerifier cookieVerifier = new CookieVerifier(myUserManager, myGameManager);
        try
        {
            server = HttpServer.create(new InetSocketAddress(port), 10);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        server.setExecutor(null);
        IUserFacade userFacade = new UserFacade(myUserManager);
        HttpHandler userHandler = new UserHandler(cookieVerifier, userFacade);
        
        IGameFacade gameFacade = new GameFacade(myUserManager, myGameManager);
        HttpHandler gameHandler = new GameHandler(cookieVerifier, gameFacade);
        
        IGamesFacade gamesFacade = new GamesFacade(myGameManager);
        HttpHandler gamesHandler = new GamesHandler(cookieVerifier, gamesFacade);
        
        IMovesFacade movesFacade = new MovesFacade(myGameManager);
        HttpHandler movesHandler = new MovesHandler(cookieVerifier, movesFacade);
        
        server.createContext("/games", gamesHandler);
        server.createContext("/game", gameHandler);
        server.createContext("/moves", movesHandler);
        server.createContext("/user", userHandler);
        server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
        server.createContext("/docs/api/view", new Handlers.BasicFile(""));
        server.start();
    }
    
    private void run(String persistenceProvider, int numberOfDiffs) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class persistenceClass = Class.forName("server.persistence."+persistenceProvider);
        IPersistenceFactory factory = (IPersistenceFactory)persistenceClass.newInstance();
        GameManager myGameManager = new GameManager(factory, numberOfDiffs);
        UserManager myUserManager = new UserManager(factory);
        run(myGameManager, myUserManager);
        
    }
    
    private final int port;
}
