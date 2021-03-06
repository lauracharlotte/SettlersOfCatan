package client.discard;

import java.util.Observable;

import client.misc.IWaitView;
import model.ClientModel;
import model.player.TurnStatusEnumeration;
import shared.definitions.ResourceType;

public class WaitingState implements IDiscardState
{
	public WaitingState(IWaitView view)
	{
		view.showModal();
	}

	@Override
	public IDiscardState modelUpdated(Observable o, Object arg, IDiscardView disView, IWaitView waitView) 
	{
            //System.out.println("Waiting State going to");
            ClientModel model = (ClientModel) arg;
            if (model.getTurnTracker().getStatus() != TurnStatusEnumeration.discarding)
            {
            	if(waitView.isModalShowing())
            	{
            		waitView.closeModal();
            	}
				return new NotDiscardingState();
            }
            else
            {
		return this;
            }
	}

	@Override
	public void increaseAmount(ResourceType resource, IDiscardView view) 
	{
		
	}

	@Override
	public void decreaseAmount(ResourceType resource, IDiscardView view) 
	{
		
	}

	@Override
	public void discard(IDiscardView disView, IWaitView waitView) 
	{
		return;
	}
}
