import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
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
        ProgramState.setCurrentState(ProgramState.PLAY);
        BoardGame.initialize(new Player[1]);
        primaryStage.setScene(new Scene(BoardGame.getHexGridPane().toPane(false)));
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