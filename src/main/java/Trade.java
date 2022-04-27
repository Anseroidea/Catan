import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Trade
{
    @FXML
    private VBox a, b, c, d, e, a1, b1, c1, d1, e1;

    @FXML
    private Button confirm;

    public static Map<String, Image> cardGraphics;

    private String give, get, givePane, getPane;


    public Trade() throws Exception
    {
        cardGraphics = new HashMap<>();

        String[] temp = Tile.tileTypes;

        for(int i = 0; i < temp.length; i++)
        {
            if (i == 1 || i == 6)
                continue;

            BufferedImage im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/cards/resources--" + temp[i].toLowerCase() + ".png")));

            cardGraphics.put(temp[i].toLowerCase(), SwingFXUtils.toFXImage(im, null));

        }

    }

    @FXML
    public void initialize()
    {
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

    public void back(MouseEvent mouseEvent) {
        ProgramState.setCurrentState(ProgramState.MAIN);
        CatanApplication c = new CatanApplication();
        c.updateDisplay();
    }

    public void toTradeOthers(ActionEvent actionEvent)
    {
        ((Stage)((Button)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void addBorder(MouseEvent mouseEvent)
    {
        System.out.println("border");
        VBox clicked = ((VBox)mouseEvent.getSource());
        if(!(clicked.getId().equals(getPane) || clicked.getId().equals(givePane)))
            clicked.setStyle("-fx-border-color: black");
    }

    public void removeBorder(MouseEvent mouseEvent)
    {
        VBox clicked = ((VBox)mouseEvent.getSource());
        if(!(clicked.getId().equals(getPane) || clicked.getId().equals(givePane)))
            clicked.setStyle("-fx-border-color: transparent");
    }

    public void select(MouseEvent mouseEvent)
    {
        clearGiveBorders();
        VBox chosen = (VBox)mouseEvent.getSource();
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
        VBox chosen = (VBox)mouseEvent.getSource();
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
