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
import model.player.TurnStatusEnumeration;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
public class NotInGameState implements IMapState
{
    @Override
    public IMapState update(Observable o, Object arg)
    {
        if(arg == null)
            return this;
        else
        {
            ClientModelSupplier modelSupplier = (ClientModelSupplier)o;
            ClientModel model = (ClientModel)arg;
            boolean isMyTurn = model.getTurnTracker().getCurrentTurn().equals(modelSupplier.getClientPlayerObject().getPlayerIndex());
            boolean isSetup = model.getTurnTracker().getStatus() == TurnStatusEnumeration.firstround;
            isSetup = isSetup || model.getTurnTracker().getStatus() == TurnStatusEnumeration.secondround;
            IMapState result;
            if (isSetup && isMyTurn)
                result = new SetupMyTurn();
            else if (!isMyTurn && isSetup)
                result = new NotMyTurnSetup();
            else if (isMyTurn && !isSetup)
                result = new MyTurnNormal();
            else
                result = new NotMyTurnNormal();
            result.update(o, arg);
            return result;
        }
    }

    @Override
    public void render(MapController controller)
    {
        return; //nothing to render
    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return false;
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return false;
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return false;
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return false;
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc)
    {
        return;
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc)
    {
        return;
    }

    @Override
    public void placeCity(VertexLocation vertLoc)
    {
        return;
    }

    @Override
    public void placeRobber(HexLocation hexLoc)
    {
        return;
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {
        return;
    }

    @Override
    public void cancelMove()
    {
        return;
    }

    @Override
    public void playSoldierCard()
    {
        return;
    }

    @Override
    public void playRoadBuildingCard()
    {
        return;
    }

    @Override
    public void robPlayer(RobPlayerInfo victim)
    {
        return;
    }
    
}
