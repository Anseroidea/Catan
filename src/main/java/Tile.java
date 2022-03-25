import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Tile implements Displayable{

    public final static String[] tileTypes = {"Brick", "Desert", "Forest", "Ore", "Sheep", "Wheat", "Water"};
    public final static BufferedImage[] graphics = new BufferedImage[7];
    private Integer id;
    private String type;

    static {
        for (int i = 0; i < tileTypes.length; i++) {
            try {
                graphics[i] = ImageIO.read(Objects.requireNonNull(Tile.class.getClassLoader().getResourceAsStream("images/tiles/" + tileTypes[i] + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Tile(Integer id){
        this.id = id;
        type = tileTypes[id];
    }

    public Image getImage() {
        return SwingFXUtils.toFXImage(graphics[id], null);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
