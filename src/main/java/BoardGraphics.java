import javafx.scene.layout.StackPane;

public class BoardGraphics {
    public StackPane boardStackPane;

    public void refreshDisplay() {
        boardStackPane.getChildren().clear();
        boardStackPane.getChildren().add(BoardGame.getHexGridPane().toPane(false));
    }
}
