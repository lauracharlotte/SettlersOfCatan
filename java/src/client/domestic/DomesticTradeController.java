package client.domestic;

import shared.definitions.*;
import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TradeServerOperationsManager;
import clientcommunicator.operations.AcceptTradeRequest;
import clientcommunicator.operations.OfferTradeRequest;
import guicommunicator.ResourceModelFacade;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModel;

import model.ClientModelSupplier;
import model.cards.ResourceCards;
import model.cards.TradeOffer;
import model.player.Player;
import model.player.PlayerIdx;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer 
{

    private IDomesticTradeOverlay tradeOverlay;
    private IWaitView waitOverlay;
    private IAcceptTradeOverlay acceptOverlay;
    
    /**
     * These tradeResource variables represent whether the resource is being
     * sent or being received.
     * 0 = none
     * 1 = sent
     * -1 = received
     */
    private int tradeWood = 0;
    private int tradeBrick = 0;
    private int tradeOre = 0;
    private int tradeWheat = 0;
    private int tradeSheep = 0;
    private boolean sending = false;
    private boolean receiving = false;
    private boolean personToAsk = false;
    private TradeOffer trade;

    /**
     * DomesticTradeController constructor
     * 
     * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
     * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
     * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
     * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
     */
    public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
                                                                    IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

            super(tradeView);

            setTradeOverlay(tradeOverlay);
            setWaitOverlay(waitOverlay);
            setAcceptOverlay(acceptOverlay);
            ClientModelSupplier.getInstance().addObserver(this);
    }

    public IDomesticTradeView getTradeView() {

            return (IDomesticTradeView)super.getView();
    }

    public IDomesticTradeOverlay getTradeOverlay() {
            return tradeOverlay;
    }

    public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
            this.tradeOverlay = tradeOverlay;
    }

    public IWaitView getWaitOverlay() {
            return waitOverlay;
    }

    public void setWaitOverlay(IWaitView waitView) {
            this.waitOverlay = waitView;
    }

    public IAcceptTradeOverlay getAcceptOverlay() {
            return acceptOverlay;
    }

    public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
            this.acceptOverlay = acceptOverlay;
    }

    @Override
    public void startTrade() {
        PlayerIdx playerIdx = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        setPlayers(playerIdx);
        trade = new TradeOffer(playerIdx, null, new ResourceCards(0,0,0,0,0));
        
        this.tradeOverlay.hideUpDownArrows(ResourceType.WOOD);
        this.tradeOverlay.hideUpDownArrows(ResourceType.BRICK);
        this.tradeOverlay.hideUpDownArrows(ResourceType.ORE);
        this.tradeOverlay.hideUpDownArrows(ResourceType.SHEEP);
        this.tradeOverlay.hideUpDownArrows(ResourceType.WHEAT);
        getTradeOverlay().showModal();
    }
    
    private void setPlayers(PlayerIdx playerIdx)
    {
        Player[] players = ClientModelSupplier.getInstance().getModel().getPlayers().toArray(new Player[4]);
        ArrayList<PlayerInfo> playerInfos = new ArrayList();
        for (int i = 0; i < players.length; i++)
        {
            
            if (playerIdx.getIndex() != i)
            {
                Player player = players[i];
                playerInfos.add(new PlayerInfo());
                playerInfos.get(playerInfos.size() - 1).setId(player.getPlayerId());
                playerInfos.get(playerInfos.size() - 1).setPlayerIndex(player.getPlayerIndex().getIndex());
                playerInfos.get(playerInfos.size() - 1).setName(player.getName());
                playerInfos.get(playerInfos.size() - 1).setColor(player.getColor()); 
            }
        }
        PlayerInfo[] newPlayerInfos = new PlayerInfo[3];
        this.tradeOverlay.resetPlayers(playerInfos.toArray(newPlayerInfos));
        this.tradeOverlay.reset();
    }

    @Override
    public void decreaseResourceAmount(ResourceType resource) {
        switch (resource) {
            case WOOD:
                if (tradeWood == 1)
                {
                    decreaseTradeResource(resource);
                }
                else
                {
                    decreaseReceiveResource(resource);
                }
                break;
            case BRICK:
                if (tradeBrick == 1)
                {
                    decreaseTradeResource(resource);
                }
                else
                {
                    decreaseReceiveResource(resource);
                }
                break;
            case SHEEP:
                if (tradeSheep == 1)
                {
                    decreaseTradeResource(resource);
                }
                else
                {
                    decreaseReceiveResource(resource);
                }
                break;
            case ORE:
                if (tradeOre == 1)
                {
                    decreaseTradeResource(resource);
                }
                else
                {
                    decreaseReceiveResource(resource);
                }
                break;
            case WHEAT:
                if (tradeWheat == 1)
                {
                    decreaseTradeResource(resource);
                }
                else
                {
                    decreaseReceiveResource(resource);
                }
                break;
        }
        if (!sending || !receiving)
        {
            // message says choose your trade
        }
    }

    @Override
    public void increaseResourceAmount(ResourceType resource) {
        ResourceCards playerCards = ClientModelSupplier.getInstance().getClientPlayerObject().getHand().getResourceCards();
        switch (resource) {
            case WOOD:
                if (tradeWood == 1)
                {
                    increaseTradeResource(resource, playerCards);
                }
                else
                {
                    increaseReceiveResource(resource);
                }
                break;
            case BRICK:
                if (tradeBrick == 1)
                {
                    increaseTradeResource(resource, playerCards);
                }
                else
                {
                    increaseReceiveResource(resource);
                }
                break;
            case SHEEP:
                if (tradeSheep == 1)
                {
                    increaseTradeResource(resource, playerCards);
                }
                else
                {
                    increaseReceiveResource(resource);
                }
                break;
            case ORE:
                if (tradeOre == 1)
                {
                    increaseTradeResource(resource, playerCards);
                }
                else
                {
                    increaseReceiveResource(resource);
                }
                break;
            case WHEAT:
                if (tradeWheat == 1)
                {
                    increaseTradeResource(resource, playerCards);
                }
                else
                {
                    increaseReceiveResource(resource);
                }
                break;
        }
        
        if (sending && receiving)
        {
            // message says choose your partner or whatever
        }
    }

    @Override
    public void sendTradeOffer() {
        OfferTradeRequest request = new OfferTradeRequest(trade.getSenderNumber(), 
                trade.getResourceCards(), trade.getReceiverNumber().getIndex());
        
        try 
        {
            getTradeOverlay().closeModal();
            getWaitOverlay().showModal();
            TradeServerOperationsManager manager = (TradeServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TradeServerOperationsManager.class);
            
            manager.offerTrade(request);
            
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClientException ex) {
            Logger.getLogger(DomesticTradeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setPlayerToTradeWith(int playerIndex) {
        if (playerIndex > -1)
        {
            // set the player index in the trade offer
            trade.setReceiverNumber(new PlayerIdx(playerIndex));
            personToAsk = true;
            updateEnableTrade();
        }
        else
        {
          personToAsk = false;
          updateEnableTrade();
        }
    }

    @Override
    public void setResourceToReceive(ResourceType resource) {
        switch (resource) {
            case WOOD:
                tradeWood = -1;
                break;
            case BRICK:
                tradeBrick = -1;
                break;
            case SHEEP:
                tradeSheep = -1;
                break;
            case ORE:
                tradeOre = -1;
                break;
            case WHEAT:
                tradeWheat = -1;
                break;
        }
        
        // makes the view show the increase buttons
        this.tradeOverlay.setResourceAmount(resource, "0");
        tradeOverlay.setResourceAmountChangeEnabled(resource, true, false);
    }

    @Override
    public void setResourceToSend(ResourceType resource) {
        switch (resource) {
            case WOOD:
                tradeWood = 1;
                break;
            case BRICK:
                tradeBrick = 1;
                break;
            case SHEEP:
                tradeSheep = 1;
                break;
            case ORE:
                tradeOre = 1;
                break;
            case WHEAT:
                tradeWheat = 1;
                break;
        }
        
        // get current client model
        this.tradeOverlay.setResourceAmount(resource, "0");
        ClientModelSupplier clientModelSupplier = ClientModelSupplier.getInstance();
        ResourceCards playerCards = clientModelSupplier.getClientPlayerObject().getHand().getResourceCards();
 
        // if the player has none of that resource, the up arrow is disabled
        if (playerCardLimit(resource, playerCards, 0))
        {
            tradeOverlay.setResourceAmountChangeEnabled(resource, false, false);
            return;
        }
        tradeOverlay.setResourceAmountChangeEnabled(resource, true, false);
    }

    @Override
    public void unsetResource(ResourceType resource) {
        switch (resource) {
            case WOOD:
                tradeWood = 0;
                trade.getResourceCards().setLumber(0);
                this.tradeOverlay.setResourceAmount(resource, "0");
                break;
            case BRICK:
                tradeBrick = 0;
                trade.getResourceCards().setBrick(0);
                this.tradeOverlay.setResourceAmount(resource, "0");
                break;
            case SHEEP:
                tradeSheep = 0;
                trade.getResourceCards().setWool(0);
                this.tradeOverlay.setResourceAmount(resource, "0");
                break;
            case ORE:
                tradeOre = 0;
                trade.getResourceCards().setOre(0);
                this.tradeOverlay.setResourceAmount(resource, "0");
                break;
            case WHEAT:
                tradeWheat = 0;
                trade.getResourceCards().setGrain(0);
                this.tradeOverlay.setResourceAmount(resource, "0");
                break;
        }
        if (tradeWood < 1 && tradeBrick < 1 && tradeSheep < 1 && tradeOre < 1 && tradeWheat < 1)
        {
            sending = false;
        }
        if (tradeWood > -1 && tradeBrick > -1 && tradeSheep > -1 && tradeOre > -1 && tradeWheat > -1)
        {
            receiving = false;
        }
        updateEnableTrade();
    }

    @Override
    public void cancelTrade() {
        trade = null;
        tradeWood = 0;
        tradeBrick = 0;
        tradeOre = 0;
        tradeWheat = 0;
        tradeSheep = 0;
        sending = false;
        receiving = false;
        personToAsk = false;
        getTradeOverlay().reset();
        getTradeOverlay().closeModal();
    }

    @Override
    public void acceptTrade(boolean willAccept) {
        PlayerIdx index = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        AcceptTradeRequest request = new AcceptTradeRequest(index, willAccept);
        
        try {
            getAcceptOverlay().reset();
            getAcceptOverlay().closeModal();
            TradeServerOperationsManager manager = (TradeServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TradeServerOperationsManager.class);
            manager.acceptTrade(request);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClientException ex) {
            Logger.getLogger(DomesticTradeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        // update if you get a trade
        ClientModel model = ClientModelSupplier.getInstance().getModel();
        if (model != null)
        {   
            this.getTradeView().enableDomesticTrade(false);
            int currentTurn = model.getTurnTracker().getCurrentTurn().getIndex();
            int clientPlayer = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex().getIndex();
            if (currentTurn == clientPlayer)
            {
                this.getTradeView().enableDomesticTrade(true);
            }
            TradeOffer currentTrade = ClientModelSupplier.getInstance().getModel().getTradeOffer();
            
            if (currentTrade == null && trade != null)
            {
                getWaitOverlay().closeModal();
                cancelTrade();
            }
            else if (currentTrade != null)
            {
                if (currentTrade.getReceiverNumber().getIndex() == ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex().getIndex())
                {
                    ResourceCards resourceCards = currentTrade.getResourceCards();
                    String offerer = getPlayerNameByIndex(currentTrade.getSenderNumber().getIndex());
                    createAcceptOverlay(resourceCards, offerer);
                }
            }           
        }
    }
    
    private String getPlayerNameByIndex(int index)
    {
        Collection<Player> players = ClientModelSupplier.getInstance().getModel().getPlayers();
        for (Player player : players)
        {
            if (player.getPlayerIndex().getIndex() == index)
            {
                return player.getName();
            }
        }
        return null;
    }
    
    private void createAcceptOverlay(ResourceCards resourceCards, String offerer)
    {
        getAcceptOverlay().setAcceptEnabled(true);
        interpretTradeOffer(resourceCards.getLumber(), ResourceType.WOOD);
        interpretTradeOffer(resourceCards.getBrick(), ResourceType.BRICK);
        interpretTradeOffer(resourceCards.getGrain(), ResourceType.WHEAT);
        interpretTradeOffer(resourceCards.getWool(), ResourceType.SHEEP);
        interpretTradeOffer(resourceCards.getOre(), ResourceType.ORE);
        getAcceptOverlay().setPlayerName(offerer);
        getAcceptOverlay().showModal();
    }
    
    private void interpretTradeOffer(int resourceAmount, ResourceType resource)
    {
        if (resourceAmount < 0)
        {
            // Player will give this resource
            getAcceptOverlay().addGiveResource(resource, Math.abs(resourceAmount));
            getAcceptOverlay().setAcceptEnabled(hasResourceToTrade(resource, Math.abs(resourceAmount)));
        }
        else if (resourceAmount > 0)
        {
            // Player will receive this resource
            getAcceptOverlay().addGetResource(resource, resourceAmount);
        }
    }
    
    private boolean hasResourceToTrade(ResourceType resource, int resourceAmount)
    {
        ResourceCards playerCards = ClientModelSupplier.getInstance().getClientPlayerObject().getHand().getResourceCards();
        switch (resource)
        {
            case BRICK:
                if (playerCards.getBrick() >= resourceAmount)
                {
                    return true;
                }
                break;
            case ORE:
                if (playerCards.getOre() >= resourceAmount)
                {
                    return true;
                }
                break;
            case SHEEP:
                if (playerCards.getWool() >= resourceAmount)
                {
                    return true;
                }
                break;
            case WHEAT:
                if (playerCards.getGrain() >= resourceAmount)
                {
                    return true;
                }
                break;
            case WOOD:
                if (playerCards.getLumber() >= resourceAmount)
                {
                    return true;
                }
                break;
        }
        return false;
    }
    
    private void decreaseTradeResource(ResourceType resource)
    {
        // decrease the given resource by one
        int change = changeAmountOfCards(resource, -1);
        // show the decrease in the overlay
        this.tradeOverlay.setResourceAmount(resource, Integer.toString(change));
        
        this.tradeOverlay.setResourceAmountChangeEnabled(resource, true, true);
        // if the resource is now zero
        if (change == 0)
        {
            this.tradeOverlay.setResourceAmountChangeEnabled(resource, true, false);
            this.sending = false;
        }
    }
    
    private void increaseTradeResource(ResourceType resource, ResourceCards playerCards)
    {
        // increase the given resource by one
        int change = changeAmountOfCards(resource, 1);
        
        // show the increase in the overlay
        this.tradeOverlay.setResourceAmount(resource, Integer.toString(change));
        this.tradeOverlay.setResourceAmountChangeEnabled(resource, true, true);
        
        // if the resource is now reached its limit
        if (playerCardLimit(resource, playerCards, change))
        {
            this.tradeOverlay.setResourceAmountChangeEnabled(resource, false, true);
        }
        sending = true;
        updateEnableTrade();
    }
    
    private void decreaseReceiveResource(ResourceType resource)
    {
        // decrease the given resource by one
        int change = changeAmountOfCards(resource, 1);
        // show the decrease in the overlay
        this.tradeOverlay.setResourceAmount(resource, Integer.toString(Math.abs(change)));
        
        // if the resource is now zero
        if (change == 0)
        {
            this.tradeOverlay.setResourceAmountChangeEnabled(resource, true, false);
            this.receiving = false;
        }
    }
    
    private void increaseReceiveResource(ResourceType resource)
    {
        // decrease the given resource by one
        int change = changeAmountOfCards(resource, -1);
        this.receiving = true;
        // show the decrease in the overlay
        if (Math.abs(change) == 1)
        {
            this.tradeOverlay.setResourceAmountChangeEnabled(resource, true, true);
        }
        this.tradeOverlay.setResourceAmount(resource, Integer.toString(Math.abs(change)));
        receiving = true;
        updateEnableTrade();
    }
    
    private int changeAmountOfCards(ResourceType resource, int amount)
    {
        int change = 0;
        switch (resource) {
            case WOOD:
                change = trade.getResourceCards().getLumber() + amount;
                trade.getResourceCards().setLumber(change);
                break;
            case BRICK:
                change = trade.getResourceCards().getBrick() + amount;
                trade.getResourceCards().setBrick(change);
                break;
            case SHEEP:
                change = trade.getResourceCards().getWool() + amount;
                trade.getResourceCards().setWool(change);
                break;
            case ORE:
                change = trade.getResourceCards().getOre() + amount;
                trade.getResourceCards().setOre(change);
                break;
            case WHEAT:
                change = trade.getResourceCards().getGrain() + amount;
                trade.getResourceCards().setGrain(change);
                break;
        }
        return change;
    }
    
    private boolean playerCardLimit(ResourceType resource, ResourceCards playerCards, int amount)
    {
        boolean limit = false;
        switch (resource) {
            case WOOD:
                if (playerCards.getLumber() == amount)
                {
                    limit = true;
                }
                break;
            case BRICK:
                if (playerCards.getBrick() == amount)
                {
                    limit = true;
                }
                break;
            case SHEEP:
                if (playerCards.getWool() == amount)
                {
                    limit = true;
                }
                break;
            case ORE:
                if (playerCards.getOre() == amount)
                {
                    limit = true;
                }
                break;
            case WHEAT:
                if (playerCards.getGrain() == amount)
                {
                    limit = true;
                }
                break;
        }
        return limit;
    }
    
    private void updateEnableTrade()
    {
        if (sending)
        {
            if (receiving)
            {
                if (personToAsk)
                {
                    this.tradeOverlay.setTradeEnabled(true);
                    this.tradeOverlay.setStateMessage("Trade!");
                    return;
                }
                this.tradeOverlay.setTradeEnabled(false);
                this.tradeOverlay.setStateMessage("choose with whom you want to trade");
                return;
            }
            this.tradeOverlay.setTradeEnabled(false);
            this.tradeOverlay.setStateMessage("select the resources you want to trade");
            return;
        }
        this.tradeOverlay.setTradeEnabled(false);
        this.tradeOverlay.setStateMessage("select the resources you want to trade");
    }

}

