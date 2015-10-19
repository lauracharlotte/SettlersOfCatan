package client.discard;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TurnServerOperationsManager;
import clientcommunicator.operations.DiscardCardsRequest;
import model.ClientModelSupplier;
import model.cards.ResourceCards;
import model.player.Player;
import model.player.PlayerIdx;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer
{
    private IWaitView waitView;
    
    private int totalWood;
    private int totalBrick;
    private int totalSheep;
    private int totalWheat;
    private int totalOre;
    
    private int discardedWood;
    private int discardedBrick;
    private int discardedSheep;
    private int discardedWheat;
    private int discardedOre;
    
    private int totalToDiscard;

    /**
     * DiscardController constructor
     * 
     * @param view View displayed to let the user select cards to discard
     * @param waitView View displayed to notify the user that they are waiting for other players to discard
     */
    public DiscardController(IDiscardView view, IWaitView waitView) 
    {
    	super(view);
    	this.waitView = waitView;
    	totalWood = 0;
    	totalBrick = 0;
    	totalSheep = 0;
    	totalWheat = 0;
    	totalOre = 0;
    	discardedWood = 0;
    	discardedBrick = 0;
    	discardedSheep = 0;
    	discardedWheat = 0;
    	discardedOre = 0;
    	totalToDiscard = 0;
    }

    public IDiscardView getDiscardView() 
    {
    	return (IDiscardView)super.getView();
    }

    public IWaitView getWaitView() 
    {
    	return waitView;
    }
    
    private void updateDiscardView()
    {
    	int totalDiscarded = discardedWood + discardedBrick + discardedSheep + discardedWheat + discardedOre;
    	StringBuilder str = new StringBuilder();
    	str.append(totalDiscarded);
    	str.append("/");
    	str.append(totalToDiscard);
    	getDiscardView().setStateMessage(str.toString());
    	if (totalDiscarded == totalToDiscard) 
    	{
    		getDiscardView().setDiscardButtonEnabled(true);
    	}
    	else
    	{
    		getDiscardView().setDiscardButtonEnabled(false);
    	}
    }

    @Override
    public void increaseAmount(ResourceType resource) 
    {
    	switch (resource)
    	{
    	case WOOD:
    		discardedWood++;
    		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, discardedWood);
    		if (discardedWood == totalWood)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
    		}
    		break;
    	case BRICK:
    		discardedBrick++;
    		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, discardedBrick);
    		if (discardedBrick == totalBrick)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
    		}
    		break;
    	case SHEEP:
    		discardedSheep++;
    		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, discardedSheep);
    		if (discardedSheep == totalSheep)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
    		}
    		break;
    	case WHEAT:
    		discardedWheat++;
    		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, discardedWheat);
    		if (discardedWheat == totalWheat)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
    		}
    		break;
    	case ORE:
    		discardedOre++;
    		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, discardedOre);
    		if (discardedOre == totalOre)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
    		}
    		break;
    	default:
    		break;
    	}
		updateDiscardView();
    }

    @Override
    public void decreaseAmount(ResourceType resource) 
    {
    	switch (resource)
    	{
    	case WOOD:
    		discardedWood--;
    		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, discardedWood);
    		if (discardedWood == 0)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
    		}
    		break;
    	case BRICK:
    		discardedBrick--;
    		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, discardedBrick);
    		if (discardedBrick == 0)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
    		}
    		break;
    	case SHEEP:
    		discardedSheep--;
    		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, discardedSheep);
    		if (discardedSheep == 0)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
    		}
    		break;
    	case WHEAT:
    		discardedWheat--;
    		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, discardedWheat);
    		if (discardedWheat == 0)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
    		}
    		break;
    	case ORE:
    		discardedOre--;
    		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, discardedOre);
    		if (discardedOre == 0)
    		{
    			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
    		}
    		break;
    	default:
    		break;
    	}
    	updateDiscardView();
    }

    @Override
    public void discard() 
    {
    	getDiscardView().closeModal();
    	getWaitView().showModal();
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
    }

    @Override
    public void update(Observable o, Object arg)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

