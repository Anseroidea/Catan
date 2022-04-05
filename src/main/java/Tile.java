import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

public class Tile implements Displayable{

    public final static String[] tileTypes = {"Brick", "Desert", "Forest", "Ore", "Sheep", "Wheat", "Water"};
    public final static Map<String, Integer> weights = new TreeMap<>();
    public final static BufferedImage[] graphics = new BufferedImage[7];
    private Integer id;
    private String type;
    private String weightLetter;

    static {
        for (int i = 0; i < tileTypes.length; i++) {
            try {
                graphics[i] = ImageIO.read(Objects.requireNonNull(Tile.class.getClassLoader().getResourceAsStream("images/tiles/" + tileTypes[i] + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Scanner sc = new Scanner(Tile.class.getClassLoader().getResourceAsStream("misc/numbers.txt"));
            while (sc.hasNext()){
                String line = sc.nextLine();
                String[] data = line.split(" ");
                weights.put(data[0], Integer.parseInt(data[1]));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Tile(Integer id){
        this.id = id;
        type = tileTypes[id];
    }

    public Image getImage() {
        return SwingFXUtils.toFXImage(graphics[id], null);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeightLetter(){
        return weightLetter;
    }

    public void setWeightLetter(String s){
        weightLetter = s;
    }
}
