import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Math;

import java.util.HashMap;
import java.util.Map;

public class DevelopmentCard
{
    /*
    0 = Victory Point
    1 = knight
    2 = road building
    3 = year of plenty: take 2 resources from bank
    4 = monopoly
     */
    private int id;
    public static Map<Integer, BufferedImage> graphics = new HashMap<>();
    private String type;
    private Player player;

    static {
        try {
            BufferedImage VictoryPoint1 = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/market--development.png"));
            BufferedImage VictoryPoint2 = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/chapel--development.png"));
            BufferedImage VictoryPoint3 = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/university--development.png"));
            BufferedImage VictoryPoint4 = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/library--development.png"));
            BufferedImage VictoryPoint5 = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/governershouse--development.png"));
            BufferedImage Knight = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/soldier--development.png"));
            BufferedImage RoadBuilding = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/roadbuilding--development.png"));
            BufferedImage Monopoly = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/monopoly--development.png"));
            BufferedImage YearofPlenty = ImageIO.read(DevelopmentCard.class.getResourceAsStream("images/development/yearofplenty--development.png"));
            graphics.put(0, VictoryPoint1);
            graphics.put(1, VictoryPoint2);
            graphics.put(2, VictoryPoint3);
            graphics.put(3, VictoryPoint4);
            graphics.put(4, VictoryPoint5);
            graphics.put(5, Knight);
            graphics.put(6, RoadBuilding);
            graphics.put(7, YearofPlenty);
            graphics.put(8, Monopoly);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DevelopmentCard(int id) {
        if (id <= 4) {
            type = "Victory Point";
        }
        if (id == 5) {
            type = "Knight";
        }
        if (id == 6) {
            type = "Road Building";
        }
        if (id == 7) {
            type = "Year of Plenty";
        }
        if (id == 8) {
            type = "Monopoly";
        }
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public BufferedImage getGraphic() {
        return graphics.get(id);
    }
}
