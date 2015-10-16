package model.map;

import shared.locations.*;
import shared.definitions.*;

/**
 * Represents a port (taking up 1 edge, 2 vertices) on the CatanMap
 * @author Madison Brooks
 *
 */
public class Port {

	/**
	 * Location of Port's Hex on the CatanMap
	 */
	private HexLocation hex;
	
	/**
	 * Port's resource (optional)
	 */
	private ResourceType resource;
	
	/**
	 * Direction of Port's edge relative to its Hex
	 */
	private EdgeDirection direction;
	
	/**
	 * Port's ratio (?:1); either 2 or 3
	 */
	private int ratio;
	
	/**
	 * Constructor for Port
	 * @param hex
	 * @param resource
	 * @param direction
	 * @param ratio
	 */
	public Port(HexLocation hex, ResourceType resource, 
			EdgeDirection direction, int ratio)
	{
		this.hex = hex;
		this.resource = resource;
		this.direction = direction;
		this.ratio = ratio;
	}

	/**
	 * @return the hex
	 */
	public HexLocation getHex() {
		return hex;
	}

	/**
	 * @param hex the hex to set
	 */
	public void setHex(HexLocation hex) {
		this.hex = hex;
	}

	/**
	 * @return the resource (may be null if ratio is 3)
	 */
	public ResourceType getResource() {
		return resource;
	}
        
        public PortType getPortType()
        {
            PortType typeForPort = PortType.THREE;
            if(this.getResource()!=null)
                switch(this.getResource())
                {
                    case WOOD: typeForPort = PortType.WOOD; break;
                    case BRICK: typeForPort = PortType.BRICK; break;
                    case SHEEP: typeForPort = PortType.SHEEP; break;
                    case WHEAT: typeForPort = PortType.WHEAT; break;
                    case ORE: typeForPort = PortType.ORE; break;
                }
            return typeForPort;
        }

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceType resource) {
		this.resource = resource;
	}

	/**
	 * @return the direction
	 */
	public EdgeDirection getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(EdgeDirection direction) {
		this.direction = direction;
	}

	/**
	 * @return the ratio
	 */
	public int getRatio() {
		return ratio;
	}

	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("Port:\nHex Location:\n");
		str.append(hex.toString());
		str.append("\nDirection: ");
		str.append(direction.toString());
		str.append("\nRatio: ");
		str.append(ratio);
		if (resource != null)
		{
			str.append("\nResource: ");
			str.append(resource.toString());
		}
		return str.toString();
	}
	
}
