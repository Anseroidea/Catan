import java.util.*;
import java.util.stream.IntStream;

public class HexGrid<T> {

    private Map<Integer, List<T>> map;

    public <T> HexGrid(){
         map = new TreeMap<>();
    }

    public void add(T t, int r, int c){
        if (c < 0){
            return;
        }
        if (!map.containsKey(r)){
            List<T> list = new ArrayList<>();
            IntStream.range(0, c).forEach(i -> list.add(null));
            list.add(t);
            map.put(r, list);
        } else {
            List<T> list = map.get(r);
            if (list.size() > c){
                list.set(c, t);
            } else {
                IntStream.range(list.size(), c).forEach(i -> list.add(null));
                list.add(t);
            }
        }
    }

    public String toString(){
        return map.toString();
    }

    protected Map<Integer, List<T>> getMap(){
        return map;
    }
}
