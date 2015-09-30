package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.locations.VertexLocation;

public class Abbreviate {
	
    public Abbreviate()
    {
    }

    public String abbreviate(String originalDirection)
    {
    	String newDirection = "";
    	if(originalDirection.equals("South"))
    	{
    		newDirection = "S";
    	}
    	else if(originalDirection.equals("SouthWest")) 
    	{
    		newDirection = "SW";
    	}
    	else if(originalDirection.equals("SouthEast")) 
    	{
    		newDirection = "SE";
    	}
    	else if(originalDirection.equals("North")) 
    	{
    		newDirection = "N";
    	}
    	else if(originalDirection.equals("NorthWest")) 
    	{
    		newDirection = "NW";
    	}
    	else if(originalDirection.equals("NorthEast")) 
    	{
    		newDirection = "NE";
    	}
    	return newDirection;
    }
	
}
