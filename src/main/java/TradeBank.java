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
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class TradeBank
{
    @FXML
    private Button confirmTrade, changeRequest;

    @FXML
    private StackPane a, b, c, d, e, a1, b1, c1, d1, e1;

    private String give, get, givePane, getPane;

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
        ((ImageView)a1.getChildren().get(0)).setImage(cardGraphics.get("brick"));
        ((ImageView)b1.getChildren().get(0)).setImage(cardGraphics.get("wheat"));
        ((ImageView)c1.getChildren().get(0)).setImage(cardGraphics.get("forest"));
        ((ImageView)d1.getChildren().get(0)).setImage(cardGraphics.get("ore"));
        ((ImageView)e1.getChildren().get(0)).setImage(cardGraphics.get("sheep"));
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
        if(get.equals(give))
        {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Cannot give and get the same resource!");
            a.show();
        }
        else if(res.get(loss) < 4)
        {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Not enough cards!");
            a.show();
        }
        else
        {
            res.put(loss, res.get(loss) - 4);
            Resource gain = new Resource(get);
            res.put(gain, res.get(gain) + 1);

            for(Iterator<Map.Entry<Resource, Integer>> e = res.entrySet().iterator(); e.hasNext();)
            {
                Map.Entry<Resource, Integer> temp = e.next();
                System.out.println(temp.getKey().getResource() + " " + temp.getValue());
            }
            give = get = givePane = getPane = " ";
            ((Stage)((Button)mouseEvent.getSource()).getScene().getWindow()).close();
        }
    }

    public void addBorder(MouseEvent mouseEvent)
    {
        Pane clicked = ((Pane)mouseEvent.getSource());
        if(!(clicked.getId().equals(getPane) || clicked.getId().equals(givePane)))
            ((Pane)mouseEvent.getSource()).setStyle("-fx-border-color: black");
    }

    public void removeBorder(MouseEvent mouseEvent)
    {
        Pane clicked = ((Pane)mouseEvent.getSource());
        if(!(clicked.getId().equals(getPane) || clicked.getId().equals(givePane)))
            ((Pane)mouseEvent.getSource()).setStyle("-fx-border-color: transparent");
    }

    public void select(MouseEvent mouseEvent)
    {
        clearGiveBorders();
        Pane chosen = (Pane)mouseEvent.getSource();
        chosen.setStyle("-fx-border-color: blue");
        give = switch(chosen.getId())
        {
            case "a" -> "brick";
            case "b" -> "wheat";
            case "c" -> "forest";
            case "d" -> "ore";
            case "e" -> "sheep";
            default -> "Nothing";
        };
        givePane = chosen.getId();
        System.out.println(give);
    }

    private void clearGiveBorders()
    {
        a.setStyle("-fx-border-color: transparent");
        b.setStyle("-fx-border-color: transparent");
        c.setStyle("-fx-border-color: transparent");
        d.setStyle("-fx-border-color: transparent");
        e.setStyle("-fx-border-color: transparent");
    }

    public void choose(MouseEvent mouseEvent)
    {
        clearGetBorders();
        Pane chosen = (Pane)mouseEvent.getSource();
        chosen.setStyle("-fx-border-color: blue");
        get = switch(chosen.getId())
                {
                    case "a1" -> "brick";
                    case "b1" -> "wheat";
                    case "c1" -> "forest";
                    case "d1" -> "ore";
                    case "e1" -> "sheep";
                    default -> "Nothing";
                };
        getPane = chosen.getId();
        System.out.println(get);
    }

    private void clearGetBorders()
    {
        a1.setStyle("-fx-border-color: transparent");
        b1.setStyle("-fx-border-color: transparent");
        c1.setStyle("-fx-border-color: transparent");
        d1.setStyle("-fx-border-color: transparent");
        e1.setStyle("-fx-border-color: transparent");
    }
}
