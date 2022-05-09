import javafx.scene.layout.AnchorPane;

import java.util.*;
import java.util.stream.Collectors;

public class BoardGame {

    private static HexGridPane hexGridPane;
    private static DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck();
    private static ResourceDeck resourceDeck = new ResourceDeck();

    private static Player longestRoad = null;
    private static Player largestArmy = null;

    public static List<Harbor> harbors = new ArrayList<Harbor>();
    private static Robber robber;

    public static void initializePlayers(Player[] players){
        TurnManager.initialize(players);
    }
    public static final String harborLocations = """
            -3 0 60 -3 0 -3 1
            -3 2 120 -3 3 -3 4
            -2 4 120 -2 7 -2 8
            0 6 180 -1 10 1 10
            2 4 240 2 7 2 8
            3 2 240 3 3 3 4
            3 0 300 3 0 3 1
            1 0 0 2 0 1 0
            -1 0 0 -1 0 -2 0
            """;

    public static List<Vertex> getBuildableVertices(){
        List<Vertex> vertices = hexGridPane.getVertexManager().getAllVertices();
        for (Player p : TurnManager.getPlayerList()){
            for (Settlement s : p.getSettlements()){
                vertices.removeAll(s.getVertex().getAdjacentVertices());
                vertices.remove(s.getVertex());
            }
        }
        return vertices;
    }

    public static void initializeGraphics(){
        initializeTiles();
    }

    private static void initializeTiles(){
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < (i <= 4 ? (i == 0 || i == 2) ? 3 : 4: 1); j++){
                Tile e = new Tile(i);
                if (i == 5){
                    robber = new Robber(e);
                }
                tiles.add(e);
            }
        }
        tiles.stream().map(Tile::getType).forEach(System.out::println);
        Collections.shuffle(tiles);
        hexGridPane = new HexGridPane(108);
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
        while (start == null || start.getId() == 5)
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
            if (hexGridPane.get(r, c).getId() != 5)
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

        List<Integer> resourceIntList = new ArrayList<>(Arrays.stream("0 1 2 3 4 5 5 5 5 5".split(" ")).map(Integer::valueOf).toList());
        Collections.shuffle(resourceIntList);
        List<Resource> resourceList = resourceIntList.stream().map(i -> i >= 5 ? null : Resource.getResourceList().get(i)).collect(Collectors.toList());
        System.out.println("resourceList = " + resourceList);
        hexGridPane.setAdjacencies();
        hexGridPane.initializeConnections();
        for (String s : harborLocations.split("\n")){
            String[] s1 = s.split(" ");
            int hr = Integer.parseInt(s1[0]);
            int hc = Integer.parseInt(s1[1]);
            Resource resou = resourceList.remove(0);
            Harbor harbor = new Harbor(resou, hexGridPane.get(hr, hc), Integer.parseInt(s1[2]));
            harbors.add(harbor);
            System.out.println(s);
            getHexGridPane().getVertexManager().getVertex(Integer.parseInt(s1[3]), Integer.parseInt(s1[4])).setHarbor(harbor);
            getHexGridPane().getVertexManager().getVertex(Integer.parseInt(s1[5]), Integer.parseInt(s1[6])).setHarbor(harbor);
        }
    }


    public static HexGridPane getHexGridPane() {
        return hexGridPane;
    }

    public static List<Vertex> getBuiltVertices() {
        List<Vertex> result = new ArrayList<>();
        for (Player p : TurnManager.getPlayerList()){
            for (Settlement s : p.getSettlements()){
                result.add(s.getVertex());
            }
        }
        return result;
    }

    public static void rollDice(){
        int sum = (int) (Math.random() * 6) + (int) (Math.random() * 6) + 2;
        if (sum == 7){
            for (Player p : TurnManager.getPlayerList()){
                if (p.getResources().values().stream().reduce(0, Integer::sum) > 7){
                    PopUp.DISCARD.loadDiscard(p);
                }
            }
            PopUp.ROBBERSELECT.loadRobber(false);
        }
        TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " rolled a " + sum);
        TurnManager.setHasRolledDice(true);
        for (Tile t : hexGridPane.getTiles()){
            if (t.getWeight() == sum && !robber.getTile().equals(t)){
                Map<Player, Integer> gottenResources = new HashMap<>();
                for (Vertex v : t.getAdjacentVertices().values()){
                    if (v.getSettlement() != null){
                        gottenResources.putIfAbsent(v.getSettlement().getPlayer(), 0);
                        gottenResources.put(v.getSettlement().getPlayer(), gottenResources.get(v.getSettlement().getPlayer()) + 1);
                        if (!v.getSettlement().isSettlement()) {
                            gottenResources.put(v.getSettlement().getPlayer(), gottenResources.get(v.getSettlement().getPlayer()) + 1);
                        }
                    }
                }
                if (gottenResources.values().stream().reduce(0, Integer::sum) < resourceDeck.getCount(t.getResource()) || gottenResources.keySet().size() == 1){
                    for (Player p : gottenResources.keySet()){
                        p.changeCards(t.getResource(), gottenResources.get(p));
                    }
                }
            }
        }
    }

    public static DevelopmentCardDeck getDevelopmentCardDeck() {
        return developmentCardDeck;
    }

    public static ResourceDeck getResourceDeck() {
        return resourceDeck;
    }

    public static Player getLargestArmy() {
        updateLargestArmy();
        return largestArmy;
    }

    public static void updateLargestArmy() {
        Player largest = (largestArmy != null) ? largestArmy : TurnManager.getCurrentPlayer();
        for (Player p : TurnManager.getPlayerList()){
            if (p.getKnights() > largest.getKnights()){
                largest = p;
            }
        }

    }

    public static Player getLongestRoad() {
        updateLongestRoad();
        return (longestRoad.getRoads().size() >= 3) ? longestRoad : null;
    }

    public static void updateLongestRoad() {
        Player longest = (longestRoad != null) ? longestRoad : TurnManager.getCurrentPlayer();
        for (Player p : TurnManager.getPlayerList()){
            p.updateLongestRoad();
            if (p.getLongestRoad() > longest.getLongestRoad()){
                longest = p;
            }
        }
        longestRoad = longest;
    }

    public static void checkWin() {
        if (TurnManager.getCurrentPlayer().getPrivateVictoryPoints() == 10){
            ProgramState.setCurrentState(ProgramState.WIN);
        }
    }

    public static Robber getRobber() {
        return robber;
    }
}

