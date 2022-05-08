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
    public ImageView player1Icon;
    public Label player1Label;
    public Label cardsLabel1;
    public Label knightsLabel1;
    public Label developmentCardsLabel1;
    public Label roadsLabel1;
    public StackPane settlementPicture1;
    public Label settlementLabel1;
    public StackPane cityPicture1;
    public Label cityLabel1;
    public ImageView player2Icon;
    public Label player2Label;
    public Label cardsLabel2;
    public Label knightsLabel2;
    public Label developmentCardsLabel2;
    public Label roadsLabel2;
    public StackPane settlementPicture2;
    public Label settlementLabel2;
    public StackPane cityPicture2;
    public Label cityLabel2;
    public Label player3Label;
    public Label cardsLabel3;
    public Label knightsLabel3;
    public Label developmentCardsLabel3;
    public Label roadsLabel3;
    public StackPane settlementPicture3;
    public Label settlementLabel3;
    public StackPane cityPicture3;
    public Label cityLabel3;
    public StackPane player3Box;
    public ImageView player3Icon;
    public ImageView playerPawn;
    public Label playerNameLabel;
    public Label cityLabel;
    public StackPane cityPicture;
    public Label settlementLabel;
    public StackPane settlementPicture;
    public Label roadsLabel;
    public Label developmentCardsLabel;
    public Label knightsLabel;
    public Label cardsLabel;
    public Label vpLabel;

    @FXML
    public void initialize(){
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
        Player currentPlayer = TurnManager.getCurrentPlayer();
        Player[] nonPlayingPlayers = new Player[3];
        int ind = 0;
        for (Player p : TurnManager.getPlayerList()){
            if (p.getName().equals(currentPlayer.getName())){
                continue;
            }
            nonPlayingPlayers[ind++] = p;
        }
        Player player1 = nonPlayingPlayers[0];
        Player player2 = nonPlayingPlayers[1];
        Player player3 = nonPlayingPlayers[2];

        player1Icon.setImage(SwingFXUtils.toFXImage(player1.getPawn(), null));
        player1Label.setText(player1.getName() + " (VP: " + player1.getPublicVictoryPoints() + ")");
        cardsLabel1.setText(player1.getResources().values().stream().reduce(0, Integer::sum) + "");
        knightsLabel1.setText(player1.getKnights() + "");
        developmentCardsLabel1.setText(player1.getDevelopmentCards().size() + "");
        roadsLabel1.setText(player1.getLongestRoad() + "");
        settlementLabel1.setText(player1.getSettlements().size() + "");
        Circle e = new Circle(25, player1.getColor());
        e.setStrokeWidth(5);
        e.setStroke(Color.BLACK);
        settlementPicture1.getChildren().add(e);
        cityLabel1.setText((int) player1.getSettlements().stream().filter(Settlement::isSettlement).count() + "");
        Rectangle e1 = new Rectangle(50, 50, player1.getColor());
        e1.setStrokeWidth(5);
        e1.setStroke(Color.BLACK);
        cityPicture1.getChildren().add(e1);
        player2Icon.setImage(SwingFXUtils.toFXImage(player2.getPawn(), null));
        player2Label.setText(player2.getName() + " (VP: " + player2.getPublicVictoryPoints() + ")");
        cardsLabel2.setText(player2.getResources().values().stream().reduce(0, Integer::sum) + "");
        knightsLabel2.setText(player2.getKnights() + "");
        developmentCardsLabel2.setText(player2.getDevelopmentCards().size() + "");
        roadsLabel2.setText(player2.getLongestRoad() + "");
        settlementLabel2.setText(player2.getSettlements().size() + "");
        Circle e2 = new Circle(25, player2.getColor());
        e2.setStrokeWidth(5);
        e2.setStroke(Color.BLACK);
        settlementPicture2.getChildren().add(e2);
        cityLabel2.setText((int) player2.getSettlements().stream().filter(Settlement::isSettlement).count() + "");
        Rectangle e3 = new Rectangle(50, 50, player2.getColor());
        e3.setStrokeWidth(5);
        e3.setStroke(Color.BLACK);
        cityPicture2.getChildren().add(e3);
        if (player3 != null){
            player3Icon.setImage(SwingFXUtils.toFXImage(player3.getPawn(), null));
            player3Label.setText(player3.getName() + " (VP: " + player3.getPublicVictoryPoints() + ")");
            cardsLabel3.setText(player3.getResources().values().stream().reduce(0, Integer::sum) + "");
            knightsLabel3.setText(player3.getKnights() + "");
            developmentCardsLabel3.setText(player3.getDevelopmentCards().size() + "");
            roadsLabel3.setText(player3.getLongestRoad() + "");
            settlementLabel3.setText(player3.getSettlements().size() + "");
            Circle e4 = new Circle(25, player3.getColor());
            e4.setStrokeWidth(5);
            e4.setStroke(Color.BLACK);
            settlementPicture3.getChildren().add(e4);
            cityLabel3.setText((int) player3.getSettlements().stream().filter(Settlement::isSettlement).count() + "");
            Rectangle e5 = new Rectangle(50, 50, player3.getColor());
            e5.setStrokeWidth(5);
            e5.setStroke(Color.BLACK);
            cityPicture3.getChildren().add(e5);
        } else {
            player3Box.setVisible(false);
        }
        playerPawn.setImage(SwingFXUtils.toFXImage(currentPlayer.getPawn(), null));
        playerNameLabel.setText(TurnManager.getCurrentPlayer().getName());
        cardsLabel.setText(currentPlayer.getResources().values().stream().reduce(0, Integer::sum) + "");
        knightsLabel.setText(currentPlayer.getKnights() + "");
        developmentCardsLabel.setText(currentPlayer.getDevelopmentCards().size() + "");
        roadsLabel.setText(currentPlayer.getLongestRoad() + "");
        settlementLabel.setText(currentPlayer.getSettlements().size() + "");
        Circle e4 = new Circle(25, currentPlayer.getColor());
        e4.setStrokeWidth(5);
        e4.setStroke(Color.BLACK);
        settlementPicture.getChildren().add(e4);
        cityLabel.setText((int) currentPlayer.getSettlements().stream().filter(Settlement::isSettlement).count() + "");
        Rectangle e5 = new Rectangle(60, 60, currentPlayer.getColor());
        e5.setStroke(Color.BLACK);
        e5.setStrokeWidth(5);
        cityPicture.getChildren().add(e5);
        vpLabel.setText("VP: " + currentPlayer.getPrivateVictoryPoints());
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
                        BoardGame.updateLongestRoad();
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


                sp.setOnMouseClicked((event) -> {
                    ContextMenu menu = new ContextMenu();
                    if (s.isSettlement()){
                        MenuItem mi = new MenuItem("Settlement Properties");
                        menu.getItems().add(mi);
                    } else {
                        MenuItem mi = new MenuItem("City Properties");
                        menu.getItems().add(mi);
                    }
                    if (p == TurnManager.getCurrentPlayer()){
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
                    }
                    menu.show(sp, Side.BOTTOM, 0, 0);
                });
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
            vpLabel.setText("Hidden");
        } else {
            resourceButton.setText("Hide Resources");
            resourcesBox.setVisible(true);
            vpLabel.setText("VP: " + TurnManager.getCurrentPlayer().getPrivateVictoryPoints());
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
        PopUp.MONOPOLY.loadMonopoly();
        //PopUp.TRADEBANK.loadTradeBank();
        refreshDisplay();
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
