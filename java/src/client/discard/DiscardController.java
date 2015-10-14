package client.discard;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import model.ClientModelSupplier;
import model.player.Player;

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
    
    private int discardedWood = 0;
    private int discardedBrick = 0;
    private int discardedSheep = 0;
    private int discardedWheat = 0;
    private int discardedOre = 0;

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
    	Player player = ClientModelSupplier.getInstance().getClientPlayerObject();
    	totalWood = player.getHand().getResourceCards().getLumber();
    	totalBrick = player.getHand().getResourceCards().getBrick();
    	totalSheep = player.getHand().getResourceCards().getWool();
    	totalWheat = player.getHand().getResourceCards().getGrain();
    	totalOre = player.getHand().getResourceCards().getOre();
    }

    public IDiscardView getDiscardView() 
    {
    	return (IDiscardView)super.getView();
    }

    public IWaitView getWaitView() 
    {
    	return waitView;
    }

    @Override
    public void increaseAmount(ResourceType resource) 
    {
    	switch (resource)
    	{
    	case WOOD:
    		discardedWood++;
    		break;
    	case BRICK:
    		discardedBrick++;
    		break;
    	case SHEEP:
    		discardedSheep++;
    		break;
    	case WHEAT:
    		discardedWheat++;
    		break;
    	case ORE:
    		discardedOre++;
    		break;
    	default:
    		break;
    	}
    }

    @Override
    public void decreaseAmount(ResourceType resource) 
    {
    	switch (resource)
    	{
    	case WOOD:
    		discardedWood--;
    		break;
    	case BRICK:
    		discardedBrick--;
    		break;
    	case SHEEP:
    		discardedSheep--;
    		break;
    	case WHEAT:
    		discardedWheat--;
    		break;
    	case ORE:
    		discardedOre--;
    		break;
    	default:
    		break;
    	}
    }

    @Override
    public void discard() 
    {
    	getDiscardView().closeModal();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

