package client.communication;

import client.base.*;
import java.util.Observable;
import java.util.Observer;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer
{

    public ChatController(IChatView view) {

            super(view);
    }

    @Override
    public IChatView getView() {
            return (IChatView)super.getView();
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void update(Observable o, Object arg)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

