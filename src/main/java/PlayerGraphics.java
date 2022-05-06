import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerGraphics {

    public AnchorPane boardPane;
    public ScrollPane scrollPane;
    public Button resourceButton;
    public VBox resourcesBox;
    public Button rollDiceButton;
    public Button tradeButton;
    public Button buyDevelopmentButton;
    public Button developmentCardsButton;
    public Button nextRoundButton;

    @FXML
    public void initialize(){
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(Background.fill(Color.TRANSPARENT));
    }

    public void refreshDisplay() {
        refreshPlayerInfo();
        refreshButtons();
        refreshText();
        refreshBoardInteractives();
    }

    private void refreshButtons(){
        if(TurnManager.hasRolledDice()){
            tradeButton.setDisable(false);
            buyDevelopmentButton.setDisable(false);
            developmentCardsButton.setDisable(false);
            rollDiceButton.setDisable(true);
            nextRoundButton.setDisable(false);
        } else {
            tradeButton.setDisable(true);
            buyDevelopmentButton.setDisable(true);
            developmentCardsButton.setDisable(true);
            rollDiceButton.setDisable(false);
            nextRoundButton.setDisable(true);
        }
    }

    private void refreshPlayerInfo() {
        int resouInd = 0;
        for (int r = 0; r < 2; r++){
            HBox h = (HBox) resourcesBox.getChildren().get(r);
            for (int i = 0; i < h.getChildren().size(); i++){
                if (i % 2 == 1){
                    Label l = (Label) h.getChildren().get(i);
                    l.setText(TurnManager.getCurrentPlayer().getResources().get(Resource.getResourceList().get(resouInd++)) + "");
                }
            }
        }
    }

    private void refreshText() {
        Label value = new Label(TurnManager.getAllActions());
        value.setBackground(Background.fill(Color.TRANSPARENT));
        scrollPane.setContent(value);
    }

    private void refreshBoardInteractives() {
        HexGridPane hexGridPane = BoardGame.getHexGridPane();
        double radius = hexGridPane.getRadius();
        double vertRadius = radius/10.;
        int maxR = 2;
        boolean showWater = false;
        for (Edge e : hexGridPane.getEdgeManager().getAllEdges()){
            List<Vertex> vertices = new ArrayList<>(e.getAdjacentVertices().values());
            Vertex v1 = vertices.get(0);
            Vertex v2 = vertices.get(1);
            double rowCoord1 = maxR * (radius + radius/2.) + radius + v1.getR() * radius/2. + v1.getR() / (double) Math.abs(v1.getR()) * (v1.getC() % 2 + (Math.abs(v1.getR()) - 1) * 2) * radius/2.;
            double colCoord1 = (Math.abs(v1.getR()) - 1 + v1.getC()) * radius * Math.sqrt(3) / 2.;
            double rowCoord2 = maxR * (radius + radius/2.) + radius + v2.getR() * radius/2. + v2.getR() / (double) Math.abs(v2.getR()) * (v2.getC() % 2 + (Math.abs(v2.getR()) - 1) * 2) * radius/2.;
            double colCoord2 = (Math.abs(v2.getR()) - 1 + v2.getC()) * radius * Math.sqrt(3) / 2.;
            Line l = new Line(colCoord1, rowCoord1, colCoord2, rowCoord2);
            l.setStroke(Color.TRANSPARENT);
            l.setStrokeWidth(5);
            l.setOnMouseClicked((event) -> {
                ContextMenu menu = new ContextMenu();
                if (TurnManager.getCurrentPlayer().getBuildableEdges().contains(e)){
                    MenuItem mi = new MenuItem("Build Road");
                    mi.setOnAction((event1) -> {
                        e.buildRoad(TurnManager.getCurrentPlayer());
                    });
                }
                MenuItem mi = new MenuItem("Edge Properties");
                if (e.hasRoad()){
                    mi.setText("Road Properties");
                }
                mi.setOnAction((event1) -> {
                    System.out.println("hi");
                });
                menu.show(l, Side.BOTTOM, 0, 0);
                //System.out.println("v1, v2:" + e.getAdjacentTiles().values().stream().map(Tile::getWeight).collect(Collectors.toList()));
            });
            l.setOnMouseEntered((event) -> {
                l.setStroke(Color.BLACK);
            });
            l.setOnMouseExited((event) -> {
                l.setStroke(Color.TRANSPARENT);
            });
            Label l1 = new Label(v1.getR() + ", " + v1.getC());
            l1.setLayoutX(rowCoord1);
            boardPane.getChildren().addAll(l);
        }
        for (Vertex v : hexGridPane.getVertexManager().getAllVertices()){
            double rowCoord = maxR * (radius + radius/2.) + radius + v.getR() * radius/2. + v.getR() / (double) Math.abs(v.getR()) * (v.getC() % 2 + (Math.abs(v.getR()) - 1) * 2) * radius/2. - vertRadius;
            double colCoord = (showWater ? 1 : 0) * radius * Math.sqrt(3) + (Math.abs(v.getR()) - 1 + v.getC()) * radius * Math.sqrt(3) / 2. - vertRadius;
            Circle circle = new Circle(vertRadius, Color.TRANSPARENT);
            StackPane sp = new StackPane(circle);
            circle.setOnMouseClicked((event) -> {
                //v.getAdjacentTiles().forEach((key, value) -> System.out.println(Tile.directions[key] + ": " + value.getWeight()));
            });
            circle.setOnMouseEntered(event -> {
                circle.setFill(Color.BLACK);
            });
            circle.setOnMouseExited(event -> {
                circle.setFill(Color.TRANSPARENT);
            });
            sp.setLayoutY(rowCoord);
            sp.setLayoutX(colCoord);
            boardPane.getChildren().add(sp);
        }
        for (Player p : TurnManager.getPlayerList()){
            for (Settlement s : p.getSettlements()){
                double x = getX(s.getVertex(), radius);
                double y = getY(s.getVertex(), radius);
                /*
                ImageView iv = new ImageView(s.getGraphic());
                iv.setFitWidth(20);
                iv.setFitHeight(20);

                 */
                StackPane sp = new StackPane(new Circle(10, p.getColor()));
                sp.setLayoutX(x - 10);
                sp.setLayoutY(y - 10);
                boardPane.getChildren().add(sp);
            }
            for (Road r : p.getRoads()){
                List<Vertex> vertices = new ArrayList<>(r.getEdge().getAdjacentVertices().values());
                Vertex v1 = vertices.get(0);
                Vertex v2 = vertices.get(1);
                double x1 = getX(v1, radius);
                double x2 = getX(v2, radius);
                double y1 = getY(v1, radius);
                double y2 = getY(v2, radius);
                Line l = new Line(x1, y1, x2, y2);
                l.setStroke(p.getColor());
                l.setStrokeWidth(5);
                boardPane.getChildren().add(l);
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

    public void pause(MouseEvent mouseEvent) {
    }

    public void toggleResourceShowing(ActionEvent actionEvent) {
        if (resourcesBox.isVisible()){
            resourceButton.setText("Show Resources");
            resourcesBox.setVisible(false);
        } else {
            resourceButton.setText("Hide Resources");
            resourcesBox.setVisible(true);
        }
    }

    public void rollDice(ActionEvent actionEvent) {
        BoardGame.rollDice();
        refreshDisplay();
    }

    public void nextRound(ActionEvent actionEvent) {
        TurnManager.nextTurn();
        refreshDisplay();
    }
}
