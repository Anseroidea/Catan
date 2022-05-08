import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.*;

public class Trade
{
    public VBox reqBrick;
    public Button reqRemBrickButton;
    public Label brickLabel;
    public Button reqAddBrickButton;
    public Button reqRemLumberButton;
    public Label lumberLabel;
    public Button reqAddLumber;
    public Button reqRemOreButton;
    public Label oreLabel;
    public Button reqAddOreButton;
    public Button reqRemWheatButton;
    public Button reqAddWheatButton;
    public Button reqRemWoolButton;
    public Label woolLabel;
    public Button reqAddWoolButton;
    public Button offRemBrickButton;
    public Label brickLabelOff;
    public Button reqAddBrickButton1;
    public VBox offLumber;
    public Button offRemLumberButton;
    public Label lumberLabelOff;
    public Button offAddLumberButton;
    public VBox offOre;
    public Button offRemOreButton;
    public Label oreLabelOff;
    public Button offAddOreButton;
    public VBox offWheat;
    public Button offRemWheatButton;
    public Button offAddWheatButton;
    public VBox offWool;
    public Button offRemWoolButton;
    public Label woolLabelOff;
    public Button offAddWoolButton;
    public VBox reqLumber;
    public VBox reqOre;
    public VBox reqWheat;
    public VBox reqWool;
    public VBox offBrick;
    private Map<Resource, Integer> request = new HashMap<>();
    private Map<Resource, Integer> offer = new HashMap<>();
    public Label wheatLabel;
    public Label wheatLabelOff;
    public Button offAddBrickButton;
    public Button reqAddLumberButton;


    @FXML
    private StackPane a, b, c, d, e, a1, b1, c1, d1, e1;

    @FXML
    private Button confirm, p0, m0, p1, m1, p2, m2, p3, m3, p4, m4, p5, m5, p6, m6, p7, m7, p8, m8, p9, m9;

    @FXML
    private TextFlow t1;

    public static Map<String, Image> cardGraphics;

    private String give, get, givePane, getPane;

    private int[] giveThese, getThese;

    public static String[] match = new String[] {"brick", "wheat", "forest", "ore", "wool"};

    private TradeBank bank;

