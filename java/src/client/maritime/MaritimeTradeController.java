package client.maritime;

import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TradeServerOperationsManager;
import java.util.Observable;
import java.util.Observer;

import model.player.PlayerIdx;
import model.ClientModelSupplier;
import model.map.CatanMap;
import clientcommunicator.operations.MaritimeTradeRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModel;
import model.cards.ResourceCards;
import model.map.Port;
import model.map.VertexObject;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer 
{

    private IMaritimeTradeOverlay tradeOverlay;
    private PlayerIdx playerIdx;
    private int ratio;
    private ResourceType inputResource;
    private ResourceType outputResource;
    private ResourceType[] enabledResources;
    private Map<ResourceType, Integer> ratios;

    public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {

            super(tradeView);

            setTradeOverlay(tradeOverlay);
            ClientModelSupplier.getInstance().addObserver(this);
    }

    public IMaritimeTradeView getTradeView() {

            return (IMaritimeTradeView)super.getView();
    }

    public IMaritimeTradeOverlay getTradeOverlay() {
            return tradeOverlay;
    }

    public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
            this.tradeOverlay = tradeOverlay;
    }

    @Override
    public void startTrade() {
        playerIdx = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        ratios = new HashMap();
        enabledResources = getAvailablePorts();
        this.tradeOverlay.setTradeEnabled(false);
        this.tradeOverlay.showGiveOptions(enabledResources);
        getTradeOverlay().showModal();   
    }

    @Override
    public void makeTrade() {
        MaritimeTradeRequest request = new MaritimeTradeRequest(
                playerIdx, 
                ratio, 
                inputResource, 
                outputResource);  
        try {
            cancelTrade();
            TradeServerOperationsManager manager = (TradeServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TradeServerOperationsManager.class);
            manager.maritimeTrade(request);
        } catch (ClientException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(MaritimeTradeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void cancelTrade() {
        playerIdx = null;
        ratio = 0;
        inputResource = null;
        outputResource = null;
        enabledResources = null;
        ratios = new HashMap();
        this.tradeOverlay.reset();
        getTradeOverlay().closeModal();
    }

    @Override
    public void setGetResource(ResourceType resource) {
        outputResource = resource;
        this.tradeOverlay.selectGetOption(resource, 1);
        this.tradeOverlay.setTradeEnabled(true);
    }

    @Override
    public void setGiveResource(ResourceType resource) {
        inputResource = resource;
        ratio = ratios.get(resource);
        this.tradeOverlay.selectGiveOption(resource, ratio);
        this.tradeOverlay.showGetOptions(getAvailableResources());
    }

    @Override
    public void unsetGetValue() {
        outputResource = null;
        this.tradeOverlay.setTradeEnabled(false);
        this.tradeOverlay.showGetOptions(getAvailableResources());
    }

    @Override
    public void unsetGiveValue() {
        inputResource = null;
        ratio = 0;
        this.tradeOverlay.setTradeEnabled(false);
        this.tradeOverlay.hideGetOptions();
        this.tradeOverlay.showGiveOptions(enabledResources);
        this.tradeOverlay.reset();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        ClientModel model = ClientModelSupplier.getInstance().getModel();
        if (model != null)
        { 
            this.getTradeView().enableMaritimeTrade(false);
            int currentTurn = model.getTurnTracker().getCurrentTurn().getIndex();
            int clientPlayer = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex().getIndex();
            if (currentTurn == clientPlayer)
            {
                this.getTradeView().enableMaritimeTrade(true);
            }
        }
    }
    
    private ResourceType[] getAvailablePorts()
    {
        ArrayList<ResourceType> resources = new ArrayList();
        CatanMap map = ClientModelSupplier.getInstance().getModel().getMap();
        ArrayList<Port> ports = (ArrayList<Port>) map.getPorts();
        ArrayList<VertexObject> settlements = (ArrayList<VertexObject>) map.getSettlements();
        settlements.addAll(map.getCities());
        ResourceCards playerCards = ClientModelSupplier.getInstance().getClientPlayerObject().getHand().getResourceCards();
        resources = getFourTrade(resources, playerCards);
        for (VertexObject settlement : settlements)
        {
            if (settlement.getOwner().getIndex() == playerIdx.getIndex())
            {
                for (int j = 0; j < ports.size(); j++)
                {
                    Port port = ports.get(j);
                    VertexObject[] portPlaces = getPortVertexObjects(port);
                    // if the player's settlement touches that port
                    if (portPlaces[0].getLocation().equals(settlement.getLocation().getNormalizedLocation()) 
                            || portPlaces[1].getLocation().equals(settlement.getLocation().getNormalizedLocation()))
                    {
                        if (port.getResource() == null)
                        {
                            resources = dealWithWildPort(port, resources, playerCards);
                        }
                        else if (playerHasResource(playerCards, port.getResource(), port.getRatio()))
                        {
                            resources.add(port.getResource());
                            getRatio(port.getRatio(), port.getResource());
                        }
                    }
                }
            }
        }
        return resources.toArray(new ResourceType[resources.size()]);
    }
    
    private ArrayList<ResourceType> getFourTrade(ArrayList<ResourceType> resources, ResourceCards playerCards)
    {
        if (playerCards.getBrick() >= 4)
        {
            resources.add(ResourceType.BRICK);
            ratios.put(ResourceType.BRICK, 4);
        }
        if (playerCards.getOre() >= 4)
        {
            resources.add(ResourceType.ORE);
            ratios.put(ResourceType.ORE, 4);
        }
        if (playerCards.getWool() >= 4)
        {
            resources.add(ResourceType.SHEEP);
            ratios.put(ResourceType.SHEEP, 4);
        }
        if (playerCards.getGrain() >= 4)
        {
            resources.add(ResourceType.WHEAT);
            ratios.put(ResourceType.WHEAT, 4);
        }
        if (playerCards.getLumber() >= 4)
        {
            resources.add(ResourceType.WOOD);
            ratios.put(ResourceType.WOOD, 4);
        }
        return resources;
    }
    
    private VertexObject[] getPortVertexObjects(Port port)
    {
        VertexObject[] vertices = new VertexObject[2];
        HexLocation hexLocation = port.getHex();
        EdgeDirection edgeDirection = port.getDirection();
        VertexDirection[] vertexDirection = edgeDirection.convertEdgeDirToVertexDir();
        vertices[0] = new VertexObject(new VertexLocation(hexLocation, vertexDirection[0]).getNormalizedLocation(), playerIdx);
        vertices[1] = new VertexObject(new VertexLocation(hexLocation, vertexDirection[1]).getNormalizedLocation(), playerIdx);
        return vertices;
    }
    
    private void getRatio(int portRatio, ResourceType portResource)
    {
        if (!ratios.containsKey(portResource) || ratios.get(portResource) > portRatio)
        {
            ratios.put(portResource, portRatio);
        }
    }
    
    private ArrayList<ResourceType> dealWithWildPort(Port port, ArrayList<ResourceType> resources, ResourceCards playerCards)
    {
        if (playerHasResource(playerCards, ResourceType.BRICK, 3))
        {
            resources.add(ResourceType.BRICK);
            getRatio(3, ResourceType.BRICK);
        }
        
        if (playerHasResource(playerCards, ResourceType.ORE, 3))
        {
           resources.add(ResourceType.ORE);
            getRatio(3, ResourceType.ORE); 
        }

        if (playerHasResource(playerCards, ResourceType.SHEEP, 3))
        {
            resources.add(ResourceType.SHEEP);
            getRatio(3, ResourceType.SHEEP);
        }
        
        if (playerHasResource(playerCards, ResourceType.WHEAT, 3))
        {
            resources.add(ResourceType.WHEAT);
            getRatio(3, ResourceType.WHEAT);
        }
        
        if (playerHasResource(playerCards, ResourceType.WOOD, 3))
        {
            resources.add(ResourceType.WOOD);
            getRatio(3, ResourceType.WOOD);
        }
        
        return resources;
    }
    
    private boolean playerHasResource(ResourceCards playerCards, ResourceType resource, int portRatio)
    {
        switch (resource)
        {
            case BRICK:
                if (playerCards.getBrick() >= portRatio)
                {
                    return true;
                }
                break;
            case ORE:
                if (playerCards.getOre() >= portRatio)
                {
                    return true;
                }
                break;
            case SHEEP:
                if (playerCards.getWool() >= portRatio)
                {
                    return true;
                }
                break;
            case WHEAT:
                if (playerCards.getGrain() >= portRatio)
                {
                    return true;
                }
                break;
            case WOOD:
                if (playerCards.getLumber() >= portRatio)
                {
                    return true;
                }
                break; 
        }
        return false;
    }
    
    private ResourceType[] getAvailableResources(){
        ArrayList<ResourceType> resources = new ArrayList();
        ResourceCards bank = ClientModelSupplier.getInstance().getModel().getBank().getResourceCards();
        if (bank.getBrick() > 0){
            resources.add(ResourceType.BRICK);
        }
        if (bank.getOre() > 0){
            resources.add(ResourceType.ORE);
        }
        if (bank.getWool() > 0){
            resources.add(ResourceType.SHEEP);
        }
        if (bank.getGrain() > 0){
            resources.add(ResourceType.WHEAT);
        }
        if (bank.getLumber() > 0){
            resources.add(ResourceType.WOOD);
        }
        return resources.toArray(new ResourceType[resources.size()]);
    }

}

