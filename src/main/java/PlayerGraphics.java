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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public Button rob;
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
    public Label heading;

    private Tile robberTile;
    private boolean isRobbing;
    private static ImageView robberGraphic;
    @FXML
    public void initialize()
    {
        BufferedImage im = null;
        try {
            im = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/player/robber.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ImageView iv = new ImageView(SwingFXUtils.toFXImage(im, null));
        double radius = BoardGame.getHexGridPane().getRadius();
        iv.setFitHeight(radius / 3 * 2);
        iv.setFitWidth(radius / 3);
        robberGraphic = iv;
        boardPane.getChildren().add(robberGraphic);
        robberGraphic.setVisible(false);
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
            final int id1 = dc.getId();
            if (dc.getId() > 4){
                im.setOnMouseClicked((event) -> {
                    if (id1 == 5){
                        TurnManager.getCurrentPlayer().addKnight();
                    } else if (id1 == 6){
                        PopUp.ROADBUILDING.loadRoadBuilding();
                    } else if (id1 == 7){
                        PopUp.YEAROFPLENTY.loadYearOfPlenty();
                    } else {
                        PopUp.MONOPOLY.loadMonopoly();
                    }
                });
            }
            h.getChildren().add(im);
            i++;
            if (i < TurnManager.getCurrentPlayer().getDevelopmentCards().size()){
                dc = TurnManager.getCurrentPlayer().getDevelopmentCards().get(i);
                im = new ImageView(SwingFXUtils.toFXImage(dc.getGraphic(), null));
                im.setFitWidth(150);
                im.setFitHeight(200);
                final int id = dc.getId();
                if (id > 4){
                    im.setOnMouseClicked((event) -> {
                        if (id == 5){
                            TurnManager.getCurrentPlayer().addKnight();
                        } else if (id == 6){
                            PopUp.ROADBUILDING.loadRoadBuilding();
                        } else if (id == 7){
                            PopUp.YEAROFPLENTY.loadYearOfPlenty();
                        } else {
                            PopUp.MONOPOLY.loadMonopoly();
                        }
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
        if (player1.getResources().values().stream().reduce(0, Integer::sum) > 7){
            cardsLabel1.setTextFill(Color.RED);
        } else {
            cardsLabel1.setTextFill(Color.BLACK);
        }
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
        if (player2.getResources().values().stream().reduce(0, Integer::sum) > 7){
            cardsLabel2.setTextFill(Color.RED);
        } else {
            cardsLabel2.setTextFill(Color.BLACK);
        }
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
            if (player3.getResources().values().stream().reduce(0, Integer::sum) > 7){
                cardsLabel3.setTextFill(Color.RED);
            } else {
                cardsLabel3.setTextFill(Color.BLACK);
            }
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
        if (currentPlayer.getResources().values().stream().reduce(0, Integer::sum) > 7){
            cardsLabel.setTextFill(Color.RED);
        } else {
            cardsLabel.setTextFill(Color.BLACK);
        }
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
        roadsLabel.setBackground(Background.EMPTY);
        roadsLabel1.setBackground(Background.EMPTY);
        roadsLabel2.setBackground(Background.EMPTY);
        roadsLabel3.setBackground(Background.EMPTY);
        knightsLabel.setBackground(Background.EMPTY);
        knightsLabel1.setBackground(Background.EMPTY);
        knightsLabel2.setBackground(Background.EMPTY);
        knightsLabel3.setBackground(Background.EMPTY);
        if (BoardGame.getLongestRoad() != null){
            if (player1.equals(BoardGame.getLongestRoad())){
                roadsLabel1.setBackground(Background.fill(Color.GOLD));
            } else if (player2.equals(BoardGame.getLongestRoad())) {
                roadsLabel2.setBackground(Background.fill(Color.GOLD));
            } else if (player3 != null && player3.equals(BoardGame.getLongestRoad())){
                roadsLabel3.setBackground(Background.fill(Color.GOLD));
            } else {
                roadsLabel.setBackground(Background.fill(Color.GOLD));
            }
        }
        if (BoardGame.getLargestArmy() != null){
            if (player1.equals(BoardGame.getLargestArmy())){
                knightsLabel1.setBackground(Background.fill(Color.GOLD));
            } else if (player2.equals(BoardGame.getLargestArmy())) {
                knightsLabel2.setBackground(Background.fill(Color.GOLD));
            } else if (player3 != null && player3.equals(BoardGame.getLargestArmy())){
                knightsLabel3.setBackground(Background.fill(Color.GOLD));
            } else {
                knightsLabel.setBackground(Background.fill(Color.GOLD));
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
                if (!TurnManager.hasRolledDice() || isRobbing){
                    return;
                }
                if (TurnManager.getCurrentPlayer().getBuildableEdges().contains(e)){
                    ContextMenu menu = new ContextMenu();
                    StackPane build_road = new StackPane(new Label("Build Road"));
                    CustomMenuItem mi = new CustomMenuItem(build_road);
                    mi.setOnAction((event1) -> {
                        TurnManager.getCurrentPlayer().buildRoad(e);
                        TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " built a road.");
                        BoardGame.updateLongestRoad();
                        BoardGame.checkWin();
                        refreshDisplay();
                    });
                    if (!TurnManager.getCurrentPlayer().canBuildRoad()){
                        mi.setDisable(true);
                    }
                    menu.getItems().add(mi);
                    Tooltip t = new Tooltip("hi!");
                    t.setShowDelay(Duration.millis(200));
                    Tooltip.install(build_road, t);
                    menu.show(l, Side.BOTTOM, 0, 0);
                }
                //System.out.println("v1, v2:" + e.getAdjacentTiles().values().stream().map(Tile::getWeight).collect(Collectors.toList()));
            });
            l.setOnMouseEntered((event) -> {
                if(!isRobbing)
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
                if (!TurnManager.hasRolledDice() || isRobbing){
                    return;
                }
                ContextMenu menu = new ContextMenu();
                if (TurnManager.getCurrentPlayer().getBuildableVertices().contains(v)){
                    StackPane build_settlement = new StackPane(new Label("Build Settlement"));
                    CustomMenuItem mi = new CustomMenuItem(build_settlement);
                    mi.setOnAction((event1) -> {
                        TurnManager.getCurrentPlayer().buildSettlement(v);
                        TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " built a settlement.");
                        BoardGame.checkWin();
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
                if(!isRobbing)
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
            for (Road r : p.getRoads()){
                List<Vertex> vertices = new ArrayList<>(r.getEdge().getAdjacentVertices().values());
                Vertex v1 = vertices.get(0);
                Vertex v2 = vertices.get(1);
                double x1 = getX(v1, radius);
                double x2 = getX(v2, radius);
                double y1 = getY(v1, radius);
                double y2 = getY(v2, radius);
                Line l = new Line(x1, y1, x2, y2);
                Line b = new Line(x1, y1, x2, y2);
                l.setStroke(p.getColor());
                l.setStrokeWidth(7);
                b.setStroke(Color.BLACK);
                b.setStrokeWidth(10);
                boardPane.getChildren().addAll(b,l);
            }
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
                sp.setOnMouseClicked((event) -> {
                    ContextMenu menu = new ContextMenu();
                    if (p == TurnManager.getCurrentPlayer()){
                        if (TurnManager.hasRolledDice()){
                            StackPane build_city = new StackPane(new Label("Build City"));
                            CustomMenuItem mi = new CustomMenuItem(build_city);
                            mi.setOnAction((event1) -> {
                                TurnManager.getCurrentPlayer().buildCity(s);
                                TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " upgraded a settlement to a city.");
                                BoardGame.checkWin();
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
        BoardGame.checkWin();
        refreshDisplay();
    }

    public void trade(){
        PopUp.TRADE.loadTrade();
        refreshDisplay();
    }

    public void bankTrade(ActionEvent actionEvent) {
        PopUp.TRADEBANK.loadTradeBank();
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
        TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " bought a development card.");
        BoardGame.checkWin();
        refreshDisplay();
    }

    private void disableButtons()
    {
        tradeButton.setDisable(true);
        bankTradeButton.setDisable(true);
        buyDevelopmentButton.setDisable(true);
        developmentCardsButton.setDisable(true);
        rollDiceButton.setDisable(true);
        nextRoundButton.setDisable(true);
    }

    public void robber(ActionEvent actionEvent)
    {
        isRobbing = true;
        disableButtons();
        heading.setText("Choose a tile to move the robber to");
        heading.setVisible(true);
        HexGridPane hexGridPane = BoardGame.getHexGridPane();
        double radius = hexGridPane.getRadius();
        Map<Integer, List<Tile>> map = hexGridPane.getMap();
        for (Integer r : map.keySet())
        {
            if(r == 3 || r == -3)
                continue;
            for (int c = 1; c < map.get(r).size() - 1; c++) {
                System.out.println(r + " " + c);
                Circle circle = new Circle(radius / 2, Color.BLACK);
                circle.setOpacity(0);

                int finalC = c;
                circle.setOnMouseClicked(event -> {
                    if(isRobbing) {
                        robberTile = hexGridPane.get(r, finalC);
                        boolean valid = false;
                        for(Vertex v : robberTile.getAdjacentVertices().values())
                            if(v.getSettlement() != null && !v.getSettlement().getPlayer().equals(TurnManager.getCurrentPlayer()))
                            {
                                valid = true;
                                break;
                            }

                        if(!valid)
                        {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setContentText("Cannot move robber to tile with no players or with only the current player!");
                            a.show();
                            return;
                        }
                        //refreshDisplay();

                        robberGraphic.setLayoutY(radius / 2 + (r + 2) * (3.0 / 2 * radius));
                        robberGraphic.setLayoutX(radius / 2 + finalC * (Math.sqrt(3) * radius) + Math.abs(r) * (Math.sqrt(3) / 2 * radius) - radius / 2 - Math.sqrt(3) / 2 * radius);
                        robberGraphic.setVisible(true);

                        heading.setText("Choose someone to rob");
                        double vertRadius = radius/10.;
                        int maxR = 2;
                        boolean showWater = false;
                        for(Vertex v : robberTile.getAdjacentVertices().values())
                            if(v.getSettlement() != null && !v.getSettlement().getPlayer().equals(TurnManager.getCurrentPlayer()))
                            {
                                //Circle cc = new Circle(200, Color.RED);
                                //boardPane.getChildren().add(cc);
                                double rowCoord = maxR * (radius + radius/2.) + radius + v.getR() * radius/2. + v.getR() / (double) Math.abs(v.getR()) * (v.getC() % 2 + (Math.abs(v.getR()) - 1) * 2) * radius/2. - vertRadius;
                                double colCoord = (showWater ? 1 : 0) * radius * Math.sqrt(3) + (Math.abs(v.getR()) - 1 + v.getC()) * radius * Math.sqrt(3) / 2. - vertRadius;

                                Circle victim = new Circle(vertRadius + 5, Color.TRANSPARENT);
                                //boardPane.getChildren().add(victim);
                                System.out.println(rowCoord + " " + colCoord);
                                StackPane sp = new StackPane(victim);
                                victim.setOnMouseClicked((badEvent) -> {

                                    PopUp.ROBBER.load();
                                    ((Robber)PopUp.ROBBER.getController()).setRobber(TurnManager.getCurrentPlayer());
                                    ((Robber)PopUp.ROBBER.getController()).setPlayer(v.getSettlement().getPlayer());

                                    heading.setVisible(false);
                                    refreshDisplay();
                                });
                                victim.setOnMouseEntered(badEvent -> {
                                    System.out.println("HERE");
                                    sp.setStyle("-fx-border-color: black");
                                });
                                victim.setOnMouseExited(badEvent -> {
                                    sp.setStyle("-fx-border-color: transparent");
                                });

                                sp.setLayoutY(rowCoord);
                                sp.setLayoutX(colCoord);
                                sp.setVisible(true);
                                boardPane.getChildren().add(sp);
                            }
                        isRobbing = false;
                    }
                });
                circle.setOnMouseEntered(event -> {
                    if(isRobbing)
                        circle.setOpacity(1.0);
                });
                circle.setOnMouseExited(event -> {
                    circle.setOpacity(0);
                });

                StackPane sp = new StackPane(circle);
                sp.setLayoutY(radius / 2 + (r + 2) * (3.0 / 2 * radius));
                sp.setLayoutX(c * (Math.sqrt(3) * radius) + Math.abs(r) * (Math.sqrt(3) / 2 * radius) - radius / 2 - Math.sqrt(3) / 2 * radius);
                boardPane.getChildren().add(sp);
            }
        }
    }
}
