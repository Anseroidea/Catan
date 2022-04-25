import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TradeBank
{
    @FXML
    private Button confirmTrade, changeRequest;

    @FXML
    private StackPane a, b, c, d, e;

    private String give, get;

    public static Map<String, Image> cardGraphics;

    public TradeBank() throws Exception
    {
        cardGraphics = new HashMap<>();
        //System.out.println(1);
        String[] temp = Tile.tileTypes;
        //System.out.println(2);
        for(int i = 0; i < temp.length; i++)
        {
            if (i == 1 || i == 6)
                continue;
            //System.out.println(i + " ho");
            BufferedImage im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/cards/resources--" + temp[i].toLowerCase() + ".png")));
            //System.out.println("li");
            cardGraphics.put(temp[i].toLowerCase(), SwingFXUtils.toFXImage(im, null));
            //System.out.println("day");
        }
        //System.out.println(3);
    }
    @FXML
    public void initialize()
    {
        //System.out.println(4);
        ((ImageView)a.getChildren().get(0)).setImage(cardGraphics.get("brick"));
        ((ImageView)b.getChildren().get(0)).setImage(cardGraphics.get("wheat"));
        ((ImageView)c.getChildren().get(0)).setImage(cardGraphics.get("forest"));
        ((ImageView)d.getChildren().get(0)).setImage(cardGraphics.get("ore"));
        ((ImageView)e.getChildren().get(0)).setImage(cardGraphics.get("sheep"));
        //System.out.println(5);
    }

    public void confirmBankTrade(MouseEvent mouseEvent)
    {
        //Player p = TurnManager.getCurrentPlayer();
        Player p = new Player("eh", 3, Color.RED);
        p.changeCards(new Resource("brick"), 4);
        p.changeCards(new Resource("wheat"), 4);
        p.changeCards(new Resource("forest"), 4);
        p.changeCards(new Resource("ore"), 4);
        p.changeCards(new Resource("sheep"), 4);
        Map<Resource, Integer> res = p.getResources();

        Resource loss = new Resource(give);
        if(res.get(loss) < 4)
        {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Not enough cards!");
            a.show();
        }
        else
        {
            res.put(loss, res.get(loss) - 4);
            Resource gain = new Resource(get);
            res.put(gain, res.get(gain) + 1);
            ((Stage)((Button)mouseEvent.getSource()).getScene().getWindow()).close();
        }
    }

    public void addBorder(MouseEvent mouseEvent)
    {
        ((Pane)mouseEvent.getSource()).setStyle("-fx-border-color: black");
    }

    public void removeBorder(MouseEvent mouseEvent)
    {
        ((Pane)mouseEvent.getSource()).setStyle("-fx-border-color: transparent");
    }

    public void select(MouseEvent mouseEvent)
    {
        clearBorders();
        Pane chosen = (Pane)mouseEvent.getSource();
        chosen.setStyle("-fx-border-color: blue");
        get = switch(chosen.getId())
        {
            case "a" -> "brick";
            case "b" -> "wheat";
            case "c" -> "forest";
            case "d" -> "ore";
            case "e" -> "sheep";
            default -> "Nothing";
        };
        System.out.println(get);
    }

    private void clearBorders()
    {
        a.setStyle("-fx-border-color: transparent");
        b.setStyle("-fx-border-color: transparent");
        c.setStyle("-fx-border-color: transparent");
        d.setStyle("-fx-border-color: transparent");
        e.setStyle("-fx-border-color: transparent");
    }
}
