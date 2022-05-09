import java.util.*;

public class TurnManager {
    private static Map<String, String> moveMap;
    private static Queue<Player> playerQueue;
    private static List<Player> originalList;
    private static StringBuilder allActionLog;
    private static boolean hasRolledDice;
    private static boolean hasBuilt;

    public static void nextTurn(){
        hasRolledDice = false;
        hasBuilt = false;
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
        return originalList;
    }

    public static String getAllActions(){
        return allActionLog.toString();
    }

    public static boolean hasBuilt() {
        return hasBuilt;
    }

    public static void initialize(Player[] players) {
        playerQueue = new LinkedList<>();
        originalList = Arrays.stream(players).toList();
        playerQueue.addAll(originalList);
        allActionLog = new StringBuilder();
    }

    public static boolean hasRolledDice() {
        return hasRolledDice;
    }

    public static void setHasRolledDice(boolean hasRolledDice) {
        TurnManager.hasRolledDice = hasRolledDice;
    }

    public static void setHasBuilt(boolean b) {
        hasBuilt = b;
    }
}
