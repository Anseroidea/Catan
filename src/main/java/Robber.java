import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Robber {

    private Tile tile;
    private static BufferedImage graphic;


    static {
        BufferedImage im = null;
        try {
            im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/player/robber.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        graphic = im;
    }

    public Robber(Tile t){
        tile = t;
    }

    public static BufferedImage getGraphic() {
        return graphic;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}