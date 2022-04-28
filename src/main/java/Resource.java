import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Resource {

    public static List<Resource> resourceList = new ArrayList<>();

    public String name;

    static {
        for (String s : "Brick Lumber Ore Wheat Wool".split(" ")){
            new Resource(s);
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
}
