package client.turntracker;

import model.ClientModel;
import model.ClientModelSupplier;
import model.player.PlayerIdx;

public interface ITurnTrackerState {
	public ITurnTrackerState modelUpdated(ClientModelSupplier supplier);//got from Login
	public void updateAPlayer();
	public void switchCurrentPlayer(PlayerIdx currentIdx);
	public void render(ITurnTrackerView view, ClientModel curModel);//got from Login
}
