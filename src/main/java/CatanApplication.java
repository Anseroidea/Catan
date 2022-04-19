import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.*;

public class CatanApplication extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CatanApplication.primaryStage = primaryStage;
        BoardGame.initialize(new Player[1]);
        for (ProgramState s : ProgramState.values()){
            System.out.println("s.name().toLowerCase() = " + s.name().toLowerCase());
            try {
                FXMLLoader fl = new FXMLLoader(CatanApplication.class.getResource("/fxml/" + s.name().toLowerCase() + ".fxml"));
                AnchorPane ap = fl.load();
                if (s.name().equals("BOARD")) {
                    FXMLLoader fl2 = new FXMLLoader(CatanApplication.class.getResource("/fxml/player.fxml"));
                    AnchorPane ap2 = fl2.load();
                    GraphicsManager.initialize(fl.getController(), fl2.getController());
                    GraphicsManager.initializeGraphics();
                    s.setPane(new StackPane(ap, ap2));
                } else {
                    s.setPane(ap);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        ProgramState.setCurrentState(ProgramState.BOARD);
        updateDisplay();
        primaryStage.show();
        primaryStage.setFullScreen(true);

    }

    public void updateDisplay(){
        primaryStage.setScene(new Scene(ProgramState.getCurrentState().getPane(), 1920, 1080));
    }

    public static void main(String[] args){
        launch(args);
    }
}