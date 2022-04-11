import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.image.BufferedImage;

public class Rules
{
    private static BufferedImage[] pages;
    private static int index;
    @FXML
    private Button backButton, nextButton, exitButton, pdfButton;

    @FXML
    private ImageView page;

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
