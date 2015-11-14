package clientcommunicator.operations;

import java.util.HashMap;
import java.util.Map;
import model.player.PlayerIdx;
import shared.locations.EdgeDirection;
import shared.locations.VertexDirection;
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
    
    private static final Map<Character, String> abbreviations;
    static
    {
        abbreviations = new HashMap<>();
        abbreviations.put('N', "North");
        abbreviations.put('E', "East");
        abbreviations.put('S', "South");
        abbreviations.put('W', "West");
    }
    
    private static String getWholeString(String direction)
    {
        direction = direction.trim().toUpperCase();
        StringBuilder totalDirection = new StringBuilder();
        for(char c : direction.toCharArray())
        {
            totalDirection.append(abbreviations.get(c));
        }
        return totalDirection.toString();
    }
    
    public static VertexDirection unAbbreviateVertex(String direction)
    {
        return VertexDirection.valueOf(getWholeString(direction));
    }
    
    public static EdgeDirection unAbbreviateEdge(String direction)
    {
        return EdgeDirection.valueOf(getWholeString(direction));
    }
	
}
