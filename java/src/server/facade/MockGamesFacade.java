package server.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import model.ClientModel;
import model.cards.DevelopmentCards;
import model.cards.Hand;
import model.cards.ResourceCards;
import model.cards.TradeOffer;
import model.map.CatanMap;
import model.messages.MessageList;
import model.player.NullablePlayerIdx;
import model.player.Player;
import model.player.PlayerIdx;
import model.player.TurnStatusEnumeration;
import model.player.TurnTracker;
import model.player.User;

/**
 * Mock facade that deals with games operations; does not actually access the server model
 * @author Madison Brooks
 *
 */
public class MockGamesFacade implements IGamesFacade 
{
	/**
	 * Constructor
	 */
	public MockGamesFacade()
	{
		
	}
	
	/**
	 * Returns a sample list of games (GameJSONResponses)
	 * @return sample list
	 */
	@Override
	public List<GameJSONResponse> list() 
	{
		List<ClientModel> games = new ArrayList<ClientModel>();
		Hand bank1 = null;
		MessageList chat1 = null;
		MessageList log1 = null;
		CatanMap map1 = null;
		Collection<Player> players1 = null;
		TradeOffer tradeOffer1 = null;
		TurnTracker turnTracker1 = null;
		int version1 = 0;
		NullablePlayerIdx winner1 = null;
		ClientModel model1 = new ClientModel(bank1, chat1, log1, map1, players1, tradeOffer1, turnTracker1, version1, winner1);
		games.add(model1);
		Hand bank2 = null;
		MessageList chat2 = null;
		MessageList log2 = null;
		CatanMap map2 = null;
		Collection<Player> players2 = null;
		TradeOffer tradeOffer2 = null;
		TurnTracker turnTracker2 = null;
		int version2 = 0;
		NullablePlayerIdx winner2 = null;
		ClientModel model2 = new ClientModel(bank2, chat2, log2, map2, players2, tradeOffer2, turnTracker2, version2, winner2);
		games.add(model2);
		return null;
	}

	/**
	 * Returns a sample result for creating a new game
	 */
	@Override
	public boolean create(CreateGameRequest request) 
	{
		return true;
	}

	/**
	 * Returns a sample result for joining a game
	 */
	@Override
	public ClientModel join(User user, JoinGameRequest request) 
	{
		ResourceCards bankResources = new ResourceCards(19, 19, 19, 19, 19);
		DevelopmentCards bankDevCards = new DevelopmentCards(2, 5, 2, 14, 2);
		Hand bank = new Hand(bankResources, bankDevCards);
		MessageList chat = new MessageList(null); 
		MessageList log = new MessageList(null);
		CatanMap map = new CatanMap();
		Collection<Player> players = new ArrayList<Player>();
		TradeOffer tradeOffer = null;
		TurnTracker turnTracker = new TurnTracker(new PlayerIdx(0), TurnStatusEnumeration.firstround, 
				new NullablePlayerIdx(-1), new NullablePlayerIdx(-1));
		int version = 0;
		NullablePlayerIdx winner = new NullablePlayerIdx(-1);
		ClientModel model = new ClientModel(bank, chat, log, map, players, tradeOffer, turnTracker, version, winner);
		return model;
	}
	
}
