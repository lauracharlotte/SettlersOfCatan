package model.player;

/**
 * 
 * @author LaurasAdventurePC
 *
 */


public class PlayerIdx extends NullablePlayerIdx
{
	public PlayerIdx(int index)
	{
            if(index == -1)
                throw new IllegalArgumentException(); 
            this.setIndex(index);
	}

	/**
	 * Gets the Player's Idx
	 * @return playerIdx
	 */
        @Override
	public int getIndex() 
	{
		return index;
	}
	
        @Override
        public void setIndex(int index)
        {
            if(index == -1)
                throw new IllegalArgumentException(); 
            super.setIndex(index); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setToNull()
        {
           throw new IllegalStateException();
        }

        @Override
        public boolean isNotNull()
        {
            return true;
        }

        @Override
        public boolean isNull()
        {
            return false;
        }
        
        @Override
    	public String toString()
    	{
    		StringBuilder str = new StringBuilder();
    		str.append(index);
    		return str.toString();
    	}

}