package model.map;

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
	private Hex[] hexes;
	
	/**
	 * List of Ports on the map
	 */
	private Port[] ports;
	
	/**
	 * List of roads currently on the map
	 */
	private EdgeObject[] roads;
	
	/**
	 * List of settlements currently on the map
	 */
	private VertexObject[] settlements;
	
	/**
	 * List of cities currently on the map
	 */
	private VertexObject[] cities;
	
	/**
	 * Radius of map (including center hex and ocean hexes)
	 */
	private int radius;
	
	/**
	 * Location of the robber piece
	 */
	private HexLocation robber;
	
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
	public CatanMap(Hex[] hexes, Port[] ports, EdgeObject[] roads,
			VertexObject[] settlements, VertexObject[] cities, int radius,
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
	public Hex[] getHexes() {
		return hexes;
	}

	/**
	 * @param hexes the hexes to set
	 */
	public void setHexes(Hex[] hexes) {
		this.hexes = hexes;
	}

	/**
	 * @return the ports
	 */
	public Port[] getPorts() {
		return ports;
	}

	/**
	 * @param ports the ports to set
	 */
	public void setPorts(Port[] ports) {
		this.ports = ports;
	}

	/**
	 * @return the roads
	 */
	public EdgeObject[] getRoads() {
		return roads;
	}

	/**
	 * @param roads the roads to set
	 */
	public void setRoads(EdgeObject[] roads) {
		this.roads = roads;
	}

	/**
	 * @return the settlements
	 */
	public VertexObject[] getSettlements() {
		return settlements;
	}

	/**
	 * @param settlements the settlements to set
	 */
	public void setSettlements(VertexObject[] settlements) {
		this.settlements = settlements;
	}

	/**
	 * @return the cities
	 */
	public VertexObject[] getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(VertexObject[] cities) {
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

}
