/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;
import clientcommunicator.Server.Cookie;
import server.ServerException;
import server.facade.IModelFacade;

/**
 * Executes the Login request.
 * @author Michael
 */
public class LoginCommand implements ICommand
{

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
