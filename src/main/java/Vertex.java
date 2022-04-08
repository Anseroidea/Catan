import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex {

    private Map<Integer, Tile> adjacentTiles;
    private Map<Integer, Edge> adjacentEdges;
    private Settlement settlement;
    public static final int NORTHEAST = 0;
    public static final int NORTHWEST = 1;
    public static final int EAST = 2;
    public static final int SOUTHEAST = 3;
    public static final int SOUTHWEST = 4;
    public static final int WEST = 5;

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
