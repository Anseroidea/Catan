import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex {

    public Map<Integer, Tile> adjacentTiles;
    public Map<Integer, Edge> adjacentEdges;
    public Settlement settlement;

    public Vertex() {

    }

    public int getPaneX() {
        return 0;
    }

    public int getPaneY() {
        return 0;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public Map<Integer, Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    public Map<Integer, Edge> getAdjacentEdges() {
        return null;
    }
}
