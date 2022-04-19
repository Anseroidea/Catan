import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class Resource {

    public String name;

    public Resource(String s)
    {
        name = s;
    }
    public Resource () {
        name = " ";
    }

    public String getResource() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(name, resource.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
