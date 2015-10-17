package client.maritime;

import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import java.util.Observable;
import java.util.Observer;

import model.player.PlayerIdx;
import model.ClientModelSupplier;
import model.map.CatanMap;
import clientcommunicator.operations.MaritimeTradeRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        getTradeOverlay().showModal();
        this.tradeOverlay.showGiveOptions(enabledResources);
    }

    @Override
    public void makeTrade() {
        MaritimeTradeRequest trade = new MaritimeTradeRequest(
                playerIdx, 
                ratio, 
                inputResource, 
                outputResource);
//       TODO: Make trade 
        getTradeOverlay().closeModal();
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
        this.tradeOverlay.selectGetOption(resource, ratio);
        this.tradeOverlay.setTradeEnabled(true);
        this.tradeOverlay.setStateMessage("Do you want to trade?");
    }

    @Override
    public void setGiveResource(ResourceType resource) {
        inputResource = resource;
        ratio = ratios.get(resource);
        this.tradeOverlay.selectGiveOption(resource, ratio);
        this.tradeOverlay.setStateMessage("Choose what to get");
    }

    @Override
    public void unsetGetValue() {
        outputResource = null;
        this.tradeOverlay.setTradeEnabled(false);
        this.tradeOverlay.setStateMessage("Choose what to get");
        this.tradeOverlay.showGetOptions(enabledResources);
    }

    @Override
    public void unsetGiveValue() {
        inputResource = null;
        ratio = 0;
        this.tradeOverlay.reset();
        this.tradeOverlay.setStateMessage("Choose what to give up");
    }

    @Override
    public void update(Observable o, Object arg)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private ResourceType[] getAvailablePorts()
    {
        ArrayList<ResourceType> resources = new ArrayList();
        CatanMap map = ClientModelSupplier.getInstance().getModel().getMap();
        ArrayList<Port> ports = (ArrayList<Port>) map.getPorts();
        ArrayList<VertexObject> settlements = (ArrayList<VertexObject>) map.getSettlements();
        for (int i = 0; i < settlements.size(); i++)
        {
            VertexObject settlement = settlements.get(i);
            if (settlement.getOwner().getIndex() == playerIdx.getIndex())
            {
                for (int j = 0; j < ports.size(); j++)
                {
                    Port port = ports.get(j);
                    VertexObject[] portPlaces = getPortVertexObjects(port);
                    // if the player's settlement touches that port
                    if (portPlaces[0].equals(settlement) || portPlaces[1].equals(settlement))
                    {
                        resources.add(port.getResource());
                        getRatio(port);
                    }
                }
            }
        }
        return resources.toArray(new ResourceType[resources.size()]);
    }
    
    private VertexObject[] getPortVertexObjects(Port port)
    {
        VertexObject[] vertices = new VertexObject[2];
        HexLocation hexLocation = port.getHex();
        EdgeDirection edgeDirection = port.getDirection();
        VertexDirection[] vertexDirection = convertEdgeDirToVertexDir(edgeDirection);
        vertices[0] = new VertexObject(new VertexLocation(hexLocation, vertexDirection[0]).getNormalizedLocation(), playerIdx);
        vertices[1] = new VertexObject(new VertexLocation(hexLocation, vertexDirection[1]).getNormalizedLocation(), playerIdx);
        return vertices;
    }
    
    private VertexDirection[] convertEdgeDirToVertexDir(EdgeDirection edgeDirection)
    {
        VertexDirection[] vertexDirections = new VertexDirection[2];
        switch(edgeDirection)
        {
            case North:
                vertexDirections[0] = VertexDirection.NorthEast;
                vertexDirections[1] = VertexDirection.NorthWest;
                break;
            case NorthWest:
                vertexDirections[0] = VertexDirection.West;
                vertexDirections[1] = VertexDirection.NorthWest;
                break;
            case NorthEast:
                vertexDirections[0] = VertexDirection.NorthEast;
                vertexDirections[1] = VertexDirection.East;
                break;
            case South:
                vertexDirections[0] = VertexDirection.SouthEast;
                vertexDirections[1] = VertexDirection.SouthWest;
                break;
            case SouthWest:
                vertexDirections[0] = VertexDirection.West;
                vertexDirections[1] = VertexDirection.SouthWest;
                break;
            case SouthEast:
                vertexDirections[0] = VertexDirection.SouthEast;
                vertexDirections[1] = VertexDirection.East;
                break;
        }
        return vertexDirections;
    }
    
    private void getRatio(Port port)
    {
        int portRatio = port.getRatio();
        ResourceType portResource = port.getResource();
        if (!ratios.containsKey(portResource) || ratios.get(portResource) > portRatio)
        {
            ratios.put(portResource, portRatio);
        }
    }

}

