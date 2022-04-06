import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex {

    private Map<Integer, Tile> adjacentTiles;
    private Map<Integer, Edge> adjacentEdges;
    private Settlement settlement;

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

}
