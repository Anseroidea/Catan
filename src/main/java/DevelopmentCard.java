import javafx.scene.image.Image;

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

    public int getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }
}
