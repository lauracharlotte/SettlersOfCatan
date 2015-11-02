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
public class PlayerWaitingController extends Controller implements IPlayerWaitingController
{

    private GameServerOperationsManager manager;
    private Timer timer;
    private int numPlayers = 0;
    private ServerPoller serverPoller;
    
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
    }

    @Override
    public IPlayerWaitingView getView() 
    {
            return (IPlayerWaitingView)super.getView();
    }

    public void checkGame()
    {
        try
        {
            ClientModel currentModel = manager.getClientModel();
            if(this.numPlayers == currentModel.getPlayers().size())
                return;
            if(currentModel.getPlayers().size() == 4)
            {
                this.timer.cancel();
                serverPoller = new ServerPoller();
                serverPoller.setFacade(ModelServerFacadeFactory.getInstance());
                serverPoller.setServer(ModelServerFacadeFactory.getInstance().getServerProxy());
                serverPoller.setPollingMilliseconds(2000);
                getView().closeModal();
                serverPoller.run();
                return;
            }
            this.numPlayers = currentModel.getPlayers().size();
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
            
            getView().closeModal();
            getView().showModal();
        }
        catch (JSONException | ClientException ex)
        {
            Logger.getLogger(PlayerWaitingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void start() 
    {
        //getView().showModal();
        String[] aiChoices = new String[1];
        manager.listAI().toArray(aiChoices);
        getView().setAIChoices(aiChoices);
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                checkGame();
            }
        };
        this.timer = new Timer();
        this.timer.schedule(timerTask, 0, 2000);
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
    
    public void stopPoller()
    {
    	serverPoller.stop();
    }

}

