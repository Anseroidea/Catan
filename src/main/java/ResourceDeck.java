import java.util.HashMap;
import java.util.Map;

public class ResourceDeck {

    public Map<Resource, Integer> resource;

    public ResourceDeck() {
        resource = new HashMap<>();
        for (Resource r : Resource.getResourceList()){
            resource.put(r, 19);
        }
    }

    public void changeCount(Resource r, int i){
        resource.put(r, resource.get(r) + i);
    }

   public boolean getResource(Resource resou, Integer i) {
        int count = getCount(resou);
        if (count >= i) {
            return true;
        }
        return false;
    }

    public int getCount(Resource r) {
        return resource.get(r);
    }
}
