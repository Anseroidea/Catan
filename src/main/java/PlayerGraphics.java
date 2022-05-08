import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
    public Button bankTradeButton;
    public StackPane developmentPanel;
    public VBox developmentCardBox;

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
        if (developmentPanel.isVisible()){
            refreshDevelopment();
        }
    }

    private void refreshDevelopment() {
        VBox v = developmentCardBox;
        for (int i = v.getChildren().size() - 1; i >= 1; i--){
            v.getChildren().remove(i);
        }
        for (int i = 0; i < TurnManager.getCurrentPlayer().getDevelopmentCards().size(); i++){
            DevelopmentCard dc = TurnManager.getCurrentPlayer().getDevelopmentCards().get(i);
            HBox h = new HBox();
            h.setSpacing(30);
            ImageView im = new ImageView(SwingFXUtils.toFXImage(dc.getGraphic(), null));
            im.setFitWidth(150);
            im.setFitHeight(200);
            if (dc.getId() > 4){
                im.setOnMouseClicked((event) -> {

                });
            }
            h.getChildren().add(im);
            i++;
            if (i < TurnManager.getCurrentPlayer().getDevelopmentCards().size()){
                dc = TurnManager.getCurrentPlayer().getDevelopmentCards().get(i);
                im = new ImageView(SwingFXUtils.toFXImage(dc.getGraphic(), null));
                im.setFitWidth(150);
                im.setFitHeight(200);
                if (dc.getId() > 4){
                    im.setOnMouseClicked((event) -> {

                    });
                }
                h.getChildren().add(im);
            }
            v.getChildren().add(h);
        }
    }

    private void refreshButtons(){
        if(TurnManager.hasRolledDice()){
            if (TurnManager.hasBuilt()){
                tradeButton.setDisable(true);
                bankTradeButton.setDisable(true);
            } else {
                tradeButton.setDisable(false);
                bankTradeButton.setDisable(false);
            }
            buyDevelopmentButton.setDisable(!TurnManager.getCurrentPlayer().canBuyDevelopment());
            developmentCardsButton.setDisable(false);
            rollDiceButton.setDisable(true);
            nextRoundButton.setDisable(false);
        } else {
            tradeButton.setDisable(true);
            bankTradeButton.setDisable(true);
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
                if (!TurnManager.hasRolledDice()){
                    return;
                }
                ContextMenu menu = new ContextMenu();
                if (TurnManager.getCurrentPlayer().getBuildableEdges().contains(e)){
                    StackPane build_road = new StackPane(new Label("Build Road"));
                    CustomMenuItem mi = new CustomMenuItem(build_road);
                    mi.setOnAction((event1) -> {
                        TurnManager.getCurrentPlayer().buildRoad(e);
                        refreshDisplay();
                    });
                    if (!TurnManager.getCurrentPlayer().canBuildRoad()){
                        mi.setDisable(true);
                    }
                    menu.getItems().add(mi);
                    Tooltip t = new Tooltip("hi!");
                    t.setShowDelay(Duration.millis(200));
                    Tooltip.install(build_road, t);
                }
                MenuItem mi = new MenuItem("Edge Properties");
                mi.setOnAction((event1) -> {
                    System.out.println("hi");
                });
                menu.getItems().add(mi);
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
                if (!TurnManager.hasRolledDice()){
                    return;
                }
                ContextMenu menu = new ContextMenu();
                if (TurnManager.getCurrentPlayer().getBuildableVertices().contains(v)){
                    StackPane build_settlement = new StackPane(new Label("Build Settlement"));
                    CustomMenuItem mi = new CustomMenuItem(build_settlement);
                    mi.setOnAction((event1) -> {
                        TurnManager.getCurrentPlayer().buildSettlement(v);
                        refreshDisplay();
                    });
                    if (!TurnManager.getCurrentPlayer().canBuildSettlement()){
                        mi.setDisable(true);
                    }
                    menu.getItems().add(mi);
                    Tooltip t = new Tooltip("hi!");
                    t.setShowDelay(Duration.millis(200));
                    Tooltip.install(build_settlement, t);
                }
                MenuItem mi = new MenuItem("Vertex Properties");
                mi.setOnAction((event1) -> {
                    System.out.println("hi");
                });
                menu.getItems().add(mi);
                menu.show(sp, Side.BOTTOM, 0, 0);
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

                StackPane sp = new StackPane();
                if (s.isSettlement()){
                    sp.getChildren().add(new Circle(10, p.getColor()));
                } else {
                    sp.setBackground(Background.fill(p.getColor()));
                }

                if (p == TurnManager.getCurrentPlayer()){
                    sp.setOnMouseClicked((event) -> {
                        ContextMenu menu = new ContextMenu();
                        if (s.isSettlement()){
                            MenuItem mi = new MenuItem("Settlement Properties");
                            menu.getItems().add(mi);
                        } else {
                            MenuItem mi = new MenuItem("City Properties");
                            menu.getItems().add(mi);
                        }
                        if (TurnManager.hasRolledDice()){
                            StackPane build_city = new StackPane(new Label("Build City"));
                            CustomMenuItem mi = new CustomMenuItem(build_city);
                            mi.setOnAction((event1) -> {
                                TurnManager.getCurrentPlayer().buildCity(s);
                                refreshDisplay();
                            });
                            if (!TurnManager.getCurrentPlayer().canBuildCity()){
                                mi.setDisable(true);
                            }
                            menu.getItems().add(mi);
                            Tooltip t = new Tooltip("hi!");
                            t.setShowDelay(Duration.millis(200));
                            Tooltip.install(build_city, t);
                        }
                        menu.show(sp, Side.BOTTOM, 0, 0);
                    });
                }
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

    public void trade(){
        PopUp.TRADE.loadTrade();
    }

    public void bankTrade(ActionEvent actionEvent) {
        PopUp.TRADEBANK.loadTradeBank();
    }

    public void back(ActionEvent actionEvent) {
        developmentPanel.setVisible(false);
        refreshDisplay();
    }

    public void goToDevelopment(ActionEvent actionEvent) {
        developmentPanel.setVisible(true);
        refreshDisplay();
    }

    public void buyDevelopment(ActionEvent actionEvent) {
        TurnManager.getCurrentPlayer().buyDevelopment();
        refreshDisplay();
    }
}
