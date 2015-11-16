package model.cards;

/**
 * Contains the amount of resource cards the player/bank has.
 * @author Scott Hampton
 */
public class ResourceCards
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
     * Constructor for a ResourceCards
     * @param brick
     * @param grain
     * @param lumber
     * @param ore
     * @param wool
     */
    public ResourceCards(int brick, int grain, int lumber, int ore, int wool)
    {
        this.brick = brick;
        this.grain = grain;
        this.lumber = lumber;
        this.ore = ore;
        this.wool = wool;
    }

    public int getTotal()
    {
        return this.brick + this.grain + this.lumber + this.ore + this.wool;
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
    
    @Override
    public String toString()
    {
    	StringBuilder str = new StringBuilder();
    	str.append("Resource Cards:\n");
    	str.append("Brick: ");
    	str.append(brick);
    	str.append("\nGrain: ");
    	str.append(grain);
    	str.append("\nLumber: ");
    	str.append(lumber);
    	str.append("\nOre: ");
    	str.append(ore);
    	str.append("\nWool: ");
    	str.append(wool);
    	return str.toString();
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + this.brick;
        hash = 97 * hash + this.grain;
        hash = 97 * hash + this.lumber;
        hash = 97 * hash + this.ore;
        hash = 97 * hash + this.wool;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ResourceCards other = (ResourceCards) obj;
        if (this.brick != other.brick)
        {
            return false;
        }
        if (this.grain != other.grain)
        {
            return false;
        }
        if (this.lumber != other.lumber)
        {
            return false;
        }
        if (this.ore != other.ore)
        {
            return false;
        }
        if (this.wool != other.wool)
        {
            return false;
        }
        return true;
    }

    
    
}
