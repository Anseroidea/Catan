import java.util.Map;

public class Edge {
    public Map<Integer, Tile> adjacentTiles;
    public Map<Integer, Vertex> adjacentVertices;
    public Road road;

    public Edge() {
    }

    public Map<Integer, Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    public Map<Integer, Vertex> getAdjacentVertices() {
        return adjacentVertices;
    }


}
