import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Rules
{
    private static BufferedImage[] pages;
    private static int index = 0;
    @FXML
    private Button backButton, nextButton, exitButton, pdfButton;

    @FXML
    private ImageView page;

    public Rules()
    {
        pages = new BufferedImage[16];
        for(int i = 0; i < pages.length; i++)
            try {
                pages[i] = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/rules/rules-" + (i + 1) + ".png")));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void initialize()
    {
        Image image = SwingFXUtils.toFXImage(pages[0], null);
        page.setImage(image);
        updateButtons();
    }

    private void updateButtons()
    {
        nextButton.setVisible(!(index == pages.length - 1));
        backButton.setVisible(!(index == 0));
    }
    public void exitRules(MouseEvent mouseEvent)
    {
        //CatanApplication.updateDisplay();
    }

    public void prevPage(MouseEvent mouseEvent)
    {
        Image image = SwingFXUtils.toFXImage(pages[--index], null);
        page.setImage(image);
        updateButtons();
    }

    public void nextPage(MouseEvent mouseEvent)
    {
        Image image = SwingFXUtils.toFXImage(pages[++index], null);
        page.setImage(image);
        updateButtons();
    }

    public void createPDF(MouseEvent mouseEvent)
    {
        //I have no idea pls help
    }
}
