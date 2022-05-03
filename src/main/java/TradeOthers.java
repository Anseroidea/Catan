import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TradeOthers
{
    @FXML
    private Button oneA, oneD, twoA, twoD, threeA, threeD, one, two, three;

    @FXML
    private HBox request, offer;

    private HashMap<String, Integer> get, give;

    public void setGet(HashMap<String, Integer> g)
    {
        get = g;
    }

    public void setGive(HashMap<String, Integer> g)
    {
        give = g;
    }

    //Graphics, enable / disable buttons
    public void setBasics()
    {
        for(Iterator<Map.Entry<String, Integer>> i = get.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry<String, Integer> e = i.next();
            VBox v = new VBox();
            v.getChildren().add(new ImageView(TradeBank.cardGraphics.get(e.getKey())));
            Label amount = new Label();
            amount.setText("" + e.getValue());
            v.getChildren().add(amount);
            v.setVisible(true);
            request.getChildren().add(v);
        }
    }
}
