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
import model.player.TurnStatusEnumeration;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
class NotMyTurnSetup implements IMapState
{

    public NotMyTurnSetup()
    {
        this.playerIndex = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    }

    private PlayerIdx playerIndex;
    
    @Override
    public IMapState update(Observable o, Object arg)
    {
        ClientModel model;
        model = (ClientModel)arg;
        boolean isMyTurn = model.getTurnTracker().getCurrentTurn().equals(this.playerIndex);
        boolean isSetup = model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.firstround) ||
                model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.secondround);
        if (isMyTurn && isSetup)
            return new SetupMyTurn();
        else if (!isMyTurn && isSetup)
            return this;
        else if (isMyTurn && !isSetup)
            return new MyTurnNormal();
        else
            return new NotMyTurnNormal();
    }

    @Override
    public void render(MapController controller)
    {
        controller.initFromModel();
    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void placeRoad(EdgeLocation edgeLoc, MapController controller)
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
    public void playSoldierCard(MapController controller)
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
    {
        //do nothing
    }
    
}
