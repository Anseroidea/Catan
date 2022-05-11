import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class UserManual {
    public ImageView slide;
    public Button backButton;
    public Button nextButton;
    private static int index = 0;
    private static int size = 5;
    private static BufferedImage[] slides = new BufferedImage[size];

    static {
        for (int i = 0; i < slides.length; i++){
            try {
                slides[i] = ImageIO.read(UserManual.class.getResourceAsStream("images/manual/User Manual-" + (i + 1) + ".png"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize(){
        refreshDisplay();
    }

    public void refreshDisplay(){
        refreshButtons();
        slide.setImage(SwingFXUtils.toFXImage(slides[index], null));
    }

    public void refreshButtons(){
        backButton.setDisable(index <= 0);
        nextButton.setDisable(index >= size - 1);
    }

    public void back(ActionEvent actionEvent) {
        index--;
        refreshDisplay();
    }

    public void next(ActionEvent actionEvent) {
        index++;
        refreshDisplay();
    }

    public void main(ActionEvent actionEvent) {
        ProgramState.setCurrentState(ProgramState.MAIN);
        CatanApplication.updateDisplay();
    }
}
