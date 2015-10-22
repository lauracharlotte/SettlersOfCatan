package client.map;

import client.data.RobPlayerInfo;
import java.util.Observable;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public interface IMapState 
{
    public IMapState update(Observable o, Object arg);
    
    public void render(MapController controller);
    
    public boolean canPlaceRoad(EdgeLocation edgeLoc);

    public boolean canPlaceSettlement(VertexLocation vertLoc);

    public boolean canPlaceCity(VertexLocation vertLoc);

    public boolean canPlaceRobber(HexLocation hexLoc);

    public void placeRoad(EdgeLocation edgeLoc);

    public void placeSettlement(VertexLocation vertLoc);

    public void placeCity(VertexLocation vertLoc);

    public void placeRobber(HexLocation hexLoc, MapController mapController);

    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, MapController controller);

    public void cancelMove();

    public void playSoldierCard();

    public void playRoadBuildingCard(MapController controller);

    public void robPlayer(RobPlayerInfo victim);
}
