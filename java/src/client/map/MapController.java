package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;
import model.ClientModel;
import model.ClientModelSupplier;
import model.map.CatanMap;
import model.map.EdgeObject;
import model.map.Hex;
import model.map.Port;
import model.map.VertexObject;
import model.player.Player;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer
{
    private IRobView robView;
	
    private IMapState currentState = new NotInGameState();
    
    private CatanColor myCatanColor;
    
    public MapController(IMapView view, IRobView robView) 
    {

        super(view);

        setRobView(robView);
        
        ClientModelSupplier.getInstance().addObserver(this);

    }

    public IMapView getView() 
    {

            return (IMapView)super.getView();
    }

    private IRobView getRobView() {
            return robView;
    }
    private void setRobView(IRobView robView) {
            this.robView = robView;
    }
    
    /**
     * all of this is hard coded info, you need to use these same 
     * functions and format to generate the map from your model
     */

    protected void initFromModel() 
    {
        ClientModel model = ClientModelSupplier.getInstance().getModel();
        CatanMap map = model.getMap();
        
        Collection<Hex> hexes = map.getHexes();
        Collection<EdgeObject> roads = map.getRoads();
        Collection<VertexObject> settlements = map.getSettlements();
        Collection<VertexObject> cities = map.getCities();
        Collection<Port> ports = map.getPorts();
        
        for (Hex hex: hexes)
        {
            getView().addHex(hex.getLocation(), hex.getType());
            if(hex.getNumber() != -1)
                getView().addNumber(hex.getLocation(), hex.getNumber());
        }
        
        for (EdgeObject road: roads)
            getView().placeRoad(road.getLocation(), ((Player)(model.getPlayers().toArray()[road.getOwner().getIndex()])).getColor());
        
        for (VertexObject settlement: settlements)
            getView().placeSettlement(settlement.getLocation(), ((Player)(model.getPlayers().toArray()[settlement.getOwner().getIndex()])).getColor());
        
        for (VertexObject city: cities)
            getView().placeCity(city.getLocation(), ((Player)(model.getPlayers().toArray()[city.getOwner().getIndex()])).getColor());
        
        for (Port port: ports)
            getView().addPort(new EdgeLocation(port.getHex(), port.getDirection()), port.getPortType());
        
        getView().placeRobber(map.getRobber());
        this.myCatanColor = ClientModelSupplier.getInstance().getClientPlayerObject().getColor();
    }





    //================================================================
    // For each of the following "Can" methods: call the appropriate canDo(0
    //For each "Do" send the request to the server and update all info as a result of the 
    //action (bank amounts, remaining settlements, map...)


    public boolean canPlaceRoad(EdgeLocation edgeLoc) 
    {
        return this.currentState.canPlaceRoad(edgeLoc);
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc) 
    {
        return this.currentState.canPlaceSettlement(vertLoc);
    }

    public boolean canPlaceCity(VertexLocation vertLoc) 
    {
        return this.currentState.canPlaceCity(vertLoc);
    }

    public boolean canPlaceRobber(HexLocation hexLoc) 
    {
        return this.currentState.canPlaceRobber(hexLoc);
    }

    public void placeRoad(EdgeLocation edgeLoc) 
    {
        this.currentState.placeRoad(edgeLoc);
    }

    public void placeSettlement(VertexLocation vertLoc) 
    {
        this.currentState.placeSettlement(vertLoc);
    }

    public void placeCity(VertexLocation vertLoc) 
    {
        this.currentState.placeCity(vertLoc);
    }

    public void placeRobber(HexLocation hexLoc) 
    {
        this.currentState.placeRobber(hexLoc);
        getRobView().showModal();
    }

    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) 
    {	
        getView().startDrop(pieceType, this.myCatanColor, !isFree);
    }

    public void cancelMove() 
    {
    }

    public void playSoldierCard() 
    {	

    }

    public void playRoadBuildingCard() 
    {	

    }

    public void robPlayer(RobPlayerInfo victim) 
    {	
        this.currentState.robPlayer(victim);
    }
	
    @Override
    public void update(Observable o, Object arg)
    {
        this.currentState = this.currentState.update(o, arg);
        this.currentState.render(this);
    }
	
}

