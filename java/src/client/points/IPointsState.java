package client.points;

import model.ClientModel;
import model.ClientModelSupplier;

public interface IPointsState {
	
	public void updatePoints();
	public IPointsState modelUpdated(ClientModelSupplier supplier);//got from Login
	public void render(IPointsView view, ClientModel curModel);//got from Login

}
