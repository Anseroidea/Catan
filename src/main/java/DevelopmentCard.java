import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Math;

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
    public static Map<Integer, Image> graphics;
    private String type;
    private Player player;

    public DevelopmentCard() {
        BufferedImage VictoryPoint1 = (BufferedImage) new ImageIcon("resources/images/development/market--development").getImage();
        BufferedImage VictoryPoint2 = (BufferedImage) new ImageIcon("resources/images/development/chapel--development").getImage();
        BufferedImage VictoryPoint3 = (BufferedImage) new ImageIcon("resources/images/development/university--development").getImage();
        BufferedImage VictoryPoint4 = (BufferedImage) new ImageIcon("resources/images/development/library--development").getImage();
        BufferedImage VictoryPoint5 = (BufferedImage) new ImageIcon("resources/images/development/govenershosue--development").getImage();
        BufferedImage Knight = (BufferedImage) new ImageIcon("resources/images/development/soldier--development").getImage();
        BufferedImage RoadBuilding = (BufferedImage) new ImageIcon("resources/images/development/roadbuilding--development").getImage();
        BufferedImage Monopoly = (BufferedImage) new ImageIcon("resources/image/development/monopoly--development.png").getImage();
        BufferedImage YearofPlenty = (BufferedImage) new ImageIcon("resources/image/development/yearofplenty--development.png").getImage();

        id = (int) Math.random()*4;
        if (id == 0) {
            type = "Victory Point";
        }
        if (id == 1) {
            type = "Knight";
        }
        if (id == 2) {
            type = "Road Building";
        }
        if (id == 3) {
            type = "Year of Plenty";
        }
        if (id == 4) {
            type = "Monopoly";
        }
        graphics.put(0, VictoryPoint1);
        graphics.put(0, VictoryPoint2);
        graphics.put(0, VictoryPoint3);
        graphics.put(0, VictoryPoint4);
        graphics.put(0, VictoryPoint5);
        graphics.put(1, Knight);
        graphics.put(2, RoadBuilding);
        graphics.put(3, YearofPlenty);
        graphics.put(4, Monopoly);
    }

    public int getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }
}
