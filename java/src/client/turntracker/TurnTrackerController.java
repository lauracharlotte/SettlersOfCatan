package client.turntracker;

import client.base.*;

import model.ClientModel;
import model.ClientModelSupplier;
import model.player.PlayerIdx;

import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer 
{
	private ITurnTrackerState currentState;
	
	public TurnTrackerController(ITurnTrackerView view) 
	{
        super(view);
        initFromModel();
        currentState = new CreateTurnTrackerState();    
        ClientModelSupplier.getInstance().addObserver(this); 
    }

    @Override
    public ITurnTrackerView getView() 
    {
           return (ITurnTrackerView)super.getView();
    }

    @Override
    public void endTurn() 
    {	
    	this.currentState = new FinishTurnState();
    	PlayerIdx curPlayerIdx = ClientModelSupplier.getInstance().getModel().getTurnTracker().getCurrentTurn();
    	this.currentState.switchCurrentPlayer(curPlayerIdx);
    	this.currentState.render(this.getView(), ClientModelSupplier.getInstance().getModel());
    	this.currentState = this.currentState.modelUpdated(ClientModelSupplier.getInstance());
    	this.currentState.render(this.getView(), ClientModelSupplier.getInstance().getModel());//?????????????
    }

    private void initFromModel() 
    {
    	//Should we keep in the stuff be
            //<temp>
           // getView().setLocalPlayerColor(CatanColor.RED);
            //<temp>
    }

    @Override
    public void update(Observable o, Object arg)
    {
    	ClientModel curModel = (ClientModel) arg;
    	this.currentState = this.currentState.modelUpdated((ClientModelSupplier)o);
    	this.currentState.render(this.getView(), curModel);
    }

}

