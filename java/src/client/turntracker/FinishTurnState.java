package client.turntracker;

import java.lang.reflect.InvocationTargetException;

import client.base.IAction;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TurnServerOperationsManager;
import clientcommunicator.operations.FinishTurnRequest;
import model.ClientModel;
import model.ClientModelSupplier;
import model.player.NullablePlayerIdx;
import model.player.Player;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import model.player.TurnTracker;
import shared.definitions.CatanColor;

public class FinishTurnState implements ITurnTrackerState 
{
	@Override
	public ITurnTrackerState modelUpdated(ClientModelSupplier supplier) 
	{
		return new SameTurnState();
	}
	
	@Override
	public void updateAPlayer()
	{
		return;
	}

	@Override
	public void switchCurrentPlayer(PlayerIdx currentIdx) 
	{
		/*if(currentIdx.getIndex() < 3)
		{
			int index = currentIdx.getIndex() + 1;
			currentIdx.setIndex(index);
		}
		else
		{
			currentIdx.setIndex(0);
		}
		
		ClientModelSupplier.getInstance().getModel().getTurnTracker().setCurrentTurn(currentIdx);*/
	}

	@Override
	public void render(ITurnTrackerView view, ClientModel curModel) 
	{
		int playerIndex = ClientModelSupplier.getInstance().getModel().getTurnTracker().getCurrentTurn().getIndex();
		int playerIndexBefore = 0;
		
		if(playerIndex > 0)
		{
			playerIndexBefore = playerIndex - 1;
		}
		else if(playerIndex == 0)
		{
			playerIndexBefore = 3;
		}
		
		Player currentPlayer = null; 
		Player newCurrentPlayer = null;
		
		for(Player findPlayer: ClientModelSupplier.getInstance().getModel().getPlayers())
		{
			if(findPlayer.getPlayerIndex().getIndex() == playerIndex)
			{
				currentPlayer = findPlayer;
			}
			if(findPlayer.getPlayerIndex().getIndex() == playerIndexBefore)
			{
				newCurrentPlayer = findPlayer;
			}
		}
		
		NullablePlayerIdx largestArmyIdx = ClientModelSupplier.getInstance().getModel().getTurnTracker().getLargestArmy();
		NullablePlayerIdx longestRoadIdx = ClientModelSupplier.getInstance().getModel().getTurnTracker().getLongestRoad();
		
		Boolean largestArmy = false;
		Boolean longestRoad = false;
		Boolean largestArmy2 = false;
		Boolean longestRoad2 = false;
		
		if(playerIndex == largestArmyIdx.getIndex())
		{
			largestArmy = true;
		}
		if(playerIndex == longestRoadIdx.getIndex())
		{
			longestRoad = true;
		}
		if(playerIndexBefore == largestArmyIdx.getIndex())
		{
			largestArmy2 = true;
		}
		if(playerIndexBefore == longestRoadIdx.getIndex())
		{
			longestRoad2 = true;
		}
		
		view.updatePlayer(playerIndex, currentPlayer.getVictoryPoints(), true, largestArmy, longestRoad);
		view.updatePlayer(playerIndexBefore, newCurrentPlayer.getVictoryPoints(), false, largestArmy2, longestRoad2);
		
		//Now we tell the Server to update
		
    	TurnServerOperationsManager manager = null;
    	try {
			manager = (TurnServerOperationsManager)ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	FinishTurnRequest finishPlease = new FinishTurnRequest(new PlayerIdx(playerIndex));
    	
    	try {
			manager.finishTurn(finishPlease);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	view.updateGameState("Waiting for other Players", false);
	}

}
