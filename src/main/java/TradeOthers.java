import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.HashMap;

public class TradeOthers
{
    @FXML
    private Button oneA, oneD, twoA, twoD, threeA, threeD, one, two, three;

    private HashMap<String, Integer> get, give;

    public void setGet(HashMap<String, Integer> g)
    {
        get = g;
    }

    public void setGive(HashMap<String, Integer> g)
    {
        give = g;
    }
}
