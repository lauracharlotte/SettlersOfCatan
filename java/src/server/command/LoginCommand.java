/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;
import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.LoginCredentials;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IModelFacade;
import server.facade.IUserFacade;

/**
 * Executes the Login request.
 * @author Michael
 */
public class LoginCommand implements ICommand
{

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
    {
        IUserFacade myFacade = (IUserFacade)facade;
        LoginCredentials creds = new LoginCredentials();
        try
        {
            creds.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            Logger.getLogger(LoginCommand.class.getName()).log(Level.SEVERE, null, ex);
            return "Invalid JSON";
        }
        User newUser = new User(creds.getUsername(), creds.getPassword());     
        int playerId = myFacade.login(newUser);
        if(playerId == -1)
            return "Invalid credentials";
        else
        {
            newUser.setPlayerId(playerId);
            currentCookie.setUser(newUser);
            return "Success";
        }
    }
    
}
