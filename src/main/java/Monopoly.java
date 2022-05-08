import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private Button confirm, back;

    @FXML
    private StackPane a, b, c, d, e;

    private static String get;

    private Map<String, String> id = Map.of(
            "a", "brick",
            "b", "wheat",
            "c", "forest",
            "d", "ore",
            "e", "wool"
    );

    public Monopoly() throws Exception
    {
        /*
        TradeBank.cardGraphics = new HashMap<>();
        //System.out.println(1);
        String[] temp = Tile.tileTypes;
        //System.out.println(2);
        for(int i = 0; i < 5; i++)
        {
            //System.out.println(i + " ho");
            BufferedImage im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/cards/resources--" + temp[i].toLowerCase() + ".png")));
            //System.out.println("li");
            TradeBank.cardGraphics.put(temp[i].toLowerCase(), SwingFXUtils.toFXImage(im, null));
            //System.out.println("day");
        }
        //System.out.println(3);

         */
    }
    @FXML
    public void initialize()
    {
        /*
        //System.out.println(4);
        ((ImageView)a.getChildren().get(0)).setImage(TradeBank.cardGraphics.get("brick"));
        ((ImageView)b.getChildren().get(0)).setImage(TradeBank.cardGraphics.get("wheat"));
        ((ImageView)c.getChildren().get(0)).setImage(TradeBank.cardGraphics.get("forest"));
        ((ImageView)d.getChildren().get(0)).setImage(TradeBank.cardGraphics.get("ore"));
        ((ImageView)e.getChildren().get(0)).setImage(TradeBank.cardGraphics.get("wool"));
        //System.out.println(5);

         */
    }

    public void confirmRob(MouseEvent mouseEvent)
    {
        Player p = TurnManager.getCurrentPlayer();
        /*Player p = new Player("Red", 1, Color.RED);
        p.changeCards(new Resource("brick"), 4);
        p.changeCards(new Resource("wheat"), 3);
        p.changeCards(new Resource("forest"), 4);
        p.changeCards(new Resource("ore"), 4);
        p.changeCards(new Resource("sheep"), 4);*/
        Map<Resource, Integer> res = p.getResources();

        Resource gain = new Resource(get);
        int total = 0;
        for(Player victim : TurnManager.getPlayerList())
        {
            Integer temp = victim.getResources().get(gain);
            if((temp != null))
            {
                total += temp;
                victim.changeCards(gain, -temp);
            }
        }

        p.changeCards(gain, total);
        get = "Nothing";
        ((Stage)((Button)mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void addBorder(MouseEvent mouseEvent)
    {
        Pane temp = (Pane)mouseEvent.getSource();
        if(!id.get(temp.getId()).equals(get))
            temp.setStyle("-fx-border-color: black");
    }

    public void removeBorder(MouseEvent mouseEvent)
    {
        Pane temp = (Pane)mouseEvent.getSource();
        if(!id.get(temp.getId()).equals(get))
            temp.setStyle("-fx-border-color: transparent");
    }

    public void select(MouseEvent mouseEvent)
    {
        clearBorders();
        Pane chosen = (Pane)mouseEvent.getSource();
        highlight(chosen);
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

    private void highlight(Pane chosen)
    {
        chosen.setStyle("-fx-border-color: blue");
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
