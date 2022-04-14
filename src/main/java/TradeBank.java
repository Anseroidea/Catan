import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TradeBank
{
    @FXML
    private Button confirmTrade, changeRequest;

    @FXML
    private StackPane a, b, c, d, e;

    public void confirmBankTrade(MouseEvent mouseEvent)
    {

    }

    public void addBorder(MouseEvent mouseEvent)
    {
        ((Pane)mouseEvent.getSource()).setStyle("-fx-border-color: black");
    }

    public void removeBorder(MouseEvent mouseEvent)
    {
        ((Pane)mouseEvent.getSource()).setStyle("-fx-border-color: transparent");
    }
}
