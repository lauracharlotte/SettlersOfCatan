package model.player;

/**
 * 
 * @author LaurasAdventurePC
 *
 */

public class NullablePlayerIdx {
	
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
}