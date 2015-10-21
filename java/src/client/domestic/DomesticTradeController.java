package client.domestic;

import shared.definitions.*;
import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
        this.tradeOverlay.hideUpDownArrows(ResourceType.WOOD);
        this.tradeOverlay.hideUpDownArrows(ResourceType.BRICK);
        this.tradeOverlay.hideUpDownArrows(ResourceType.ORE);
        this.tradeOverlay.hideUpDownArrows(ResourceType.SHEEP);
        this.tradeOverlay.hideUpDownArrows(ResourceType.WHEAT);
        
        Player[] players = ClientModelSupplier.getInstance().getModel().getPlayers().toArray(new Player[4]);
        ArrayList<PlayerInfo> playerInfos = new ArrayList();
        PlayerIdx playerIdx = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
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
        this.tradeOverlay.setPlayers(playerInfos.toArray(newPlayerInfos));
        ResourceCards resourceCards = new ResourceCards(0,0,0,0,0);
        trade = new TradeOffer(playerIdx, null, resourceCards);
        
        getTradeOverlay().showModal();
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
        // send the actual request
            getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
    }

    @Override
    public void setPlayerToTradeWith(int playerIndex) {        
        // set the player index in the trade offer
        trade.setReceiverNumber(new PlayerIdx(playerIndex));
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
        ClientModelSupplier clientModelSupplier = ClientModelSupplier.getInstance();
        ResourceCards playerCards = clientModelSupplier.getClientPlayerObject().getHand().getResourceCards();
 
        // if the player has none of that resource, the up arrow is disabled
        if (!playerCardLimit(resource, playerCards, 0))
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
    }

    @Override
    public void cancelTrade() {
        trade = null;
        getTradeOverlay().closeModal();
    }

    @Override
    public void acceptTrade(boolean willAccept) {

        getAcceptOverlay().closeModal();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void decreaseTradeResource(ResourceType resource)
    {
        // decrease the given resource by one
        int change = changeAmountOfCards(resource, -1);
        // show the decrease in the overlay
        this.tradeOverlay.setResourceAmount(resource, Integer.toString(change));
        
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
        this.sending = true;
        
        // if the resource is now reached its limit
        if (!playerCardLimit(resource, playerCards, change))
        {
            this.tradeOverlay.setResourceAmountChangeEnabled(resource, false, true);
        }
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

}

