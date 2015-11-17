package guicommunicator;

import model.ClientModelSupplier;
import model.cards.ResourceCards;
import model.player.Player;

/**
 * The mediator between the client GUI controllers and the model classes
 * There are more than one mediator; this Facade is specifically for the
 * resource model classes
 * @author Scott Hampton
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
        Player player = this.getClientPlayer();
        return canBuildCityAsPlayer(player);
    }

    public boolean canBuildCityAsPlayer(Player player)
    {
        ResourceCards neededCards = new ResourceCards(0, 2, 0, 3, 0);
        if(!this.checkPlayerAndResources(player, neededCards))
            return false;
        if(player.getCities() == 0)
            return false;
        if(player.getSettlements() == 5)
            return false;
        return true;
    }
    

    /**
     * Checks if the player has enough resources to build
     * a city
     * @return road
     */
    public boolean canBuildRoad()
    {
        Player player = this.getClientPlayer();
        return canBuildRoadAsPlayer(player);
    }

    public boolean canBuildRoadAsPlayer(Player player)
    {
        ResourceCards neededCards = new ResourceCards(1, 0, 1, 0, 0);
        if(!this.checkPlayerAndResources(player, neededCards))
            return false;
        if(player.getRoads()==0)
            return false;
        return true;
    }

    /**
     * Checks if the player has enough resources to build
     * a settlement
     * @return boolean
     */
    public boolean canBuildSettlement()
    {
        Player player = this.getClientPlayer();
        return this.canBuildSettlementAsPlayer(player);
    }
    
    public boolean canBuildSettlementAsPlayer(Player player)
    {
        ResourceCards neededCards = new ResourceCards(1, 1, 1, 0, 1);
        if(!this.checkPlayerAndResources(player, neededCards))
            return false;
        if(player.getSettlements() == 0)
            return false;
        return true;
    }

    /**
     * Checks if the player has enough resources to buy
     * a development card
     * @return boolean
     */
    public boolean canBuyDevCard()
    {
        Player player = this.getClientPlayer();
        return this.canBuyDevCardAsPlayer(player);
    }
    
    public boolean canBuyDevCardAsPlayer(Player player)
    {
        ResourceCards neededCards = new ResourceCards(0, 1, 0, 1, 1);
        return this.checkPlayerAndResources(player, neededCards);
    }
    
    private ResourceCards getPlayersResources(Player currentPlayer)
    {
        return currentPlayer.getHand().getResourceCards();
    }
    
    private Player getClientPlayer()
    {
        return ClientModelSupplier.getInstance().getClientPlayerObject();
    }
    
    /**
    @return returns true if the player is valid and if the player has enough resources
    **/
    private boolean checkPlayerAndResources(Player currentPlayer, ResourceCards neededCards)
    {
        if(currentPlayer == null)
            throw new IllegalStateException();
        ResourceCards hasCards = this.getPlayersResources(currentPlayer);
        return currentPlayer.hasEnoughResources(neededCards);
    }
    
    public boolean canPlayDevCard()
    {
    	Player currentPlayer = getClientPlayer();
    	return currentPlayer.canPlayDev();
    }
    
    
    
    public boolean hasEnoughResource(ResourceCards recCards)
    {
    	return checkPlayerAndResources(getClientPlayer(), recCards);
    }
    
    public boolean hasEnoughResourcesAsPlayer(Player currentPlayer, ResourceCards recCards)
    {
        return checkPlayerAndResources(currentPlayer, recCards);
    }

}