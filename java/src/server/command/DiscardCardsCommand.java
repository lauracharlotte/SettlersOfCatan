package server.command;

import server.facade.IModelFacade;
import clientcommunicator.Server.Cookie;
import clientcommunicator.modelserverfacade.JSONSerializer;
import clientcommunicator.operations.DiscardCardsRequest;
import model.ClientModel;
import model.cards.ResourceCards;
import model.player.PlayerIdx;
import model.player.User;
import org.json.JSONException;
import server.ServerException;
import server.facade.IMovesFacade;
/**
 * Executes the Discard Cards request.
 * @author Scott
 */
public class DiscardCardsCommand implements ICommand{

    @Override
    public String execute(IModelFacade facade, String requestBody, Cookie currentCookie) throws ServerException {
        IMovesFacade myMovesFacade = (IMovesFacade)facade;
        
        int game = currentCookie.getGameNumber();
        User playerId = currentCookie.getUser();
        
        DiscardCardsRequest discard = new DiscardCardsRequest(null, null);
        
        try
        {
            discard.deserialize(requestBody);
        }
        catch (JSONException ex)
        {
            return "Invalid JSON in request";
        }
        
        PlayerIdx playerIdx = discard.getPlayerIndex();
        ResourceCards resourceCards = discard.getDiscardedCards();
        
        ClientModel result = myMovesFacade.discardCards(playerIdx, resourceCards, game, playerId);
        
        myMovesFacade.saveCommand(requestBody, currentCookie);
        
        return JSONSerializer.SerializeModel(result);
    }
    
}
