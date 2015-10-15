package client.join;

import client.base.*;
import client.data.PlayerInfo;
import clientcommunicator.Server.ServerPoller;
import clientcommunicator.modelserverfacade.ClientException;
import clientcommunicator.modelserverfacade.GameServerOperationsManager;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientModel;
import model.ClientModelSupplier;
import model.player.Player;
import org.json.JSONException;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer 
{

    private GameServerOperationsManager manager;
    private Timer timer;
    
    public PlayerWaitingController(IPlayerWaitingView view) {

        super(view);
        try
        {
            this.manager = (GameServerOperationsManager)ModelServerFacadeFactory.getInstance().getOperationsManager(GameServerOperationsManager.class);
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
        {
            Logger.getLogger(JoinGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClientModelSupplier.getInstance().addObserver(this);
    }

    @Override
    public IPlayerWaitingView getView() 
    {
            return (IPlayerWaitingView)super.getView();
    }

    public synchronized void checkGame()
    {
        try
        {
            ClientModel currentModel = manager.getClientModel();
            if(currentModel.getPlayers().size() == 4)
            {
                this.timer.cancel();
                ServerPoller serverPoller = new ServerPoller();
                serverPoller.setFacade(ModelServerFacadeFactory.getInstance());
                serverPoller.setServer(ModelServerFacadeFactory.getInstance().getServerProxy());
                serverPoller.setPollingMilliseconds(3000);
                serverPoller.run();
            }
            Collection<Player> currentPlayers = currentModel.getPlayers();
            PlayerInfo[] playerInfo = new PlayerInfo[currentPlayers.size()];
            int idx = 0;
            for(Player player: currentPlayers)
            {
                playerInfo[idx] = new PlayerInfo();
                playerInfo[idx].setColor(player.getColor());
                playerInfo[idx].setId(player.getPlayerId());
                playerInfo[idx].setName(player.getName());
                idx++;
            }
            getView().setPlayers(playerInfo);
        }
        catch (JSONException | ClientException ex)
        {
            Logger.getLogger(PlayerWaitingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void start() 
    {
        getView().showModal();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                checkGame();
            }
        };
        this.timer = new Timer();
        this.timer.schedule(timerTask, 0, 2500);
        String[] aiChoices = new String[1];
        manager.listAI().toArray(aiChoices);
        getView().setAIChoices(aiChoices);
    }
    
    @Override
    public void addAI() 
    {
        try
        {
            manager.addAI();
            this.checkGame();
        }
        catch (ClientException ex)
        {
            Logger.getLogger(PlayerWaitingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(arg != null)
            getView().closeModal();
    }

}

