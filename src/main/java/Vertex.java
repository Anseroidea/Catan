import java.util.HashMap;
import java.util.Map;

public class Vertex {

    public Map<Integer, Tile> adjacentTiles;
    public Map<Integer, Edge> adjacentEdges;
    public Settlement settlement;
    int r, c;

    public Vertex(int x, int y) {
        r=x;
        c =y;
        adjacentTiles = new HashMap<>();
        adjacentEdges = new HashMap<>();
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public Map<Integer, Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    public void addAdjacentTile(int pos, Tile t){
        adjacentTiles.put(pos, t);
    }

    public Map<Integer, Edge> getAdjacentEdges() {
        return null;
    }
}
