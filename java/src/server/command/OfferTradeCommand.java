/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONSerializer;
import clientcommunicator.operations.OfferTradeRequest;
import model.ClientModel;
import model.cards.ResourceCards;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
/**
 * Executes the Offer Trade request.
 * @author Scott
 */
public class OfferTradeCommand implements ICommand {

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        OfferTradeRequest trade = new OfferTradeRequest(null, null, 0);
        
        try
        {
            trade.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = trade.getPlayerIdx();
        ResourceCards resourceCards = trade.getOffer();
        PlayerIdx receiver = new PlayerIdx(trade.getReceiver());
        
        ClientModel result = myMovesFacade.offerTrade(playerIdx, resourceCards, receiver, game, playerId);
        
        // return result.serialize(); or whatever
        return new JSONSerializer().SerializeModel(result);
    }
    
}
