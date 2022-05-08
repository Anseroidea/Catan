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

    public static final Resource BRICK = new Resource("Brick");
    public static final Resource LUMBER = new Resource("Lumber");
    public static final Resource ORE = new Resource("Ore");
    public static final Resource WHEAT = new Resource("Wheat");
    public static final Resource WOOL = new Resource("Wool");

    private String name;

    static {
        for (Resource r : resourceList){
            try {
                r.smallGraphic = ImageIO.read(Objects.requireNonNull(Resource.class.getResourceAsStream("images/resources/" + r.getResource().toLowerCase() + ".png")));
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
