package client.communication;

import java.util.*;

import client.base.*;
import model.ClientModel;
import model.ClientModelSupplier;
import model.messages.MessageLine;
import model.player.Player;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer
{

    public GameHistoryController(IGameHistoryView view) {

            super(view);
            initFromModel();
            ClientModelSupplier.getInstance().addObserver(this);
    }

    @Override
    public IGameHistoryView getView() 
    {

            return (IGameHistoryView)super.getView();
    }

    private void initFromModel() 
    {
    	//Is there suppose to be anything here??
    }

    @Override
    public void update(Observable o, Object arg)
    {
    	if(arg!=null)
    	{
    		ClientModel curModel = (ClientModel) arg;
    		List<LogEntry> myLogList = new ArrayList<LogEntry>();
    		
	    	for(MessageLine curMessage : curModel.getLog().getLines())
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
    	}   	
     }
	
}

