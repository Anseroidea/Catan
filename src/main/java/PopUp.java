import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public enum PopUp {
    DICE, TRADEBANK, TRADE, CONVERT, MONOPOLY, ROAD;

    private Pane pane;

    private Object controller;

    private static PopUp cur;

    public void setController(Object c)
    {
        controller = c;
    }

    public void setPane(Pane a)
    {
        pane = a;
    }

    public Pane getPane()
    {
        return pane;
    }

    public void load()
    {
        Stage pop = new Stage();
        pop.initModality(Modality.APPLICATION_MODAL);
        pop.setScene(new Scene(pane, 1920, 1080));
        pop.setResizable(false);

    }

    public void loadTrade(Player p, List<Resource> l, Player pl, List<Resource> lr)
    {
        if(this != TRADE)
        {
            System.out.println("This is not player trade");
            return;
        }
        Stage pop = new Stage();
        pop.initModality(Modality.APPLICATION_MODAL);
        pop.setScene(new Scene(pane, 1920, 1080));
        pop.setResizable(false);
    }
}
