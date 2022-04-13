import java.util.*;
import java.util.stream.IntStream;

public class HexGrid {

    private Map<Integer, List<Tile>> map;
    private VertexManager vertexManager;

    public HexGrid(){
         map = new TreeMap<>();
         vertexManager = new VertexManager();
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

    public void initializeVertices(){
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
                        } else {
                            v.addAdjacentTile(Tile.SOUTH, map.get(r).get(c / 2));
                        }
                    }
                    int r1 = r - r / Math.abs(r);
                    if (map.get(r1).get(c/2).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.SOUTHWEST, map.get(r1).get(c/2));
                        } else {
                            v.addAdjacentTile(Tile.NORTHWEST, map.get(r1).get(c/2));
                        }
                    }
                    if (map.get(r1).get(c/2 + 1).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.SOUTHEAST, map.get(r1).get(c/2 + 1));
                        } else {
                            v.addAdjacentTile(Tile.NORTHEAST, map.get(r1).get(c/2 + 1));
                        }
                    }
                } else {
                    if (map.get(r).get((c-1)/2).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.NORTHWEST, map.get(r).get((c-1)/2));
                        } else {
                            v.addAdjacentTile(Tile.SOUTHWEST, map.get(r).get((c-1)/2));
                        }
                    }
                    if (map.get(r).get((c+1)/2).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.NORTHEAST, map.get(r).get((c+1)/2));
                        } else {
                            v.addAdjacentTile(Tile.SOUTHEAST, map.get(r).get((c+1)/2));
                        }
                    }
                    int r1 = r - r / Math.abs(r);
                    if (map.get(r).get((c + 1)/2).getId() != 6){
                        if (r < 0){
                            v.addAdjacentTile(Tile.SOUTH, map.get(r).get((c + 1)/2));
                        } else {
                            v.addAdjacentTile(Tile.NORTH, map.get(r).get((c + 1)/2));
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

    public Tile get(int r, int c){
        return map.get(r).get(c);
    }

}
