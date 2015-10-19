/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.map;

import client.data.RobPlayerInfo;
import clientcommunicator.modelserverfacade.BuildItemServerOperationsManager;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TurnServerOperationsManager;
import clientcommunicator.operations.BuildCityRequest;
import clientcommunicator.operations.BuildRoadRequest;
import clientcommunicator.operations.BuildSettlementRequest;
import clientcommunicator.operations.RobPlayerRequest;
import guicommunicator.MapModelFacade;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
class MyTurnNormal implements IMapState
{

    private PlayerIdx playerIndex;
    private MapModelFacade mapFacade = new MapModelFacade();
    private BuildItemServerOperationsManager manager;
    private TurnServerOperationsManager turnManager;
    private HexLocation robberMovingTo = null;
    
    public MyTurnNormal()
    {
        this.playerIndex = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        try
        {
            manager = (BuildItemServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(BuildItemServerOperationsManager.class);
            turnManager = (TurnServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
        {
            Logger.getLogger(MyTurnNormal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public IMapState update(Observable o, Object arg)
    {
        boolean isMyTurn = ((ClientModel)arg).getTurnTracker().getCurrentTurn().equals(this.playerIndex);
        if(isMyTurn)
            return this;
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
        return this.mapFacade.canPlaceRoad(edgeLoc);
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return this.mapFacade.canPlaceSettlement(vertLoc);
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return this.canPlaceCity(vertLoc);
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return this.canPlaceRobber(hexLoc);
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc)
    {
        try
        {
            manager.buildRoad(new BuildRoadRequest(this.playerIndex, edgeLoc, false));
        }
        catch (ClientException ex)
        {
            Logger.getLogger(MyTurnNormal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc)
    {
        try
        {
            manager.buildSettlement(new BuildSettlementRequest(this.playerIndex, vertLoc, false));
        }
        catch (ClientException ex)
        {
            Logger.getLogger(MyTurnNormal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void placeCity(VertexLocation vertLoc)
    {
        try 
        {
           manager.buildCity(new BuildCityRequest(this.playerIndex, vertLoc));
        }
        catch (ClientException ex)
        {
            Logger.getLogger(MyTurnNormal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void placeRobber(HexLocation hexLoc)
    {
        if(this.canPlaceRobber(hexLoc))
            this.robberMovingTo = hexLoc;
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
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
       
    }

    @Override
    public void playRoadBuildingCard()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void robPlayer(RobPlayerInfo victim)
    {
        try
        {
            this.turnManager.robPlayer(new RobPlayerRequest(this.playerIndex, new PlayerIdx(victim.getPlayerIndex()), this.robberMovingTo));
        }
        catch (ClientException ex)
        {
            Logger.getLogger(MyTurnNormal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
