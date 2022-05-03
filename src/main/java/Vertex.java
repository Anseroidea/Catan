import java.util.*;
import java.util.stream.Collectors;

public class Vertex {

    public Map<Integer, Tile> adjacentTiles;
    public Map<Integer, Edge> adjacentEdges;
    public Settlement settlement;
    private int r, c;
    private Harbor h;

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
        return adjacentEdges;
    }

    public void addAdjacentEdge(int pos, Edge e) {
        adjacentEdges.put(pos, e);
    }

    public List<Vertex> getAdjacentVertices(){
        Set<Vertex> result = adjacentEdges.values().stream().flatMap(e -> e.getAdjacentVertices().values().stream()).collect(Collectors.toSet());
        result.remove(this);
        return new ArrayList<>(result);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "r=" + r +
                ", c=" + c +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return r == vertex.r && c == vertex.c;
    }

    public void addSettlement(Player p){
        settlement= new Settlement(p,this);
    }

    public Harbor getHarbor() {
        return h;
    }

    public void setHarbor(Harbor h) {
        this.h = h;
    }

    public boolean hasHarbor(){
        return h != null;
    }
}
