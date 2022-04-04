import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class HexGridPane<T extends Displayable> extends HexGrid<T>{

    private int radius;

    public <T> HexGridPane(int r){
        super();
        radius = r - 4;
    }

    public AnchorPane toPane(){
        AnchorPane ap = new AnchorPane();
        Map<Integer, List<T>> map = getMap();
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
                    im.setLayoutY(rowCoord);
                    im.setLayoutX(colCoord);
                    ap.getChildren().add(im);
                }
            }
        }
        return ap;
    }






}