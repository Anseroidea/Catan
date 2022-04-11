import javafx.scene.layout.Pane;

public enum ProgramState {
    MAIN, INPUT, PLAY, WIN, RULES;

    private Pane p;
    private static ProgramState currentState;

    public static void setCurrentState(ProgramState ps) {
        currentState = ps;
    }

    public static ProgramState getCurrentState() {
        return currentState;
    }

    public void setPane(Pane pane) {
        p = pane;
    }

    Pane getPane() {
        return p;
    }
}

