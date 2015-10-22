/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.map;

import client.data.RobPlayerInfo;
import clientcommunicator.modelserverfacade.BuildItemServerOperationsManager;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.DevCardServerOperationsManager;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.modelserverfacade.TurnServerOperationsManager;
import clientcommunicator.operations.BuildCityRequest;
import clientcommunicator.operations.BuildRoadRequest;
import clientcommunicator.operations.BuildSettlementRequest;
import clientcommunicator.operations.PlaySoldierRequest;
import clientcommunicator.operations.RoadBuildingCardRequest;
import clientcommunicator.operations.RobPlayerRequest;
import guicommunicator.MapModelFacade;
import guicommunicator.ResourceModelFacade;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModel;
import model.ClientModelSupplier;
import model.player.Player;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
class MyTurnNormal implements IMapState
{

    private PlayerIdx playerIndex;
    private MapModelFacade mapFacade = new MapModelFacade();
    private ResourceModelFacade resourceFacade = new ResourceModelFacade();
    private BuildItemServerOperationsManager manager;
    private TurnServerOperationsManager turnManager;
    private DevCardServerOperationsManager devCardManager;
    private HexLocation robberMovingTo = null;
    private boolean soldiering = false;
    private int roadBuilding = 0;
    private EdgeLocation roadBuilding1 = null;
    private EdgeLocation roadBuilding2 = null;
    private boolean isRobbing = false;
    
    public MyTurnNormal()
    {
        this.playerIndex = ClientModelSupplier.getInstance().getClientPlayerObject().getPlayerIndex();
        try
        {
            manager = (BuildItemServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(BuildItemServerOperationsManager.class);
            turnManager = (TurnServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(TurnServerOperationsManager.class);
            devCardManager = (DevCardServerOperationsManager) ModelServerFacadeFactory.getInstance().getOperationsManager(DevCardServerOperationsManager.class);
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
        isRobbing = ((ClientModel)arg).getTurnTracker().getStatus() == TurnStatusEnumeration.robbing;
        if (isMyTurn && isRobbing)
        {
            return this;
        }
        if(isMyTurn)
        {
            this.robberMovingTo = null;
            return this;
        }
        else
        {
            this.robberMovingTo = null;
            return new NotMyTurnNormal();
        }
    }

    @Override
    public void render(MapController controller)
    {
        if(isRobbing && this.robberMovingTo == null)
            controller.startMove(PieceType.ROBBER, false, false);   
        controller.initFromModel();
    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        if(this.roadBuilding != 1)
            return this.mapFacade.canPlaceRoad(edgeLoc);
        else
            return this.mapFacade.canPlaceRoadIfOtherRoadThere(edgeLoc, this.roadBuilding1);
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return this.mapFacade.canPlaceSettlement(vertLoc);
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return this.mapFacade.canPlaceCity(vertLoc);
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return this.mapFacade.canPlaceRobber(hexLoc);
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc)
    {
        if(this.roadBuilding==2 && this.canPlaceRoad(edgeLoc))
        {
            this.roadBuilding1 = edgeLoc;
            this.roadBuilding--;
            return;
        }
        else if(this.roadBuilding == 1)
        {
            this.roadBuilding2 = edgeLoc;
            this.roadBuilding--;
            try
            {
                this.devCardManager.playRoadBuilding(new RoadBuildingCardRequest(this.playerIndex, this.roadBuilding1, this.roadBuilding2));
            }
            catch (ClientException ex)
            {
                Logger.getLogger(MyTurnNormal.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        
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
    public void placeRobber(HexLocation hexLoc, MapController mapController)
    {
        if(this.canPlaceRobber(hexLoc))
            this.robberMovingTo = hexLoc;
        else
            return;
        Set<Player> allPlayersBy = new HashSet<Player>(this.mapFacade.playersByHex(hexLoc));
        if(allPlayersBy.contains(ClientModelSupplier.getInstance().getClientPlayerObject()))
            allPlayersBy.remove(ClientModelSupplier.getInstance().getClientPlayerObject());
        if(allPlayersBy.isEmpty())
            return;
        RobPlayerInfo[] playerInfo = new RobPlayerInfo[allPlayersBy.size()];
        int idx = 0;
        for(Player p: allPlayersBy)
        {
            int numCards = p.getHand().getResourceCards().getTotal();
            if(numCards>0)
            {
                playerInfo[idx] = new RobPlayerInfo();
                playerInfo[idx].setNumCards(numCards);
                playerInfo[idx].setColor(p.getColor());
                playerInfo[idx].setPlayerIndex(p.getPlayerIndex().getIndex());
                idx++;
            }
        }
        mapController.getRobView().setPlayers(playerInfo);
        mapController.getRobView().showModal();
    }


    @Override
    public void cancelMove()
    {
        return;
    }

    @Override
    public void playSoldierCard()
    {
        this.soldiering = true;
    }

    @Override
    public void playRoadBuildingCard(MapController controller)
    {
        this.roadBuilding = 2;
        this.startMove(PieceType.ROAD, false, false, controller);
    }

    @Override
    public void robPlayer(RobPlayerInfo victim)
    {
        try
        {
            this.isRobbing = false;
            if(!this.soldiering)
                this.turnManager.robPlayer(new RobPlayerRequest(this.playerIndex, new PlayerIdx(victim.getPlayerIndex()), this.robberMovingTo));
            else
                this.devCardManager.playSoldier(new PlaySoldierRequest(this.playerIndex, new PlayerIdx(victim.getPlayerIndex()), this.robberMovingTo));
            this.soldiering = false;
            this.robberMovingTo = null;
        }
        catch (ClientException ex)
        {
            Logger.getLogger(MyTurnNormal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, MapController controller)
    {
        boolean canCancel = !isFree;
        if(this.roadBuilding>2)
            canCancel = false;
        controller.getView().startDrop(pieceType, ClientModelSupplier.getInstance().getClientPlayerObject().getColor(), canCancel);
    }
    
}
