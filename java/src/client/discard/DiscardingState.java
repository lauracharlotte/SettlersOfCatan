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
    
    private PlayerIdx whoseTurn;
	
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
		this.whoseTurn = ClientModelSupplier.getInstance().getModel().getTurnTracker().getCurrentTurn();
		initView(view);
		updateView(view);
	}

	@Override
	public IDiscardState modelUpdated(Observable o, Object arg, IDiscardView disView, IWaitView waitView) 
	{
            //System.out.println("Discarding State going to...");
            ClientModel model = (ClientModel) arg;
            if (model.getTurnTracker().getStatus() != TurnStatusEnumeration.discarding)
            {
                //System.out.println("Not discarding state");
		return new NotDiscardingState();
            }
            else if (!this.whoseTurn.equals(ClientModelSupplier.getInstance().getModel().getTurnTracker().getCurrentTurn()))
            {
                //System.out.println("new discarding state");
                disView.closeModal();
                waitView.closeModal();
                return new DiscardingState(disView);
            }
            else
            {
                    if (discarded)
                    {
                        //System.out.println("New waiting state");
                            disView.closeModal();
                            return new WaitingState(waitView);
                    }
                    else
                    {
                        //System.out.println("Same discarding state.");
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
    		if (discardedWood == totalWood || discardedWood == totalToDiscard)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
            }
    		break;
    	case BRICK:
    		discardedBrick++;
    		view.setResourceDiscardAmount(ResourceType.BRICK, discardedBrick);
    		if (discardedBrick == totalBrick || discardedBrick == totalToDiscard)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
            }
    		break;
    	case SHEEP:
    		discardedSheep++;
    		view.setResourceDiscardAmount(ResourceType.SHEEP, discardedSheep);
    		if (discardedSheep == totalSheep || discardedSheep == totalToDiscard)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
            }
    		break;
    	case WHEAT:
    		discardedWheat++;
    		view.setResourceDiscardAmount(ResourceType.WHEAT, discardedWheat);
    		if (discardedWheat == totalWheat || discardedWheat == totalToDiscard)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
            }
    		break;
    	case ORE:
    		discardedOre++;
    		view.setResourceDiscardAmount(ResourceType.ORE, discardedOre);
    		if (discardedOre == totalOre || discardedOre == totalToDiscard)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
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
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
            }
    		break;
    	case BRICK:
    		discardedBrick--;
    		view.setResourceDiscardAmount(ResourceType.BRICK, discardedBrick);
    		if (discardedBrick == 0)
    		{
                    view.setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
            }
    		break;
    	case SHEEP:
    		discardedSheep--;
    		view.setResourceDiscardAmount(ResourceType.SHEEP, discardedSheep);
    		if (discardedSheep == 0)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
            }
    		break;
    	case WHEAT:
    		discardedWheat--;
    		view.setResourceDiscardAmount(ResourceType.WHEAT, discardedWheat);
    		if (discardedWheat == 0)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
            }
    		break;
    	case ORE:
    		discardedOre--;
    		view.setResourceDiscardAmount(ResourceType.ORE, discardedOre);
    		if (discardedOre == 0)
    		{
    			view.setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
    		}
            else
            {
                view.setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
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
		view.setResourceMaxAmount(ResourceType.WOOD, totalWood);
		view.setResourceMaxAmount(ResourceType.BRICK, totalBrick);
		view.setResourceMaxAmount(ResourceType.SHEEP, totalSheep);
		view.setResourceMaxAmount(ResourceType.WHEAT, totalWheat);
		view.setResourceMaxAmount(ResourceType.ORE, totalOre);
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
        view.setResourceDiscardAmount(ResourceType.WOOD, 0);
        view.setResourceDiscardAmount(ResourceType.WHEAT, 0);
        view.setResourceDiscardAmount(ResourceType.ORE, 0);
        view.setResourceDiscardAmount(ResourceType.BRICK, 0);
        view.setResourceDiscardAmount(ResourceType.SHEEP, 0);
	}
	
	private void updateResourceArrows(ResourceType type, int discarded, int total, IDiscardView view)
	{
		if (discarded > 0 && discarded < total)
		{
			view.setResourceAmountChangeEnabled(type, true, true);
		}
		else if (discarded > 0 && discarded == total)
		{
			view.setResourceAmountChangeEnabled(type, false, true);
		}
		else if (discarded == 0 && total == 0) 
		{
			view.setResourceAmountChangeEnabled(type, false, false);
		}
		else
		{
			view.setResourceAmountChangeEnabled(type, true, false);
		}
	}
	
	private void updateEnabledArrows(IDiscardView view, int totalDiscarded)
	{
		if (totalDiscarded == totalToDiscard)
		{
			//All up arrows should be disabled
			if (discardedWood > 0) view.setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
			else view.setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
			if (discardedBrick > 0) view.setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
			else view.setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
			if (discardedSheep > 0) view.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
			else view.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
			if (discardedWheat > 0) view.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
			else view.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
			if (discardedOre > 0) view.setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
			else view.setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
		}
		else
		{
			updateResourceArrows(ResourceType.WOOD, discardedWood, totalWood, view);
			updateResourceArrows(ResourceType.BRICK, discardedBrick, totalBrick, view);
			updateResourceArrows(ResourceType.SHEEP, discardedSheep, totalSheep, view);
			updateResourceArrows(ResourceType.WHEAT, discardedWheat, totalWheat, view);
			updateResourceArrows(ResourceType.ORE, discardedOre, totalOre, view);
		}
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
    		updateEnabledArrows(view, totalDiscarded);
    	}
    	else
    	{
    		view.setDiscardButtonEnabled(false);
    		updateEnabledArrows(view, totalDiscarded);
    	}
	}

	@Override
	public void discard(IDiscardView disView, IWaitView waitView) 
	{
        ResourceCards cards = new ResourceCards(discardedBrick, discardedWheat, discardedWood, discardedOre, discardedSheep);
        PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        DiscardCardsRequest request = new DiscardCardsRequest(index, cards);
        TurnServerOperationsManager manager;
        try 
        {
            disView.closeModal();
            manager = (TurnServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
            discarded = true;
            manager.discardCards(request);
        } 
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException
        			| InvocationTargetException | ClientException e) 
        {
        	Logger.getLogger(DiscardingState.class.getName()).log(Level.SEVERE, null, e);
        }
        return;
	}

}
