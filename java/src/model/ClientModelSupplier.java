package model;

public class ClientModelSupplier {
	
	/**
	 * Static instance of the ClientModelSupplier
	 */
	private static ClientModelSupplier _instance = null;
	
        private int playerID = -1;

        public int getPlayerID()
        {
            return playerID;
        }

        public void setPlayerID(int playerID)
        {
            this.playerID = playerID;
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
