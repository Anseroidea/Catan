import java.util.*;

public class Edge {
    private Map<Integer, Tile> adjacentT;
    private Map<Integer, Vertex> adjacentV;
    private Road r;
    private Vertex v1, v2;

    public Edge(Vertex c, Vertex d) {
        adjacentT = new HashMap<>();
        adjacentV = new HashMap<>();
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

    public Map<Integer, Tile> getAdjacentT() {
        return adjacentT;
    }

    public void setAdjacentT(Map<Integer, Tile> adjacentT) {
        this.adjacentT = adjacentT;
    }

    public Map<Integer, Vertex> getAdjacentVertices() {
        return adjacentV;
    }

    public void setAdjacentV(Map<Integer, Vertex> adjacentV) {
        this.adjacentV = adjacentV;
    }
}