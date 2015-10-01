package guicommunicator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import model.ClientModel;
import model.ClientModelSupplier;
import model.map.CatanMap;
import model.map.EdgeObject;
import model.map.Hex;
import model.map.VertexObject;
import model.player.NullablePlayerIdx;
import model.player.Player;
import model.player.PlayerIdx;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * The mediator between the client GUI controllers and the model classes
 * There are more than one mediator; this Facade is specifically for the
 * map model classes
 * @author Scott Hampton
 */
public class MapModelFacade
{
    // private ClientModel currentModel;

    /**
     * The constructor for the MapModelFacade
     */
    public MapModelFacade() { }

    /**
     * Using the given edgeLocation, determines whether
     * the player can place a road on that edge
     * @param edgeLocation
     * @return boolean
     */
    public boolean canPlaceRoad(EdgeLocation location)
    {
        
        location = location.getNormalizedLocation();
        CatanMap map = this.getCurrentMap();
        Map<VertexLocation, VertexObject> allObjects = this.getAllVertexObjects(map);
        PlayerIdx currentPlayer = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        
        boolean doesBlockWest = false;
        boolean doesBlockEast = false;
        
        VertexLocation westVertex, eastVertex;
        
        Set<EdgeLocation> neededRoadLocations = new HashSet<>();
        NullablePlayerIdx westOwner, eastOwner;
        if (location.getDir().equals(EdgeDirection.North))
        {
            westVertex = new VertexLocation(location.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation();
            eastVertex = new VertexLocation(location.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation();
            eastOwner = this.getOwner(allObjects, eastVertex);
            westOwner = this.getOwner(allObjects, westVertex);
            if(westOwner.equals(currentPlayer) || eastOwner.equals(currentPlayer))
                return true;
            doesBlockWest = westOwner.isNotNull();
            doesBlockEast = eastOwner.isNotNull();
            if(!doesBlockWest)
            {
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.NorthWest).getNormalizedLocation());
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.North), EdgeDirection.SouthWest).getNormalizedLocation());
            }
            if(!doesBlockEast)
            {
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.NorthEast).getNormalizedLocation()); 
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.North), EdgeDirection.SouthEast).getNormalizedLocation());
            }
        }
        else if(location.getDir().equals(EdgeDirection.NorthWest))
        {
            westVertex = new VertexLocation(location.getHexLoc(), VertexDirection.West).getNormalizedLocation();
            eastVertex = new VertexLocation(location.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation();
            eastOwner = this.getOwner(allObjects, eastVertex);
            westOwner = this.getOwner(allObjects, westVertex);
            if(westOwner.equals(currentPlayer) || eastOwner.equals(currentPlayer))
                return true;
            doesBlockWest = westOwner.isNotNull();
            doesBlockEast = eastOwner.isNotNull();
            if(!doesBlockWest)
            {
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.SouthWest).getNormalizedLocation());
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.South).getNormalizedLocation());
            }
            if(!doesBlockEast)
            {
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.North).getNormalizedLocation());
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast).getNormalizedLocation());
            }
        }
        else //NorthEast
        {
            westVertex = new VertexLocation(location.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation();
            eastVertex = new VertexLocation(location.getHexLoc(), VertexDirection.East).getNormalizedLocation();
            eastOwner = this.getOwner(allObjects, eastVertex);
            westOwner = this.getOwner(allObjects, westVertex);
            if(westOwner.equals(currentPlayer) || eastOwner.equals(currentPlayer))
                return true;
            doesBlockWest = westOwner.isNotNull();
            doesBlockEast = eastOwner.isNotNull();
            if(!doesBlockWest)
            {
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.North).getNormalizedLocation());
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest).getNormalizedLocation());
            }
            if(!doesBlockEast)
            {
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.South).getNormalizedLocation());
                neededRoadLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.SouthEast).getNormalizedLocation());
            }
        }
        
        if(map.getRoads() == null)
            return false;
        if(neededRoadLocations.isEmpty())
            return false;
        Iterator<EdgeObject> roadItr = map.getRoads().iterator();
        boolean hasConnector = false;
        while(roadItr.hasNext())
        {
            EdgeObject road = roadItr.next();
            if(road.getLocation().getNormalizedLocation().equals(location))
                return false;
            else if(road.getOwner().equals(currentPlayer) && neededRoadLocations.contains(road.getLocation().getNormalizedLocation()))
                hasConnector = true;
        }
        return hasConnector;
    }

    /**
     * Using the given vertexLocation, determines whether
     * the player can place a settlement on that hex point
     * @param vertexLocation
     * @return boolean
     */
    public boolean canPlaceSettlement(VertexLocation location)
    {
        location = location.getNormalizedLocation();
        CatanMap currentMap = this.getCurrentMap();
        Set<VertexLocation> vertexLocations = new HashSet<>();
        //is there already something here
        if(currentMap.getCities() != null)
            for(VertexObject obj : currentMap.getCities())
                vertexLocations.add(obj.getLocation().getNormalizedLocation());
        if(currentMap.getSettlements() != null)
            for(VertexObject obj : currentMap.getSettlements())
                vertexLocations.add(obj.getLocation().getNormalizedLocation());

        //Is there a vertex between this one and another piece
        Set<VertexLocation> toBeTested = new HashSet<>();
        toBeTested.add(new VertexLocation(location.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation());
        toBeTested.add(new VertexLocation(location.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation());
        if(location.getDir().equals(VertexDirection.NorthEast))
        {
            toBeTested.add(new VertexLocation(location.getHexLoc(), VertexDirection.East).getNormalizedLocation());
            toBeTested.add(new VertexLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), VertexDirection.NorthWest).getNormalizedLocation());
        }
        else //Northwest
        {
            toBeTested.add(new VertexLocation(location.getHexLoc(), VertexDirection.West).getNormalizedLocation());
            toBeTested.add(new VertexLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), VertexDirection.NorthEast).getNormalizedLocation());
        }
        //test it
        if(vertexLocations.removeAll(toBeTested)) //returns true if vertexLocations changed
            return false;
        
        //do I have a road connecting here
        Set<EdgeLocation> edgeLocations = new HashSet<>();
        PlayerIdx currentPlayerIdx = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        edgeLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.North).getNormalizedLocation());
        if(location.getDir().equals(VertexDirection.NorthEast))
        {
            edgeLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.NorthEast).getNormalizedLocation());
            edgeLocations.add(new EdgeLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.SouthWest).getNormalizedLocation());
        }
        else
        {
            edgeLocations.add(new EdgeLocation(location.getHexLoc(), EdgeDirection.NorthWest).getNormalizedLocation());
            edgeLocations.add(new EdgeLocation(location.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.SouthEast).getNormalizedLocation());
        }
        
        for(EdgeObject road: currentMap.getRoads())
            if(edgeLocations.contains(road.getLocation().getNormalizedLocation()))
                if(road.getOwner().equals(currentPlayerIdx))
                    return true;
        return false;
    }

    /**
     * Using the given vertexLocation, determines whether
     * the player can place a city on that hex point
     * @param vertexLocation
     * @return boolean
     */
    public boolean canPlaceCity(VertexLocation location)
    {
        location = location.getNormalizedLocation();
        CatanMap currentMap = this.getCurrentMap();
        CatanColor pieceColor = null;
        PlayerIdx playerIndex = null;
        if(currentMap.getSettlements() == null)
            return false;
        for(VertexObject obj : currentMap.getSettlements())
            if(obj.getLocation().getNormalizedLocation().equals(location))
                playerIndex = obj.getOwner();
        if(playerIndex == null)
            return false;
        Iterator<Player> itr = ClientModelSupplier.getInstance().getModel().getPlayers().iterator();
        while(itr.hasNext())
        {
            Player possiblePlayer = itr.next();
            if(possiblePlayer.getPlayerIndex().equals(playerIndex))
                return ClientModelSupplier.getInstance().getClientPlayerObject().equals(possiblePlayer);
        }
        return false;
    }

    /**
     * Using the given hexLocation, determines whether
     * the player can place the robber on that hex
     * @param hexLocation
     * @return boolean
     */
    public boolean canPlaceRobber(HexLocation location)
    {
        //The robber must be moved. You may not choose to leave the robber in the same hex. 
        //Can be placed on any hex
        CatanMap currentMap = this.getCurrentMap();
        if(location.equals(currentMap.getRobber()))
            return false;
        Hex hexOfDesiredRobberLocation = null;
        for(Hex current: currentMap.getHexes())
            if(current.getLocation().equals(location))
            {
                hexOfDesiredRobberLocation = current;
                break;
            }
        if(hexOfDesiredRobberLocation == null)
            return false;
        if(hexOfDesiredRobberLocation.getType().equals(HexType.WATER))
            return false;
        return true;
    }
    
    private CatanMap getCurrentMap()
    {
        ClientModel model = ClientModelSupplier.getInstance().getModel();
        if(model == null)
            throw new IllegalStateException();
        return model.getMap();
    }
    
    private Map<VertexLocation, VertexObject> getAllVertexObjects(CatanMap currentMap)
    {
        Map<VertexLocation, VertexObject> vertexLocations = new HashMap<>();
        if(currentMap.getCities() != null)
            for(VertexObject obj : currentMap.getCities())
                vertexLocations.put(obj.getLocation().getNormalizedLocation(), obj);
        if(currentMap.getSettlements() != null)
            for(VertexObject obj : currentMap.getSettlements())
                vertexLocations.put(obj.getLocation().getNormalizedLocation(), obj);
        return vertexLocations;
    }
    
    private NullablePlayerIdx getOwner(Map<VertexLocation, VertexObject> map, VertexLocation key)
    {
        if(map.containsKey(key))
            return map.get(key).getOwner();
        else
            return new NullablePlayerIdx(-1);
    }
}