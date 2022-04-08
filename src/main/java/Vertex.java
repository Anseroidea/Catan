import java.util.*;

public class Vertex {

    public ArrayList<Tile> adjacentTiles;
    public ArrayList<Edge> adjacentEdges;
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

    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

}
