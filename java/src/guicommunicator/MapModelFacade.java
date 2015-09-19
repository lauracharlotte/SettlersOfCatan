package guicommunicator;

/**
 * The mediator between the client GUI controllers and the model classes
 * There are more than one mediator; this Facade is specifically for the
 * map model classes
 * @author Scott Hampton
 */
public class MapModelFacade
{
    // private ClientModel currentModel;

    /**
     * The constructor for the MapModelFacade
     */
    public MapModelFacade() { }

    /**
     * Using the given edgeLocation, determines whether
     * the player can place a road on that edge
     * @param edgeLocation
     * @return boolean
     */
    public boolean canPlaceRoad(edgeLocation)
    {
        return true;
    }

    /**
     * Using the given vertexLocation, determines whether
     * the player can place a settlement on that hex point
     * @param vertexLocation
     * @return boolean
     */
    public boolean canPlaceSettlement(vertexLocation)
    {
        return true;
    }

    /**
     * Using the given vertexLocation, determines whether
     * the player can place a city on that hex point
     * @param vertexLocation
     * @return boolean
     */
    public boolean canPlaceCity(vertexLocation)
    {
        return true;
    }

    /**
     * Using the given hexLocation, determines whether
     * the player can place the robber on that hex
     * @param hexLocation
     * @return boolean
     */
    public boolean canPlaceRobber(hexLocation)
    {
        return true;
    }
}