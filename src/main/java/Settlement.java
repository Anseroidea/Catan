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
    boolean city = false;

    public Settlement (Player p, Vertex v) {
        p.getSettlements().add(this);
        player=p;
        vertex=v;
    }

    public Player getPlayer() {
        return player;
    }

    public BufferedImage getGraphic() {
        if(city) return player.p.get(player.getColor()).get(3);
        return player.p.get(player.getColor()).get(1);
    }

    public Vertex getVertex() {
        return vertex;
    }

    public boolean isSettlement() {
        return !city;
    }

    public void upGrade(){city=true;}

    public void getResources(){
        Map m =vertex.getAdjacentTiles();
        Set<Tile> k=m.entrySet();
        int REMOVETHIS;
        for(Tile r:k){
            //if(r.getWeight()==REMOVETHIS){
            r.get(this);//}
        }
    }
}
