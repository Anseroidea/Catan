import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PlayerGraphics {

    public AnchorPane boardPane;

    public void refreshDisplay() {
        refreshBoardInteractives();

    }

    private void refreshBoardInteractives() {
        HexGridPane hexGridPane = BoardGame.getHexGridPane();
        double radius = hexGridPane.getRadius();
        double vertRadius = radius/10.;
        int maxR = 2;
        boolean showWater = false;
        for (Vertex v : hexGridPane.getVertexManager().getAllVertices()){
            double rowCoord = maxR * (radius + radius/2.) + radius + v.getR() * radius/2. + v.getR() / (double) Math.abs(v.getR()) * (v.getC() % 2 + (Math.abs(v.getR()) - 1) * 2) * radius/2. - vertRadius;
            double colCoord = (showWater ? 1 : 0) * radius * Math.sqrt(3) + (Math.abs(v.getR()) - 1 + v.getC()) * radius * Math.sqrt(3) / 2. - vertRadius;
            Circle circle = new Circle(vertRadius, Color.TRANSPARENT);
            StackPane sp = new StackPane(circle);
            circle.setOnMouseClicked((event) -> {
                v.getAdjacentTiles().forEach((key, value) -> System.out.println(Tile.directions[key] + ": " + value.getWeight()));
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
    }
}
