package client.roll;

import client.base.*;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TurnServerOperationsManager;
import clientcommunicator.operations.RollNumberRequest;
import model.ClientModel;
import model.ClientModelSupplier;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer 
{
    private IRollResultView resultView;
    private final long firstDelay = 1000;
    private final long secondDelay = 2000;
    private final long thirdDelay = 3000;
    private final long fourthDelay = 4000;
    private final long fifthDelay = 5000;
    private final int diceUpperBound = 12;
    private final String rollMessage = "Rolling automatically in...";
    private Timer timer;

    /**
     * RollController constructor
     * 
     * @param view Roll view
     * @param resultView Roll result view
     */
    public RollController(IRollView view, IRollResultView resultView) 
    {
    	super(view);
    	setResultView(resultView);
        ClientModelSupplier.getInstance().addObserver(this);
    }

    public IRollResultView getResultView() 
    {
    	return resultView;
    }
    
    public void setResultView(IRollResultView resultView) 
    {
    	this.resultView = resultView;
    }

    public IRollView getRollView() 
    {
    	return (IRollView)getView();
    }

    @Override
    public void rollDice() 
    {
    	Random dice = new Random();
    	int roll = dice.nextInt(diceUpperBound) + 1;
    	getRollView().closeModal();
    	getResultView().setRollValue(roll);
    	getResultView().showModal();
    	PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    	RollNumberRequest request = new RollNumberRequest(index, roll);
		TurnServerOperationsManager manager;
		try 
		{
			manager = (TurnServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
			manager.rollNumber(request);
		} 
		catch (NoSuchMethodException | InstantiationException | IllegalAccessException
				| InvocationTargetException | ClientException e) 
		{
			Logger.getLogger(RollController.class.getName()).log(Level.SEVERE, null, e);
		}
    }
    
    public void stopTimer()
    {
    	timer.cancel();
    }
    
    private void startTimer()
    {
    	timer = new Timer();
    	TimerTask firstTask = new TimerTask()
    	{
    		@Override
    		public void run()
    		{
    			getRollView().setMessage(rollMessage + "4 seconds");
    		}
    	};
    	TimerTask secondTask = new TimerTask()
    	{
    		@Override
    		public void run()
    		{
    			getRollView().setMessage(rollMessage + "3 seconds");
    		}
    	};
    	TimerTask thirdTask = new TimerTask()
    	{
    		@Override
    		public void run()
    		{
    			getRollView().setMessage(rollMessage + "2 seconds");
    		}
    	};
    	TimerTask fourthTask = new TimerTask()
    	{
    		@Override
    		public void run()
    		{
    			getRollView().setMessage(rollMessage + "1 second");
    		}
    	};
    	TimerTask fifthTask = new TimerTask() 
    	{
    		@Override
    		public void run()
    		{
    			rollDice();
    		}
    	};
    	timer.schedule(fifthTask, fifthDelay);
    	timer.schedule(fourthTask, fourthDelay);
    	timer.schedule(thirdTask, thirdDelay);
    	timer.schedule(secondTask, secondDelay);
    	timer.schedule(firstTask, firstDelay);
    	getRollView().setMessage(rollMessage + "5 seconds");
    }

    @Override
    public void update(Observable o, Object arg)
    {
    	if (arg != null)
    	{
    		ClientModel model = (ClientModel) arg;
    		TurnStatusEnumeration status = model.getTurnTracker().getStatus();
    		PlayerIdx client = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    		if (status == TurnStatusEnumeration.rolling && model.getTurnTracker().getCurrentTurn().equals(client))
    		{
    			if (!getRollView().isModalShowing())
    			{
	    	    	getRollView().setMessage(rollMessage);
	    			getRollView().showModal();
	    			startTimer();
    			}
    		}
    	}
    }

}

