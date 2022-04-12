import java.util.*;

public class Edge {
    private ArrayList<Tile> adjacentT;
    private ArrayList<Vertex> adjacentV;
    private Road r;
    private Vertex v1, v2;

    public Edge(Vertex c, Vertex d) {
        adjacentT = new ArrayList<Tile>();
        adjacentV = new ArrayList<Vertex>();
        adjacentV.add(c);
        adjacentV.add(d);
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
    }

    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentT;
    }

    public ArrayList<Vertex> getAdjacentVertex() {
        return adjacentV;
    }

    public Road buildRoad(Player l) {
        r = new Road(this,l);
        return r;
    }

    public Road getRoad() {
        return r;
    }

    public ArrayList<Vertex> getAdjacentVertices() {
        return null;
    }
}