import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Robber
{
    @FXML
    private HBox hand;

    private WritableImage image;

    Tile t;
    ArrayList<Player> p;

    private Player victim, robber;

    public Robber()
    {

    }

    public void setRobber(Player p)
    {
        robber = p;
    }
    public void setPlayer(Player p)
    {
        hand.getChildren().clear();
        victim = p;
        Map<Resource, Integer> cards = p.getResources();
        List<Resource> shuffled = new ArrayList<>();
        int count = 0;
        for(Iterator<Map.Entry<Resource, Integer>> i = cards.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry<Resource, Integer> e = i.next();
            for(int k = 0; k < e.getValue(); k++) {
                System.out.println(e.getKey().getResource() + " dddddd");
                shuffled.add(e.getKey());

                Resource robbed = null;
                ImageView temp = gen();
                StackPane sp = new StackPane(temp);
                int thisNum = count;
                sp.setOnMouseClicked((event) -> {
                    System.out.println("BEFORE");
                    System.out.println(victim.getResources().get(shuffled.get(thisNum)));
                    System.out.println(robber.getResources().get(shuffled.get(thisNum)));
                    victim.changeCards(shuffled.get(thisNum), -1);
                    robber.changeCards(shuffled.get(thisNum), 1);
                    System.out.println(victim.getResources().get(shuffled.get(thisNum)));
                    System.out.println(robber.getResources().get(shuffled.get(thisNum)));
                    GraphicsManager.refreshDisplay();
                    back(event);
                });
                sp.setOnMouseEntered((event) -> {
                    sp.setStyle("-fx-border-color: blue");
                });
                sp.setOnMouseExited((event) -> {
                    sp.setStyle("-fx-border-color: transparent");
                });
                hand.getChildren().add(sp);
                count++;
            }
        }

        Collections.shuffle(shuffled);
    }
    @FXML
    public void initialize()
    {
        BufferedImage im = null;
        try {
            im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/cards/resources--general.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        image = SwingFXUtils.toFXImage(im, null);
    }

    public void back(MouseEvent mouseEvent) {
        Stage thisStage = (Stage) hand.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    private ImageView gen()
    {
        ImageView iv = new ImageView(image);

        double ogH = iv.getFitHeight();
        iv.setFitHeight(hand.getPrefHeight());
        iv.setFitWidth(iv.getFitHeight() / 2);
        return iv;
    }
    /*public Robber(List<Player> pa)
    {
        p= (ArrayList<Player>) pa;
    }*/

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

    /*
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
    }*/
}
