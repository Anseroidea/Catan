import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Road{
    Player p;
    Edge e;
    BufferedImage b;

    public Road(Edge a, Player be){
        e=a;
        p=be;

    }

    public Edge getEdge() {
        return e;
    }
    public Player getPlayer(){
        return p;
    }
    public BufferedImage getGraphics(){
        return p.p.get(p.getColor()).get(2);
    }
}
