/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.login;

import client.base.IAction;
import client.misc.IMessageView;
import model.ClientModelSupplier;

/**
 *
 * @author Michael
 */
public class LoggedInState implements ILoginState
{

    @Override
    public ILoginState modelUpdated(ClientModelSupplier supplier)
    {
        return this;
    }

    @Override
    public void login(String username, String password, IMessageView messageView)
    {
        return;
    }

    @Override
    public void registerUser(String username, String password, String passwordRepeated, IMessageView messageView)
    {
        return;
    }

    @Override
    public void render(ILoginView view, IAction loginAction)
    {
        if(view.isModalShowing())
        {
            view.closeModal();
            loginAction.execute();
        }
    }
    
}
