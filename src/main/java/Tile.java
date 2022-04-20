import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Tile implements Displayable{

    public final static String[] tileTypes = {"Brick", "Desert", "Forest", "Ore", "Sheep", "Wheat", "Water"};
    public final static Map<String, Integer> weights = new TreeMap<>();
    public final static BufferedImage[] graphics = new BufferedImage[7];
    private final Map<Integer, Tile> adjacentTiles = new HashMap<>();
    private final Map<Integer, Vertex> adjacentVertices = new HashMap<>();
    private final Map<Integer, Edge> adjacentEdges = new HashMap<>();

    public static final int NORTH = 0;
    public static final int NORTHEAST = 1;
    public static final int EAST = 2;
    public static final int SOUTHEAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTHWEST = 5;
    public static final int WEST = 6;
    public static final int NORTHWEST = 7;
    public static final String[] directions = {"NORTH", "NORTHEAST", "EAST", "SOUTHEAST", "SOUTH", "SOUTHWEST", "WEST", "NORTHWEST"};

    private int r;
    private int c;
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

    public int getWeight(){
        return weights.get(weightLetter);
    }

    public void setWeightLetter(String s){
        weightLetter = s;
    }

    public Map<Integer, Tile> getNearbyTiles() {
        return adjacentTiles;
    }

    public boolean setNearbyTile(Integer i, Tile t){
        if (i < 0 || i >= 6 || t == null) {
            return false;
        } else {
            if (t.getId() >= 5) return false;
            adjacentTiles.put(i, t);
            return true;
        }
    }

    public Map<Integer, Vertex> getAdjacentVertices(){
        return adjacentVertices;
    }

    public void setPos(int r, int c){
        this.r = r;
        this.c = c;
    }

    public int getR(){
        return r;
    }

    public int getC(){
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return r == tile.r && c == tile.c;
    }

    public void addAdjacentVertex(Integer i, Vertex v) {
        adjacentVertices.put(i, v);
    }

    public void addAdjacentEdge(Integer i, Edge e) {
        adjacentEdges.put(i, e);
    }
}
