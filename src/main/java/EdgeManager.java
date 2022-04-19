import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdgeManager {
    private Map<Integer, List<Edge>> edges;

    public EdgeManager(){
        edges = new HashMap<>();
    }

    public void addEdge(Edge e, int r, int c){
        edges.putIfAbsent(r, new ArrayList<>());
        if (edges.get(r).size() < c){
            edges.get(r).add(e);
        } else {
            edges.get(r).set(c, e);
        }
    }
}
