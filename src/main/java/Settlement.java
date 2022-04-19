import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Settlement {
    private Player player;
    private Vertex vertex;
    private HashMap graphic=new HashMap<Color, Image[]>();
    boolean city = false;

    public Settlement (Player p, Vertex v) {
        player=p;
        vertex=v;
    }

    public Player getPlayer() {
        return player;
    }

    public Image getGraphic() {
        Image[] a= (Image[]) graphic.get(player.getColor());
        if(city==false)
            return a[0];
        return a[1];
    }

    public Vertex getVertex() {
        return vertex;
    }

    public boolean isSettlement() {
        return !city;
    }

    public void upGrade(){city=true;}
}
