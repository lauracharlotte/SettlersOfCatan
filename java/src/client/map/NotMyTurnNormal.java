/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.map;

import client.data.RobPlayerInfo;
import java.util.Observable;
import model.ClientModel;
import model.ClientModelSupplier;
import model.player.PlayerIdx;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
class NotMyTurnNormal implements IMapState
{

    PlayerIdx playerIndex;
    
    public NotMyTurnNormal()
    {
        this.playerIndex = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    }

    @Override
    public IMapState update(Observable o, Object arg)
    {
        //need to fix this!
        ClientModel model = (ClientModel)arg;
        if(model.getTurnTracker().getCurrentTurn().equals(this.playerIndex))
            return new MyTurnNormal();
        else
            return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(MapController controller)
    {
        controller.initFromModel();
    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return true;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void placeCity(VertexLocation vertLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void placeRobber(HexLocation hexLoc, MapController mapController)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void cancelMove()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playSoldierCard()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playRoadBuildingCard(MapController controller)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void robPlayer(RobPlayerInfo victim)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, MapController controller)
    {} //do nothing
    
}
