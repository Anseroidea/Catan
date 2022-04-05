import java.util.Map;

public class ResourceDeck {

    public Map<Resource, Integer> resource;

    public ResourceDeck() {
    }

   public boolean getResource(Resource resou, Integer i) {
        int count = resource.get(resou);
        if (count >= i) {
            return true;
        }
        return false;
    }

    public int getCount(Resource r) {
        int count = 0;
        for (Resource res : resource.keySet()) {
            if (res.equals(r)) {
                count++;
            }
        }
        return count;
    }
}
