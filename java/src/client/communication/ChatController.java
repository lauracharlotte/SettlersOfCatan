package client.communication;

import client.base.*;
import java.util.Observable;
import java.util.Observer;

import model.ClientModelSupplier;
import model.messages.MessageLine;
import model.messages.MessageList;


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
        MessageList chat = ClientModelSupplier.getInstance().getModel().getChat();
        chat.getLines().add(new MessageLine(message, "TODO: SOURCE"));
        // Send the chat to the server
    }

    @Override
    public void update(Observable o, Object arg)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

