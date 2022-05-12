import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class RoadBuilding {
    public AnchorPane boardPane;
    public Label roadsLeftLabel;
    public Button confirm;
    private List<Edge> selectedEdges = new ArrayList<>();

    public void initPopUp(){
        boardPane.getChildren().clear();
        boardPane.getChildren().add(BoardGame.getHexGridPane().toPane(false));
        selectedEdges = new ArrayList<>();
        refreshAll();
    }

    public void refreshAll(){
        roadsLeftLabel.setText("Roads Left: " + (2 - selectedEdges.size()));
        refreshInteractives();
        refreshButtons();
    }

    public void refreshButtons(){
        confirm.setDisable(selectedEdges.size() < Math.min(2, 15 - TurnManager.getCurrentPlayer().getRoads().size()));
    }

    public void refreshInteractives(){
        HexGridPane hexGridPane = BoardGame.getHexGridPane();
        int maxR = 2;
        double radius = hexGridPane.getRadius();
        for (Edge e : TurnManager.getCurrentPlayer().getBuildableEdges()){
            List<Vertex> vertices = new ArrayList<>(e.getAdjacentVertices().values());
            Vertex v1 = vertices.get(0);
            Vertex v2 = vertices.get(1);
            double rowCoord1 = maxR * (radius + radius/2.) + radius + v1.getR() * radius/2. + v1.getR() / (double) Math.abs(v1.getR()) * (v1.getC() % 2 + (Math.abs(v1.getR()) - 1) * 2) * radius/2.;
            double colCoord1 = (Math.abs(v1.getR()) - 1 + v1.getC()) * radius * Math.sqrt(3) / 2.;
            double rowCoord2 = maxR * (radius + radius/2.) + radius + v2.getR() * radius/2. + v2.getR() / (double) Math.abs(v2.getR()) * (v2.getC() % 2 + (Math.abs(v2.getR()) - 1) * 2) * radius/2.;
            double colCoord2 = (Math.abs(v2.getR()) - 1 + v2.getC()) * radius * Math.sqrt(3) / 2.;
            System.out.println(rowCoord1);
            System.out.println(colCoord1);
            Line l = new Line(colCoord1, rowCoord1, colCoord2, rowCoord2);
            l.setStroke(Color.TRANSPARENT);
            l.setStrokeWidth(5);
            l.setOnMouseClicked((event) -> {
                if (!TurnManager.hasRolledDice() || selectedEdges.size() == Math.min(2, 15 - TurnManager.getCurrentPlayer().getRoads().size())){
                    return;
                }
                selectedEdges.add(e);
                refreshAll();
            });
            l.setStroke(new Color(0.1,0.7, 0, .7));
            boardPane.getChildren().addAll(l);
        }
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
        for (Edge e : selectedEdges){
            List<Vertex> vertices = new ArrayList<>(e.getAdjacentVertices().values());
            Vertex v1 = vertices.get(0);
            Vertex v2 = vertices.get(1);
            double x1 = getX(v1, BoardGame.getHexGridPane().getRadius());
            double x2 = getX(v2, BoardGame.getHexGridPane().getRadius());
            double y1 = getY(v1, BoardGame.getHexGridPane().getRadius());
            double y2 = getY(v2, BoardGame.getHexGridPane().getRadius());
            Line l = new Line(x1, y1, x2, y2);
            Line b = new Line(x1, y1, x2, y2);
            l.setStroke(TurnManager.getCurrentPlayer().getColor());
            l.setStrokeWidth(7);
            b.setStroke(Color.BLACK);
            b.setStrokeWidth(10);
            boardPane.getChildren().addAll(b,l);
        }
    }

    public void back(ActionEvent actionEvent) {
        Stage stage = (Stage) boardPane.getScene().getWindow();
        stage.getScene().setRoot(new AnchorPane());
        stage.close();
    }

    public void confirm(ActionEvent actionEvent) {
        TurnManager.getCurrentPlayer().useDevelopment(TurnManager.getCurrentPlayer().getDevelopmentCards().stream().filter(d -> d.getId() == 6).findFirst().get());
        for (Edge e : selectedEdges){
            e.buildRoad(TurnManager.getCurrentPlayer());
        }
        TurnManager.setHasPlayedDevelopmentCard(true);
        TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " played a Road Building Card.");
        BoardGame.updateLongestRoad();
        Stage stage = (Stage) boardPane.getScene().getWindow();
        stage.getScene().setRoot(new AnchorPane());
        stage.close();
        BoardGame.checkWin();
        GraphicsManager.refreshDisplay();
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
}
