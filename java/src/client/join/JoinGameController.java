package client.join;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.GameServerOperationsManager;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import clientcommunicator.operations.PlayerJSONResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModelSupplier;
import org.json.JSONException;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController, Observer 
{

    private INewGameView newGameView;
    private ISelectColorView selectColorView;
    private IMessageView messageView;
    private IAction joinAction;
    private GameServerOperationsManager manager;
    /**
     * JoinGameController constructor
     * 
     * @param view Join game view
     * @param newGameView New game view
     * @param selectColorView Select color view
     * @param messageView Message view (used to display error messages that occur while the user is joining a game)
     */
    public JoinGameController(IJoinGameView view, INewGameView newGameView, 
                                                            ISelectColorView selectColorView, IMessageView messageView) 
    {
        super(view);
        setNewGameView(newGameView);
        setSelectColorView(selectColorView);
        setMessageView(messageView);
        try
        {
            this.manager = (GameServerOperationsManager)ModelServerFacadeFactory.getInstance().getOperationsManager(GameServerOperationsManager.class);
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
        {
            Logger.getLogger(JoinGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public IJoinGameView getJoinGameView() 
    {
            return (IJoinGameView)super.getView();
    }

    /**
     * Returns the action to be executed when the user joins a game
     * 
     * @return The action to be executed when the user joins a game
     */
    public IAction getJoinAction() 
    {

            return joinAction;
    }

    /**
     * Sets the action to be executed when the user joins a game
     * 
     * @param value The action to be executed when the user joins a game
     */
    public void setJoinAction(IAction value) 
    {	

            joinAction = value;
    }

    public INewGameView getNewGameView() 
    {

            return newGameView;
    }

    public void setNewGameView(INewGameView newGameView) 
    {

            this.newGameView = newGameView;
    }

    public ISelectColorView getSelectColorView() 
    {

            return selectColorView;
    }
    public void setSelectColorView(ISelectColorView selectColorView) 
    {

            this.selectColorView = selectColorView;
    }

    public IMessageView getMessageView() 
    {
            return messageView;
    }
    
    public void setMessageView(IMessageView messageView) 
    {

            this.messageView = messageView;
    }

    @Override
    public void start() 
    {
        getJoinGameView().showModal();
        Collection<GameJSONResponse> gamesCollection = null;
        try
        {
            gamesCollection = manager.listGames();
        }
        catch (ClientException | JSONException ex)
        {
            Logger.getLogger(JoinGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (gamesCollection == null)
            return;
        GameInfo[] games = new GameInfo[gamesCollection.size()];
        int idx = 0;
        for(GameJSONResponse game: gamesCollection)
        {
            games[idx] = new GameInfo();
            games[idx].setId(game.getGameId());
            games[idx].setTitle(game.getTitle());
            int playerIndex = 0;
            for(PlayerJSONResponse player: game.getPlayers())
            {
                PlayerInfo onePlayersInfo = new PlayerInfo();
                onePlayersInfo.setColor(player.getColor());
                onePlayersInfo.setId(player.getId());
                onePlayersInfo.setName(player.getName());
                onePlayersInfo.setPlayerIndex(playerIndex);
                playerIndex++;
                games[idx].addPlayer(onePlayersInfo);
            }
            idx++;   
        }
        PlayerInfo localPlayer = new PlayerInfo();
        localPlayer.setId(ClientModelSupplier.getInstance().getClientPlayerID());
        getJoinGameView().setGames(games, localPlayer);
    }

    @Override
    public void startCreateNewGame() 
    {
            getNewGameView().showModal();
    }

    @Override
    public void cancelCreateNewGame() 
    {
        getNewGameView().closeModal();
    }

    @Override
    public void createNewGame() 
    {
        boolean randomlyPlaceHexes = getNewGameView().getRandomlyPlaceHexes();
        boolean randomlyPlaceNumbers = getNewGameView().getRandomlyPlaceNumbers();
        String title = getNewGameView().getTitle();
        boolean randomPorts = getNewGameView().getUseRandomPorts();
        getNewGameView().closeModal();
        try
        {
            GameJSONResponse game = manager.createGame(new CreateGameRequest(randomlyPlaceHexes, randomlyPlaceNumbers, randomPorts, title));
            manager.joinGame(new JoinGameRequest(game.getGameId(), CatanColor.RED), false);
        }
        catch (ClientException | JSONException ex)
        {
            Logger.getLogger(JoinGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start();
    }

    @Override
    public void startJoinGame(GameInfo game) 
    {

            getSelectColorView().showModal();
    }

    @Override
    public void cancelJoinGame() 
    {

            getJoinGameView().closeModal();
    }

    @Override
    public void joinGame(CatanColor color) 
    {

            // If join succeeded
            getSelectColorView().closeModal();
            getJoinGameView().closeModal();
            joinAction.execute();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

