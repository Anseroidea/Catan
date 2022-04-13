import java.util.*;

public class TurnManager {
    private static Map<String, String> moveMap;
    private static Queue<Player> playerQueue;
    private static Stack<String> actionStrings;
    private static boolean hasBuilt;

    public static void nextTurn(){

    }

    public static void addAction(String s) {
        actionStrings.add(s);
    }

    public static void undo() {
        actionStrings.pop();
    }

    public static Player getCurrentPlayer() {
        return playerQueue.peek();
    }

    public static List<Player> getPlayerList() {
        List p = (List) playerQueue;
        return p;
    }

    public static boolean hasBuilt() {
        return hasBuilt;
    }

    public static void initialize(Player[] players) {
        playerQueue.addAll(Arrays.stream(players).toList());
    }
}
