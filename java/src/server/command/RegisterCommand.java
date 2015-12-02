/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.operations.LoginCredentials;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IUserFacade;
/**
 * Executes the Register request.
 * @author Michael
 */
public class RegisterCommand implements ICommand
{
    private String requestBody;
    private Cookie currentCookie;
    
    @Override
    public String getRequestBody() {
        return this.requestBody;
    }

    @Override
    public Cookie getCurrentCookie() {
        return this.currentCookie;
    }
 
    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException
    {
        this.requestBody = requestBody;
        this.currentCookie = currentCookie;
        IUserFacade myUserFacade = (IUserFacade)facade;
        LoginCredentials creds = new LoginCredentials();
        if(!currentCookie.getCompleteCookieString().equals(""))
            return "Invalid request";
        try
        {
            creds.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        User newUser = new User(creds.getUsername(), creds.getPassword());
        int playerId = myUserFacade.register(newUser);
        if(playerId == -1)
        {
            return "Invalid request";
        }
        else
        {
            newUser.setPlayerId(playerId);
            currentCookie.setUser(newUser);
            return "Success";
        }
    }
    
}
