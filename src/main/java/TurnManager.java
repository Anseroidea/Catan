import java.util.*;

public class TurnManager {
    private Map<String, String> moveMap;
    private Queue<Player> playerQueue;
    private Stack<String> actionStrings;
    private boolean hasBuilt;

    public void nextTurn(){

    }

    public void addAction(String s) {
        actionStrings.add(s);
    }

    public void undo() {
        actionStrings.pop();
    }

    public Player getCurrentPlayer() {
        return playerQueue.peek();
    }

    public List<Player> getPlayerList() {
        List p = (List) playerQueue;
        return p;
    }

    public boolean hasBuilt() {
        return hasBuilt;
    }
}
