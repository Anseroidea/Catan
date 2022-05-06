import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
        System.out.println("HELOOOOOOOOOOOOOOOO");
        for(Iterator<Map.Entry<String, Integer>> i = get.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry<String, Integer> e = i.next();
            VBox v = new VBox();
            v.setPrefHeight(request.getHeight());
            v.setPrefWidth(request.getWidth() / 5);
            v.setAlignment(Pos.CENTER); //Alignment of its children

            ImageView im = new ImageView(Trade.cardGraphics.get(e.getKey()));
            im.setFitHeight(v.getPrefHeight() - 20); //Different from getHeight()
            im.setFitWidth(v.getPrefWidth());
            v.getChildren().add(im);

            Label amount = new Label();
            amount.setText("" + e.getValue());
            amount.setAlignment(Pos.CENTER);
            v.getChildren().add(amount);

            v.setVisible(true);
            request.getChildren().add(v);
            System.out.println(v.getPrefHeight());
            System.out.println(v.getPrefWidth());
            System.out.println("WEll, " + request.getHeight() + " " + request.getWidth());
        }

        for(Iterator<Map.Entry<String, Integer>> i = give.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry<String, Integer> e = i.next();
            VBox v = new VBox();
            v.setPrefHeight(offer.getHeight());
            v.setPrefWidth(offer.getWidth() / 5);
            v.setAlignment(Pos.CENTER);

            ImageView im = new ImageView(Trade.cardGraphics.get(e.getKey()));
            im.setFitHeight(v.getPrefHeight() - 20);
            im.setFitWidth(v.getPrefWidth());
            v.getChildren().add(im);

            Label amount = new Label();
            amount.setText("" + e.getValue());
            amount.setAlignment(Pos.CENTER);
            v.getChildren().add(amount);

            v.setVisible(true);
            offer.getChildren().add(v);
        }
        System.out.println("BYEEEEEEEEEEEEEEEE");
    }
}
