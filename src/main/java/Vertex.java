import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex {

    public Map<Integer, Tile> adjacentTiles;
    public Map<Integer, Edge> adjacentEdges;
    public Settlement settlement;
    int px, py;

    public Vertex(int x, int y) {
        px=x;py=y;
    }

    public int getPaneX() {
        return px;
    }

    public int getPaneY() {
        return py;
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
