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
            for (int i = 0; i <= c; i++){
                edges.get(r).add(null);
            }
            edges.get(r).set(c, e);
        }
    }

    public List<Edge> getAllEdges() {
        List<Edge> results = new ArrayList<>();
        for (Integer i : edges.keySet()){
            results.addAll(edges.get(i));
        }
        return results;
    }
}
