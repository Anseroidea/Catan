import javafx.scene.layout.AnchorPane;

import java.util.*;

public class BoardGame {

    private static HexGridPane hexGridPane;

    public static void initialize(Player[] players){
        TurnManager.initialize(players);
        initializeTiles();
    }

    private static void initializeTiles(){
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < (i <= 3 ? (i == 1) ? 1 : (i == 2) ? 4 : 3 : 4); j++){
                tiles.add(new Tile(i));
            }
        }
        tiles.stream().map(Tile::getType).forEach(System.out::println);
        Collections.shuffle(tiles);
        HexGridPane hexGridPane = new HexGridPane(108);
        List<Tile> selectableTiles = new ArrayList<>();
        for (int r = -2; r < 3; r++){
            for (int c = 1; c <= 5 - Math.abs(r); c++){
                Tile t = tiles.remove(0);
                hexGridPane.add(t, r, c);
                if ((Math.abs(r) == 2) && (c == 1 || c == 5 - Math.abs(r))){
                    selectableTiles.add(t);
                }
            }
        }
        Collections.shuffle(selectableTiles);
        Tile start = null;
        while (start == null || start.getId() == 1)
            start = selectableTiles.get(0);
        Set<String> weights = Tile.weights.keySet();
        Iterator<String> it = weights.iterator();
        int r = start.getR();
        int c = start.getC();
        int loopsIn = 0;
        start.setWeightLetter(it.next());
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
            if (hexGridPane.get(r, c).getWeightLetter() != null){
                if (loopsIn == 1){
                    r = 0;
                    c = 3;
                } else if (r == -2 + loopsIn && c < 5 - Math.abs(r) - loopsIn){
                    r += 1;
                    c -= rDir;
                } else if (c == 5 - Math.abs(r) - loopsIn && r < 0) {
                    r -= (rDir - 1);
                    c -= rDir;
                } else if (c == 5 - Math.abs(r) - loopsIn && r >= 0 && r < 2 - loopsIn){
                    r += (cDir - 1)/2;
                    c -= (cDir + 1)/2;
                } else if (r == 2 - loopsIn && c > 1 + loopsIn){
                    r--;
                    c -= rDir - 1;
                } else if (c == 1 + loopsIn && r > 0){
                    r -= rDir + 1;
                    c++;
                } else {
                    r = 0;
                    c++;
                }
                loopsIn++;
                if (hexGridPane.get(r, c).getWeightLetter() != null){
                    break;
                }
            }
            if (hexGridPane.get(r, c).getId() != 1)
                hexGridPane.get(r, c).setWeightLetter(it.next());
        }
        for (int i = -3; i < 4; i++) {
            if (Math.abs(i) == 3) {
                for (int j = 0; j < 7 - Math.abs(i); j++) {
                    hexGridPane.add(new Tile(6), i, j);
                }
            }
            hexGridPane.add(new Tile(6), i, 0);
            hexGridPane.add(new Tile(6), i, 7 - Math.abs(i) - 1);
        }
        hexGridPane.setAdjacencies();
        hexGridPane.initializeVertices();
    }


    public static HexGridPane getHexGridPane() {
        return hexGridPane;
    }
}

