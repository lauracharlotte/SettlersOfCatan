package model.map;

import java.util.ArrayList;
import java.util.Collection;

import shared.locations.*;

/**
 * Represents the Catan Map of the game
 * @author Madison Brooks
 *
 */
public class CatanMap {
	
	/**
	 * List of Hexes that make up the map
	 */
	private Collection<Hex> hexes;
	
	/**
	 * List of Ports on the map
	 */
	private Collection<Port> ports;
	
	/**
	 * List of roads currently on the map
	 */
	private Collection<EdgeObject> roads;
	
	/**
	 * List of settlements currently on the map
	 */
	private Collection<VertexObject> settlements;
	
	/**
	 * List of cities currently on the map
	 */
	private Collection<VertexObject> cities;
	
	/**
	 * Radius of map (including center hex and ocean hexes)
	 */
	private int radius;
	
	/**
	 * Location of the robber piece
	 */
	private HexLocation robber;
	
        public CatanMap() //just for testing
        {}
        
	/**
	 * Constructor for the CatanMap
	 * @param hexes
	 * @param ports
	 * @param roads
	 * @param settlements
	 * @param cities
	 * @param radius
	 * @param robber
	 */
	public CatanMap(Collection<Hex> hexes, Collection<Port> ports, Collection<EdgeObject> roads,
			Collection<VertexObject> settlements, Collection<VertexObject> cities, int radius,
			HexLocation robber)
	{
		this.hexes = hexes;
		this.ports = ports;
		this.roads = roads;
		this.settlements = settlements;
		this.cities = cities;
		this.radius = radius;
		this.robber = robber;
	}

	/**
	 * @return the hexes
	 */
	public Collection<Hex> getHexes() {
		return hexes;
	}

	/**
	 * @param hexes the hexes to set
	 */
	public void setHexes(Collection<Hex> hexes) {
		this.hexes = hexes;
	}

	/**
	 * @return the ports
	 */
	public Collection<Port> getPorts() {
		return ports;
	}

	/**
	 * @param ports the ports to set
	 */
	public void setPorts(Collection<Port> ports) {
		this.ports = ports;
	}

	/**
	 * @return the roads
	 */
	public Collection<EdgeObject> getRoads() {
		return roads;
	}

	/**
	 * @param roads the roads to set
	 */
	public void setRoads(Collection<EdgeObject> roads) {
		this.roads = roads;
	}

	/**
	 * @return the settlements
	 */
	public Collection<VertexObject> getSettlements() {
		return settlements;
	}

	/**
	 * @param settlements the settlements to set
	 */
	public void setSettlements(Collection<VertexObject> settlements) {
		this.settlements = settlements;
	}

	/**
	 * @return the cities
	 */
	public Collection<VertexObject> getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(Collection<VertexObject> cities) {
		this.cities = cities;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * @return the robber
	 */
	public HexLocation getRobber() {
		return robber;
	}

	/**
	 * @param robber the robber to set
	 */
	public void setRobber(HexLocation robber) {
		this.robber = robber;
	}
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("Map:\n");
		str.append("Radius: ");
		str.append(radius);
		str.append("\nHexes:\n");
		for (int i = 0; i < hexes.size(); i++)
		{
			str.append(((ArrayList<Hex>) hexes).get(i).toString());
			str.append("\n");
		}
		str.append("Ports:\n");
		for (int i = 0; i < ports.size(); i++)
		{
			str.append(((ArrayList<Port>) ports).get(i).toString());
			str.append("\n");
		}
		str.append("Roads:\n");
		for (int i = 0; i < roads.size(); i++)
		{
			str.append(((ArrayList<EdgeObject>) roads).get(i).toString());
			str.append("\n");
		}
		str.append("Settlements:\n");
		for (int i = 0; i < settlements.size(); i++)
		{
			str.append(((ArrayList<VertexObject>) settlements).get(i).toString());
			str.append("\n");
		}
		str.append("Cities:\n");
		for (int i = 0; i < cities.size(); i++)
		{
			str.append(((ArrayList<VertexObject>) cities).get(i).toString());
			str.append("\n");
		}
		str.append("Robber:\n");
		str.append(robber.toString());
		return str.toString();
	}

}
