import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class YearOfPlenty {
    public Button confirm;
    public VBox reqBrick;
    public Button reqRemBrickButton;
    public Label brickLabel;
    public Button reqAddBrickButton;
    public Button reqRemLumberButton;
    public Label lumberLabel;
    public Button reqRemOreButton;
    public Label oreLabel;
    public Button reqAddOreButton;
    public Button reqRemWheatButton;
    public Button reqAddWheatButton;
    public Button reqRemWoolButton;
    public Label woolLabel;
    public Button reqAddWoolButton;
    public VBox reqLumber;
    public VBox reqOre;
    public VBox reqWheat;
    public VBox reqWool;
    public Label wheatLabel;
    public Button reqAddLumberButton;
    private Map<Resource, Integer> request = new HashMap<>();

    public void initPopUp(){
        for (Resource r : Resource.getResourceList()){
            request.put(r, 0);
        }
    }

    public void back(ActionEvent actionEvent) {
        Stage stage = (Stage) reqRemBrickButton.getScene().getWindow();
        stage.getScene().setRoot(new AnchorPane());
        stage.close();
    }

    public void confirm(ActionEvent actionEvent) {
        TurnManager.getCurrentPlayer().useDevelopment(TurnManager.getCurrentPlayer().getDevelopmentCards().stream().filter(d -> d.getId() == 7).findFirst().get());
        for (Resource r : Resource.getResourceList()){
            if (request.get(r) > 0){
                TurnManager.getCurrentPlayer().changeCards(r, request.get(r));
            }
        }
        TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " played a Year of Plenty Card.");
        GraphicsManager.refreshDisplay();
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
        refreshLabels();
        refreshButtons();
    }

    public void refreshLabels(){
        brickLabel.setText(request.get(Resource.BRICK) + "");
        lumberLabel.setText(request.get(Resource.LUMBER) + "");
        oreLabel.setText(request.get(Resource.ORE) + "");
        wheatLabel.setText(request.get(Resource.WHEAT) + "");
        woolLabel.setText(request.get(Resource.WOOL) + "");
    }

    public void refreshButtons(){
        int total = request.values().stream().reduce(0, Integer::sum);
        reqRemBrickButton.setDisable(request.get(Resource.BRICK) == 0);
        reqRemLumberButton.setDisable(request.get(Resource.LUMBER) == 0);
        reqRemOreButton.setDisable(request.get(Resource.ORE) == 0);
        reqRemWheatButton.setDisable(request.get(Resource.WHEAT) == 0);
        reqRemWoolButton.setDisable(request.get(Resource.WOOL) == 0);
        reqAddBrickButton.setDisable(request.get(Resource.BRICK) == BoardGame.getResourceDeck().getCount(Resource.BRICK) || total == 2);
        reqAddLumberButton.setDisable(request.get(Resource.LUMBER) == BoardGame.getResourceDeck().getCount(Resource.LUMBER) || total == 2);
        reqAddOreButton.setDisable(request.get(Resource.ORE) == BoardGame.getResourceDeck().getCount(Resource.ORE) || total == 2);
        reqAddWheatButton.setDisable(request.get(Resource.WHEAT) == BoardGame.getResourceDeck().getCount(Resource.WHEAT) || total == 2);
        reqAddWoolButton.setDisable(request.get(Resource.WOOL) == BoardGame.getResourceDeck().getCount(Resource.WOOL) || total == 2);
    }


}
