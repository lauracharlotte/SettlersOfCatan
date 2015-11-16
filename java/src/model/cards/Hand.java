package model.cards;

/**
 * Contains the list of resource and development cards the player/bank has
 * @author Scott Hampton
 */
public class Hand
{
	private final int CARDS_PER_RESOURCE = 19;
	private final int MONUMENTS = 5;
	private final int YEAR_OF_PLENTY = 2;
	private final int MONOPOLY = 2;
	private final int ROAD_BUILDING = 2;
	private final int SOLDIER = 14;
	
    /**
     * An instance of a ResourceCards
     * (The list of resource cards)
      */
    private ResourceCards resourceCards;

    /**
     * An instance of a DevelopmentCards
     * (The list of development cards)
     */
    private DevelopmentCards developmentCards;

    /**
     * Constructor for the Hand
     * @param resourceCards
     * @param developmentCards
     */
    public Hand(ResourceCards resourceCards, DevelopmentCards developmentCards)
    {
        this.resourceCards = resourceCards;
        this.developmentCards = developmentCards;
    }
    
    /**
     * Constructor for a new Bank
     * @param newBank
     */
    public Hand(boolean newBank)
    {
		this.resourceCards = new ResourceCards(CARDS_PER_RESOURCE, CARDS_PER_RESOURCE, CARDS_PER_RESOURCE, 
    			CARDS_PER_RESOURCE, CARDS_PER_RESOURCE);
		this.developmentCards = new DevelopmentCards(MONOPOLY, MONUMENTS, ROAD_BUILDING, SOLDIER, 
				YEAR_OF_PLENTY);
    }

    /**
     * Obvious
     * @return resourceCards
     */
    public ResourceCards getResourceCards()
    {
        return resourceCards;
    }

    /**
     * Obvious
     * @param resourceCards
     */
    public void setResourceCards(ResourceCards resourceCards)
    {
        this.resourceCards = resourceCards;
    }

    /**
     * Obvious
     * @return developmentCards
     */
    public DevelopmentCards getDevelopmentCards()
    {
        return developmentCards;
    }

    /**
     * Obvious
     * @param DevelopmentCards
     */
    public void setDevelopmentCards(DevelopmentCards developmentCards)
    {
        this.developmentCards = developmentCards;
    }
    
    @Override
    public String toString()
    {
    	StringBuilder str = new StringBuilder();
    	str.append("Hand:\n");
    	str.append(developmentCards.toString());
    	str.append("\n");
    	str.append(resourceCards.toString());
    	return str.toString();
    }
}