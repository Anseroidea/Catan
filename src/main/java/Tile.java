import javafx.scene.image.Image;

public class Tile implements Displayable{

    private Image image;

    public Tile(Image i){
        image = i;
    }

    public Image getImage() {
        return image;
    }
}
