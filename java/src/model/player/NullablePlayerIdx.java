package model.player;

import java.io.Serializable;

/**
 * 
 * @author LaurasAdventurePC
 *
 */

@SuppressWarnings("serial")
public class NullablePlayerIdx implements Serializable
{
	
    /**
     * The players index, it can be null.
     */
    protected int index = -1;

    public static int NullIndex = -1;

    /**
     * Constructor for the Nullable Player Idx.
     * @param index
     */
    public NullablePlayerIdx(int index)
    {
    if (index < -1 || index > 3)
        throw new IllegalArgumentException();
    this.index = index;
    }
        
    public NullablePlayerIdx()
    {
        
    }
    
    public boolean isNull()
    {
        return index == -1;
    }
    
    public boolean isNotNull()
    {
        return !this.isNull();
    }
    
    public void setToNull()
    {
        this.index = -1;
    }
        
	/**
	 * Gets the Nullable Player Idx;
	 * @return index
	 */
    public int getIndex() 
    {
	return index;
    }
	
    /**
     * sets the Nullable Player Idx
     * @param index
     */
    public void setIndex(int index) 
    {
        if (index < -1 || index > 3)
                throw new IllegalArgumentException();
        this.index = index;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 73 * hash + this.index;
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
        final NullablePlayerIdx other = (NullablePlayerIdx) obj;
        if (this.index != other.index)
        {
            return false;
        }
        return true;
    }
    
    @Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(index);
		return str.toString();
	}
}