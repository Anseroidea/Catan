import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Settlement {
    private Map<Integer, Image> graphic;
    private int Player;
    private Vertex vertex;


    public Settlement() {

    }

    /*public Player getPlayer() {
        return ;
    }

    public Image getGraphic() {

    }*/

    public Vertex getVertex() {
        return vertex;
    }

    public boolean isSettlement() {
        return false;
    }

}


