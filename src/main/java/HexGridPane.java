import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;

public class HexGridPane extends HexGrid{

    private int radius;

    public HexGridPane(int r){
        super();
        radius = r - 4;
    }

    public AnchorPane toPane(){
        AnchorPane ap = new AnchorPane();
        Map<Integer, List<Tile>> map = getMap();
        double maxX = map.keySet().stream().map(r -> (radius/2. * Math.sqrt(3)) * Math.abs(r) + radius * Math.sqrt(3) * map.get(r).size()).max(Comparator.naturalOrder()).get();
        double maxY = new TreeSet<>(map.keySet()).last() * (radius + radius/2.);
        int maxR = Math.max(Math.abs(new TreeSet<>(map.keySet()).first()), Math.abs(new TreeSet<>(map.keySet()).last()));
        ap.setPrefSize(maxX, maxY);
        for (Integer r : map.keySet()){
            for (int c = 0; c < map.get(r).size(); c++){
                if (map.get(r).get(c) != null){
                    double rowCoord = (r + maxR) * (radius + radius/2.);
                    double colCoord = Math.abs(r) * 0.85 * radius + radius * Math.sqrt(3) * c;
                    Image i = map.get(r).get(c).getImage();
                    ImageView im = new ImageView(i);
                    double factor = radius * 2 / i.getHeight();
                    im.setFitHeight(radius * 2);
                    im.setFitWidth(i.getWidth() * factor);
                    StackPane sp = new StackPane(im);
                    if (map.get(r).get(c).getWeightLetter() != null){
                        int weight = map.get(r).get(c).getWeight();
                        Color col = (weight == 6 || weight == 8) ? Color.RED : Color.BLACK;
                        Label l = new Label("" + weight);
                        l.setTextFill(col);
                        HBox h = new HBox();
                        for (int ind = 0; ind < 6 - Math.abs(7 - weight); ind++){
                            h.getChildren().add(new Circle(radius/40., col));
                        }
                        h.setAlignment(Pos.CENTER);
                        l.setAlignment(Pos.CENTER);
                        VBox v = new VBox(l, h);
                        v.setAlignment(Pos.CENTER);
                        sp.getChildren().addAll(new Circle(radius/2., Color.rgb(255, 201, 130)), v);
                    }
                    sp.setLayoutY(rowCoord);
                    sp.setLayoutX(colCoord);
                    ap.getChildren().add(sp);
                }
            }
        }
        return ap;
    }


}