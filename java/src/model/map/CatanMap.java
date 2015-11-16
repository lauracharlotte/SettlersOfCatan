package model.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.*;

/**
 * Represents the Catan Map of the game
 * @author Madison Brooks
 *
 */
public class CatanMap {
	
	private final int DEFAULT_RADIUS = 4;
	
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
	 * Constructor which makes a map for a new game
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 */
	public CatanMap(boolean randomTiles, boolean randomNumbers, boolean randomPorts)
	{
		hexes = createNewHexes(randomTiles, randomNumbers);
		ports = createNewPorts(randomPorts);
		roads = new ArrayList<EdgeObject>();
		settlements = new ArrayList<VertexObject>();
		cities = new ArrayList<VertexObject>();
		radius = DEFAULT_RADIUS;
		robber = findDesertHex(hexes);
	}
	
	private Collection<Hex> createNewHexes(boolean randomTiles, boolean randomNumbers)
	{
		int chits[] = new int[18];
		if (randomNumbers)
		{
			chits = generateRandomChits();
		}
		else
		{
			chits = new int[]{5, 2, 6, 8, 10, 9, 3, 3, 11, 4, 8, 4, 9, 5, 10, 11, 12, 6};
		}
		HexType types[] = new HexType[19];
		if (randomTiles)
		{
			types = generateRandomTypes();
		}
		else
		{
			types = new HexType[]{HexType.ORE, HexType.WHEAT, HexType.WOOD, HexType.BRICK,
					HexType.SHEEP, HexType.SHEEP, HexType.ORE, HexType.DESERT, HexType.WOOD,
					HexType.WHEAT, HexType.WOOD, HexType.WHEAT, HexType.BRICK, HexType.ORE,
					HexType.BRICK, HexType.SHEEP, HexType.WOOD, HexType.SHEEP, HexType.WHEAT};
		}
		ArrayList<Hex> hexes = new ArrayList<Hex>();
		hexes.addAll(createWaterHexes());
		hexes.addAll(createLandHexes(types, chits));
		return hexes;
	}
	
