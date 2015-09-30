package model;

import java.util.Collection;
import model.player.Player;

public class ClientModelSupplier {
	
	/**
	 * Static instance of the ClientModelSupplier
	 */
	private static ClientModelSupplier _instance = null;
	
        private int ClientPlayerID = -1;

        public int getClientPlayerID()
        {
            return ClientPlayerID;
        }

        public void setClientPlayerID(int ClientPlayerID)
        {
            this.ClientPlayerID = ClientPlayerID;
        }
        
        public Player getClientPlayerObject()
        {
            if(this.ClientPlayerID == -1)
                return null;
            else
            {
                Collection<Player> allPlayers = this.currentModel.getPlayers();
                for(Player player: allPlayers)
                    if(player.getPlayerId() == this.ClientPlayerID)
                        return player;
            }
            return null;
        }
        
	/**
	 * Current version of the ClientModel
	 */
	private ClientModel currentModel;
	
	/**
	 * Gets the singleton instance of the ClientModelSupplier
	 * @return the ClientModelSupplier
	 */
	public static ClientModelSupplier getInstance()
	{
		if (_instance == null)
		{
			_instance = new ClientModelSupplier();
		}
		return _instance;
	}
	
	/**
	 * Private constructor for the ClientModelSupplier; only called by getInstance()
	 */
	private ClientModelSupplier()
	{
		currentModel = null;
	}
	
	/**
	 * Sets the current ClientModel
	 * @param newModel the ClientModel to set
	 */
	public void setModel(ClientModel newModel)
	{
		currentModel = newModel;
	}
	
	/**
	 * Gets the current ClientModel
	 * @return the ClientModel
	 */
	public ClientModel getModel()
	{
		return currentModel;
	}

}
