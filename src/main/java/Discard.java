import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Discard {
    public Label discardLabel;
    public Button confirm;
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

    private Player p;
    private Map<Resource, Integer> request = new HashMap<>();
    private int leftToDiscard;

    public void initPopUp(Player p){
        this.p = p;
        leftToDiscard = p.getResources().values().stream().reduce(0, Integer::sum) /2;
        for (Resource r : Resource.getResourceList()){
            request.put(r, 0);
        }
        refreshAll();
    }

    public void refreshAll(){
        refreshButtons();
        refreshLabels();
    }

    public void refreshButtons(){
        int total = request.values().stream().reduce(0, Integer::sum);
        reqRemBrickButton.setDisable(request.get(Resource.BRICK) <= 0);
        reqRemLumberButton.setDisable(request.get(Resource.LUMBER) <= 0);
        reqRemOreButton.setDisable(request.get(Resource.ORE) <= 0);
        reqRemWheatButton.setDisable(request.get(Resource.WHEAT) <= 0);
        reqRemWoolButton.setDisable(request.get(Resource.WOOL) <= 0);
        reqAddBrickButton.setDisable(total == leftToDiscard || request.get(Resource.BRICK) == p.getResources().get(Resource.BRICK));
        reqAddLumberButton.setDisable(total == leftToDiscard || request.get(Resource.LUMBER) == p.getResources().get(Resource.LUMBER));
        reqAddOreButton.setDisable(total == leftToDiscard || request.get(Resource.ORE) == p.getResources().get(Resource.ORE));
        reqAddWheatButton.setDisable(total == leftToDiscard || request.get(Resource.WHEAT) == p.getResources().get(Resource.WHEAT));
        reqAddWoolButton.setDisable(total == leftToDiscard || request.get(Resource.WOOL) == p.getResources().get(Resource.WOOL));
        confirm.setDisable(total < leftToDiscard);
    }

    public void refreshLabels(){
        discardLabel.setText(p.getName() + ", Cards left to discard: " + (leftToDiscard - request.values().stream().reduce(0, Integer::sum)));
        brickLabel.setText(request.get(Resource.BRICK) + "");
        lumberLabel.setText(request.get(Resource.LUMBER) + "");
        oreLabel.setText(request.get(Resource.ORE) + "");
        wheatLabel.setText(request.get(Resource.WHEAT) + "");
        woolLabel.setText(request.get(Resource.WOOL) + "");
    }

    public void confirm(ActionEvent actionEvent) {
        for (Resource r : Resource.getResourceList()){
            p.changeCards(r, -1 * request.get(r));
        }
        close();
    }

    private void close() {
        Stage thisStage = (Stage) reqRemBrickButton.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
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
}
