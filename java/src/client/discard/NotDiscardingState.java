package client.discard;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.misc.IWaitView;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TurnServerOperationsManager;
import clientcommunicator.operations.DiscardCardsRequest;
import model.ClientModel;
import model.ClientModelSupplier;
import model.cards.ResourceCards;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import shared.definitions.ResourceType;

public class NotDiscardingState implements IDiscardState
{
        private boolean inMiddleOfDiscard = false;
    
	public NotDiscardingState()
	{
		
	}

	@Override
	public IDiscardState modelUpdated(Observable o, Object arg, IDiscardView disView, IWaitView waitView) 
	{
            //System.out.println("Not Discarding going to...");
            ClientModel model = (ClientModel) arg;
            if(this.inMiddleOfDiscard)
                return this;
            if (model.getTurnTracker().getStatus() == TurnStatusEnumeration.discarding)
            {
                    //Check amount of cards in current player's hand
                    ResourceCards cards = ClientModelSupplier.getInstance().getClientPlayerObject().getHand().getResourceCards();
                    int totalCards = cards.getBrick() + cards.getGrain() + cards.getLumber() + cards.getOre() + cards.getWool();
                    if (totalCards > 7)
                    {
                            //System.out.println("Discarding State");
                            return new DiscardingState(disView);
                    }
                    else
                    {
                        //System.out.println("Waiting State");
                        ResourceCards emptyCards = new ResourceCards(0, 0, 0, 0, 0);
                        PlayerIdx turnItIs = model.getTurnTracker().getCurrentTurn();
                        PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
                        DiscardCardsRequest request = new DiscardCardsRequest(index, emptyCards);
                        TurnServerOperationsManager manager;
                            try 
                            {
                                this.inMiddleOfDiscard = true;
                                manager = (TurnServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
                                manager.discardCards(request);
                                this.inMiddleOfDiscard = false;
                            } 
                            catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                                            | InvocationTargetException | ClientException e) 
                            {
                                    Logger.getLogger(NotDiscardingState.class.getName()).log(Level.SEVERE, null, e);
                            }
                            ClientModel newModel = ClientModelSupplier.getInstance().getModel();
                            if(newModel.getTurnTracker().getStatus() == TurnStatusEnumeration.discarding && newModel.getTurnTracker().getCurrentTurn().equals(turnItIs))
                                return new WaitingState(waitView);
                            else if(newModel.getTurnTracker().getStatus() == TurnStatusEnumeration.discarding)
                                return this.modelUpdated(o, arg, disView, waitView);
                            else 
                                return this;
                    }
		}
		else
		{
                    //System.out.println("Not discarding state.");
                    return this;
		}
	}

	@Override
	public void increaseAmount(ResourceType resource, IDiscardView view) 
	{
		
	}

	@Override
	public void decreaseAmount(ResourceType resource, IDiscardView view) 
	{
		
	}

	@Override
	public void discard(IDiscardView disView, IWaitView waitView) 
	{
		return;
	}

}
