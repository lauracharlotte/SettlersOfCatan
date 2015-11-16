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
import model.messages.MessageLine;
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
		Hand bank1 = new Hand(true);
		MessageList chat1 = new MessageList(new ArrayList<MessageLine>());
		MessageList log1 = new MessageList(new ArrayList<MessageLine>());
		CatanMap map1 = new CatanMap(false, true, false);
		Collection<Player> players1 = new ArrayList<Player>();
		TradeOffer tradeOffer1 = null;
		TurnTracker turnTracker1 = new TurnTracker();
		int version1 = 5;
		NullablePlayerIdx winner1 = new NullablePlayerIdx(-1);
		ClientModel model1 = new ClientModel("Game 1", bank1, chat1, log1, map1, players1, tradeOffer1, turnTracker1, version1, winner1);
		games.add(model1);
		ClientModel model2 = new ClientModel(true, true, false, "Game 2");
		games.add(model2);
		List<GameJSONResponse> JSONGames = new ArrayList<GameJSONResponse>();
		for (int i = 0; i < games.size(); i++)
		{
			GameJSONResponse game = new GameJSONResponse(games.get(i), i);
			JSONGames.add(game);
		}
		return JSONGames;
	}

	/**
	 * Returns a sample result for creating a new game
	 */
	@Override
	public GameJSONResponse create(CreateGameRequest request) 
	{
		return null;
	}

	/**
	 * Returns a sample result for joining a game
	 */
	@Override
	public boolean join(User user, JoinGameRequest request) 
	{
		DevelopmentCards emptyDevCards = new DevelopmentCards(0, 0, 0, 0, 0);
		ResourceCards emptyResCards = new ResourceCards(0, 0, 0, 0, 0);
		ResourceCards bankResources = new ResourceCards(19, 19, 19, 19, 19);
		DevelopmentCards bankDevCards = new DevelopmentCards(2, 5, 2, 14, 2);
		Hand bank = new Hand(bankResources, bankDevCards);
		MessageList chat = new MessageList(null); 
		MessageList log = new MessageList(null);
		CatanMap map = new CatanMap();
		Collection<Player> players = new ArrayList<Player>();
		players.add(new Player(4, request.getPlayerColor(), false, 0, user.getUsername(), null, false, 
				emptyDevCards, user.getPlayerId(), 15, 5, 0, 0, new Hand(emptyResCards, emptyDevCards)));
		TradeOffer tradeOffer = null;
		TurnTracker turnTracker = new TurnTracker(new PlayerIdx(0), TurnStatusEnumeration.firstround, 
				new NullablePlayerIdx(-1), new NullablePlayerIdx(-1));
		int version = 0;
		NullablePlayerIdx winner = new NullablePlayerIdx(-1);
		ClientModel model = new ClientModel("Game 1", bank, chat, log, map, players, tradeOffer, turnTracker, version, winner);
		return true;
	}
	
}
