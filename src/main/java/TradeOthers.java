import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class TradeOthers
{
    public HBox requestHbox;
    public HBox offerHbox;

    @FXML
    private Button oneA, oneD, twoA, twoD, threeA, threeD, one, two, three;

    @FXML
    private HBox request, offer;

    @FXML
    private VBox consider;

    private Map<Resource, Integer> get, give;
    private Trade t;
    private Map<Player, Integer> playerStatus;
    private final int PENDING = 0;
    private final int ACCEPTED = 1;
    private final int REJECTED = -1;

    public static BufferedImage check, cross;

    static {
        try {
            check = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/trade/si-removebg-preview.png")));
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("Reading the check symbol didn't work");
        }

        try {
            cross = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/trade/Nay-removebg-preview.png")));
        } catch (Exception e) {
            System.out.println("Reading the nay symbol didn't work");
        }
    }

    public void initPopUp(Map<Resource, Integer> request, Map<Resource, Integer> offer, Trade t){
        get = request;
        give = offer;
        this.t = t;
        playerStatus = new HashMap<>();
        for (Player p : TurnManager.getPlayerList()){
            playerStatus.put(p, PENDING);
        }
        initReqOff();
        refreshButtons();
    }

    private void refreshButtons() {
        consider.getChildren().clear();
        consider.setAlignment(Pos.CENTER);
        consider.setSpacing(20);
        for (Player p : TurnManager.getPlayerList()){
            if (p.equals(TurnManager.getCurrentPlayer())){
                continue;
            }
            Label name = new Label(p.getName());
            name.setFont(Font.font(30));
            consider.getChildren().add(name);
            HBox h = new HBox();
            h.setAlignment(Pos.CENTER_LEFT);
            Button opinion = new Button();
            opinion.setPrefSize(100, 100);
            if (playerStatus.get(p) == -1){
                ImageView iv = new ImageView(SwingFXUtils.toFXImage(cross, null));
                iv.setFitHeight(100);
                iv.setFitWidth(100);
                opinion.setGraphic(iv);
                opinion.setDisable(true);
            } else if (playerStatus.get(p) == 0){
                opinion.setVisible(false);
            } else {
                ImageView iv = new ImageView(SwingFXUtils.toFXImage(check, null));
                iv.setFitHeight(100);
                iv.setFitWidth(100);
                opinion.setGraphic(iv);
                opinion.setOnMouseClicked((event) -> {
                    accept(p);
                });
            }
            Button accept = new Button();
            ImageView ac = new ImageView(SwingFXUtils.toFXImage(check, null));
            ac.setFitHeight(200);
            ac.setFitWidth(200);
            accept.setGraphic(ac);
            accept.setOnMouseClicked((event) -> {
                playerStatus.put(p, 1);
                refreshButtons();
            });
            boolean canAccept = true;
            for (Resource r : get.keySet()){
                if (p.getResources().get(r) < get.get(r)) {
                    System.out.println(r.getResource());
                    System.out.println(get);
                    System.out.println(p.getResources());
                    canAccept = false;
                }
            }
            accept.setDisable(!canAccept);
            Button reject = new Button();
            ImageView re = new ImageView(SwingFXUtils.toFXImage(cross, null));
            re.setFitHeight(200);
            re.setFitWidth(200);
            reject.setGraphic(re);
            reject.setOnMouseClicked((event) -> {
                playerStatus.put(p, -1);
                refreshButtons();
            });
            h.getChildren().addAll(opinion, accept, reject);
            consider.getChildren().add(h);
        }
    }

    private void accept(Player p) {
        for (Resource r : give.keySet()){
            TurnManager.getCurrentPlayer().changeCards(r, -1 * give.get(r));
            TurnManager.getCurrentPlayer().changeCards(r, get.get(r));
            p.changeCards(r, give.get(r));
            p.changeCards(r, -1 * get.get(r));
        }
        StringBuilder s = new StringBuilder();
        s.append(TurnManager.getCurrentPlayer().getName()).append(" traded ");
        StringJoiner giveString = new StringJoiner(", ");
        StringJoiner getString = new StringJoiner(", ");
        for (Resource r : Resource.getResourceList()){
            if (give.get(r) > 0){
                giveString.add(give.get(r) + " " + r.getResource());
            }
            if (get.get(r) > 0) {
                getString.add(get.get(r) + " " + r.getResource());
            }
        }
        s.append(giveString).append(" to ").append(p.getName()).append(" for ").append(getString);
        TurnManager.addAction(s.toString());
        back(null);
        t.back(null);
        GraphicsManager.refreshDisplay();
    }

    public void initReqOff(){
        for (int i = 0; i < 5; i++){
            VBox v = (VBox) requestHbox.getChildren().get(i);
            if (v.getChildren().size() > 1){
                v.getChildren().remove(1);
            }
            Label l = new Label(get.get(Resource.getResourceList().get(i)) + "");
            l.setFont(Font.font(20));
            v.getChildren().add(l);
            VBox v1 = (VBox) offerHbox.getChildren().get(i);
            if (v1.getChildren().size() > 1){
                v1.getChildren().remove(1);
            }
            Label l1 = new Label(give.get(Resource.getResourceList().get(i)) + "");
            l.setFont(Font.font(20));
            v1.getChildren().add(l1);
        }
    }



    public void back(MouseEvent mouseEvent) {
        Stage thisStage = (Stage) requestHbox.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }
}
