package client.communication;

import client.base.*;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TurnServerOperationsManager;
import clientcommunicator.operations.SendChatRequest;
import model.ClientModel;
import model.ClientModelSupplier;
import model.messages.MessageLine;
import model.player.Player;
import model.player.PlayerIdx;
import shared.definitions.CatanColor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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

    public ChatController(IChatView view) 
    {
            super(view);
            ClientModelSupplier.getInstance().addObserver(this);
    }

    @Override
    public IChatView getView() {
            return (IChatView)super.getView();
    }

    @Override
    public void sendMessage(String message) {
    	CatanColor currentColor = ClientModelSupplier.getInstance().getClientPlayerObject().getColor();
    	LogEntry newEntry = new LogEntry(currentColor, message);
    	List<LogEntry> myLogList = new ArrayList<LogEntry>();
    	myLogList.add(newEntry);
    	
    	MessageLine newLine = new MessageLine(message, currentColor.name());
    	
    	ClientModelSupplier.getInstance().getModel().getChat().getLines().add(newLine);
    	
    	//Telling the server to update... maybe have copy and pasted and changed from a different function...
    	
    	SendChatRequest request = new SendChatRequest(ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex(), message);
    
    	try {
			ModelServerFacadeFactory.getInstance().sendChat(request);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	update(ClientModelSupplier.getInstance(),ClientModelSupplier.getInstance().getModel());
    }

    @Override
    public void update(Observable o, Object arg)
    {
    	if(arg != null)
    	{
	    	ClientModel curModel = (ClientModel) arg;
	    	List<LogEntry> myLogList = new ArrayList<LogEntry>();
	    	for(MessageLine curMessage : curModel.getChat().getLines())
	    	{
	    		String theMessage = curMessage.getMessage();
	    		String theSource = curMessage.getSource();

	    		CatanColor theColor = null;//CatanColor.valueOf(theSource);
	    		for(Player playa: curModel.getPlayers())
	    		{
	    			if(playa.getName().equals(theSource))
	    			{
	    				theColor = playa.getColor();
	    			}
	    		}
	        	LogEntry newEntry = new LogEntry(theColor, theMessage);      	
	        	myLogList.add(newEntry);
	    	}
	    	getView().setEntries(myLogList);
	    	ClientModelSupplier.getInstance().getModel().setChat(curModel.getChat());
    	}
    }
}

