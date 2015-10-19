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
import clientcommunicator.operations.BuildRoadRequest;
import clientcommunicator.operations.BuildSettlementRequest;
import clientcommunicator.operations.FinishTurnRequest;
import guicommunicator.MapModelFacade;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModel;
import model.ClientModelSupplier;
import model.map.EdgeObject;
import model.map.VertexObject;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
class SetupMyTurn implements IMapState
{
    
    public SetupMyTurn()
    {
        try
        {
            this.manager = (BuildItemServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(BuildItemServerOperationsManager.class);
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
        {
            Logger.getLogger(SetupMyTurn.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.facade = new MapModelFacade();
        this.playerIndex = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
    }
    
    private BuildItemServerOperationsManager manager;
    private MapModelFacade facade;
    private PlayerIdx playerIndex;
    private boolean placedRoad;
    private boolean placedSettlement;
    
    @Override
    public IMapState update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel)arg;
        boolean isMyTurn = model.getTurnTracker().getCurrentTurn().equals(this.playerIndex);
        boolean isSetupFirstRound = model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.firstround);
        boolean isSetupSecondRound = model.getTurnTracker().getStatus().equals(TurnStatusEnumeration.secondround);
        int numberOfRoads = 0, numberOfSettlements = 0;
        for(EdgeObject road: model.getMap().getRoads())
            if(road.getOwner().equals(this.playerIndex))
                numberOfRoads++;
        for(VertexObject settlement: model.getMap().getSettlements())
            if(settlement.getOwner().equals(this.playerIndex))
                numberOfSettlements++;
        boolean isSetup = isSetupFirstRound || isSetupSecondRound;
        if (!isSetup && isMyTurn)
            return new MyTurnNormal();
        else if (isSetupFirstRound && isMyTurn)
        {
            this.placedRoad = numberOfRoads >= 1;
            this.placedSettlement = numberOfSettlements >= 1;
            return this;
        }
        else if (isSetupSecondRound && isMyTurn)
        {
            this.placedRoad = numberOfRoads>=2;
            this.placedSettlement = numberOfSettlements>=2;
            return this;
        }
        else if(isSetup && !isMyTurn)
        {
            return new NotMyTurnSetup();
        }
        else
        {
            return new NotMyTurnNormal();
        }
    }

    @Override
    public void render(MapController controller)
    {
        controller.initFromModel();
        if (!placedRoad)
            controller.startMove(PieceType.ROAD, true, false);
        else if(!placedSettlement)
            controller.startMove(PieceType.SETTLEMENT, true, false);
    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        VertexDirection[] directions = edgeLoc.getDir().convertEdgeDirToVertexDir();
        VertexLocation[] vertLocations = new VertexLocation[]{
            new VertexLocation(edgeLoc.getHexLoc(), directions[0]).getNormalizedLocation(), 
                new VertexLocation(edgeLoc.getHexLoc(), directions[1]).getNormalizedLocation()};
        return facade.onLand(edgeLoc) && (facade.spaceForSettlement(vertLocations[0]) || facade.spaceForSettlement(vertLocations[1]));
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return facade.canPlaceSettlement(vertLoc);
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
       if(this.canPlaceRoad(edgeLoc) && !this.placedRoad)
       {
           try
           {
               this.manager.buildRoad(new BuildRoadRequest(this.playerIndex, edgeLoc, true));
           }
           catch (ClientException ex)
           {
               Logger.getLogger(SetupMyTurn.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc)
    {
        if(this.canPlaceSettlement(vertLoc) && this.placedRoad && !this.placedSettlement)
        {
            try
            {
                this.manager.buildSettlement(new BuildSettlementRequest(this.playerIndex, vertLoc, true));
                TurnServerOperationsManager turnManager = (TurnServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
                turnManager.finishTurn(new FinishTurnRequest(this.playerIndex));
            }
            catch (ClientException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
            {
                Logger.getLogger(SetupMyTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void placeCity(VertexLocation vertLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void placeRobber(HexLocation hexLoc)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelMove()
    {
        return;
    }

    @Override
    public void playSoldierCard()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playRoadBuildingCard()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void robPlayer(RobPlayerInfo victim)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
