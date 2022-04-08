import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.*;

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
        HexGridPane hexGridPane = new HexGridPane(50);
        List<Tile> selectableTiles = new ArrayList<>();
        for (int r = -2; r < 3; r++){
            for (int c = 1; c <= 5 - Math.abs(r); c++){
                Tile t = tiles.remove(0);
                hexGridPane.add(t, r, c);
                if (Math.abs(r) == 2 || c == 1 || c == 5 - Math.abs(r)){
                    selectableTiles.add(t);
                }
            }
        }
        Collections.shuffle(selectableTiles);
        Tile start = selectableTiles.get(0);
        Set<String> weights = Tile.weights.keySet();
        Iterator<String> it = weights.iterator();
        int r = start.getR();
        int c = start.getC();
        int loopsIn = 0;
        start.setWeightLetter(it.next());
        //System.out.println("(r + \",\" + c) = " + (r + "," + c));
        while(true){
            int rDir = 0;
            int cDir = 0;
            if (r == 2 - loopsIn && c != 1 + loopsIn) {
                cDir = -1;
            } else if (r == -2 + loopsIn && c != 5 - Math.abs(r) - loopsIn){
                cDir = 1;
            } else if (c == 1 + loopsIn){
                rDir = -1;
            } else if (c == 5 - Math.abs(r) - loopsIn) {
                rDir = 1;
                if (r >= 0){
                    cDir = -1;
                } else {
                    cDir = 1;
                }
            } else {
                System.out.println("This shouldn't happen");
            }
            r += rDir;
            c += cDir;
            //System.out.println("(r + \",\" + c) = " + (r + "," + c));
            if (hexGridPane.get(r, c).getWeightLetter() != null){
                //System.out.println("Going in...");
                //System.out.println("(r + \",\" + c) = " + (r + "," + c));
                if (r == -2 + loopsIn && c < 5 - Math.abs(r) - loopsIn){
                    r -= rDir + 1;
                    c++;
                } else if (c == 5 - Math.abs(r) - loopsIn && r < 0) {
                    r -= (-rDir - 1);
                    c += rDir;
                } else if (c == 5 - Math.abs(r) - loopsIn && r >= 0 && r < 2 - loopsIn){
                    r += (cDir - 1)/2;
                    c -= (cDir + 1)/2;
                } else if (r == 2 - loopsIn && c > 1 + loopsIn){
                    r--;
                    c += rDir + 1;
                } else if (c == 1 + loopsIn && r > 0){
                    r += rDir + 1;
                    c++;
                } else {
                    r = 0;
                    c++;
                }
                //System.out.println("(r + \",\" + c) = " + (r + "," + c));
                loopsIn++;
                if (hexGridPane.get(r, c).getWeightLetter() != null){
                    break;
                }
            }
            if (hexGridPane.get(r, c).getId() != 1)
                hexGridPane.get(r, c).setWeightLetter(it.next());
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