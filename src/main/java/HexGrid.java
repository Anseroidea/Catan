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

    public String toString(){
        return map.toString();
    }

    public boolean contains(int r, int c){
        return map.containsKey(r) && c < map.get(r).size() && map.get(r).get(c) != null;
    }

    protected Map<Integer, List<Tile>> getMap(){
        return map;
    }

    public Tile get(int r, int c){
        return map.get(r).get(c);
    }

}
