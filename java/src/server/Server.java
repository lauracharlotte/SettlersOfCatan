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
import server.facade.IUserFacade;
import server.facade.UserFacade;
import server.handlers.GameHandler;
import server.handlers.GamesHandler;
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
    public static void main(String[] args)
    {
        int port = 8081;
        if(args.length>0)
            port = Integer.parseInt(args[0]);
        new Server(port).run();
    }
    
    private void run()
    {
        GameManager myGameManager = new GameManager();
        UserManager myUserManager = new UserManager();
        HttpServer server;
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
        HttpHandler userHandler = new UserHandler(userFacade);
        /*
        Give handlers appropriate Facades and create them
        private HttpHandler gameHandler = new GameHandler();
        private HttpHandler gamesHandler = new GamesHandler();
        private HttpHandler movesHandler = new MovesHandler();
        server.createContext("/games", gamesHandler);
        server.createContext("/game", gameHandler);
        server.createContext("/moves", movesHandler);*/
        server.createContext("/user", userHandler);
        server.start();
        
    }
    
    private final int port;
}
