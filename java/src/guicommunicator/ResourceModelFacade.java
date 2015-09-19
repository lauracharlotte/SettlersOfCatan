package guicommunicator;

/**
 * The mediator between the client GUI controllers and the model classes
 * There are more than one mediator; this Facade is specifically for the
 * resource model classes
 */
public class ResourceModelFacade
{
    /**
     * The constructor for ResourceModelFacade class
     */
    public ResourceModelFacade() { }

    /**
     * Checks if the player has enough resources to build
     * a city
     * @return boolean
     */
    public boolean canBuildCity()
    {
        return true;
    }

    /**
     * Checks if the player has enough resources to build
     * a city
     * @return road
     */
    public boolean canBuildRoad()
    {
        return true;
    }

    /**
     * Checks if the player has enough resources to build
     * a settlement
     * @return boolean
     */
    public boolean canBuildSettlement()
    {
        return true;
    }

    /**
     * Checks if the player has enough resources to buy
     * a development card
     * @return boolean
     */
    public boolean canBuyDevCard()
    {
        return true;
    }
}