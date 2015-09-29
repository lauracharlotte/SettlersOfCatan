package guicommunicator;

import java.util.Iterator;
import model.ClientModel;
import model.ClientModelSupplier;
import model.map.CatanMap;
import model.map.Hex;
import model.map.VertexObject;
import model.player.Player;
import model.player.PlayerIdx;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
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
        return true;
    }

    /**
     * Using the given vertexLocation, determines whether
     * the player can place a settlement on that hex point
     * @param vertexLocation
     * @return boolean
     */
    public boolean canPlaceSettlement(VertexLocation location)
    {
        return true;
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
        for(VertexObject obj : currentMap.getSettlements())
            if(obj.getLocation().equals(location))
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
}