import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex {

    public Map<Integer, Tile> adjacentTiles;
    public Map<Integer, Edge> adjacentEdges;
    public settlement Settlement;

    public int getPaneX() {
        return 0;
    }

    public int getPaneY() {
        return 0;
    }

    public settlement getSettlement() {
        return Settlement;
    }

    public Map<Integer, Tile> getAdjacentTiles() {

    }

}
