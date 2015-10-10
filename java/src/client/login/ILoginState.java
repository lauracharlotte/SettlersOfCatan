package client.login;

import client.base.IAction;
import client.misc.IMessageView;
import model.ClientModelSupplier;

public interface ILoginState 
{
    public ILoginState modelUpdated(ClientModelSupplier supplier);
    
    public void login(String username, String password, IMessageView messageView);
    
    public void registerUser(String username, String password, String passwordRepeated, IMessageView messageView);
    
    public void render(ILoginView view, IAction loginAction);
}
