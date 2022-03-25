import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CatanApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < (i <= 3 ? (i == 1) ? 1 : (i == 2) ? 4 : 3 : 4); j++){
                tiles.add(new Tile(i));
            }
        }
        tiles.stream().map(Tile::getType).forEach(System.out::println);
        Collections.shuffle(tiles);
        HexGridPane<Tile> hexGridPane = new HexGridPane<>(50);
        for (int r = -2; r < 3; r++){
            for (int c = 0; c < 2; c++){
                tiles.stream().map(Tile::getType).forEach(System.out::print);
                System.out.println();
                hexGridPane.add(tiles.remove(0), r, c);
            }
        }
        for (int i = -3; i < 4; i++) {
            if (Math.abs(i) == 3){
                for (int j = 0; j < 7 - Math.abs(i); j++){
                    hexGridPane.add(new Tile(6), i, j);
                }
            }
            hexGridPane.add(new Tile(6), i, 0);
            hexGridPane.add(new Tile(6), i, 7 - Math.abs(i) - 1);
        }
        primaryStage.setScene(new Scene(new StackPane(hexGridPane.toPane())));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}