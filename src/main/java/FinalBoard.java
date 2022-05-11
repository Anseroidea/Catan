import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class FinalBoard {
    public AnchorPane buildingsPane;
    public StackPane boardPane;

    public void initPopUp(){
        if (boardPane.getChildren().size() == 2){
            boardPane.getChildren().set(0, BoardGame.getHexGridPane().toPane(false));
        } else {
            boardPane.getChildren().add(0, BoardGame.getHexGridPane().toPane(false));
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
                buildingsPane.getChildren().addAll(b,l);
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
                buildingsPane.getChildren().add(sp);
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
