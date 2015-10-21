package client.points;

import client.base.*;
import model.ClientModel;
import model.ClientModelSupplier;
import model.player.Player;

import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer
{

    private IGameFinishedView finishedView;

    /**
     * PointsController constructor
     * 
     * @param view Points view
     * @param finishedView Game finished view, which is displayed when the game is over
     */
    public PointsController(IPointsView view, IGameFinishedView finishedView) {

            super(view);

            setFinishedView(finishedView);

            initFromModel();
    }

    public IPointsView getPointsView() {

            return (IPointsView)super.getView();
    }

    public IGameFinishedView getFinishedView() {
            return finishedView;
    }
    public void setFinishedView(IGameFinishedView finishedView) {
            this.finishedView = finishedView;
    }

    private void initFromModel() {//CHANGE------------------------------------------------------
            //<temp>		
            //getPointsView().setPoints(0);
           //I'm not sure where this is even showing up.
            //</temp>
    }

    @Override
    public void update(Observable o, Object arg)
    {
    	ClientModel curModel = (ClientModel) arg;
    	ClientModelSupplier curSupplier = (ClientModelSupplier) o;
		int locPlayerIdx = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex().getIndex();
		
		for(Player player: curModel.getPlayers())
		{
			if(player.getPlayerIndex().getIndex() == locPlayerIdx)
			{
				getPointsView().setPoints(player.getVictoryPoints());
				//Do I need to change the Victory points in the other as well or is that done elsewhere?
				//I'm not sure where this is even showing up.
			}
			if(player.getVictoryPoints() >= 10)
			{
				if(player.getPlayerIndex().getIndex() == locPlayerIdx)
				{
					getFinishedView().setWinner(player.getName(), true);
				}
				else
				{
					getFinishedView().setWinner(player.getName(), false);
				}
			}
		}	
    }
}

