import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SettlementSelect {

    public StackPane boardStackPane;
    public Label playerLabel;
    private static SettlementSelect ss;
    private boolean isRoad;
    private boolean isSecondSelection = false;
    private Vertex lastPlacedVertex;
    private int iterations;

    public static void initialize(SettlementSelect ss){
        SettlementSelect.ss = ss;
    }

    public static void updateDisplay(){
        ss.playerLabel.setText(TurnManager.getCurrentPlayer().getName() + ", select your settlement location.");
        ss.refreshBoard();
        ss.refreshInteractions();
    }

    public void refreshBoard(){
        if (boardStackPane.getChildren().size() > 1){
            boardStackPane.getChildren().set(0, BoardGame.getHexGridPane().toPane(false));
        } else {
            boardStackPane.getChildren().add(0, BoardGame.getHexGridPane().toPane(false));
        }
    }

    public void refreshInteractions(){
        Pane boardPane = (Pane) boardStackPane.getChildren().get(1);
        boardPane.getChildren().clear();
        HexGridPane hexGridPane = BoardGame.getHexGridPane();
        double radius = hexGridPane.getRadius();
        double vertRadius = radius/10.;
        int maxR = 2;
        boolean showWater = false;
        if (lastPlacedVertex != null){
            for (Edge e : lastPlacedVertex.getAdjacentEdges().values()){
                List<Vertex> vertices = new ArrayList<>(e.getAdjacentVertices().values());
                Vertex v1 = vertices.get(0);
                Vertex v2 = vertices.get(1);
                double rowCoord1 = maxR * (radius + radius/2.) + radius + v1.getR() * radius/2. + v1.getR() / (double) Math.abs(v1.getR()) * (v1.getC() % 2 + (Math.abs(v1.getR()) - 1) * 2) * radius/2.;
                double colCoord1 = (Math.abs(v1.getR()) - 1 + v1.getC()) * radius * Math.sqrt(3) / 2.;
                double rowCoord2 = maxR * (radius + radius/2.) + radius + v2.getR() * radius/2. + v2.getR() / (double) Math.abs(v2.getR()) * (v2.getC() % 2 + (Math.abs(v2.getR()) - 1) * 2) * radius/2.;
                double colCoord2 = (Math.abs(v2.getR()) - 1 + v2.getC()) * radius * Math.sqrt(3) / 2.;
                Line l = new Line(colCoord1, rowCoord1, colCoord2, rowCoord2);
                l.setStroke(new Color(0.1,0.7, 0, .7));
                l.setStrokeWidth(5);
                l.setOnMouseClicked((event) -> {
                    iterations++;
                    e.buildRoad(TurnManager.getCurrentPlayer());
                    TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " placed a road.");
                    lastPlacedVertex = null;
                    System.out.println(iterations);
                    if (isSecondSelection && iterations >= TurnManager.getPlayerList().size() * 2){
                        ProgramState.setCurrentState(ProgramState.BOARD);
                        CatanApplication.updateDisplay();
                    } else if (iterations >= TurnManager.getPlayerList().size()) {
                        isSecondSelection = true;
                        if (iterations > TurnManager.getPlayerList().size()){
                            TurnManager.previousTurn();
                        }
                    } else {
                        TurnManager.nextTurn();
                    }
                    updateDisplay();
                });
                Label l1 = new Label(v1.getR() + ", " + v1.getC());
                l1.setLayoutX(rowCoord1);
                boardPane.getChildren().addAll(l);
            }
        } else {
            for (Vertex v : BoardGame.getBuildableVertices()){
                double rowCoord = maxR * (radius + radius/2.) + radius + v.getR() * radius/2. + v.getR() / (double) Math.abs(v.getR()) * (v.getC() % 2 + (Math.abs(v.getR()) - 1) * 2) * radius/2. - vertRadius;
                double colCoord = (showWater ? 1 : 0) * radius * Math.sqrt(3) + (Math.abs(v.getR()) - 1 + v.getC()) * radius * Math.sqrt(3) / 2. - vertRadius;
                Circle circle = new Circle(vertRadius, new Color(0.1,0.7, 0, .7));
                StackPane sp = new StackPane(circle);
                circle.setOnMouseClicked((event) -> {
                    v.addSettlement(TurnManager.getCurrentPlayer());
                    if (isSecondSelection){
                        for (Tile t : v.getAdjacentTiles().values()) {
                            if (t.getResource()!= null){
                                System.out.println(t.getResource().getResource());
                            }
                            if (t.getResource() != null) {
                                TurnManager.getCurrentPlayer().changeCards(t.getResource(), 1);
                            }
                        }
                    }
                    TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " placed a settlement.");
                    lastPlacedVertex = v;
                    updateDisplay();
                });
                sp.setLayoutY(rowCoord);
                sp.setLayoutX(colCoord);
                boardPane.getChildren().add(sp);
            }
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
