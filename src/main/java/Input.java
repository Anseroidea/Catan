import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class Input {
    public ColorPicker colorOne;
    public TextField playerTextOne;
    public ColorPicker colorTwo;
    public TextField playerTextTwo;
    public ColorPicker colorThree;
    public TextField playerTextThree;
    public HBox playerFourBox;
    public ColorPicker colorFour;
    public TextField playerTextFour;
    public Button addButton;
    public Button removeButton;

    public void addPlayerFour(ActionEvent actionEvent) {
        playerFourBox.setVisible(true);
        addButton.setDisable(true);
        removeButton.setDisable(false);
    }

    public void removePlayerFour(ActionEvent actionEvent) {
        playerFourBox.setVisible(false);
        addButton.setDisable(false);
        removeButton.setDisable(true);
    }

    public void start(ActionEvent actionEvent) {
        Player p1 = new Player(playerTextOne.getText(), 0, colorOne.getValue());
        Player p2 = new Player(playerTextTwo.getText(), 1, colorTwo.getValue());
        Player p3 = new Player(playerTextThree.getText(), 2, colorThree.getValue());
        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        if (playerFourBox.isVisible()){
            Player p4 = new Player(playerTextFour.getText(), 3, colorFour.getValue());
            list.add(p4);
        }
        CatanApplication.initializeGame(list.toArray(new Player[(playerFourBox.isVisible() ? 4 : 3)]));
        ProgramState.setCurrentState(ProgramState.SETTLEMENT);
        SettlementSelect.updateDisplay();
        CatanApplication.updateDisplay();
    }
}
