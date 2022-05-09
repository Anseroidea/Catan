
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Harbor {

    public Resource resource;
    public Tile tile;
    public static BufferedImage harborImage;
    private static BufferedImage threeToOne;
    public int rotation;

    static {
        try {
            harborImage = ImageIO.read(Harbor.class.getClassLoader().getResourceAsStream("images/resources/harbor.png"));
            threeToOne = ImageIO.read(Harbor.class.getClassLoader().getResourceAsStream("images/resources/three.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Harbor(Resource r, Tile t, int rotation) {
        t.setHarbor(this);
        resource = r;
        tile = t;
        this.rotation = rotation;
    }

    public static BufferedImage getThreeToOne() {
        return threeToOne;
    }

    public Resource getResource() {
        return resource;
    }

    public int getRotation(){
        return rotation;
    }
}
