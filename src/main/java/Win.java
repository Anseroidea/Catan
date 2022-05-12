import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Win {


    public Label winLabel;

    public void initWin(){
        winLabel.setText(TurnManager.getCurrentPlayer().getName() + ", you win!");
    }

    public void quit(ActionEvent actionEvent) {
        Stage stage = (Stage) winLabel.getScene().getWindow();
        stage.close();
    }

    public void finalBoard(ActionEvent actionEvent) {
        PopUp.FINALBOARD.loadFinalBoard();
    }
}
