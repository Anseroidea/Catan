import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RobberSelect {


    public AnchorPane boardPane;
    public Button back;
    public VBox resourceDisplay;
    public Label stealLabel;
    public AnchorPane ap;
    private boolean knight;
    private boolean isSelectingTile;
    private boolean hasStolen;
    private Tile robberTile;

    public void initPopUp(boolean knight){
        boardPane.getChildren().clear();
        boardPane.getChildren().add(BoardGame.getHexGridPane().toPane(false, false));
        this.knight = knight;
        back.setVisible(knight);
        isSelectingTile = true;
        hasStolen = false;
        robberTile = BoardGame.getRobber().getTile();
        resourceDisplay.setVisible(false);
        refreshAll();
    }

    public void refreshAll(){
        ap.getChildren().clear();
        refreshBoardInteractives();
    }

    public void refreshBoardInteractives(){
        for (Player p : TurnManager.getPlayerList()){
            for (Road r : p.getRoads()){
                List<Vertex> vertices = new ArrayList<>(r.getEdge().getAdjacentVertices().values());
                Vertex v1 = vertices.get(0);
                Vertex v2 = vertices.get(1);
                double x1 = getX(v1, BoardGame.getHexGridPane().getRadius());
                double x2 = getX(v2, BoardGame.getHexGridPane().getRadius());
                double y1 = getY(v1, BoardGame.getHexGridPane().getRadius());
                double y2 = getY(v2, BoardGame.getHexGridPane().getRadius());
                Line l = new Line(x1, y1, x2, y2);
                Line b = new Line(x1, y1, x2, y2);
                l.setStroke(p.getColor());
                l.setStrokeWidth(7);
                b.setStroke(Color.BLACK);
                b.setStrokeWidth(10);
                boardPane.getChildren().addAll(b,l);
            }
            for (Settlement s : p.getSettlements()){
                double x = getX(s.getVertex(), BoardGame.getHexGridPane().getRadius());
                double y = getY(s.getVertex(), BoardGame.getHexGridPane().getRadius());
                /*
                ImageView iv = new ImageView(s.getGraphic());
                iv.setFitWidth(20);
                iv.setFitHeight(20);

                 */

                StackPane sp = new StackPane();
                if (s.isSettlement()){
                    Circle e = new Circle(10, p.getColor());
                    e.setStroke(Color.BLACK);
                    e.setStrokeWidth(2);
                    sp.getChildren().add(e);
                } else {
                    Rectangle e = new Rectangle(20, 20, p.getColor());
                    e.setStrokeWidth(2);
                    e.setStroke(Color.BLACK);
                    sp.getChildren().add(e);
                }
                sp.setLayoutX(x - 10);
                sp.setLayoutY(y - 10);
                boardPane.getChildren().add(sp);
            }
        }
        Map<Integer, List<Tile>> map = BoardGame.getHexGridPane().getMap();
        boolean showWater = false;
        int maxR = 2;
        double radius = BoardGame.getHexGridPane().getRadius();
        int robberR = robberTile.getR();
        int robberC = robberTile.getC();
        double rowC = (robberR + maxR) * (radius + radius/2.) + radius/Math.sqrt(3) * 2 - 70;
        double colC = Math.abs(robberR) * radius * Math.sqrt(3)/2. + radius * Math.sqrt(3) * (robberC - (showWater ? 0 : 1)) + radius/2 * Math.sqrt(3) - 54;
        StackPane robberSP = new StackPane();
        robberSP.setLayoutX(colC);
        robberSP.setLayoutY(rowC);
        ImageView e = new ImageView(SwingFXUtils.toFXImage(Robber.getGraphic(), null));
        e.setFitHeight(72);
        e.setFitWidth(36);
        robberSP.getChildren().add(e);
        ap.getChildren().add(robberSP);
        if (isSelectingTile) {
            for (Tile t : BoardGame.getTilesWithSettlementsWithoutOnlyPlayer(TurnManager.getCurrentPlayer())){
                if (t.getWeightLetter() == null){
                    continue;
                }
                int r = t.getR();
                int c = t.getC();
                if (map.get(r).get(c) != null && (showWater || map.get(r).get(c).getId() != 6)){
                    double rowCoord = (r + maxR) * (radius + radius/2.) + radius/Math.sqrt(3) * 2 - 70;
                    double colCoord = Math.abs(r) * radius * Math.sqrt(3)/2. + radius * Math.sqrt(3) * (c - (showWater ? 0 : 1)) + radius/2 * Math.sqrt(3) - 54;
                    StackPane sp = new StackPane();
                    if (!map.get(r).get(c).equals(robberTile)){
                        Circle cir = new Circle(radius/2., new Color(0.1,0.7, 0, .7));
                        int finalC = c;
                        cir.setOnMouseClicked((event) -> {
                            isSelectingTile = false;
                            robberTile = map.get(r).get(finalC);
                            boolean settlementsNearby = false;
                            for (Vertex v : robberTile.getAdjacentVertices().values()){
                                if (v.getSettlement() != null && !v.getSettlement().getPlayer().equals(TurnManager.getCurrentPlayer())){
                                    settlementsNearby = true;
                                }
                            }
                            if (settlementsNearby){
                                refreshAll();
                            } else {
                                BoardGame.getRobber().setTile(robberTile);
                                GraphicsManager.refreshDisplay();
                                back(null);
                            }
                        });
                        sp.getChildren().add(cir);
                    }
                    sp.setLayoutY(rowCoord);
                    sp.setLayoutX(colCoord);
                    ap.getChildren().add(sp);
                }
            }
        } else {
            for (Vertex v : robberTile.getAdjacentVertices().values()){
                if (v.getSettlement() != null){
                    if (v.getSettlement().getPlayer().equals(TurnManager.getCurrentPlayer())){
                        continue;
                    }
                    double rowCoord = maxR * (radius + radius/2.) + radius + v.getR() * radius/2. + v.getR() / (double) Math.abs(v.getR()) * (v.getC() % 2 + (Math.abs(v.getR()) - 1) * 2) * radius/2. - 15;
                    double colCoord = (showWater ? 1 : 0) * radius * Math.sqrt(3) + (Math.abs(v.getR()) - 1 + v.getC()) * radius * Math.sqrt(3) / 2. - 15;
                    Circle circle = new Circle(15, new Color(0.1,0.7, 0, .7));
                    circle.setOnMouseClicked((event) -> {
                        if (hasStolen){
                            return;
                        }
                        hasStolen = true;
                        List<Resource> resources = new ArrayList<>();
                        for (Resource r : Resource.getResourceList()){
                            for (int i = 0; i < v.getSettlement().getPlayer().getResources().get(r); i++){
                                resources.add(r);
                            }
                        }
                        resourceDisplay.setVisible(true);
                        System.out.println("hi!!!!");
                        if (knight){
                            TurnManager.getCurrentPlayer().useDevelopment(TurnManager.getCurrentPlayer().getDevelopmentCards().stream().filter(d -> d.getId() == 5).findFirst().get());
                        }
                        if (resources.isEmpty()) {
                            if (knight) {
                                TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " used a knight.");
                            }
                            stealLabel.setText("You stole nothing");
                        } else {
                            Collections.shuffle(resources);
                            Resource stolen = resources.get(0);
                            v.getSettlement().getPlayer().changeCards(stolen, -1);
                            TurnManager.getCurrentPlayer().changeCards(stolen, 1);
                            resourceDisplay.setVisible(true);
                            if (knight) {
                                TurnManager.setHasPlayedDevelopmentCard(true);
                                TurnManager.getCurrentPlayer().addKnight();
                                TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " used a knight and stole " + stolen.getResource());
                            } else {
                                TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " stole " + stolen.getResource());
                            }
                            stealLabel.setText("You stole a " + stolen.getResource());
                            BoardGame.getRobber().setTile(robberTile);
                        }
                        BoardGame.getRobber().setTile(robberTile);
                    });
                    StackPane sp = new StackPane(circle);
                    sp.setLayoutY(rowCoord);
                    sp.setLayoutX(colCoord);
                    ap.getChildren().add(sp);
                }
            }
        }
    }

    public void back(ActionEvent actionEvent) {
        Stage thisStage = (Stage) boardPane.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    private double getY(Vertex v, double radius){
        return getY(v, radius, 2);
    }

    private double getY(Vertex v, double radius, int maxR){
        return maxR * (radius + radius/2.) + radius + v.getR() * radius/2. + v.getR() / (double) Math.abs(v.getR()) * (v.getC() % 2 + (Math.abs(v.getR()) - 1) * 2) * radius/2.;
    }

    private double getX(Vertex v, double radius){
        return getX(v, radius, false);
    }

    private double getX(Vertex v, double radius, boolean showWater){
        return (showWater ? 1 : 0) * radius * Math.sqrt(3) + (Math.abs(v.getR()) - 1 + v.getC()) * radius * Math.sqrt(3) / 2.;
    }

    public void confirm(ActionEvent actionEvent) {
        back(null);
        GraphicsManager.refreshDisplay();
    }
}
