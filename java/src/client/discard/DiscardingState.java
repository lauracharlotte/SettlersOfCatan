package client.discard;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

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

public class DiscardingState implements IDiscardState
{
	private boolean discarded;
	
	private int totalWood;
    private int totalBrick;
    private int totalSheep;
    private int totalWheat;
    private int totalOre;
    
    private int discardedWood = 0;
    private int discardedBrick = 0;
    private int discardedSheep = 0;
    private int discardedWheat = 0;
    private int discardedOre = 0;
    
    private int totalToDiscard;
	
	public DiscardingState(IDiscardView view) 
	{
		discarded = false;
		ResourceCards cards = ClientModelSupplier.getInstance().getClientPlayerObject().getHand().getResourceCards();
		totalWood = cards.getLumber();
		totalBrick = cards.getBrick();
		totalSheep = cards.getWool();
		totalWheat = cards.getGrain();
		totalOre = cards.getOre();
		totalToDiscard = (int) ((totalWood + totalBrick + totalSheep + totalWheat + totalOre) / 2);
		initView(view);
		updateView(view);
	}

	@Override
	public IDiscardState modelUpdated(Observable o, Object arg, IDiscardView disView, IWaitView waitView) 
	{
		ClientModel model = (ClientModel) arg;
		if (model.getTurnTracker().getStatus() != TurnStatusEnumeration.discarding)
		{
			disView.closeModal();
			return new NotDiscardingState();
		}
		else
		{
			if (discarded)
			{
				disView.closeModal();
				return new WaitingState(waitView);
			}
			else
			{
				return this;
			}
		}
	}

	@Override
	public void increaseAmount(ResourceType resource, IDiscardView view) 
	{
		switch (resource)
    	{
    	case WOOD:
    		discardedWood++;
    		view.setResourceDiscardAmount(ResourceType.WOOD, discardedWood);
    		if (discardedWood == totalWood)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
    		}
    		break;
    	case BRICK:
    		discardedBrick++;
    		view.setResourceDiscardAmount(ResourceType.BRICK, discardedBrick);
    		if (discardedBrick == totalBrick)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
    		}
    		break;
    	case SHEEP:
    		discardedSheep++;
    		view.setResourceDiscardAmount(ResourceType.SHEEP, discardedSheep);
    		if (discardedSheep == totalSheep)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
    		}
    		break;
    	case WHEAT:
    		discardedWheat++;
    		view.setResourceDiscardAmount(ResourceType.WHEAT, discardedWheat);
    		if (discardedWheat == totalWheat)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
    		}
    		break;
    	case ORE:
    		discardedOre++;
    		view.setResourceDiscardAmount(ResourceType.ORE, discardedOre);
    		if (discardedOre == totalOre)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
    		}
    		break;
    	default:
    		break;
    	}
		updateView(view);
	}

	@Override
	public void decreaseAmount(ResourceType resource, IDiscardView view) 
	{
		switch (resource)
    	{
    	case WOOD:
    		discardedWood--;
    		view.setResourceDiscardAmount(ResourceType.WOOD, discardedWood);
    		if (discardedWood == 0)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
    		}
    		break;
    	case BRICK:
    		discardedBrick--;
    		view.setResourceDiscardAmount(ResourceType.BRICK, discardedBrick);
    		if (discardedBrick == 0)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
    		}
    		break;
    	case SHEEP:
    		discardedSheep--;
    		view.setResourceDiscardAmount(ResourceType.SHEEP, discardedSheep);
    		if (discardedSheep == 0)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
    		}
    		break;
    	case WHEAT:
    		discardedWheat--;
    		view.setResourceDiscardAmount(ResourceType.WHEAT, discardedWheat);
    		if (discardedWheat == 0)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
    		}
    		break;
    	case ORE:
    		discardedOre--;
    		view.setResourceDiscardAmount(ResourceType.ORE, discardedOre);
    		if (discardedOre == 0)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
    		}
    		break;
    	default:
    		break;
    	}
    	updateView(view);
	}
	
	private void initView(IDiscardView view)
	{
		view.showModal();
		view.setDiscardButtonEnabled(false);
		view.setResourceMaxAmount(ResourceType.WOOD, Integer.min(totalWood, totalToDiscard));
		view.setResourceMaxAmount(ResourceType.BRICK, Integer.min(totalBrick, totalToDiscard));
		view.setResourceMaxAmount(ResourceType.SHEEP, Integer.min(totalSheep, totalToDiscard));
		view.setResourceMaxAmount(ResourceType.WHEAT, Integer.min(totalWheat, totalToDiscard));
		view.setResourceMaxAmount(ResourceType.ORE, Integer.min(totalOre, totalToDiscard));
		if (totalWood == 0) view.setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		else view.setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
		if (totalBrick == 0) view.setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
		else view.setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
		if (totalSheep == 0) view.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		else view.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
		if (totalWheat == 0) view.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		else view.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
		if (totalOre == 0) view.setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
		else view.setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
	}

	private void updateView(IDiscardView view) 
	{
		int totalDiscarded = discardedWood + discardedBrick + discardedSheep + discardedWheat + discardedOre;
    	StringBuilder str = new StringBuilder();
    	str.append(totalDiscarded);
    	str.append("/");
    	str.append(totalToDiscard);
    	view.setStateMessage(str.toString());
    	if (totalDiscarded == totalToDiscard) 
    	{
    		view.setDiscardButtonEnabled(true);
    	}
    	else
    	{
    		view.setDiscardButtonEnabled(false);
    	}
	}

	@Override
	public IDiscardState discard(IDiscardView disView, IWaitView waitView) 
	{
    	ResourceCards cards = new ResourceCards(discardedBrick, discardedWheat, discardedWood, discardedOre, discardedSheep);
    	PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    	DiscardCardsRequest request = new DiscardCardsRequest(index, cards);
    	TurnServerOperationsManager manager;
		try {
			manager = (TurnServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
	    	manager.discardCards(request);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		discarded = true;
		disView.closeModal();
		return new WaitingState(waitView);
	}

}
