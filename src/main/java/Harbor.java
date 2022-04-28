
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Harbor {

    public Resource resource;
    public Tile tile;

    public Harbor(Resource r, Tile t) {
        resource = r;
        tile = t;
    }

    public Resource getResource() {
        return resource;
    }
}
