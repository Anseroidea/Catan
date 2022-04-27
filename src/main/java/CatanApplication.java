import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.skin.ColorPickerSkin;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.*;

public class CatanApplication extends Application {

    private static Stage primaryStage;

    public static void initializeGame(Player[] p){
        BoardGame.initializePlayers(p);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        CatanApplication.primaryStage = primaryStage;
        BoardGame.initializeGraphics();
        for (ProgramState s : ProgramState.values()){
            System.out.println("s.name().toLowerCase() = " + s.name().toLowerCase());
            try {
                FXMLLoader fl = new FXMLLoader(CatanApplication.class.getResource("/fxml/" + s.name().toLowerCase() + ".fxml"));
                Pane ap = fl.load();
                if (s.name().equals("BOARD")) {
                    FXMLLoader fl2 = new FXMLLoader(CatanApplication.class.getResource("/fxml/player.fxml"));
                    Pane ap2 = fl2.load();
                    GraphicsManager.initialize(fl.getController(), fl2.getController());
                    s.setPane(new StackPane(ap, ap2));
                } else if (s.name().equals("SETTLEMENT")) {
                    SettlementSelect.initialize(fl.getController());
                    s.setPane(ap);
                } else {
                    s.setPane(ap);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        ProgramState.setCurrentState(ProgramState.MAIN);
        updateDisplay();
        primaryStage.show();
        primaryStage.setFullScreen(true);

        for(PopUp p : PopUp.values())
        {
            try {
                FXMLLoader fl = new FXMLLoader(CatanApplication.class.getResource("/fxml/" + p.name().toLowerCase() + ".fxml"));
                AnchorPane ap = fl.load();
                p.setPane(ap);
                p.setController(fl.getController());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void updateDisplay(){
        if (ProgramState.getCurrentState().equals(ProgramState.BOARD)){
            GraphicsManager.refreshDisplay();
        }
        primaryStage.setScene(new Scene(ProgramState.getCurrentState().getPane(), 1920, 1080));
    }

    public static void main(String[] args){
        launch(args);
    }
}