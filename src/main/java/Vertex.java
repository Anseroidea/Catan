import java.util.HashMap;
import java.util.Map;

public class Vertex {

    public Map<Integer, Tile> adjacentTiles;
    public Map<Integer, Edge> adjacentEdges;
    public Settlement settlement;
    int r, c;

    public static final int NORTH = 0;
    public static final int NORTHEAST = 1;
    public static final int EAST = 2;
    public static final int SOUTHEAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTHWEST = 5;
    public static final int WEST = 6;
    public static final int NORTHWEST = 7;
    public static final String[] directions = {"NORTH", "NORTHEAST", "EAST", "SOUTHEAST", "SOUTH", "SOUTHWEST", "WEST", "NORTHWEST"};

    public Vertex(int x, int y) {
        r=x;
        c =y;
        adjacentTiles = new HashMap<Integer, Tile>();
        adjacentEdges = new HashMap<Integer, Edge>();
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

    public void addAdjacentEdge(int pos, Edge e) {
        adjacentEdges.put(pos, e);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "r=" + r +
                ", c=" + c +
                '}';
    }
}
