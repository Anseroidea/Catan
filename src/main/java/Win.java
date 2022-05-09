import javafx.scene.control.Label;

public class Win {


    public Label winLabel;

    public void initWin(){
        winLabel.setText(TurnManager.getCurrentPlayer().getName() + ", you win!");
    }

}
