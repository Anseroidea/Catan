import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Settlement {
    private int player;
    private Vertex vertex;
    private Map<Integer, Image> graphic;

    public Settlement () {
    }

    public Player getPlayer() {
        return null;
    }

    public Image getGraphic() {
        return null;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public boolean isSettlement() {
        return true;
    }
}
