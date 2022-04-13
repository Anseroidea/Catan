import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class City extends Settlement{
    public City(Settlement s) {
        super(s.getPlayer(), s.getVertex());
    }
}
