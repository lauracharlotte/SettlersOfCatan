package shared.locations;

public enum EdgeDirection
{
	
	NorthWest, North, NorthEast, SouthEast, South, SouthWest;
	
	private EdgeDirection opposite;
	
	static
	{
		NorthWest.opposite = SouthEast;
		North.opposite = South;
		NorthEast.opposite = SouthWest;
		SouthEast.opposite = NorthWest;
		South.opposite = North;
		SouthWest.opposite = NorthEast;
	}
	
	public EdgeDirection getOppositeDirection()
	{
		return opposite;
	}
        
        public VertexDirection[] convertEdgeDirToVertexDir()
        {
            VertexDirection[] vertexDirections = new VertexDirection[2];
            switch(this)
            {
                case North:
                    vertexDirections[0] = VertexDirection.NorthEast;
                    vertexDirections[1] = VertexDirection.NorthWest;
                    break;
                case NorthWest:
                    vertexDirections[0] = VertexDirection.West;
                    vertexDirections[1] = VertexDirection.NorthWest;
                    break;
                case NorthEast:
                    vertexDirections[0] = VertexDirection.NorthEast;
                    vertexDirections[1] = VertexDirection.East;
                    break;
                case South:
                    vertexDirections[0] = VertexDirection.SouthEast;
                    vertexDirections[1] = VertexDirection.SouthWest;
                    break;
                case SouthWest:
                    vertexDirections[0] = VertexDirection.West;
                    vertexDirections[1] = VertexDirection.SouthWest;
                    break;
                case SouthEast:
                    vertexDirections[0] = VertexDirection.SouthEast;
                    vertexDirections[1] = VertexDirection.East;
                    break;
            }
            return vertexDirections;
        }
        
}

