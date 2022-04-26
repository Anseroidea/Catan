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
    private HashMap<Color, Image[]> graphic= new HashMap<>();
    boolean city = false;

    public Settlement (Player p, Vertex v) {
        p.getSettlements().add(this);
        player=p;
        vertex=v;
    }

    public Player getPlayer() {
        return player;
    }

    public Image getGraphic() {
        int s;
        if(player.getColor().equals(javafx.scene.paint.Color.BLUE)) s=0;
        if(player.getColor().equals(javafx.scene.paint.Color.RED)) s=1;
        if(player.getColor().equals(javafx.scene.paint.Color.WHITE)) s=2;
        if(player.getColor().equals(javafx.scene.paint.Color.YELLOW)) s=3;
        if(city) return null;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public boolean isSettlement() {
        return !city;
    }

    public void upGrade(){city=true;}
}
