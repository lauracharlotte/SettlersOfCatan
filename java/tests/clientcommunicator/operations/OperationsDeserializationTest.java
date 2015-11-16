/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.cards.ResourceCards;
import model.player.NullablePlayerIdx;
import model.player.PlayerIdx;
import org.json.JSONException;
import org.junit.Test;
import static org.junit.Assert.*;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
public class OperationsDeserializationTest
{
    
    private void deserialize(IJSONSerializable jsonSerializable, String JSON)
    {
        try
        {
            jsonSerializable.deserialize(JSON);
        }
        catch (JSONException ex)
        {
            Logger.getLogger(OperationsDeserializationTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }
    
    @Test
    public void testAcceptTradeRequestDeserialize() throws Exception
    {
        AcceptTradeRequest obj = new AcceptTradeRequest(new PlayerIdx(2), false);
        String JSON = obj.serialize();
        obj = new AcceptTradeRequest();
        deserialize(obj, JSON);
        assert(obj.getAcceptingPlayerIdx().getIndex() == 2);
        assert(!obj.willAccept());
    }
    
    @Test
    public void testBuildCityDeserialize() throws Exception
    {
        BuildCityRequest req = new BuildCityRequest(new PlayerIdx(0), new VertexLocation(new HexLocation(-5,5), VertexDirection.NorthWest));
        String JSON = req.serialize();
        req = new BuildCityRequest();
        deserialize(req, JSON);
        assert(req.getPlayerIndex().getIndex() == 0);
        assert(req.getLocation().getHexLoc().getX() == -5 && req.getLocation().getHexLoc().getY() == 5);
    }
    
    @Test
    public void testBuildRoadDeserialize() throws Exception
    {
        BuildRoadRequest req = new BuildRoadRequest(new PlayerIdx(2), new EdgeLocation(new HexLocation(-5, 5), EdgeDirection.SouthWest), false);
        String JSON = req.serialize();
        req = new BuildRoadRequest();
        deserialize(req, JSON);
        assert(req.getPlayerIndex().getIndex() == 2);
        assert(req.getLocation().getHexLoc().getX() == -5 && req.getLocation().getHexLoc().getY() == 5);
    }
    
    @Test
    public void testBuildSettlementDeserialize() throws Exception
    {
        BuildSettlementRequest req = new BuildSettlementRequest(new PlayerIdx(1), new VertexLocation(new HexLocation(-5, 5), VertexDirection.SouthEast), true);
        String JSON = req.serialize();
        req = new BuildSettlementRequest();
        deserialize(req, JSON);
        assert(req.getPlayerIndex().getIndex() == 1);
        assert(req.getLocation().getHexLoc().getX() == -5 && req.getLocation().getHexLoc().getY() == 5);
        assert(req.getLocation().getDir() == VertexDirection.SouthEast);
        assert(req.isFree());
    }
    
    @Test
    public void testBuyDevCardDeserialize() throws Exception
    {
        BuyDevCardRequest req = new BuyDevCardRequest(new PlayerIdx(0));
        String JSON = req.serialize();
        req = new BuyDevCardRequest();
        deserialize(req, JSON);
        assert(req.getPlayerIndex().getIndex() == 0);
    }
    
    @Test
    public void testCreateGameDeserialize() throws Exception
    {
        CreateGameRequest req = new CreateGameRequest(true, true, false, "name");
        CreateGameRequest result = new CreateGameRequest();
        deserialize(result, req.serialize());
        assertEquals(req.isRandomNumbers(), result.isRandomNumbers());
        assertEquals(req.isRandomPorts(), result.isRandomPorts());
        assertEquals(req.getName(), result.getName());
    }
    
    @Test
    public void testDiscardCardsDeserialize() throws Exception
    {
        DiscardCardsRequest req = new DiscardCardsRequest(new PlayerIdx(1), new ResourceCards(1,2,3,4,5));
        DiscardCardsRequest result = new DiscardCardsRequest();
        deserialize(result, req.serialize());
        assertEquals(req.getPlayerIndex(), result.getPlayerIndex());
        assertEquals(req.getDiscardedCards(), result.getDiscardedCards());
    }
    
    @Test
    public void testFinishTurnDeserialize() throws Exception
    {
        FinishTurnRequest req = new FinishTurnRequest(new PlayerIdx(2));
        FinishTurnRequest result = new FinishTurnRequest();
        deserialize(result, req.serialize());
        assertEquals(req.getPlayerIndex(), result.getPlayerIndex());
    }
    
    @Test
    public void testJoinGameDeserialize() throws Exception
    {
        JoinGameRequest req = new JoinGameRequest(55, CatanColor.BLUE);
        JoinGameRequest result = new JoinGameRequest();
        deserialize(result, req.serialize());
        assertEquals(result.getPlayerColor(), req.getPlayerColor());
        assertEquals(result.getGameId(), req.getGameId());
    }
    
    @Test
    public void testMaritimeTradeRequestDeserialize() throws Exception
    {
        MaritimeTradeRequest req = new MaritimeTradeRequest(new PlayerIdx(1), 3, ResourceType.WOOD, ResourceType.BRICK);
        MaritimeTradeRequest result = new MaritimeTradeRequest();
        deserialize(result, req.serialize());
        assertEquals(result.getPlayerIndex(), req.getPlayerIndex());
        assertEquals(result.getInputResource(), req.getInputResource());
        assertEquals(result.getRatio(), req.getRatio());
    }
    
    @Test
    public void testMonopolyDeserialize() throws Exception
    {
        MonopolyRequest req = new MonopolyRequest(new PlayerIdx(1), ResourceType.ORE);
        MonopolyRequest result = new MonopolyRequest();
        deserialize(result, req.serialize());
        assertEquals(result.serialize(), req.serialize());
    }
    
    @Test
    public void testMonumentRequestDeserialize() throws Exception
    {
        MonumentRequest req = new MonumentRequest(new PlayerIdx(3));
        MonumentRequest result = new MonumentRequest();
        deserialize(result, req.serialize());
        assertEquals(req.serialize(), result.serialize());
    }
    
    @Test
    public void testOfferTradeRequestDeserialize() throws Exception
    {
        OfferTradeRequest req = new OfferTradeRequest(new PlayerIdx(3), new ResourceCards(1,2,3,-4,5), 2);
        OfferTradeRequest result = new OfferTradeRequest();
        deserialize(result, req.serialize());
        assertEquals(req.serialize(), result.serialize());
    }
    
    @Test
    public void testPlaySoldierDeserialize() throws Exception
    {
        PlaySoldierRequest req = new PlaySoldierRequest(new PlayerIdx(0), new NullablePlayerIdx(-1), new HexLocation(5,-5));
        PlaySoldierRequest result = new PlaySoldierRequest();
        deserialize(result, req.serialize());
        assertEquals(req.serialize(), result.serialize());
    }
    
    @Test
    public void testRoadBuildingCardDeserialize() throws Exception
    {
        RoadBuildingCardRequest req  = new RoadBuildingCardRequest(new PlayerIdx(1), 
                new EdgeLocation(new HexLocation(5, -5), EdgeDirection.NorthEast), 
                new EdgeLocation(new HexLocation(-5, 5), EdgeDirection.SouthEast));
        RoadBuildingCardRequest result = new RoadBuildingCardRequest();
        deserialize(result, req.serialize());
        assertEquals(req.serialize(), result.serialize());
    }
    
    @Test
    public void testRobPlayerDeserialize() throws Exception
    {
        RobPlayerRequest req = new RobPlayerRequest(new PlayerIdx(0), new NullablePlayerIdx(-1), new HexLocation(5,-5));
        RobPlayerRequest result = new RobPlayerRequest();
        deserialize(result, req.serialize());
        assertEquals(req.serialize(), result.serialize());
    }
    
    @Test
    public void testRollNumberDeserialize() throws Exception
    {
        RollNumberRequest req = new RollNumberRequest(new PlayerIdx(2), 7);
        RollNumberRequest result = new RollNumberRequest();
        deserialize(result, req.serialize());
        assertEquals(req.serialize(), result.serialize());
    }
    
    @Test
    public void testSendChatDeserialize() throws Exception
    {
        SendChatRequest req = new SendChatRequest(new PlayerIdx(0), "This is a chat");
        SendChatRequest result = new SendChatRequest();
        deserialize(result, req.serialize());
        assertEquals(req.serialize(), result.serialize());
    }
    
    @Test
    public void testYearOfPlentyDeserialize() throws Exception
    {
        YearOfPlentyRequest req = new YearOfPlentyRequest(new PlayerIdx(1), ResourceType.BRICK, ResourceType.SHEEP);
        YearOfPlentyRequest result = new YearOfPlentyRequest();
        deserialize(result, req.serialize());
        assertEquals(req.serialize(), result.serialize());
    }
}
