package client.join;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import clientcommunicator.Server.ServerPoller;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.GameServerOperationsManager;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import clientcommunicator.operations.CreateGameRequest;
import clientcommunicator.operations.GameJSONResponse;
import clientcommunicator.operations.JoinGameRequest;
import clientcommunicator.operations.PlayerJSONResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModelSupplier;
import org.json.JSONException;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController
{

    private INewGameView newGameView;
    private ISelectColorView selectColorView;
    private IMessageView messageView;
    private IAction joinAction;
    private GameServerOperationsManager manager;
    private Timer timer;
    private GameInfo game = null;
    private boolean shouldShowGameList = true;
    private GameInfo[] lastList = null;
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

    private synchronized void populateList()
    {
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
        for(GameJSONResponse gameChoice: gamesCollection)
        {
            games[idx] = new GameInfo();
            games[idx].setId(gameChoice.getGameId());
            games[idx].setTitle(gameChoice.getTitle());
            int playerIndex = 0;
            for(PlayerJSONResponse player: gameChoice.getPlayers())
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
        if(Arrays.equals(games, this.lastList))
            return;
        this.lastList = games;
        PlayerInfo localPlayer = new PlayerInfo();
        localPlayer.setId(ClientModelSupplier.getInstance().getClientPlayerID());
        getJoinGameView().setGames(games, localPlayer);
        if(this.shouldShowGameList)
        {
            getJoinGameView().closeModal();
            getJoinGameView().showModal();
        }
    }
    
    @Override
    public void start() 
    {
        getJoinGameView().showModal();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                populateList();
            }
        };
        this.timer = new Timer();
        this.timer.schedule(timerTask, 0, 3000);
    }
    

    @Override
    public void startCreateNewGame() 
    {
        getJoinGameView().closeModal();
        this.shouldShowGameList = false;
        getNewGameView().showModal();
    }

    @Override
    public void cancelCreateNewGame() 
    {
        getNewGameView().closeModal();
        this.shouldShowGameList = true;
        getJoinGameView().showModal();
    }

    @Override
    public void createNewGame() 
    {
        boolean randomlyPlaceHexes = getNewGameView().getRandomlyPlaceHexes();
        boolean randomlyPlaceNumbers = getNewGameView().getRandomlyPlaceNumbers();
        String title = getNewGameView().getTitle();
        boolean randomPorts = getNewGameView().getUseRandomPorts();
        if(title.trim().equals("") || title == null)
        {
            this.getMessageView().setMessage("Invalid title");
            this.getMessageView().showModal();
            return;
        }
        getNewGameView().closeModal();
        try
        {
            GameJSONResponse game = manager.createGame(new CreateGameRequest(randomlyPlaceHexes, randomlyPlaceNumbers, randomPorts, title));
            //manager.joinGame(new JoinGameRequest(game.getGameId(), CatanColor.RED), randomPorts);
        }
        catch (ClientException | JSONException ex)
        {
            Logger.getLogger(JoinGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getJoinGameView().showModal();
        this.shouldShowGameList = true;
        this.populateList();
    }

    @Override
    public void startJoinGame(GameInfo game) 
    {
        this.game = game;
        this.shouldShowGameList = false;
        Collection<PlayerInfo> playersInGame = game.getPlayers();
        for(CatanColor color: CatanColor.values())
            getSelectColorView().setColorEnabled(color, true);
        for(PlayerInfo pInfo: playersInGame)
            if(pInfo.getId() != ClientModelSupplier.getInstance().getClientPlayerID())
                getSelectColorView().setColorEnabled(pInfo.getColor(), false);
            else
            {
                this.joinGame(pInfo.getColor());
                return;
            }
        getSelectColorView().showModal();
    }

    @Override
    public void cancelJoinGame() 
    {
        this.game = null;
        this.shouldShowGameList = true;
        getJoinGameView().closeModal();
    }

    @Override
    public void joinGame(CatanColor color) 
    {
        try
        {
            this.manager.joinGame(new JoinGameRequest(this.game.getId(), color), true);
        }
        catch (ClientException ex)
        {
            Logger.getLogger(JoinGameController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        // If join succeeded
        this.timer.cancel();
        getSelectColorView().closeModal();
        getJoinGameView().closeModal();
        joinAction.execute();
    }


}

