import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Road{
    Player p;
    Edge e;
    BufferedImage b;

    public Road(Edge a, Player b){
        e=a;
        p=b;
    }

    public Edge getEdge() {
        return e;
    }
    public Player getPlayer(){
        return p;
    }
    public BufferedImage getGraphics(){
        return b;
    }
}
