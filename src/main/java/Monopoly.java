import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Monopoly
{
    public Label woolLabel;
    public Label wheatLabel;
    public Label oreLabel;
    public Label lumberLabel;
    public Label brickLabel;
    @FXML
    private Button confirm, back;

    @FXML
    private StackPane a, b, c, d, e;

    private static Resource get;

    private Map<String, Integer> id = Map.of(
            "a", 0,
            "b", 1,
            "c", 2,
            "d", 3,
            "e", 4
    );

    public void initPlayerInfo(){
        get = null;
        confirm.setDisable(true);
        brickLabel.setText(TurnManager.getCurrentPlayer().getResources().get(Resource.BRICK) + "");
        lumberLabel.setText(TurnManager.getCurrentPlayer().getResources().get(Resource.LUMBER) + "");
        oreLabel.setText(TurnManager.getCurrentPlayer().getResources().get(Resource.ORE) + "");
        wheatLabel.setText(TurnManager.getCurrentPlayer().getResources().get(Resource.WHEAT) + "");
        woolLabel.setText(TurnManager.getCurrentPlayer().getResources().get(Resource.WOOL) + "");
    }

    public void confirmRob(ActionEvent actionEvent)
    {
        Player p = TurnManager.getCurrentPlayer();
        /*Player p = new Player("Red", 1, Color.RED);
        p.changeCards(new Resource("brick"), 4);
        p.changeCards(new Resource("wheat"), 3);
        p.changeCards(new Resource("forest"), 4);
        p.changeCards(new Resource("ore"), 4);
        p.changeCards(new Resource("sheep"), 4);*/

        TurnManager.getCurrentPlayer().useDevelopment(TurnManager.getCurrentPlayer().getDevelopmentCards().stream().filter(d -> d.getId() == 8).findFirst().get());
        Resource gain = get;
        int total = 0;
        for(Player victim : TurnManager.getPlayerList())
        {
            Integer temp = victim.getResources().get(gain);
            if((temp > 0))
            {
                total += temp;
                victim.changeCards(gain, -temp);
            }
        }
        p.changeCards(gain, total);
        if (total != 0){
            TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " stole all the " + gain.getResource() + ".");
        } else {
            TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " tried to steal all the " + gain.getResource() + ". There wasn't any!");
        }
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        ((Button) actionEvent.getSource()).getScene().setRoot(new AnchorPane());
        stage.close();
    }

    public void addBorder(MouseEvent mouseEvent)
    {
        confirm.setDisable(false);
        clearSelection();
        a.setStyle("-fx-border-color: black;");
        get = Resource.BRICK;
    }
    public void addBorder1(MouseEvent mouseEvent)
    {
        confirm.setDisable(false);
        clearSelection();
        b.setStyle("-fx-border-color: black;");
        get = Resource.LUMBER;
    }
    public void addBorder2(MouseEvent mouseEvent)
    {
        confirm.setDisable(false);
        clearSelection();
        c.setStyle("-fx-border-color: black;");
        get = Resource.ORE;
    }
    public void addBorder3(MouseEvent mouseEvent)
    {
        confirm.setDisable(false);
        clearSelection();
        d.setStyle("-fx-border-color: black;");
        get = Resource.WHEAT;
    }
    public void addBorder4(MouseEvent mouseEvent)
    {
        confirm.setDisable(false);
        clearSelection();
        e.setStyle("-fx-border-color: black;");
        get = Resource.WOOL;
    }

    public void back(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        ((Button) actionEvent.getSource()).getScene().setRoot(new AnchorPane());
        stage.close();
    }

    public void clearSelection(){
        a.setStyle("-fx-border-color: transparent;");
        b.setStyle("-fx-border-color: transparent;");
        c.setStyle("-fx-border-color: transparent;");
        d.setStyle("-fx-border-color: transparent;");
        e.setStyle("-fx-border-color: transparent;");
    }
}
