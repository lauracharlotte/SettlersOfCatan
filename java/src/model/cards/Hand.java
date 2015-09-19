package model.cards;

/**
 * Contains the list of resource and development cards the player/bank has
 * @author Scott Hampton
 */
public class Hand
{
    /**
     * An instance of a ResourceCardList
     * (The list of resource cards)
      */
    private ResourceCardList resourceCardList;

    /**
     * An instance of a DevelopmentCardList
     * (The list of development cards)
     */
    private DevelopmentCardList developmentCardList;

    /**
     * Constructor for the Hand
     * @param resourceCardList
     * @param developmentCardList
     */
    public Hand(resourceCardList, developmentCardList)
    {
        this.resourceCardList = resourceCardList;
        this.developmentCardList = developmentCardList;
    }

    /**
     * Obvious
     * @return resourceCardList
     */
    public ResourceCardList getResourceCardList()
    {
        return resourceCardList;
    }

    /**
     * Obvious
     * @param resourceCardList
     */
    public void setResourceCardList(ResourceCardList resourceCardList)
    {
        this.resourceCardList = resourceCardList;
    }

    /**
     * Obvious
     * @return developmentCardList
     */
    public DevelopmentCardList getDevelopmentCardList()
    {
        return developmentCardList;
    }

    /**
     * Obvious
     * @param developmentCardList
     */
    public void setDevelopmentCardList(DevelopmentCardList developmentCardList)
    {
        this.developmentCardList = developmentCardList;
    }
}