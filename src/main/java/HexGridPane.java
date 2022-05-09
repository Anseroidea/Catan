import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class HexGridPane extends HexGrid{

    private double radius;

    public HexGridPane(double r){
        super();
        radius = r;
    }

    public double getRadius(){
        return radius;
    }

    public AnchorPane toPane(){
        return toPane(true);
    }

    public AnchorPane toPane(boolean showWater){
        return toPane(showWater, true);
    }

    public AnchorPane toPane(boolean showWater, boolean showRobber){
        AnchorPane ap = new AnchorPane();
        Map<Integer, List<Tile>> map = getMap();
        double maxX = map.keySet().stream().map(r -> (radius/2. * Math.sqrt(3)) * Math.abs(r) + radius * Math.sqrt(3) * map.get(r).size()).max(Comparator.naturalOrder()).get();
        double maxY = new TreeSet<>(map.keySet()).last() * (radius + radius/2.);
        int maxR = Math.max(Math.abs(new TreeSet<>(map.keySet()).first()), Math.abs(new TreeSet<>(map.keySet()).last()));
        ap.setPrefSize(maxX, maxY);
        maxR--;
        for (Integer r : map.keySet()){
            for (int c = (0); c < map.get(r).size(); c++){
                if (map.get(r).get(c) != null && (showWater || map.get(r).get(c).getId() != 6)){
                    double rowCoord = (r + maxR) * (radius + radius/2.);
                    double colCoord = Math.abs(r) * radius * Math.sqrt(3)/2. + radius * Math.sqrt(3) * (c - (showWater ? 0 : 1));
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
                        l.setFont(Font.font(30));
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
                    if (showRobber && map.get(r).get(c).equals(BoardGame.getRobber().getTile())){
                        ImageView e = new ImageView(SwingFXUtils.toFXImage(Robber.getGraphic(), null));
                        e.setFitHeight(72);
                        e.setFitWidth(36);
                        sp.getChildren().add(e);
                    }
                    sp.setLayoutY(rowCoord);
                    sp.setLayoutX(colCoord);
                    ap.getChildren().add(sp);
                } else if (map.get(r).get(c).isHarbor()){
                    double rowCoord = (r + maxR) * (radius + radius/2.);
                    double colCoord = Math.abs(r) * radius * Math.sqrt(3)/2. + radius * Math.sqrt(3) * (c - 1);
                    WritableImage i = SwingFXUtils.toFXImage(Harbor.harborImage, null);
                    ImageView iv = new ImageView(i);
                    double factor = radius * 2 / i.getHeight();
                    iv.setFitHeight(radius * 2);
                    iv.setFitWidth(i.getWidth() * factor);
                    StackPane sp = new StackPane(iv);
                    sp.setLayoutX(colCoord);
                    sp.setLayoutY(rowCoord);
                    iv.setRotate(map.get(r).get(c).getHarbor().getRotation());
                    if (map.get(r).get(c).getHarbor().getResource() != null){
                        WritableImage image = SwingFXUtils.toFXImage(map.get(r).get(c).getHarbor().getResource().getGraphic(), null);
                        ImageView res = new ImageView(image);
                        factor = radius/ 1.5 / image.getHeight();
                        res.setFitHeight(radius/1.5);
                        res.setFitWidth(image.getWidth() * factor);
                        sp.getChildren().add(res);
                    } else {
                        WritableImage image = SwingFXUtils.toFXImage(Harbor.getThreeToOne(), null);
                        ImageView res = new ImageView(image);
                        factor = radius/ 1.5 / image.getHeight();
                        res.setFitHeight(radius/1.5);
                        res.setFitWidth(image.getWidth() * factor);
                        sp.getChildren().add(res);
                    }
                    ap.getChildren().add(sp);
                }
            }
        }
        return ap;
    }


}