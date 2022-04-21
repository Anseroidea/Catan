import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HexGrid {

    private Map<Integer, List<Tile>> map;
    private VertexManager vertexManager;
    private EdgeManager edgeManager;

    public HexGrid(){
         map = new TreeMap<>();
         vertexManager = new VertexManager();
         edgeManager = new EdgeManager();
    }

    public void add(Tile t, int r, int c){
        if (c < 0){
            return;
        }
        if (!map.containsKey(r)){
            List<Tile> list = new ArrayList<>();
            IntStream.range(0, c).forEach(i -> list.add(null));
            list.add(t);
            map.put(r, list);
        } else {
            List<Tile> list = map.get(r);
            if (list.size() > c){
                list.set(c, t);
            } else {
                IntStream.range(list.size(), c).forEach(i -> list.add(null));
                list.add(t);
            }
        }
        t.setPos(r, c);
    }

    public void setAdjacencies(){
        for (Integer r : map.keySet()){
            for (int c = 0; c < map.get(r).size(); c++) {
                Tile t = map.get(r).get(c);
                if (t == null){
                    continue;
                }
                if (map.containsKey(r - 1)){
                    if (map.get(r - 1).size() > c - 1 && c > 0) {
                        t.setNearbyTile(Tile.SOUTHWEST, map.get(r - 1).get(c - 1));
                    }
                    if (map.get(r - 1).size() > c + 1) {
                        t.setNearbyTile(Tile.SOUTHEAST, map.get(r - 1).get(c + 1));
                    }
                }
                if (map.containsKey(r + 1)){
                    if (map.get(r + 1).size() > c - 1 && c > 0) {
                        t.setNearbyTile(Tile.NORTHWEST, map.get(r + 1).get(c - 1));
                    }
                    if (map.get(r + 1).size() > c + 1) {
                        t.setNearbyTile(Tile.NORTHEAST, map.get(r + 1).get(c + 1));
                    }
                }
                if (map.get(r).size() > c + 1){
                    t.setNearbyTile(Tile.EAST, map.get(r).get(c));
                }
                if (c > 0){
                    t.setNearbyTile(Tile.WEST, map.get(r).get(c - 1));
                }
            }
        }
    }

    public void initializeConnections(){
        initializeVertices();
        initializeEdges();
    }

    private void initializeVertices(){
        int size = map.size()/2;
        System.out.println(size);
        for (int r = -size; r <= size; r++){
            if (r == 0) {
                continue;
            }
            for (int c = 0; c < 11 - 2 * (Math.abs(r) - 1); c++){
                Vertex v = new Vertex(r, c);
                if (c % 2 == 0){
                    if (map.get(r).get(c/2).getId() != 6) {
                        if (r < 0) {
                            v.addAdjacentTile(Tile.NORTH, map.get(r).get(c / 2));
                            map.get(r).get(c / 2).addAdjacentVertex(Vertex.SOUTH, v);
                        } else {
                            v.addAdjacentTile(Tile.SOUTH, map.get(r).get(c / 2));
                            map.get(r).get(c / 2).addAdjacentVertex(Vertex.NORTH, v);
                        }
                    }
                    int r1 = r - r / Math.abs(r);
                    if (map.get(r1).get(c/2).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.SOUTHWEST, map.get(r1).get(c/2));
                            map.get(r1).get(c / 2).addAdjacentVertex(Vertex.NORTHEAST, v);
                        } else {
                            v.addAdjacentTile(Tile.NORTHWEST, map.get(r1).get(c/2));
                            map.get(r1).get(c / 2).addAdjacentVertex(Vertex.SOUTHEAST, v);
                        }
                    }
                    if (map.get(r1).get(c/2 + 1).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.SOUTHEAST, map.get(r1).get(c/2 + 1));
                            map.get(r1).get(c/2 + 1).addAdjacentVertex(Vertex.NORTHWEST, v);
                        } else {
                            v.addAdjacentTile(Tile.NORTHEAST, map.get(r1).get(c/2 + 1));
                            map.get(r1).get(c/2 + 1).addAdjacentVertex(Vertex.SOUTHWEST, v);
                        }
                    }
                } else {
                    if (map.get(r).get((c-1)/2).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.NORTHWEST, map.get(r).get((c-1)/2));
                            map.get(r).get((c-1)/2).addAdjacentVertex(Vertex.SOUTHEAST, v);
                        } else {
                            v.addAdjacentTile(Tile.SOUTHWEST, map.get(r).get((c-1)/2));
                            map.get(r).get((c-1)/2).addAdjacentVertex(Vertex.NORTHEAST, v);
                        }
                    }
                    if (map.get(r).get((c+1)/2).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.NORTHEAST, map.get(r).get((c+1)/2));
                            map.get(r).get((c+1)/2).addAdjacentVertex(Vertex.SOUTHWEST, v);
                        } else {
                            v.addAdjacentTile(Tile.SOUTHEAST, map.get(r).get((c+1)/2));
                            map.get(r).get((c+1)/2).addAdjacentVertex(Vertex.NORTHWEST, v);
                        }
                    }
                    int r1 = r - r / Math.abs(r);
                    if (map.get(r1).get((c + 1)/2).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.SOUTH, map.get(r1).get((c + 1)/2));
                            map.get(r1).get((c + 1)/2).addAdjacentVertex(Tile.NORTH, v);
                        } else {
                            v.addAdjacentTile(Tile.NORTH, map.get(r1).get((c + 1)/2));
                            map.get(r1).get((c + 1)/2).addAdjacentVertex(Tile.SOUTH, v);
                        }
                    }
                }
                vertexManager.addVertex(v, r, c);
            }
        }
        /*
        int size = map.size();
        for (Integer i : new TreeSet<>(map.keySet())){
            if (Math.abs(i) == size/2){
                continue;
            }
            for (Tile t : map.get(i)){
                if (t.getId() == 6){
                    continue;
                }
                int r = t.getR();
                int c = t.getC();
                int r1 = r + c / Math.abs(c);
                if (c == 1){
                    Vertex v = new Vertex(r1, 0);
                    v.addAdjacentTile(Tile.SOUTHEAST, t);
                    vertexManager.addVertex(v, r1, 0);
                }
                Vertex v1 = new Vertex(r, 2 * c - 1);
                v1.addAdjacentTile(Tile.SOUTH);
            }
        }

         */
    }

    private void initializeEdges(){
        Map<Integer, List<Vertex>> vertices = getVertexManager().getMap();
        for (Integer r : vertices.keySet()){
            int edgeR = 2 * r + (r < 0 ? 1 : -1);
            for (int i = 1; i < vertices.get(r).size(); i++){
                System.out.println(r + ", " + i);
                Vertex v1 = vertices.get(r).get(i - 1);
                Vertex v2 = vertices.get(r).get(i);
                Edge e = new Edge(v1, v2);
                edgeManager.addEdge(e, edgeR, i - 1);
                if (i % 2 == 1 && edgeR < 0 || edgeR > 0 && i % 2 == 0){
                    v1.addAdjacentEdge(Edge.NORTHEAST, e);
                    v2.addAdjacentEdge(Edge.SOUTHWEST, e);
                    e.addAdjacentVertex(Vertex.SOUTHWEST, v1);
                    e.addAdjacentVertex(Vertex.NORTHEAST, v2);
                } else {
                    v1.addAdjacentEdge(Edge.NORTHWEST, e);
                    v2.addAdjacentEdge(Edge.SOUTHEAST, e);
                    e.addAdjacentVertex(Vertex.SOUTHEAST, v1);
                    e.addAdjacentVertex(Vertex.NORTHWEST, v2);
                }
                /*
                Set<Tile> tileSet = new TreeSet<>(v1.getAdjacentTiles().values()); //error
                tileSet.retainAll(v2.getAdjacentTiles().values());

                List<Tile> tiles = new ArrayList<>();
                if (edgeR < 0 && i % 2 == 1 || edgeR > 0 && (i & 2) == 0){
                    e.addAdjacentTile(Tile.SOUTHEAST, tiles.get(0));
                    tiles.get(0).addAdjacentEdge(Edge.NORTHWEST, e);
                    if (tiles.size() > 1){
                        e.addAdjacentTile(Tile.NORTHWEST, tiles.get(1));
                        tiles.get(1).addAdjacentEdge(Edge.SOUTHEAST, e);
                    }
                } else {
                    e.addAdjacentTile(Tile.NORTHEAST, tiles.get(0));
                    tiles.get(0).addAdjacentEdge(Edge.SOUTHWEST, e);
                    if (tiles.size() > 1){
                        e.addAdjacentTile(Tile.SOUTHWEST, tiles.get(1));
                        tiles.get(1).addAdjacentEdge(Edge.NORTHEAST, e);
                    }
                }

                 */
            }
        }
        Map<Integer, List<Tile>> tiles = getMap();
        for (Integer r : tiles.keySet()){
            if (tiles.get(r).get(1).getId() == 6){
                continue;
            }
            int i;
            for (i = 1; i < tiles.get(r).size() - 1; i++) {
                Tile tile = tiles.get(r).get(i);
                Vertex v1 = tile.getAdjacentVertices().get(Vertex.NORTHWEST);
                Vertex v2 = tile.getAdjacentVertices().get(Vertex.SOUTHWEST);
                Edge e = new Edge(v1, v2);
                edgeManager.addEdge(e, 2 * r, i - 1);
                v1.addAdjacentEdge(Edge.SOUTH, e);
                v2.addAdjacentEdge(Edge.NORTH, e);
                e.addAdjacentTile(Tile.EAST, tile);
                e.addAdjacentVertex(Vertex.NORTH, v1);
                e.addAdjacentVertex(Vertex.SOUTH, v2);
                tile.addAdjacentEdge(Edge.WEST, e);
                Edge temp = edgeManager.getEdge(2 * r - 1, 2 * (i - 1));
                tile.addAdjacentEdge(Edge.NORTHWEST, temp);
                temp.addAdjacentTile(Tile.SOUTHEAST, tile);
                temp = edgeManager.getEdge(2 * r - 1, 2 * (i - 1) + 1);
                tile.addAdjacentEdge(Edge.NORTHEAST, temp);
                temp.addAdjacentTile(Tile.SOUTHWEST, tile);
                temp = edgeManager.getEdge(2 * r + 1, 2 * (i - 1));
                tile.addAdjacentEdge(Edge.SOUTHWEST, temp);
                temp.addAdjacentTile(Tile.NORTHEAST, tile);
                temp = edgeManager.getEdge(2 * r + 1, 2 * (i - 1) + 1);
                tile.addAdjacentEdge(Edge.SOUTHEAST, temp);
                temp.addAdjacentTile(Tile.NORTHWEST, tile);
            }
            i--;
            Tile tile = tiles.get(r).get(i);
            Vertex v1 = tile.getAdjacentVertices().get(Vertex.NORTHEAST);
            Vertex v2 = tile.getAdjacentVertices().get(Vertex.SOUTHEAST);
            Edge e = new Edge(v1, v2);
            edgeManager.addEdge(e, 2 * r, i);
            v1.addAdjacentEdge(Edge.SOUTH, e);
            v2.addAdjacentEdge(Edge.NORTH, e);
            e.addAdjacentVertex(Vertex.NORTH, v1);
            e.addAdjacentVertex(Vertex.SOUTH, v2);
            e.addAdjacentTile(Tile.WEST, tile);
            tile.addAdjacentEdge(Edge.EAST, e);
        }
    }

    public String toString(){
        return map.toString();
    }

    public boolean contains(int r, int c){
        return map.containsKey(r) && c < map.get(r).size() && map.get(r).get(c) != null;
    }

    protected Map<Integer, List<Tile>> getMap(){
        return map;
    }

    protected VertexManager getVertexManager(){
        return vertexManager;
    }

    protected EdgeManager getEdgeManager(){
        return edgeManager;
    }

    public Tile get(int r, int c){
        return map.get(r).get(c);
    }

}
