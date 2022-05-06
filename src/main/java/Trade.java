import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Trade
{
    @FXML
    private StackPane a, b, c, d, e, a1, b1, c1, d1, e1;

    @FXML
    private Button confirm, p0, m0, p1, m1, p2, m2, p3, m3, p4, m4, p5, m5, p6, m6, p7, m7, p8, m8, p9, m9;

    @FXML
    private TextFlow t1;

    public static Map<String, Image> cardGraphics;

    private String give, get, givePane, getPane;

    private int[] giveThese, getThese;

    public static String[] match = new String[] {"brick", "wheat", "forest", "ore", "sheep"};

    private TradeBank bank;

    static
    {
        cardGraphics = new HashMap<>();

        for(int i = 0; i < 5; i++)
        {
            BufferedImage im = null;
            try {
                im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/cards/resources--" + match[i] + ".png")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            cardGraphics.put(match[i], SwingFXUtils.toFXImage(im, null));

        }
    }

    public Trade() throws Exception
    {
        /*cardGraphics = new HashMap<>();

        for(int i = 0; i < 5; i++)
        {
            BufferedImage im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/cards/resources--" + match[i] + ".png")));

            cardGraphics.put(match[i], SwingFXUtils.toFXImage(im, null));

        }

        giveThese = new int[5];
        getThese = new int[5];
        bank = new TradeBank();*/
    }

    @FXML
    public void initialize()
    {
        giveThese = new int[5];
        getThese = new int[5];
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

    /*
    Other players accept or decline
     */
    public void toTradeOthers(ActionEvent actionEvent) throws IOException
    {
        boolean allTheSame = true;
        boolean emptyGive = true;
        for(int i = 0; i < 5; i++)
        {
            if ((giveThese[i] == 0 && getThese[i] > 0) || (getThese[i] == 0 && giveThese[i] > 0))
                allTheSame = false;
            if (giveThese[i] > 0)
                emptyGive = false;
        }

        if(emptyGive)
        {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("You MUST give something in trade!");
            a.show();
        }
        else if(allTheSame)
        {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Cannot give and get the same resource(s)!");
            a.show();
        }
        else
        {
            FXMLLoader fl = new FXMLLoader(CatanApplication.class.getResource("/fxml/" + PopUp.TRADEOTHERS.name().toLowerCase() + ".fxml"));
            AnchorPane ap = fl.load();
            PopUp.TRADEOTHERS.setPane(ap);
            PopUp.TRADEOTHERS.setController(fl.getController());
            PopUp.TRADEOTHERS.load();

            HashMap<String, Integer> temp = new HashMap<>();
            for(int i = 0; i < 5; i++)
                if(getThese[i] > 0)
                    temp.put(match[i], getThese[i]);

            ((TradeOthers)PopUp.TRADEOTHERS.getController()).setGet(temp);

            HashMap<String, Integer> temp2 = new HashMap<>();
            for(int i = 0; i < 5; i++)
                if(giveThese[i] > 0)
                    temp2.put(match[i], giveThese[i]);

            ((TradeOthers)PopUp.TRADEOTHERS.getController()).setGive(temp2);

            ((TradeOthers)PopUp.TRADEOTHERS.getController()).setBasics();
            //((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
        }
    }

    public void addBorder(MouseEvent mouseEvent)
    {
        //System.out.println("border");
        StackPane clicked = ((StackPane)mouseEvent.getSource());
        //System.out.println(clicked.getId());
        if(!(clicked.getId().equals(getPane) || clicked.getId().equals(givePane)))
            clicked.setStyle("-fx-border-color: black");
    }

    public void removeBorder(MouseEvent mouseEvent)
    {
        //System.out.println("Left");
        StackPane clicked = ((StackPane)mouseEvent.getSource());
        //System.out.println(clicked.getId());
        if(!(clicked.getId().equals(getPane) || clicked.getId().equals(givePane)))
            clicked.setStyle("-fx-border-color: transparent");
    }

    public void select(MouseEvent mouseEvent)
    {
        //System.out.println("Click");
        clearGiveBorders();
        StackPane chosen = (StackPane)mouseEvent.getSource();
        //System.out.println(chosen.getId());
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
        //System.out.println("Click");
        clearGetBorders();
        StackPane chosen = (StackPane)mouseEvent.getSource();
        //System.out.println(chosen.getId());
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
        //System.out.println(get);
    }

    private void clearGetBorders()
    {
        a1.setStyle("-fx-border-color: transparent");
        b1.setStyle("-fx-border-color: transparent");
        c1.setStyle("-fx-border-color: transparent");
        d1.setStyle("-fx-border-color: transparent");
        e1.setStyle("-fx-border-color: transparent");
    }

    public void add(MouseEvent mouseEvent)
    {
        HBox h = (HBox)((Button)mouseEvent.getSource()).getParent();
        TextFlow t = (TextFlow)h.getChildren().get(1);
        int id = t.getId().charAt(1) - '0';
        System.out.println(Arrays.toString(getThese));
        System.out.println(Arrays.toString(giveThese));
        System.out.println("CLICKED!!! " + id);
        if(id >= 5)
        {
            Player p = new Player("eh", 3, Color.RED);
            p.changeCards(new Resource("brick"), 4);
            p.changeCards(new Resource("wheat"), 4);
            p.changeCards(new Resource("forest"), 4);
            p.changeCards(new Resource("ore"), 4);
            p.changeCards(new Resource("sheep"), 4);
            if(p.getResources().get(new Resource(match[id - 5])) <= giveThese[id - 5])
                System.out.println("You don't have enough cards!");
            else {
                giveThese[id - 5]++;
                ((Text)t.getChildren().get(0)).setText("" + giveThese[id - 5]);
            }
        }
        else if(getThese[id] < 19)
        {
            getThese[id]++;
            ((Text)t.getChildren().get(0)).setText("" + getThese[id]);
        }

        System.out.println(Arrays.toString(getThese));
        System.out.println(Arrays.toString(giveThese));
    }

    public void minus(MouseEvent mouseEvent)
    {
        HBox h = (HBox)((Button)mouseEvent.getSource()).getParent();
        TextFlow t = (TextFlow)h.getChildren().get(1);
        int id = t.getId().charAt(1) - '0';
        System.out.println("CLICKED!!! " + id);
        if(id >= 5)
        {
            if(giveThese[id - 5] > 0)
            {
                giveThese[id - 5]--;
                ((Text) t.getChildren().get(0)).setText("" + getThese[id]);
            }
        }
        else if(getThese[id] > 0)
        {
            getThese[id]--;
            ((Text)t.getChildren().get(0)).setText("" + getThese[id]);
        }

        System.out.println(Arrays.toString(getThese));
        System.out.println(Arrays.toString(giveThese));
    }
}
