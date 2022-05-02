import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Resource {

    public static List<Resource> resourceList = new ArrayList<>();
    private BufferedImage smallGraphic;

    private String name;

    static {
        for (String s : "Brick Lumber Ore Wheat Wool".split(" ")){
            Resource r = new Resource(s);
            try {
                r.smallGraphic = ImageIO.read(Objects.requireNonNull(Resource.class.getResourceAsStream("images/resources/" + s.toLowerCase() + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Resource(String s)
    {
        name = s;
        resourceList.add(this);
    }
    public Resource () {
        name = " ";
    }

    public String getResource() {
        return name;
    }

    public static List<Resource> getResourceList() {
        return resourceList;
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

    public BufferedImage getGraphic() {
        return smallGraphic;
    }
}
