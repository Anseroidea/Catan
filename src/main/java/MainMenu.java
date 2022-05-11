import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenu {
    public Button button;

    public void play(ActionEvent actionEvent) {
        ProgramState.setCurrentState(ProgramState.INPUT);
        CatanApplication.updateDisplay();
    }

    public void rules(ActionEvent actionEvent) {
        ProgramState.setCurrentState(ProgramState.RULES);
        CatanApplication.updateDisplay();
    }

    public void credits(ActionEvent actionEvent) {
    }

    public void quit(ActionEvent actionEvent) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void manual(ActionEvent actionEvent) {
        ProgramState.setCurrentState(ProgramState.USERMANUAL);
        CatanApplication.updateDisplay();
    }
}
