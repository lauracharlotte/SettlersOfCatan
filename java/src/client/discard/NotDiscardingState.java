package client.discard;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.misc.IWaitView;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TurnServerOperationsManager;
import clientcommunicator.operations.DiscardCardsRequest;
import model.ClientModel;
import model.ClientModelSupplier;
import model.cards.ResourceCards;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import shared.definitions.ResourceType;

public class NotDiscardingState implements IDiscardState
{
	public NotDiscardingState()
	{
		
	}

	@Override
	public IDiscardState modelUpdated(Observable o, Object arg, IDiscardView disView, IWaitView waitView) 
	{
		ClientModel model = (ClientModel) arg;
		if (model.getTurnTracker().getStatus() == TurnStatusEnumeration.discarding)
		{
			//Check amount of cards in current player's hand
			ResourceCards cards = ClientModelSupplier.getInstance().getClientPlayerObject().getHand().getResourceCards();
			int totalCards = cards.getBrick() + cards.getGrain() + cards.getLumber() + cards.getOre() + cards.getWool();
			if (totalCards > 7)
			{
				return new DiscardingState(disView);
			}
			else
			{
				ResourceCards emptyCards = new ResourceCards(0, 0, 0, 0, 0);
                PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
                DiscardCardsRequest request = new DiscardCardsRequest(index, emptyCards);
                TurnServerOperationsManager manager;
				try 
				{
					manager = (TurnServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
			    	manager.discardCards(request);
				} 
				catch (NoSuchMethodException | InstantiationException | IllegalAccessException
						| InvocationTargetException | ClientException e) 
				{
					Logger.getLogger(NotDiscardingState.class.getName()).log(Level.SEVERE, null, e);
				}
				return new WaitingState(waitView);
			}
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
