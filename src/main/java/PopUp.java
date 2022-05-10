import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum PopUp {
    TRADEBANK, TRADE, MONOPOLY, ROADBUILDING, TRADEOTHERS, YEAROFPLENTY, ROBBERSELECT, DISCARD, BUILDINGS;

    private static boolean lastPopUpCancelled = false;

    /*
    Each value above has the following private instance variables, and the enum values can be called like public static variables
     */
    private Pane pane;

    private Object controller;

    public static boolean isLastPopUpCancelled() {
        return lastPopUpCancelled;
    }

    public void setController(Object c)
    {
        controller = c;
    }

    public Object getController() {return controller;}
    public void setPane(Pane a)
    {
        pane = a;
    }

    public Pane getPane()
    {
        return pane;
    }

    public void load() {
        load(1920, 1080);
    }

    public void load(double w, double h){
        load(w, h, false);
    }

    public void load(double w, double h, boolean cannotClose){
        lastPopUpCancelled = false;
        Stage pop = new Stage();
        pop.initModality(Modality.APPLICATION_MODAL);
        pop.setScene(new Scene(pane, w, h));
        pop.setOnCloseRequest((event) -> {
            if (cannotClose){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Close Confirmation");
                alert.setHeaderText("Closing this dialog will close the entire application. Are you sure you want to quit?");
                alert.initOwner(pop);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Platform.exit();
                    System.exit(0);
                }
                event.consume();
            } else {
                lastPopUpCancelled = true;
                pop.getScene().setRoot(new AnchorPane());
            }
        });
        pop.setResizable(false);
        pop.showAndWait();
    }

    public void loadTrade()
    {
        if(this != TRADE)
        {
            System.out.println("This is not player trade");
            return;
        }
        ((Trade) controller).initialize();
        ((Trade) controller).refreshAll();
        load();
    }

    public void loadTradeBank(){
        if (this != TRADEBANK) {
            System.out.println("This is the bank trade");
            return;
        }
        ((TradeBank) controller).initPopUp();
        ((TradeBank) controller).refreshAll();
        load();
    }

    public void loadMonopoly() {
        if (this != MONOPOLY){
            System.out.println("This is monopoly");
            return;
        }
        ((Monopoly) controller).initPlayerInfo();
        load(600, 400);
    }

    public void loadYearOfPlenty(){
        if (this != YEAROFPLENTY){
            System.out.println("This is year of plenty");
            return;
        }
        ((YearOfPlenty) controller).initPopUp();
        load(1280, 673);
    }

    public void loadRoadBuilding(){
        if (this != ROADBUILDING){
            System.out.println("This is roadbuilding");
            return;
        }
        ((RoadBuilding) controller).initPopUp();
        load(1600, 1080);
    }

    public void loadRobber(boolean knight){
        if (this != ROBBERSELECT) {
            System.out.println("This is robber");
            return;
        }
        ((RobberSelect) controller).initPopUp(knight);
        load(1600, 1080, true);
    }

    public void loadDiscard(Player p) {
        if (this != DISCARD){
            System.out.println("This is discard");
            return;
        }
        ((Discard) controller).initPopUp(p);
        load(1280, 673, true);
    }

    public void loadBuildings(){
        if (this != BUILDINGS){
            System.out.println("This is buildings");
        }
        ((Buildings) controller).initPopUp();
        load(600, 400);
    }

    public void loadTradeOthers(Map<Resource, Integer> request, Map<Resource, Integer> offer, Trade t){
        if (this != TRADEOTHERS){
            System.out.println("This is trade others");

        }
        ((TradeOthers) controller).initPopUp(request, offer, t);
        load(1920, 1080);
    }
}