	private int[] generateRandomChits()
	{
		int numbers[] = new int[]{2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
		ArrayList<Integer> availableNums = new ArrayList<Integer>();
		for (int i = 0; i < numbers.length; i++)
		{
			availableNums.add(numbers[i]);
		}
		int randomNums[] = new int[18];
		int nextIndex = 0;
		while (!availableNums.isEmpty())
		{
			int randomIndex = (int)(Math.random() * (availableNums.size()));
			randomNums[nextIndex] = availableNums.get(randomIndex);
			availableNums.remove(randomIndex);
			nextIndex++;
		}
		return randomNums;
	}
	
	private HexType[] generateRandomTypes()
	{
		HexType types[] = new HexType[]{HexType.BRICK, HexType.BRICK, HexType.BRICK, 
				HexType.ORE, HexType.ORE, HexType.ORE, 
				HexType.SHEEP, HexType.SHEEP, HexType.SHEEP, HexType.SHEEP,
				HexType.WHEAT, HexType.WHEAT, HexType.WHEAT, HexType.WHEAT,
				HexType.WOOD, HexType.WOOD, HexType.WOOD, HexType.WOOD, 
				HexType.DESERT};
		ArrayList<HexType> availableTypes = new ArrayList<HexType>();
		for (int i = 0; i < types.length; i++)
		{
			availableTypes.add(types[i]);
		}
		HexType randomTypes[] = new HexType[19];
		int nextIndex = 0;
		while (!availableTypes.isEmpty())
		{
			int randomIndex = (int)(Math.random() * (availableTypes.size()));
			randomTypes[nextIndex] = availableTypes.get(randomIndex);
			availableTypes.remove(randomIndex);
			nextIndex++;
		}
		return randomTypes;
	}
	
	private Collection<Hex> createWaterHexes()
	{
		Collection<Hex> hexes = new ArrayList<Hex>();
		int x = -3;
		int y;
		for (y = 0; y <= 3; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, HexType.WATER, -1);
			hexes.add(hex);
		}
		x = 3;
		for (y = 0; y >= -3; y--)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, HexType.WATER, -1);
			hexes.add(hex);
		}
		y = -3;
		for (x = 0; x <= 2; x++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, HexType.WATER, -1);
			hexes.add(hex);
		}
		y = 3;
		for (x = 0; x >= -2; x--)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, HexType.WATER, -1);
			hexes.add(hex);
		}
		HexLocation loc = new HexLocation(-2, -1);
		Hex hex = new Hex(loc, HexType.WATER, -1);
		hexes.add(hex);
		loc = new HexLocation(-1, -2);
		hex = new Hex(loc, HexType.WATER, -1);
		hexes.add(hex);
		loc = new HexLocation(2, 1);
		hex = new Hex(loc, HexType.WATER, -1);
		hexes.add(hex);
		loc = new HexLocation(1, 2);
		hex = new Hex(loc, HexType.WATER, -1);
		hexes.add(hex);
		return hexes;
	}
	
	private Collection<Hex> createLandHexes(HexType[] types, int[] chits)
	{
		Collection<Hex> hexes = new ArrayList<Hex>();
		int typeIndex = 0;
		int chitIndex = 0;
		int x = -2;
		int y;
		for (y = 0; y <= 2; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		x = -1;
		for (y = -1; y <= 2; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		x = 0;
		for (y = -2; y <= 2; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		x = 1;
		for (y = -2; y <= 1; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		x = 2;
		for (y = -2; y <= 0; y++)
		{
			HexLocation loc = new HexLocation(x, y);
			Hex hex = new Hex(loc, types[typeIndex], -1);
			if (types[typeIndex] != HexType.DESERT)
			{
				hex.setNumber(chits[chitIndex]);
				chitIndex++;
			}
			hexes.add(hex);
			typeIndex++;
		}
		return hexes;
	}
	
	private Collection<Port> createNewPorts(boolean randomPorts)
	{
		Collection<Port> ports = new ArrayList<Port>();
		HexLocation loc = new HexLocation(0, 3);
		EdgeDirection dir = EdgeDirection.North;
		Port port = new Port(loc, null, dir, -1);
		ports.add(port);
		loc = new HexLocation(-2, 3);
		dir = EdgeDirection.NorthEast;
		port = new Port(loc, null, dir, -1);
		ports.add(port);
		loc = new HexLocation(-3, 2);
		dir = EdgeDirection.NorthEast;
		port = new Port(loc, null, dir, -1);
		ports.add(port);
		loc = new HexLocation(-3, 0);
		dir = EdgeDirection.SouthEast;
		port = new Port(loc, null, dir, -1);
		ports.add(port);
		loc = new HexLocation(-1, -2);
		dir = EdgeDirection.South;
		port = new Port(loc, null, dir, -1);
		ports.add(port);
		loc = new HexLocation(1, -3);
		dir = EdgeDirection.South;
		port = new Port(loc, null, dir, -1);
		ports.add(port);
		loc = new HexLocation(3, -3);
		dir = EdgeDirection.SouthWest;
		port = new Port(loc, null, dir, -1);
		ports.add(port);
		loc = new HexLocation(3, -1);
		dir = EdgeDirection.NorthWest;
		port = new Port(loc, null, dir, -1);
		ports.add(port);
		loc = new HexLocation(2, 1);
		dir = EdgeDirection.NorthWest;
		port = new Port(loc, null, dir, -1);
		ports.add(port);
		addRatiosToPorts(ports, randomPorts);
		return ports;
	}
	
	private void addRatiosToPorts(Collection<Port> ports, boolean randomPorts)
	{
		ArrayList<Port> portList = (ArrayList<Port>)ports;
		int ratios[] = new int[]{3, 2, 2, 3, 2, 2, 3, 2, 3};
		ResourceType resources[] = new ResourceType[]{ResourceType.BRICK, ResourceType.WOOD, 
				ResourceType.WHEAT, ResourceType.ORE, ResourceType.SHEEP};
		if (randomPorts)
		{
			ratios = generateRandomRatios();
			resources = generateRandomResources();
		}
		int ratioIndex = 0;
		int resourceIndex = 0;
		for (int i = 0; i < portList.size(); i++)
		{
			Port port = portList.get(i);
			port.setRatio(ratios[ratioIndex]);
			if (ratios[ratioIndex] == 2)
			{
				port.setResource(resources[resourceIndex]);
				resourceIndex++;
			}
			ratioIndex++;
			portList.set(i, port);
		}
	}
	
	private int[] generateRandomRatios()
	{
		int ratios[] = new int[]{2, 2, 2, 2, 2, 3, 3, 3, 3};
		ArrayList<Integer> availableRatios = new ArrayList<Integer>();
		for (int i = 0; i < ratios.length; i++)
		{
			availableRatios.add(ratios[i]);
		}
		int randomRatios[] = new int[9];
		int nextIndex = 0;
		while (!availableRatios.isEmpty())
		{
			int randomIndex = (int)(Math.random() * (availableRatios.size()));
			randomRatios[nextIndex] = availableRatios.get(randomIndex);
			availableRatios.remove(randomIndex);
			nextIndex++;
		}
		return randomRatios;
	}
	
	private ResourceType[] generateRandomResources()
	{
		ResourceType resources[] = new ResourceType[]{ResourceType.BRICK, ResourceType.ORE, ResourceType.SHEEP,
				ResourceType.WHEAT, ResourceType.WOOD};
		ArrayList<ResourceType> availableResources = new ArrayList<ResourceType>();
		for (int i = 0; i < resources.length; i++)
		{
			availableResources.add(resources[i]);
		}
		ResourceType randomResources[] = new ResourceType[5];
		int nextIndex = 0;
		while (!availableResources.isEmpty())
		{
			int randomIndex = (int)(Math.random() * (availableResources.size()));
			randomResources[nextIndex] = availableResources.get(randomIndex);
			availableResources.remove(randomIndex);
			nextIndex++;
		}
		return randomResources;
	}
	
	private HexLocation findDesertHex(Collection<Hex> hexes)
	{
		Iterator<Hex> iterator = hexes.iterator();
		while (iterator.hasNext())
		{
			Hex nextHex = iterator.next();
			if (nextHex.getType() == HexType.DESERT)
			{
				return nextHex.getLocation();
			}
		}
		return null;
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
