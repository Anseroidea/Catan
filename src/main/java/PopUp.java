import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public enum PopUp {
    TRADEBANK, TRADE, MONOPOLY, ROADBUILDING, TRADEOTHERS, YEAROFPLENTY, ROBBER;

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
        lastPopUpCancelled = false;
        Stage pop = new Stage();
        pop.initModality(Modality.APPLICATION_MODAL);
        pop.setScene(new Scene(pane, w, h));
        pop.setOnCloseRequest((event) -> {
            lastPopUpCancelled = true;
            pop.getScene().setRoot(new AnchorPane());
        });
        pop.setResizable(false);
        pop.show();
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
        ((YearOfPlenty) controller).initPopUp();
        load(1600, 1080);
    }
}
