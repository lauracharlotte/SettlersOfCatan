package client.discard;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import model.ClientModelSupplier;

import java.util.Observable;
import java.util.Observer;

/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer
{
    private IWaitView waitView;
    
    private IDiscardState state;

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
    	ClientModelSupplier.getInstance().addObserver(this);
    	
    	state = new NotDiscardingState();
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
    	state.increaseAmount(resource, getDiscardView());
    }

    @Override
    public void decreaseAmount(ResourceType resource) 
    {
    	state.decreaseAmount(resource, getDiscardView());
    }

    @Override
    public void discard() 
    {
    	state = state.discard(getDiscardView(), waitView);
    }

    @Override
    public void update(Observable o, Object arg)
    {
    	state = state.modelUpdated(o, arg, getDiscardView(), waitView);
    }

}

