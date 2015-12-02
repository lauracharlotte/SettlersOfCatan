package client.turntracker;

import model.ClientModel;
import model.ClientModelSupplier;
import model.player.Player;
import model.player.PlayerIdx;

public class CreateTurnTrackerState implements ITurnTrackerState {
	private Boolean playersReady = false;

	@Override
	public ITurnTrackerState modelUpdated(ClientModelSupplier supplier) 
	{
		if(playersReady == true)
		{
			return new SameTurnState();
		}
		return new CreateTurnTrackerState();
	}
	
	@Override
	public void updateAPlayer()
	{
		return;
	}

	@Override
	public void switchCurrentPlayer(PlayerIdx currentIdx) 
	{
		return;
	}

	@Override
	public void render(ITurnTrackerView view, ClientModel curModel) 
	{
		if(ClientModelSupplier.getInstance().getModel()!=null)
		{
			if(ClientModelSupplier.getInstance().getModel().getPlayers().size() == 4)//or would you actually put 4?
			{
	    		for(Player findPlayer: ClientModelSupplier.getInstance().getModel().getPlayers())
	    		{
	    			view.initializePlayer(findPlayer.getPlayerIndex().getIndex(), findPlayer.getName(), findPlayer.getColor());
	    			
	    			Boolean whooseTurn = false;
	    			Boolean longestRoad = false;
	    			Boolean largestArmy = false;
	    			
	    			if(ClientModelSupplier.getInstance().getModel().getTurnTracker().getCurrentTurn().equals(findPlayer.getPlayerIndex()))
	    			{
	    				whooseTurn = true;
	    			}
	    			if(ClientModelSupplier.getInstance().getModel().getTurnTracker().getLargestArmy().getIndex() == findPlayer.getPlayerIndex().getIndex())
	    			{
	    				largestArmy = true;
	    			}
	    			if(ClientModelSupplier.getInstance().getModel().getTurnTracker().getLongestRoad().getIndex() == findPlayer.getPlayerIndex().getIndex())
	    			{
	    				longestRoad = true;
	    			}
	    			
	    			view.updatePlayer(findPlayer.getPlayerIndex().getIndex(), findPlayer.getVictoryPoints(), whooseTurn, largestArmy, longestRoad);
	    		}
	    		playersReady = true;
	    		
	    		if(ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex().getIndex() == 
	    				ClientModelSupplier.getInstance().getModel().getTurnTracker().getCurrentTurn().getIndex())
	    		{
	    			view.updateGameState("Finish Turn", true);
	    		}
	    		else
	    		{
	    			view.updateGameState("Waiting for other Players", false);
	    		}
	    		view.setLocalPlayerColor(ClientModelSupplier.getInstance().getClientPlayerObject().getColor());
			}
		}
	}
}
