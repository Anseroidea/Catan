import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VertexManager {
    private Map<Integer, List<Vertex>> vertices;

    public VertexManager(){
        vertices = new HashMap<>();
    }

    public boolean addVertex(Vertex v, int r, int c){
        if (r == 0) {
            return false;
        }
        if (!vertices.containsKey(r)){
            vertices.put(r, new ArrayList<>());
        }
        List<Vertex> list = vertices.get(r);
        while (list.size() <= c){
            list.add(null);
        }
        list.set(r, v);
        return true;
    }


}