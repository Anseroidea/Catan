import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Trade {


    public void toTradeBank(ActionEvent actionEvent) {

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
}
