import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.Objects;

public class CatanApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        HexGridPane<Tile> hexGridPane = new HexGridPane<>(50);
        for (int i = -3; i < 4; i++) {
            if (Math.abs(i) == 3){
                for (int j = 0; j < 7 - Math.abs(i); j++){
                    hexGridPane.add(new Tile(SwingFXUtils.toFXImage(ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("Water.png"))), null)), i, j);
                }
            }
            hexGridPane.add(new Tile(SwingFXUtils.toFXImage(ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("Water.png"))), null)), i, 0);
            hexGridPane.add(new Tile(SwingFXUtils.toFXImage(ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("Water.png"))), null)), i, 7 - Math.abs(i) - 1);
        }
        primaryStage.setScene(new Scene(new StackPane(hexGridPane.toPane())));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}