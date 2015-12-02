package client.turntracker;

import model.ClientModel;
import model.ClientModelSupplier;
import model.player.NullablePlayerIdx;
import model.player.Player;
import model.player.PlayerIdx;

public class SameTurnState implements ITurnTrackerState {
	
	ClientModel curModel;
	ITurnTrackerView view;

	@Override
	public ITurnTrackerState modelUpdated(ClientModelSupplier supplier) 
	{
		return this; //returning this because nothing has changed
	}
	
	@Override
	public void updateAPlayer()
	{
		for(Player playerInCurModel: ClientModelSupplier.getInstance().getModel().getPlayers())
		{
	    		view.initializePlayer(playerInCurModel.getPlayerIndex().getIndex(), playerInCurModel.getName(), playerInCurModel.getColor());
			int longestRoad = curModel.getTurnTracker().getLongestRoad().getIndex();
			int largestArmy = curModel.getTurnTracker().getLargestArmy().getIndex();
			int curTurn = curModel.getTurnTracker().getCurrentTurn().getIndex();
			
			for(Player playerInNewModel: curModel.getPlayers())
			{
				if(playerInCurModel.getPlayerIndex().getIndex() == playerInNewModel.getPlayerIndex().getIndex())
				{
					Boolean longestRoadBool = false;
					Boolean largestArmyBool = false;
					Boolean curTurnBool = false;
					if(playerInCurModel.getPlayerIndex().getIndex() == longestRoad)
					{
						longestRoadBool = true;
						curModel.getTurnTracker().setLongestRoad(new NullablePlayerIdx(longestRoad));
					}
					if(playerInCurModel.getPlayerIndex().getIndex() == largestArmy)
					{
						largestArmyBool = true;
						curModel.getTurnTracker().setLargestArmy(new NullablePlayerIdx(largestArmy));
					}
					if(playerInCurModel.getPlayerIndex().getIndex() == curTurn)
					{
						curTurnBool = true;
					}
					playerInCurModel.setVictoryPoints(playerInNewModel.getVictoryPoints());
					view.updatePlayer(playerInNewModel.getPlayerIndex().getIndex(), playerInNewModel.getVictoryPoints(), curTurnBool, largestArmyBool, longestRoadBool);
				}
			}
		}
	}

	@Override
	public void switchCurrentPlayer(PlayerIdx currentIdx) 
	{
		return;
	}

	@Override
	public void render(ITurnTrackerView view1, ClientModel curModel1) 
	{
		view = view1;
		curModel = curModel1;
		if(ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex().getIndex() == curModel1.getTurnTracker().getCurrentTurn().getIndex())
		{
			view.updateGameState("Finish Turn", true);
		}
		else
		{
			view.updateGameState("Waiting for other Players", false);
		}
		updateAPlayer();
		return; //nothing happened/changed
	}

}
