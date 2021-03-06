package client.login;

import client.base.*;
import client.misc.*;

import java.util.*;

import model.ClientModelSupplier;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController, Observer 
{

    private IMessageView messageView;
    private IAction loginAction;
    private ILoginState currentState;
    
    /**
     * LoginController constructor
     * 
     * @param view Login view
     * @param messageView Message view (used to display error messages that occur during the login process)
     */
    public LoginController(ILoginView view, IMessageView messageView) 
    {
            super(view);
            this.messageView = messageView;
            currentState = new NotLoggedInState();
            ClientModelSupplier.getInstance().addObserver(this);
    }

    public ILoginView getLoginView() 
    {
            return (ILoginView)super.getView();
    }

    public IMessageView getMessageView() 
    {
            return messageView;
    }

    /**
     * Sets the action to be executed when the user logs in
     * 
     * @param value The action to be executed when the user logs in
     */
    public void setLoginAction(IAction value) 
    {
            loginAction = value;
    }

    /**
     * Returns the action to be executed when the user logs in
     * 
     * @return The action to be executed when the user logs in
     */
    public IAction getLoginAction() 
    {
            return loginAction;
    }

    @Override
    public void start() 
    {
            getLoginView().showModal();
    }

    @Override
    public void signIn() 
    {
        this.currentState.login(getLoginView().getLoginUsername(), getLoginView().getLoginPassword(), this.getMessageView());
    }

    @Override
    public void register() 
    {
        this.currentState.registerUser(getLoginView().getRegisterUsername(), getLoginView().getRegisterPassword(), getLoginView().getRegisterPasswordRepeat(), this.getMessageView());
    }

    @Override
    public void update(Observable o, Object arg)
    {
        this.currentState = this.currentState.modelUpdated((ClientModelSupplier)o);
        this.currentState.render(this.getLoginView(), this.loginAction);
    }

}

