package client.turntracker;

import model.ClientModel;
import model.ClientModelSupplier;
import model.player.Player;
import model.player.PlayerIdx;
import shared.definitions.CatanColor;

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
	    			//
	    			view.updatePlayer(findPlayer.getPlayerIndex().getIndex(), findPlayer.getVictoryPoints(), true, false, false);
	    			//
	    		}
	    		playersReady = true;
	    		view.updatePlayer(ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex().getIndex(), ClientModelSupplier.getInstance().getClientPlayerObject().getVictoryPoints(), true, false, false);
	    		//view.updatePlayer(0, 0, true, false, false);
	    		if(ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex().getIndex() == 0)
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
