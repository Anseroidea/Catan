import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class TradeOthers
{
    @FXML
    private AnchorPane scene;

    @FXML
    private Button oneA, oneD, twoA, twoD, threeA, threeD, one, two, three;

    @FXML
    private HBox request, offer;

    @FXML
    private VBox consider;

    private Player[] partners;
    private Button[] acceptedPartners;
    private HashMap<Resource, Integer> get, give;
    private Trade t;

    public static BufferedImage check, cross;

    public void temp(Trade t)
    {
        this.t = t;
    }

    public TradeOthers()
    {
        try {
            check = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/trade/si-removebg-preview.png")));
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("Reading the check symbol didn't work");
        }

        try {
            cross = ImageIO.read(Objects.requireNonNull(Rules.class.getResourceAsStream("/images/trade/Nay-removebg-preview.png")));
        } catch (Exception e) {
            System.out.println("Reading the nay symbol didn't work");
        }
    }

    public void setGet(Map<Resource, Integer> g)
    {
        get = (HashMap)g;
    }

    public void setGive(Map<Resource, Integer> g)
    {
        give = (HashMap)g;
    }



    //Graphics, enable / disable buttons
    public void setBasics()
    {
        System.out.println("HELOOOOOOOOOOOOOOOO");

        /*Player p = new Player("eh", 3, Color.RED);
        p.changeCards(Resource.BRICK, 4);
        p.changeCards(Resource.WHEAT, 4);
        p.changeCards(Resource.ORE, 4);
        p.changeCards(Resource.LUMBER, 4);
        p.changeCards(Resource.WOOL, 4);

        Player p2 = new Player("eh", 3, Color.RED);
        p2.changeCards(Resource.BRICK, 5);
        p2.changeCards(Resource.WHEAT, 3);
        p2.changeCards(Resource.ORE, 4);
        p2.changeCards(Resource.LUMBER, 4);
        p2.changeCards(Resource.WOOL, 4);

        Player p3 = new Player("eh", 3, Color.RED);
        p3.changeCards(Resource.BRICK, 5);
        p3.changeCards(Resource.WHEAT, 4);
        p3.changeCards(Resource.ORE, 4);
        p3.changeCards(Resource.LUMBER, 4);
        p3.changeCards(Resource.WOOL, 4);*/

        List<Player> tradePartners = TurnManager.getPlayerList();
        /*List<Player> tradePartners = new ArrayList<>();
        tradePartners.add(p);
        tradePartners.add(p2);
        tradePartners.add(p3);*/

        if(tradePartners == null)
        {
            System.out.println("For some reason the list of players is null");
            return;
        }

        partners = new Player[tradePartners.size() - 1];
        acceptedPartners = new Button[partners.length];
        int count = 0;

        EventHandler<MouseEvent> trade = new EventHandler<>(){
            @Override
            public void handle(MouseEvent event)
            {
                Button pressed = ((Button)event.getSource());
                Player partner = partners[pressed.getId().charAt(1) - '0'];
                Player p0 = TurnManager.getCurrentPlayer();
                /*Player p0 = new Player("eh", 3, Color.RED);
                p0.changeCards(Resource.BRICK, 4);
                p0.changeCards(Resource.WHEAT, 4);
                p0.changeCards(Resource.ORE, 4);
                p0.changeCards(Resource.LUMBER, 4);
                p0.changeCards(Resource.WOOL, 4);*/

                for(Iterator<Map.Entry<Resource, Integer>> i = give.entrySet().iterator(); i.hasNext();)
                {
                    Map.Entry<Resource, Integer> e = i.next();
                    partner.changeCards(e.getKey(), e.getValue());
                    p0.changeCards(e.getKey(), -e.getValue());
                }

                for(Iterator<Map.Entry<Resource, Integer>> i = get.entrySet().iterator(); i.hasNext();)
                {
                    Map.Entry<Resource, Integer> e = i.next();
                    p0.changeCards(e.getKey(), e.getValue());
                    partner.changeCards(e.getKey(), -e.getValue());
                }

                Stage thisStage = (Stage) pressed.getScene().getWindow();
                thisStage.getScene().setRoot(new AnchorPane());
                thisStage.close();

                t.back(event);

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                StringBuilder s = new StringBuilder();
                s.append(TurnManager.getCurrentPlayer().getName()).append(" traded ");
                StringJoiner giveString = new StringJoiner(", ");
                StringJoiner getString = new StringJoiner(", ");
                for (Resource r : Resource.getResourceList()){
                    if (give.get(r) > 0){
                        giveString.add(give.get(r) + " " + r.getResource());
                    }
                    if (get.get(r) > 0) {
                        getString.add(get.get(r) + " " + r.getResource());
                    }
                }
                s.append(giveString).append(" to ").append(partner.getName()).append(" for ").append(getString);
                TurnManager.addAction(s.toString());
                a.setContentText("Trade successful!");
                GraphicsManager.refreshDisplay();
                a.show();
            }
        };

        EventHandler<ActionEvent> yes = new EventHandler<>(){
            public void handle(ActionEvent event)
            {
                System.out.println("Accept");
                Button source = (Button)event.getSource();

                System.out.println("HUGE BUTTON");
                int id = source.getId().charAt(1) - '0';
                if(acceptedPartners[id] == null)
                {
                    Button accepted = new Button();
                    accepted.setOnMouseClicked(trade);
                    accepted.setId("y" + id);
                    accepted.setPrefWidth(100);
                    accepted.setPrefHeight(100);
                    accepted.setVisible(true);


                    double x = consider.getLayoutX() - 30;
                    double y = consider.getLayoutY() + consider.getHeight() / (partners.length * 8) * 5 - accepted.getPrefHeight() / 2
                            + (id * consider.getHeight() / partners.length);

                    System.out.println("ALIGN " + partners.length + " ID: " + id + " Y coor: " + y + " " + consider.getLayoutY());


                    AnchorPane.setRightAnchor(accepted, scene.getWidth() - x); // distance 0 from right side of
                    AnchorPane.setTopAnchor(accepted, y);
                    System.out.println(scene.getWidth() - x + " " + y);
                    scene.getChildren().add(accepted);

                    acceptedPartners[id] = accepted;
                }

                ImageView iv = new ImageView(SwingFXUtils.toFXImage(check, null));
                iv.setFitHeight(acceptedPartners[id].getPrefHeight());
                iv.setFitWidth(acceptedPartners[id].getPrefWidth());
                acceptedPartners[id].setGraphic(iv);
                acceptedPartners[id].setDisable(false);

                System.out.println(id);
                System.out.println("DONE");
            }
        };

        EventHandler<ActionEvent> no = new EventHandler<>(){
            public void handle(ActionEvent event)
            {
                System.out.println("Decline");
                Button source = (Button)event.getSource();


                int id = source.getId().charAt(1) - '0';
                System.out.println(id);
                if(acceptedPartners[id] != null)
                {
                    acceptedPartners[id].setDisable(true);

                    ImageView iv = new ImageView(SwingFXUtils.toFXImage(cross, null));
                    iv.setFitHeight(acceptedPartners[id].getPrefHeight());
                    iv.setFitWidth(acceptedPartners[id].getPrefWidth());
                    acceptedPartners[id].setGraphic(iv);
                }
            }
        };

        for(int i = 0; i < tradePartners.size(); i++)
        {
            if(tradePartners.get(i).equals(TurnManager.getCurrentPlayer()))
                continue;

            Player temp = tradePartners.get(i);
            partners[count] = temp;

            VBox v = new VBox();
            v.setPrefHeight(consider.getHeight() / partners.length);
            v.setPrefWidth(consider.getWidth());
            v.setAlignment(Pos.TOP_CENTER);
            HBox h = new HBox();
            h.setPrefHeight(v.getPrefHeight() / 4 * 3);
            h.setPrefWidth(consider.getWidth());
            h.setAlignment(Pos.CENTER);

            Button accept = new Button();
            accept.setId("a" + count);
            accept.setPrefHeight(h.getPrefHeight());
            accept.setPrefWidth(h.getPrefWidth() / 2);
            accept.setOnAction(yes);
            ImageView iv = new ImageView(SwingFXUtils.toFXImage(check, null));
            iv.setFitHeight(accept.getPrefHeight());
            iv.setFitWidth(accept.getPrefWidth());
            accept.setGraphic(iv);

            Button decline = new Button();
            decline.setId("d" + count);
            decline.setPrefHeight(h.getPrefHeight());
            decline.setPrefWidth(h.getPrefWidth() / 2);
            decline.setOnAction(no);
            ImageView iv2 = new ImageView(SwingFXUtils.toFXImage(cross, null));
            iv2.setFitHeight(decline.getPrefHeight());
            iv2.setFitWidth(decline.getPrefWidth());
            decline.setGraphic(iv2);

            Label l = new Label();
            l.setAlignment(Pos.CENTER);
            l.setText(temp.getName());
            l.setPrefHeight(v.getPrefHeight() / 4);
            l.setPrefHeight(v.getPrefWidth());

            h.getChildren().addAll(accept, decline);
            v.getChildren().addAll(l, h);
            consider.getChildren().add(v);

            System.out.println("Y spot " + v.getLayoutY() + " Height: " + v.getPrefHeight());
            count++;
        }

        for(Iterator<Map.Entry<Resource, Integer>> i = get.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry<Resource, Integer> e = i.next();
            VBox v = new VBox();
            v.setPrefHeight(request.getHeight());
            v.setPrefWidth(request.getWidth() / 5);
            v.setAlignment(Pos.CENTER); //Alignment of its children

            ImageView im = new ImageView(SwingFXUtils.toFXImage(e.getKey().getGraphic(), null));
            im.setFitHeight(v.getPrefHeight() - 20); //Different from getHeight()
            im.setFitWidth(v.getPrefWidth());
            v.getChildren().add(im);

            Label amount = new Label();
            amount.setText("" + e.getValue());
            amount.setAlignment(Pos.CENTER);
            v.getChildren().add(amount);

            v.setVisible(true);
            request.getChildren().add(v);
            System.out.println(v.getPrefHeight());
            System.out.println(v.getPrefWidth());
            System.out.println("WEll, " + request.getHeight() + " " + request.getWidth());

            for(int k = 0; k < partners.length; k++)
            {
                System.out.println(e.getKey());
                System.out.println(partners[k].getResources().get(e.getKey()));
                System.out.println(e.getValue());
                if(partners[k].getResources().get(e.getKey()) < e.getValue())
                    ((Button)((HBox)((VBox)consider.getChildren().get(k)).getChildren().get(1)).getChildren().get(0)).setDisable(true);
            }
        }

        for(Iterator<Map.Entry<Resource, Integer>> i = give.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry<Resource, Integer> e = i.next();
            VBox v = new VBox();
            v.setPrefHeight(offer.getHeight());
            v.setPrefWidth(offer.getWidth() / 5);
            v.setAlignment(Pos.CENTER);

            ImageView im = new ImageView(SwingFXUtils.toFXImage(e.getKey().getGraphic(), null));
            im.setFitHeight(v.getPrefHeight() - 20);
            im.setFitWidth(v.getPrefWidth());
            v.getChildren().add(im);

            Label amount = new Label();
            amount.setText("" + e.getValue());
            amount.setAlignment(Pos.CENTER);
            v.getChildren().add(amount);

            v.setVisible(true);
            offer.getChildren().add(v);
        }



        System.out.println("BYEEEEEEEEEEEEEEEE");
    }

    public void back(MouseEvent mouseEvent) {
        Stage thisStage = (Stage) scene.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }
}
