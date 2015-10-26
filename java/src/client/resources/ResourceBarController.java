package client.resources;

import java.util.*;

import client.base.*;
import guicommunicator.ResourceModelFacade;
import model.ClientModelSupplier;
import model.cards.ResourceCards;
import model.player.Player;
import model.player.PlayerIdx;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, Observer
{

    private Map<ResourceBarElement, IAction> elementActions;

    public ResourceBarController(IResourceBarView view) {

            super(view);

            elementActions = new HashMap<ResourceBarElement, IAction>();
            
            ClientModelSupplier.getInstance().addObserver(this);
    }

    @Override
    public IResourceBarView getView() {
            return (IResourceBarView)super.getView();
    }

    /**
     * Sets the action to be executed when the specified resource bar element is clicked by the user
     * 
     * @param element The resource bar element with which the action is associated
     * @param action The action to be executed
     */
    public void setElementAction(ResourceBarElement element, IAction action) {

            elementActions.put(element, action);
    }

    @Override
    public void buildRoad() {
            executeElementAction(ResourceBarElement.ROAD);
    }

    @Override
    public void buildSettlement() {
            executeElementAction(ResourceBarElement.SETTLEMENT);
    }

    @Override
    public void buildCity() {
            executeElementAction(ResourceBarElement.CITY);
    }

    @Override
    public void buyCard() {
            executeElementAction(ResourceBarElement.BUY_CARD);
    }

    @Override
    public void playCard() {
            executeElementAction(ResourceBarElement.PLAY_CARD);
    }

    private void executeElementAction(ResourceBarElement element) {

            if (elementActions.containsKey(element)) {

                    IAction action = elementActions.get(element);
                    action.execute();
            }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        ResourceModelFacade resourceModelFacade = new ResourceModelFacade();
        Player player = ClientModelSupplier.getInstance().getClientPlayerObject();
        if (player != null)
        {
            ResourceCards resourceCards = player.getHand().getResourceCards();
        
            // update the resources you have
            this.getView().setElementAmount(ResourceBarElement.WOOD, resourceCards.getLumber());
            this.getView().setElementAmount(ResourceBarElement.BRICK, resourceCards.getBrick());
            this.getView().setElementAmount(ResourceBarElement.SHEEP, resourceCards.getWool());
            this.getView().setElementAmount(ResourceBarElement.ORE, resourceCards.getOre());
            this.getView().setElementAmount(ResourceBarElement.WHEAT, resourceCards.getGrain());

            // update roads/settlements/cities you have left
            this.getView().setElementAmount(ResourceBarElement.ROAD, player.getRoads());
            this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, player.getSettlements());
            this.getView().setElementAmount(ResourceBarElement.CITY, player.getCities());

            // update soldiers
            this.getView().setElementAmount(ResourceBarElement.SOLDIERS, player.getSoldiers());

            // update can build road/settlement/city
            this.getView().setElementEnabled(ResourceBarElement.ROAD, 
                    resourceModelFacade.canBuildRoad());
            this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, 
                    resourceModelFacade.canBuildSettlement());
            this.getView().setElementEnabled(ResourceBarElement.CITY, 
                    resourceModelFacade.canBuildCity());

            // update can buy card and can play card
            this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD,
                    true);
            this.getView().setElementEnabled(ResourceBarElement.BUY_CARD,
                    resourceModelFacade.canBuyDevCard());
            
            PlayerIdx currentTurn = ClientModelSupplier.getInstance().getModel().getTurnTracker().getCurrentTurn();
            if (player.getPlayerIndex().getIndex() != currentTurn.getIndex()){
                this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
                this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
                this.getView().setElementEnabled(ResourceBarElement.CITY, false);
                this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
                this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
            }      
        }
    }

}

