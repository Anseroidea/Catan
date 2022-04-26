import javafx.event.ActionEvent;

public class MainMenu {
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

    }
}
