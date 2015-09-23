package model.cards;

/**
 * Contains the list of resource and development cards the player/bank has
 * @author Scott Hampton
 */
public class Hand
{
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
        this.DevelopmentCards = developmentCards;
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
}