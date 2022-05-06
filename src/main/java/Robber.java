import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Robber {
    Tile t;
    ArrayList<Player> p;
    public Robber(List<Player> pa){
        p= (ArrayList<Player>) pa;
    }
    public void move7(Tile j){
        t=j;
        t.robber=true;
        for(Player a:p){
            if(a.getResources().size()>7){
                ArrayList<Resource> y=(ArrayList<Resource>) a.getResources();
                for(int t=0;t<y.size()/2;t++){
                    int o=(int)Math.random()*y.size()+1;
                    Resource m=y.remove(o);
                    a.changeCards(m,-1);
                }
            }
        }
    }
    public void move(Tile w){
        t=w;
        t.robber=true;
    }
    public BufferedImage getGraphics(){
        try {
            return ImageIO.read(new File("robber.png"));
        } catch (IOException e) {
            System.out.println("Error");
        }
        return null;
    }
}
