/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 * Thrown when an illegal move is requested by the client
 * @author Michael
 */
@SuppressWarnings("serial")
public class ServerException extends Exception
{
    public ServerException(String msg)
    {
        super(msg);
    }
}
