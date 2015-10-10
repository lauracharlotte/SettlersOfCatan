/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.login;

import client.base.IAction;
import client.misc.IMessageView;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.UserServerOperationsManager;
import clientcommunicator.operations.LoginCredentials;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModelSupplier;

/**
 *
 * @author Michael
 */
public class NotLoggedInState implements ILoginState
{

    UserServerOperationsManager manager;
    
    protected NotLoggedInState()
    {
        try
        {
            manager = (UserServerOperationsManager)ModelServerFacadeFactory.getInstance().getOperationsManager(UserServerOperationsManager.class);
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
        {
            Logger.getLogger(NotLoggedInState.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
    public ILoginState modelUpdated(ClientModelSupplier supplier)
    {
        if(supplier.getClientPlayerID() != -1)
            return new LoggedInState();
        else
            return this;
    }

    public void showMessage(IMessageView messageView, String title, String message)
    {
        messageView.setTitle(title);
        messageView.setMessage(message);
        messageView.showModal();
    }
    
    @Override
    public void login(String username, String password, IMessageView messageView)
    {
        if(!this.isValidPassword(password) || !this.isValidUsername(username))
        {
            this.showMessage(messageView, "Invalid input", "Bad username or password combination");
            return;
        }
        this.tryLogin(username, password, true, messageView);
    }
    
    private boolean isValidPassword(String password)
    {
        if(password.trim().isEmpty())
            return false;
        final int MIN_PASS_LENGTH = 5;

        if (password.length() < MIN_PASS_LENGTH)
        {
            return false;
        }
        else
        {
            for (char c : password.toCharArray())
            {
                if (!Character.isLetterOrDigit(c)
                        && c != '_' && c != '-')
                {
                    return false;
                }
            }
        }

        return true;
    }
    
    private boolean isValidUsername(String username)
    {
        if(username.trim().isEmpty())
            return false;
        final int MIN_UNAME_LENGTH = 3;
        final int MAX_UNAME_LENGTH = 7;

        if (username.length() < MIN_UNAME_LENGTH
                || username.length() > MAX_UNAME_LENGTH)
        {
            return false;
        }
        else
        {
            for (char c : username.toCharArray())
            {
                if (!Character.isLetterOrDigit(c)
                        && c != '_' && c != '-')
                {
                    return false;
                }
            }
        }

        return true;
    }
    
    @Override
    public void registerUser(String username, String password, String passwordRepeated, IMessageView messageView)
    {
        if(!this.isValidPassword(password) || !this.isValidUsername(username) || !password.equals(passwordRepeated))
        {
            this.showMessage(messageView, "Invalid input", "Bad username or password combination");
            return;
        }
        this.tryLogin(username, password, false, messageView);
    }
    
    private void tryLogin(String username, String password, boolean isRegistered, IMessageView messageView)
    {      
        LoginCredentials credentials = new LoginCredentials(username, password);
        int playerId = -1;
        try
        {
            if(isRegistered)
                playerId = manager.loginUser(credentials);
            else
                playerId = manager.registerUser(credentials);
        }
        catch(ClientException exc)
        {
            this.showMessage(messageView, "Client Exception", exc.getMessage());
            return;
        }
        if(playerId != -1)
            ClientModelSupplier.getInstance().setClientPlayerID(playerId);
    }

    @Override
    public void render(ILoginView view, IAction loginAction)
    {
        if(!view.isModalShowing())
            view.showModal();
    }
    
}
