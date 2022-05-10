import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Buildings {

    public HBox hbox;
    private static BufferedImage road;

    static {
        try {
            road = ImageIO.read(Buildings.class.getResourceAsStream("images/cards/road.png"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Button back;

    public void initPopUp(){
        hbox.getChildren().clear();
        hbox.setAlignment(Pos.TOP_CENTER);
        for (Player p : TurnManager.getPlayerList()){
            VBox v = new VBox(10);
            HBox h = new HBox();
            v.setAlignment(Pos.TOP_CENTER);
            ImageView e = new ImageView(SwingFXUtils.toFXImage(road, null));
            e.setFitHeight(80);
            e.setFitWidth(80);
            Label l = new Label(15 - p.getRoads().size() + "");
            l.setFont(Font.font(30));
            h.getChildren().addAll(e, l);
            h.setSpacing(10);
            h.setAlignment(Pos.CENTER);
            v.getChildren().add(h);
            StackPane s = new StackPane();
            s.setPrefSize(80, 80);
            Circle e1 = new Circle(40, p.getColor());
            e1.setStroke(Color.BLACK);
            e1.setStrokeWidth(5);
            s.getChildren().add(e1);
            HBox h1 = new HBox();
            Label l1 = new Label(5 - p.getSettlements().stream().filter(Settlement::isSettlement).count() + "");
            l1.setFont(Font.font(30));
            h1.getChildren().addAll(s, l1);
            h1.setSpacing(10);
            h1.setAlignment(Pos.CENTER);
            StackPane s1 = new StackPane();
            s1.setPrefSize(80, 80);
            Rectangle e2 = new Rectangle(80, 80, p.getColor());
            e2.setStroke(Color.BLACK);
            e2.setStrokeWidth(5);
            s1.getChildren().add(e2);
            HBox h2 = new HBox();
            Label l2 = new Label(4 - p.getSettlements().stream().filter(se -> !se.isSettlement()).count() + "");
            l2.setFont(Font.font(30));
            h2.getChildren().addAll(s1, l2);
            h2.setSpacing(10);
            h2.setAlignment(Pos.CENTER);
            Label playerName = new Label(p.getName());
            playerName.setFont(Font.font(20));
            v.getChildren().addAll(h1, h2, playerName);
            hbox.getChildren().add(v);
        }
    }

    public void back(ActionEvent actionEvent) {
        Stage stage = (Stage) hbox.getScene().getWindow();
        stage.getScene().setRoot(new AnchorPane());
        stage.close();
    }
}