    static
    {
        /*
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

         */
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
    public void initialize()
    {
        for (Resource r : Resource.getResourceList()){
            request.put(r, 0);
            offer.put(r, 0);
        }
        /*
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

         */
    }

    public void back(MouseEvent mouseEvent) {
        Stage thisStage = (Stage) reqRemBrickButton.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    /*
    Other players accept or decline
     */
    /*
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

     */

    public void refreshLabels(){
        brickLabel.setText(request.get(Resource.BRICK) + "");
        lumberLabel.setText(request.get(Resource.LUMBER) + "");
        oreLabel.setText(request.get(Resource.ORE) + "");
        wheatLabel.setText(request.get(Resource.WHEAT) + "");
        woolLabel.setText(request.get(Resource.WOOL) + "");
        brickLabelOff.setText(offer.get(Resource.BRICK) + "");
        lumberLabelOff.setText(offer.get(Resource.LUMBER) + "");
        oreLabelOff.setText(offer.get(Resource.ORE) + "");
        wheatLabelOff.setText(offer.get(Resource.WHEAT) + "");
        woolLabelOff.setText(offer.get(Resource.WOOL) + "");
    }
    
    public void refreshButtons(){
        Map<Resource, Integer> owned = TurnManager.getCurrentPlayer().getResources();
        offRemBrickButton.setDisable(false);
        offAddBrickButton.setDisable(false);
        if (offer.get(Resource.BRICK) == 0){
            offRemBrickButton.setDisable(true);
        }
        if (owned.get(Resource.BRICK).intValue() == offer.get(Resource.BRICK)){
            offAddBrickButton.setDisable(true);
        }
        offRemLumberButton.setDisable(false);
        offAddLumberButton.setDisable(false);
        if (offer.get(Resource.LUMBER) == 0){
            offRemLumberButton.setDisable(true);
        }
        if (owned.get(Resource.LUMBER).intValue() == offer.get(Resource.LUMBER)){
            offAddLumberButton.setDisable(true);
        }
        offRemOreButton.setDisable(false);
        offAddOreButton.setDisable(false);
        if (offer.get(Resource.ORE) == 0){
            offRemOreButton.setDisable(true);
        }
        if (owned.get(Resource.ORE).intValue() == offer.get(Resource.ORE)){
            offAddOreButton.setDisable(true);
        }
        offRemWheatButton.setDisable(false);
        offAddWheatButton.setDisable(false);
        if (offer.get(Resource.WHEAT) == 0){
            offRemWheatButton.setDisable(true);
        }
        if (owned.get(Resource.WHEAT).intValue() == offer.get(Resource.WHEAT)){
            offAddWheatButton.setDisable(true);
        }
        offRemWoolButton.setDisable(false);
        offAddWoolButton.setDisable(false);
        if (offer.get(Resource.WOOL) == 0){
            offRemWoolButton.setDisable(true);
        }
        if (owned.get(Resource.WOOL).intValue() == offer.get(Resource.WOOL)){
            offAddWoolButton.setDisable(true);
        }
        if (request.get(Resource.BRICK) == 0){
            reqRemBrickButton.setDisable(true);
        }
        if (request.get(Resource.LUMBER) == 0){
            reqRemLumberButton.setDisable(true);
        }
        if (request.get(Resource.ORE) == 0){
            reqRemOreButton.setDisable(true);
        }
        if (request.get(Resource.WHEAT) == 0){
            reqRemWheatButton.setDisable(true);
        }
        if (request.get(Resource.WOOL) == 0){
            reqRemWoolButton.setDisable(true);
        }
    }
    
    public void refreshIcons(){
        if (offer.get(Resource.BRICK) > 0){
            reqBrick.setDisable(true);
            reqAddBrickButton.setDisable(true);
            reqRemBrickButton.setDisable(true);
            request.put(Resource.BRICK, 0);
        }
        if (offer.get(Resource.LUMBER) > 0){
            reqLumber.setDisable(true);
            reqAddLumberButton.setDisable(true);
            reqRemLumberButton.setDisable(true);
            request.put(Resource.LUMBER, 0);
        }
        if (offer.get(Resource.ORE) > 0){
            reqOre.setDisable(true);
            reqAddOreButton.setDisable(true);
            reqRemOreButton.setDisable(true);
            request.put(Resource.ORE, 0);
        }
        if (offer.get(Resource.WHEAT) > 0){
            reqWheat.setDisable(true);
            reqAddWheatButton.setDisable(true);
            reqRemWheatButton.setDisable(true);
            request.put(Resource.WHEAT, 0);
        }
        if (offer.get(Resource.WOOL) > 0){
            reqWool.setDisable(true);
            reqAddWoolButton.setDisable(true);
            reqRemWoolButton.setDisable(true);
            request.put(Resource.WOOL, 0);
        }
        
    }
    
    public void refreshAll(){
        reqAddBrickButton.setDisable(false);
        reqRemBrickButton.setDisable(false);
        reqAddLumberButton.setDisable(false);
        reqRemLumberButton.setDisable(false);
        reqAddOreButton.setDisable(false);
        reqRemOreButton.setDisable(false);
        reqAddWheatButton.setDisable(false);
        reqRemWheatButton.setDisable(false);
        reqAddWoolButton.setDisable(false);
        reqRemWoolButton.setDisable(false);
        reqBrick.setDisable(false);
        reqLumber.setDisable(false);
        reqOre.setDisable(false);
        reqWheat.setDisable(false);
        reqWool.setDisable(false);
        refreshIcons();
        refreshLabels();
        refreshButtons();
    }
    
    public void minBrick(ActionEvent actionEvent) {
        request.put(Resource.BRICK, request.get(Resource.BRICK) - 1);
        refreshAll();
    }
    public void maxBrick(ActionEvent actionEvent) {
        request.put(Resource.BRICK, request.get(Resource.BRICK) + 1);
        refreshAll();
    }
    public void minLumber(ActionEvent actionEvent) {
        request.put(Resource.LUMBER, request.get(Resource.LUMBER) - 1);
        refreshAll();
    }
    public void maxLumber(ActionEvent actionEvent) {
        request.put(Resource.LUMBER, request.get(Resource.LUMBER) + 1);
        refreshAll();
    }
    public void minOre(ActionEvent actionEvent) {
        request.put(Resource.ORE, request.get(Resource.ORE) - 1);
        refreshAll();
    }
    public void maxOre(ActionEvent actionEvent) {
        request.put(Resource.ORE, request.get(Resource.ORE) + 1);
        refreshAll();
    }
    public void minWheat(ActionEvent actionEvent) {
        request.put(Resource.WHEAT, request.get(Resource.WHEAT) - 1);
        refreshAll();
    }
    public void maxWheat(ActionEvent actionEvent) {
        request.put(Resource.WHEAT, request.get(Resource.WHEAT) + 1);
        refreshAll();
    }
    public void minWool(ActionEvent actionEvent) {
        request.put(Resource.WOOL, request.get(Resource.WOOL) - 1);
        refreshAll();
    }
    public void maxWool(ActionEvent actionEvent) {
        request.put(Resource.WOOL, request.get(Resource.WOOL) + 1);
        refreshAll();
    }
    public void minBrickOff(ActionEvent actionEvent) {
        offer.put(Resource.BRICK, offer.get(Resource.BRICK) - 1);
        refreshAll();
    }
    public void maxBrickOff(ActionEvent actionEvent) {
        offer.put(Resource.BRICK, offer.get(Resource.BRICK) + 1);
        refreshAll();
    }
    public void minLumberOff(ActionEvent actionEvent) {
        offer.put(Resource.LUMBER, offer.get(Resource.LUMBER) - 1);
        refreshAll();
    }
    public void maxLumberOff(ActionEvent actionEvent) {
        offer.put(Resource.LUMBER, offer.get(Resource.LUMBER) + 1);
        refreshAll();
    }
    public void minOreOff(ActionEvent actionEvent) {
        offer.put(Resource.ORE, offer.get(Resource.ORE) - 1);
        refreshAll();
    }
    public void maxOreOff(ActionEvent actionEvent) {
        offer.put(Resource.ORE, offer.get(Resource.ORE) + 1);
        refreshAll();
    }
    public void minWheatOff(ActionEvent actionEvent) {
        offer.put(Resource.WHEAT, offer.get(Resource.WHEAT) - 1);
        refreshAll();
    }
    public void maxWheatOff(ActionEvent actionEvent) {
        offer.put(Resource.WHEAT, offer.get(Resource.WHEAT) + 1);
        refreshAll();
    }
    public void minWoolOff(ActionEvent actionEvent) {
        offer.put(Resource.WOOL, offer.get(Resource.WOOL) - 1);
        refreshAll();
    }
    public void maxWoolOff(ActionEvent actionEvent) {
        offer.put(Resource.WOOL, offer.get(Resource.WOOL) + 1);
        refreshAll();
    }

    public void toTradeOthers(ActionEvent actionEvent) {
    }
}
