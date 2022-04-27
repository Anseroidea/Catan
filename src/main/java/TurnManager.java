import java.util.*;

public class TurnManager {
    private static Map<String, String> moveMap;
    private static Queue<Player> playerQueue;
    private static StringBuilder allActionLog;
    private static boolean hasBuilt;

    public static void nextTurn(){
        playerQueue.add(playerQueue.remove());
    }

    public static void previousTurn(){
        for (int i = 0; i < playerQueue.size() - 1; i++){
            nextTurn();
        }
    }

    public static void addAction(String s) {
        allActionLog.append("\n").append(s);
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
        playerQueue = new LinkedList<>();
        playerQueue.addAll(Arrays.stream(players).toList());
        allActionLog = new StringBuilder();
    }
}
