import java.awt.*;
import java.util.Map;

public class City extends Settlement {
    public Map<Integer, Image> graphic;

    public City() {

    }

    public boolean isSettlement() {
        return false;
    }

}
