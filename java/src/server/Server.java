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

/**
 *
 * @author Michael
 */
public class Server extends HttpServer
{
    
    @Override
    public void bind(InetSocketAddress arg0, int arg1) throws IOException {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public HttpContext createContext(String arg0) {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    public HttpContext createContext(String arg0, HttpHandler arg1) {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    public InetSocketAddress getAddress() {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    public Executor getExecutor() {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    public void removeContext(String arg0) throws IllegalArgumentException {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public void removeContext(HttpContext arg0) {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public void setExecutor(Executor arg0) {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public void start() {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public void stop(int arg0) {
    	// TODO Auto-generated method stub
    	
    }

    /**
     * Starts a Java Server that can handle httpRequests dealing with Settlers of Catan.
     * @param args args[0] must be the port number the server should be ran on
     */
    public static void main(String[] args)
    {
        
    }
}
