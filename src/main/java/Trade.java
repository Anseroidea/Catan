import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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

    public static Map<String, Image> cardGraphics;

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

    public void toTradeOthers(ActionEvent actionEvent) {

    }

    public void tradeWithOthers(MouseEvent mouseEvent) {

    }

    public void tradeWithBank(MouseEvent mouseEvent) {

    }

    public void WoodCards(MouseEvent mouseEvent) {
    }

    public void BrickCards(MouseEvent mouseEvent) {
    }

    public void OreCards(MouseEvent mouseEvent) {
    }

    public void WheatCards(MouseEvent mouseEvent) {
    }
}
