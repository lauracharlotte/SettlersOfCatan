package client.discard;

import java.util.Observable;

import client.misc.IWaitView;
import model.ClientModel;
import model.ClientModelSupplier;
import model.cards.ResourceCards;
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
	public IDiscardState discard(IDiscardView disView, IWaitView waitView) 
	{
		return null;
	}

}
