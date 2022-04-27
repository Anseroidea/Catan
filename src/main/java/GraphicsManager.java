public class GraphicsManager {

    private static BoardGraphics bg;
    private static PlayerGraphics pg;

    public static void initialize(BoardGraphics b, PlayerGraphics p){
        bg = b;
        pg = p;
    }

    public static void refreshDisplay(){
        bg.refreshDisplay();
        pg.refreshDisplay();
    }

}
