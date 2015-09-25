/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.Server;

/**
 *
 * @author Michael
 */
public class MalformedCookieException extends Exception
{

    public MalformedCookieException()
    {
    }

    public MalformedCookieException(String message)
    {
        super(message);
    }
    
}
