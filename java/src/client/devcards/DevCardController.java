package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.*;
import clientcommunicator.modelserverfacade.BuildItemServerOperationsManager;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.DevCardServerOperationsManager;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.operations.BuyDevCardRequest;
import clientcommunicator.operations.MonopolyRequest;
import clientcommunicator.operations.MonumentRequest;
import clientcommunicator.operations.YearOfPlentyRequest;
import model.ClientModelSupplier;
import model.cards.DevelopmentCards;
import model.player.PlayerIdx;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, Observer 
{
    private IBuyDevCardView buyCardView;
    private IAction soldierAction;
    private IAction roadAction;
    private DevCardServerOperationsManager manager;

    /**
     * DevCardController constructor
     * 
     * @param view "Play dev card" view
     * @param buyCardView "Buy dev card" view
     * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
     * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
     */
    public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
                                                            IAction soldierAction, IAction roadAction) 
    {
    	super(view);

    	this.buyCardView = buyCardView;
    	this.soldierAction = soldierAction;
    	this.roadAction = roadAction;
    	
    	try 
    	{
			manager = (DevCardServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(DevCardServerOperationsManager.class);
		} 
    	catch (NoSuchMethodException | InstantiationException | IllegalAccessException
				| InvocationTargetException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ClientModelSupplier.getInstance().addObserver(this);
    }

    public IPlayDevCardView getPlayCardView() 
    {
    	return (IPlayDevCardView)super.getView();
    }

    public IBuyDevCardView getBuyCardView() 
    {
    	return buyCardView;
    }

    @Override
    public void startBuyCard() 
    {
    	getBuyCardView().showModal();
    }

    @Override
    public void cancelBuyCard() 
    {
    	getBuyCardView().closeModal();
    }

    @Override
    public void buyCard() 
    {
    	getBuyCardView().closeModal();
    	PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    	BuyDevCardRequest request = new BuyDevCardRequest(index);
    	try 
    	{
			BuildItemServerOperationsManager buildManager = (BuildItemServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(BuildItemServerOperationsManager.class);
			buildManager.buyDevCard(request);
		} 
    	catch (NoSuchMethodException | InstantiationException | IllegalAccessException
				| InvocationTargetException | ClientException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void startPlayCard() 
    {
    	getPlayCardView().showModal();
    	DevelopmentCards cards = ClientModelSupplier.getInstance().getClientPlayerObject().getHand().getDevelopmentCards();
    	
    	//Set each type of card as enabled or disabled
    	if (cards.getMonopoly() > 0) getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
    	else getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
    	if (cards.getMonument() > 0) getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
    	else getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
    	if (cards.getRoadBuilding() > 0) getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
    	else getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
    	if (cards.getSoldier() > 0) getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
    	else getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
    	if (cards.getYearOfPlenty() > 0) getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
    	else getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
    	
    	//Set card amounts
    	getPlayCardView().setCardAmount(DevCardType.MONOPOLY, cards.getMonopoly());
    	getPlayCardView().setCardAmount(DevCardType.MONUMENT, cards.getMonument());
    	getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, cards.getRoadBuilding());
    	getPlayCardView().setCardAmount(DevCardType.SOLDIER, cards.getSoldier());
    	getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, cards.getYearOfPlenty());
    }

    @Override
    public void cancelPlayCard() 
    {
    	getPlayCardView().closeModal();
    }

    @Override
    public void playMonopolyCard(ResourceType resource) 
    {
    	PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    	MonopolyRequest request = new MonopolyRequest(index, resource);
    	try 
    	{
			manager.playMonopoly(request);
		} 
    	catch (ClientException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void playMonumentCard() 
    {
    	PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    	MonumentRequest request = new MonumentRequest(index);
    	try 
    	{
			manager.playMonument(request);
		} 
    	catch (ClientException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void playRoadBuildCard() 
    {
    	roadAction.execute();
    }

    @Override
    public void playSoldierCard() 
    {
    	soldierAction.execute();
    }

    @Override
    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) 
    {
    	PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    	YearOfPlentyRequest request = new YearOfPlentyRequest(index, resource1, resource2);
    	try 
    	{
			manager.yearOfPlenty(request);
		} 
    	catch (ClientException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void update(Observable o, Object arg)
    {
    	//Remember to check if arg is null
    	//Just need to check if it's the player's turn (maybe?)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

