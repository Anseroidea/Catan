import javafx.scene.layout.Pane;

public enum ProgramState {
    MAIN, INPUT, PLAY, WIN, RULES;

    private Pane p;

    void setProgramState(ProgramState ps) {

    }

    ProgramState getProgramState() {

    }

    void setPane(Pane pane) {
        p = pane;
    }

    Pane getPane() {
        return p;
    }
}

