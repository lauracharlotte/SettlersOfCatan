package server.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import shared.definitions.CatanColor;

/**
 * 
 * @author LaurasAdventurePC
 *
 * MockGameFacade class
 */
public class MockGameFacade implements IGameFacade 
{
	List<User> theUsers = null;
	List<ClientModel> theGames = null;
	/**
	 * The Constructor
	 * @param users
	 * @param games
	 */
	public MockGameFacade(List<User> users, List<ClientModel> games)
	{
		theUsers = users;
		theGames = games;
	}
	/**
	 * Gets the model
	 * @return the model
	 */
	@Override
	public  ClientModel model(int gameIndex) {
		// TODO Auto-generated method stub
		ClientModel testClientModel = new ClientModel();
		
		ResourceCards recCards = new ResourceCards(3,3,3,3,3);
		DevelopmentCards devCards = new DevelopmentCards(3,3,3,3,3);
		DevelopmentCards newDevCards = new DevelopmentCards(1,0,1,0,0);
		Hand playHand = new Hand(recCards, devCards);
		
		Player player1 = new Player(0, CatanColor.RED, false, 0, "Laura1", new PlayerIdx(0), false, newDevCards, 1, 1, 1, 1, 1, playHand);
		Player player2 = new Player(0, CatanColor.BLUE, false, 0, "Laura2", new PlayerIdx(1), false, newDevCards, 2, 1, 1, 1, 1, playHand);
		Player player3 = new Player(0, CatanColor.PURPLE, false, 0, "Laura3", new PlayerIdx(2), false, newDevCards, 3, 1, 1, 1, 1, playHand);
		Player player4 = new Player(0, CatanColor.BROWN, false, 0, "Laura4", new PlayerIdx(3), false, newDevCards, 4, 1, 1, 1, 1, playHand);

		Collection<Player> playerCollect = new ArrayList<>();
		playerCollect.add(player1);
		playerCollect.add(player2);
		playerCollect.add(player3);
		playerCollect.add(player4);
		testClientModel.setPlayers(playerCollect);
		
		ResourceCards recCardsGame = new ResourceCards(10,10,10,10,10);
		DevelopmentCards devCardsGame = new DevelopmentCards(2,2,2,2,2);
		Hand playHandGame = new Hand(recCards, devCards);
		testClientModel.setBank(playHandGame);
		
		MessageList chat = new MessageList(null);
		testClientModel.setChat(chat);
		testClientModel.setLog(chat);
		
		CatanMap map = new CatanMap();
		testClientModel.setMap(map);
		
		TradeOffer tradeOffer = null;
		testClientModel.setTradeOffer(tradeOffer);
		
		TurnTracker turnTracker = new TurnTracker(new PlayerIdx(1), TurnStatusEnumeration.firstround, new NullablePlayerIdx(-1), new NullablePlayerIdx(-1));
		testClientModel.setTurnTracker(turnTracker);
		
		int version = 0;
		testClientModel.setVersion(version);
		
		NullablePlayerIdx winner = new NullablePlayerIdx(-1);
		testClientModel.setWinner(winner);
		//Hand bank, MessageList chat, MessageList log,
		//CatanMap map, Collection<Player> players, TradeOffer tradeOffer,
		//TurnTracker turnTracker, int version, NullablePlayerIdx winner
		
		return testClientModel;
	}
	
}
