import java.util.*;
import java.util.stream.Collectors;

public class Edge {
    private Map<Integer, Tile> adjacentTiles;
    private Map<Integer, Vertex> adjacentVertices;
    private Road r;

    private int row;
    private int col;

    public static final int NORTH = 0;
    public static final int NORTHEAST = 1;
    public static final int EAST = 2;
    public static final int SOUTHEAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTHWEST = 5;
    public static final int WEST = 6;
    public static final int NORTHWEST = 7;

    public Edge(Vertex c, Vertex d) {
        adjacentTiles = new HashMap<>();
        adjacentVertices = new TreeMap<>();
        /*
        Tile[] a = new Tile[6];
        Tile[] k = new Tile[6];
        Tile[] m = new Tile[2];
        for (int b = 0; b < 6; b++) {
            a[b] = c.adjacentTiles.get(b);
            k[b] = d.adjacentTiles.get(b);
        }
        int r = 0;
        for (int b = 0; b < 6; b++) {
            for (int j = 0; j < 6; j++) {
                if (a[b].equals(k[j])) {
                    m[r] = a[b];
                    r++;
                    adjacentT.add(a[b]);
                }
            }
        }

        adjacentT = new ArrayList<Tile>();
        adjacentV = new ArrayList<Vertex>();

         */
    }


    public Road buildRoad(Player l) {
        r = new Road(this,l);
        return r;
    }

    public Road getRoad() {
        return r;
    }

    public Map<Integer, Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    public Map<Integer, Vertex> getAdjacentVertices() {
        return adjacentVertices;
    }

    public Map<Integer, Edge> getAdjacentEdges(){
        List<Edge> adjacentEdges = adjacentVertices.values().stream().flatMap(v -> v.getAdjacentEdges().values().stream()).distinct().collect(Collectors.toList());
        Map<Integer, Edge> result = new HashMap<>();
        for (Map.Entry<Integer, Edge> en : adjacentVertices.values().stream().flatMap(v -> v.getAdjacentEdges().entrySet().stream()).collect(Collectors.toList())){
            if (adjacentEdges.contains(en.getValue())){
                result.put(en.getKey(), en.getValue());
            }
        }
        return result;
    }

    public void addAdjacentTile(int dir, Tile t) {
        adjacentTiles.put(dir, t);
    }

    public void addAdjacentVertex(Integer i, Vertex v) {
        adjacentVertices.put(i, v);
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "adjacentVertices=" + adjacentVertices +
                '}';
    }

    public boolean hasRoad() {
        return r != null;
    }
}