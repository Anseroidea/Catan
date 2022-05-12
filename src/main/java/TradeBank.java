import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class TradeBank
{
    public Label numCardsRemain;
    public VBox reqBrick;
    public Button reqRemBrickButton;
    public Label brickLabel;
    public Button reqAddBrickButton;
    public VBox reqLumber;
    public Button reqRemLumberButton;
    public Label lumberLabel;
    public Button reqAddLumberButton;
    public VBox reqOre;
    public Button reqRemOreButton;
    public Label oreLabel;
    public Button reqAddOreButton;
    public VBox reqWheat;
    public Button reqRemWheatButton;
    public Label wheatLabel;
    public Button reqAddWheatButton;
    public VBox reqWool;
    public Button reqRemWoolButton;
    public Label woolLabel;
    public Button reqAddWoolButton;
    public VBox offBrick;
    public Button offRemBrickButton;
    public Label brickLabelOff;
    public Button offAddBrickButton;
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
    public Label wheatLabelOff;
    public Button offAddWheatButton;
    public VBox offWool;
    public Button offRemWoolButton;
    public Label woolLabelOff;
    public Button offAddWoolButton;
    public Button confirm;
    private HashMap<Resource, Integer> give = new HashMap<>();
    private HashMap<Resource, Integer> get = new HashMap<>();
    private HashMap<Resource, Integer> conversion = new HashMap<>();
    private int numCardsAvailable = 0;

    private StackPane a, b, c, d, e, a1, b1, c1, d1, e1;
    public TradeBank() throws Exception
    {
        /*cardGraphics = new HashMap<>();
        //System.out.println(1);
        String[] temp = Trade.match;
        //System.out.println(2);
        for(int i = 0; i < 5; i++)
        {
            BufferedImage im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/cards/resources--" + temp[i].toLowerCase() + ".png")));

            cardGraphics.put(temp[i].toLowerCase(), SwingFXUtils.toFXImage(im, null));

        }*/
    }
    public void initPopUp()
    {
        confirm.setDisable(true);
        numCardsAvailable = 0;
        for (Resource r : Resource.getResourceList()){
            give.put(r, 0);
            get.put(r, 0);
            conversion.put(r, 4);
        }
        boolean threeToOne = false;
        for (Settlement s : TurnManager.getCurrentPlayer().getSettlements()) {
            if (s.getVertex().getHarbor() != null){
                if (s.getVertex().getHarbor().getResource() != null){
                    conversion.put(s.getVertex().getHarbor().getResource(), 2);
                } else {
                    threeToOne = true;
                }
            }
        }
        if (threeToOne){
            for (Resource r : Resource.getResourceList()){
                conversion.put(r, Math.min(conversion.get(r), 3));
            }
        }
        offAddBrickButton.setText("+" + conversion.get(Resource.BRICK));
        offRemBrickButton.setText("-" + conversion.get(Resource.BRICK));
        offAddLumberButton.setText("+" + conversion.get(Resource.LUMBER));
        offRemLumberButton.setText("-" + conversion.get(Resource.LUMBER));
        offAddOreButton.setText("+" + conversion.get(Resource.ORE));
        offRemOreButton.setText("-" + conversion.get(Resource.ORE));
        offAddWheatButton.setText("+" + conversion.get(Resource.WHEAT));
        offRemWheatButton.setText("-" + conversion.get(Resource.WHEAT));
        offAddWoolButton.setText("+" + conversion.get(Resource.WOOL));
        offRemWoolButton.setText("-" + conversion.get(Resource.WOOL));
        /*
        //System.out.println(4);
        ((ImageView)a.getChildren().get(0)).setImage(cardGraphics.get("brick"));
        ((ImageView)b.getChildren().get(0)).setImage(cardGraphics.get("wheat"));
        ((ImageView)c.getChildren().get(0)).setImage(cardGraphics.get("forest"));
        ((ImageView)d.getChildren().get(0)).setImage(cardGraphics.get("ore"));
        ((ImageView)e.getChildren().get(0)).setImage(cardGraphics.get("wool"));
        ((ImageView)a1.getChildren().get(0)).setImage(cardGraphics.get("brick"));
        ((ImageView)b1.getChildren().get(0)).setImage(cardGraphics.get("wheat"));
        ((ImageView)c1.getChildren().get(0)).setImage(cardGraphics.get("forest"));
        ((ImageView)d1.getChildren().get(0)).setImage(cardGraphics.get("ore"));
        ((ImageView)e1.getChildren().get(0)).setImage(cardGraphics.get("wool"));
        //System.out.println(5);

         */
        refreshAll();
    }

    public void refreshAll(){
        if (numCardsAvailable < 0){
            for (int i = Resource.getResourceList().size() - 1; i >= 0; i--){
                if (get.get(Resource.getResourceList().get(i)) > 0){
                    get.put(Resource.getResourceList().get(i), get.get(Resource.getResourceList().get(i)) - 1);
                    numCardsAvailable++;
                }
                if (numCardsAvailable >= 0){
                    break;
                }
            }
        }
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
        refreshButtons();
        refreshLabels();
    }

    public void refreshLabels() {
        brickLabel.setText(get.get(Resource.BRICK) + "");
        lumberLabel.setText(get.get(Resource.LUMBER) + "");
        oreLabel.setText(get.get(Resource.ORE) + "");
        wheatLabel.setText(get.get(Resource.WHEAT) + "");
        woolLabel.setText(get.get(Resource.WOOL) + "");
        brickLabelOff.setText(give.get(Resource.BRICK) + "");
        lumberLabelOff.setText(give.get(Resource.LUMBER) + "");
        oreLabelOff.setText(give.get(Resource.ORE) + "");
        wheatLabelOff.setText(give.get(Resource.WHEAT) + "");
        woolLabelOff.setText(give.get(Resource.WOOL) + "");
        numCardsRemain.setText(numCardsAvailable + "");
    }

    public void refreshButtons(){
        confirm.setDisable(give.values().stream().reduce(0, Integer::sum) == 0 || get.values().stream().reduce(0, Integer::sum) == 0);
        Map<Resource, Integer> owned = TurnManager.getCurrentPlayer().getResources();
        offRemBrickButton.setDisable(false);
        offAddBrickButton.setDisable(false);
        if (give.get(Resource.BRICK) == 0){
            offRemBrickButton.setDisable(true);
        }
        if (owned.get(Resource.BRICK).intValue()/conversion.get(Resource.BRICK) * conversion.get(Resource.BRICK) == give.get(Resource.BRICK)){
            offAddBrickButton.setDisable(true);
        }
        offRemLumberButton.setDisable(false);
        offAddLumberButton.setDisable(false);
        if (give.get(Resource.LUMBER) == 0){
            offRemLumberButton.setDisable(true);
        }
        if (owned.get(Resource.LUMBER).intValue()/conversion.get(Resource.LUMBER) * conversion.get(Resource.LUMBER) == give.get(Resource.LUMBER)){
            offAddLumberButton.setDisable(true);
        }
        offRemOreButton.setDisable(false);
        offAddOreButton.setDisable(false);
        if (give.get(Resource.ORE) == 0){
            offRemOreButton.setDisable(true);
        }
        if (owned.get(Resource.ORE).intValue()/conversion.get(Resource.ORE) * conversion.get(Resource.ORE) == give.get(Resource.ORE)){
            offAddOreButton.setDisable(true);
        }
        offRemWheatButton.setDisable(false);
        offAddWheatButton.setDisable(false);
        if (give.get(Resource.WHEAT) == 0){
            offRemWheatButton.setDisable(true);
        }
        if (owned.get(Resource.WHEAT).intValue()/conversion.get(Resource.WHEAT) * conversion.get(Resource.WHEAT) == give.get(Resource.WHEAT)){
            offAddWheatButton.setDisable(true);
        }
        offRemWoolButton.setDisable(false);
        offAddWoolButton.setDisable(false);
        if (give.get(Resource.WOOL) == 0){
            offRemWoolButton.setDisable(true);
        }
        if (owned.get(Resource.WOOL).intValue()/conversion.get(Resource.WOOL) * conversion.get(Resource.WOOL) == give.get(Resource.WOOL)){
            offAddWoolButton.setDisable(true);
        }
        if (get.get(Resource.BRICK) == 0 || get.get(Resource.BRICK) == BoardGame.getResourceDeck().getCount(Resource.BRICK)){
            reqRemBrickButton.setDisable(true);
        }
        if (get.get(Resource.LUMBER) == 0 || get.get(Resource.LUMBER) == BoardGame.getResourceDeck().getCount(Resource.LUMBER)){
            reqRemLumberButton.setDisable(true);
        }
        if (get.get(Resource.ORE) == 0 || get.get(Resource.ORE) == BoardGame.getResourceDeck().getCount(Resource.ORE)){
            reqRemOreButton.setDisable(true);
        }
        if (get.get(Resource.WHEAT) == 0 || get.get(Resource.ORE) == BoardGame.getResourceDeck().getCount(Resource.WHEAT)){
            reqRemWheatButton.setDisable(true);
        }
        if (get.get(Resource.WOOL) == 0 || get.get(Resource.ORE) == BoardGame.getResourceDeck().getCount(Resource.WOOL)){
            reqRemWoolButton.setDisable(true);
        }
        if (numCardsAvailable <= 0){
            reqAddBrickButton.setDisable(true);
            reqAddLumberButton.setDisable(true);
            reqAddOreButton.setDisable(true);
            reqAddWheatButton.setDisable(true);
            reqAddWoolButton.setDisable(true);
        } else {
            ResourceDeck rd = BoardGame.getResourceDeck();
            reqAddBrickButton.setDisable(!rd.getResource(Resource.BRICK, get.get(Resource.BRICK) + 1));
            reqAddLumberButton.setDisable(!rd.getResource(Resource.LUMBER, get.get(Resource.LUMBER) + 1));
            reqAddOreButton.setDisable(!rd.getResource(Resource.ORE, get.get(Resource.ORE) + 1));
            reqAddWheatButton.setDisable(!rd.getResource(Resource.WHEAT, get.get(Resource.WHEAT) + 1));
            reqAddWoolButton.setDisable(!rd.getResource(Resource.WOOL, get.get(Resource.WOOL) + 1));
        }
    }

    public void back(MouseEvent mouseEvent) {
        Stage thisStage = (Stage) reqRemBrickButton.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    public void confirmTrade(ActionEvent actionEvent) {
        for (Resource r : Resource.getResourceList()){
            TurnManager.getCurrentPlayer().changeCards(r, get.get(r) - give.get(r));
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
        s.append(giveString).append(" to the bank for ").append(getString);
        TurnManager.addAction(s.toString());
        GraphicsManager.refreshDisplay();
        back(null);
    }

    public void minBrick(ActionEvent actionEvent) {
        numCardsAvailable++;
        get.put(Resource.BRICK, get.get(Resource.BRICK) - 1);
        refreshAll();
    }
    public void maxBrick(ActionEvent actionEvent) {
        numCardsAvailable--;
        get.put(Resource.BRICK, get.get(Resource.BRICK) + 1);
        refreshAll();
    }
    public void minLumber(ActionEvent actionEvent) {
        numCardsAvailable++;
        get.put(Resource.LUMBER, get.get(Resource.LUMBER) - 1);
        refreshAll();
    }
    public void maxLumber(ActionEvent actionEvent) {
        numCardsAvailable--;
        get.put(Resource.LUMBER, get.get(Resource.LUMBER) + 1);
        refreshAll();
    }
    public void minOre(ActionEvent actionEvent) {
        numCardsAvailable++;
        get.put(Resource.ORE, get.get(Resource.ORE) - 1);
        refreshAll();
    }
    public void maxOre(ActionEvent actionEvent) {
        numCardsAvailable--;
        get.put(Resource.ORE, get.get(Resource.ORE) + 1);
        refreshAll();
    }
    public void minWheat(ActionEvent actionEvent) {
        numCardsAvailable++;
        get.put(Resource.WHEAT, get.get(Resource.WHEAT) - 1);
        refreshAll();
    }
    public void maxWheat(ActionEvent actionEvent) {
        numCardsAvailable--;
        get.put(Resource.WHEAT, get.get(Resource.WHEAT) + 1);
        refreshAll();
    }
    public void minWool(ActionEvent actionEvent) {
        numCardsAvailable++;
        get.put(Resource.WOOL, get.get(Resource.WOOL) - 1);
        refreshAll();
    }
    public void maxWool(ActionEvent actionEvent) {
        numCardsAvailable--;
        get.put(Resource.WOOL, get.get(Resource.WOOL) + 1);
        refreshAll();
    }
    public void minBrickOff(ActionEvent actionEvent) {
        numCardsAvailable--;
        give.put(Resource.BRICK, give.get(Resource.BRICK) - conversion.get(Resource.BRICK));
        refreshAll();
    }
    public void maxBrickOff(ActionEvent actionEvent) {
        numCardsAvailable++;
        give.put(Resource.BRICK, give.get(Resource.BRICK) + conversion.get(Resource.BRICK));
        refreshAll();
    }
    public void minLumberOff(ActionEvent actionEvent) {
        numCardsAvailable--;
        give.put(Resource.LUMBER, give.get(Resource.LUMBER) - conversion.get(Resource.LUMBER));
        refreshAll();
    }
    public void maxLumberOff(ActionEvent actionEvent) {
        numCardsAvailable++;
        give.put(Resource.LUMBER, give.get(Resource.LUMBER) + conversion.get(Resource.LUMBER));
        refreshAll();
    }
    public void minOreOff(ActionEvent actionEvent) {
        numCardsAvailable--;
        give.put(Resource.ORE, give.get(Resource.ORE) - conversion.get(Resource.ORE));
        refreshAll();
    }
    public void maxOreOff(ActionEvent actionEvent) {
        numCardsAvailable++;
        give.put(Resource.ORE, give.get(Resource.ORE) + conversion.get(Resource.ORE));
        refreshAll();
    }
    public void minWheatOff(ActionEvent actionEvent) {
        numCardsAvailable--;
        give.put(Resource.WHEAT, give.get(Resource.WHEAT) - conversion.get(Resource.WHEAT));
        refreshAll();
    }
    public void maxWheatOff(ActionEvent actionEvent) {
        numCardsAvailable++;
        give.put(Resource.WHEAT, give.get(Resource.WHEAT) + conversion.get(Resource.WHEAT));
        refreshAll();
    }
    public void minWoolOff(ActionEvent actionEvent) {
        numCardsAvailable--;
        give.put(Resource.WOOL, give.get(Resource.WOOL) - conversion.get(Resource.WOOL));
        refreshAll();
    }
    public void maxWoolOff(ActionEvent actionEvent) {
        numCardsAvailable++;
        give.put(Resource.WOOL, give.get(Resource.WOOL) + conversion.get(Resource.WOOL));
        refreshAll();
    }

    /*
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
    */
}

