package model.cards;

/**
 * Contains the amount of resource cards the player/bank has.
 * @author Scott Hampton
 */
public class ResourceCardList
{
    /**
     * brick holds the amount of brick resource cards
     */
    private int brick;

    /**
     * grain holds the amount of grain resource cards
     */
    private int grain;

    /**
     * lumber holds the amount of lumber resource cards
     */
    private int lumber;

    /**
     * ore holds the amount of ore resource cards
     */
    private int ore;

    /**
     * wool holds the amount of wool resource cards
     */
    private int wool;

    /**
     * Constructor for a ResourceCardList
     * @param brick
     * @param grain
     * @param lumber
     * @param ore
     * @param wool
     */
    public ResourceCardList(int brick, int grain, int lumber, int ore, int wool)
    {
        this.brick = brick;
        this.grain = grain;
        this.lumber = lumber;
        this.ore = ore;
        this.wool = wool;
    }

    /**
     * Obvious
     * @return brick
     */
    public int getBrick()
    {
        return brick;
    }

    /**
     * Obvious
     * @param brick
     */
    public void setBrick(int brick)
    {
        this.brick = brick;
    }

    /**
     * Obvious
     * @return grain
     */
    public int getGrain()
    {
        return grain;
    }

    /**
     * Obvious
     * @param grain
     */
    public void setGrain(int grain)
    {
        this.grain = grain;
    }

    /**
     * Obvious
     * @return lumber
     */
    public int getLumber()
    {
        return lumber;
    }

    /**
     * Obvious
     * @param lumber
     */
    public void setLumber(int lumber)
    {
        this.lumber = lumber;
    }

    /**
     * Obvious
     * @return ore
     */
    public int getOre()
    {
        return ore;
    }

    /**
     * Obvious
     * @param ore
     */
    public void setOre(int ore)
    {
        this.ore = ore;
    }

    /**
     * Obvious
     * @return wool
     */
    public int getWool()
    {
        return wool;
    }

    /**
     * Obvious
     * @param wool
     */
    public void setWool(int wool)
    {
        this.wool = wool;
    }

}
