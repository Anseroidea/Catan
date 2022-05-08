import javafx.scene.paint.Color;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Player
{
    private String name;
    private Map<Resource, Integer> resources;
    private List<DevelopmentCard> developmentCards;
    private List<Settlement> settlements;
    private List<Road> roads;
    private ArrayList<City> cities;
    private int id;
    private Color color;
    private HashSet<String> curRoads;
    private int longestRoad;
    private BufferedImage pawn;
    private int knights;
    static HashMap<Color, HashMap<String, BufferedImage>> p=new HashMap<Color, HashMap<String, BufferedImage>>();

    public Player(String n, int ID, Color c){
        resources = new HashMap<Resource, Integer>();
        developmentCards =new ArrayList<DevelopmentCard>();
        settlements = new ArrayList<Settlement>();
        roads = new ArrayList<Road>();
        cities = new ArrayList<City>();
        curRoads=new HashSet<String>();
        longestRoad=0;
        name=n;
        id=ID;
        color=c;

        String[] t={"Pawn", "Settlement", "Road", "City"};
        ArrayList<BufferedImage> v=new ArrayList<BufferedImage>();
        /*
        Scanner sc=new Scanner("blueP.png settB.png roadB.png cityB.png redP.png settR.png roadR.png cityR.png whiteP.png settW.png roadW.png cityW.png yellowP.png settY.png roadY.png cityY.png");
        for(int a=0;a<4;a++){
            for(int b=0;b<4;b++){
                try {
                    v.add(ImageIO.read(new File(sc.next())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


         */

        BufferedImage bi = null;
        try {
            bi = ImageIO.read(Player.class.getResourceAsStream("images/player/pawnFill.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), 2);
        BufferedImage lines = null;
        try {
            lines = ImageIO.read(Player.class.getResourceAsStream("images/player/pawnOutline.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D graphics2D = result.createGraphics();
        graphics2D.drawImage(bi, 0, 0, null);
        graphics2D.setComposite(AlphaComposite.SrcAtop);
        graphics2D.setColor(new java.awt.Color((float) c.getRed(), (float) c.getGreen(), (float) c.getBlue()));
        graphics2D.fillRect(0,0, bi.getWidth(), bi.getHeight());
        graphics2D.drawImage(lines, 0, 0, null);
        graphics2D.dispose();
        this.pawn = result;

        for (Resource r : Resource.getResourceList()){
            resources.put(r, 0);
        }

    }

    public List<Vertex> getBuildableVertices()
    {
        List<Vertex> buildable = new ArrayList<>();
        for(int i = 0; i < roads.size(); i++)
            for(Vertex k : roads.get(i).getEdge().getAdjacentVertices().values())
                if(k.getSettlement() == null)
                    buildable.add(k);
        for (Settlement s : settlements){
            buildable.removeAll(s.getVertex().getAdjacentVertices());
        }
        return buildable;
    }

    public List<Edge> getBuildableEdges(){
        List<Edge> buildable = new ArrayList<>();
        for (Road r : roads){
            buildable.addAll(r.getEdge().getAdjacentEdges().values());
        }
        buildable.removeAll(roads.stream().map(Road::getEdge).toList());
        return buildable;
    }

    /*
    public List<Edge> getBuildableEdges()
    {
        List<Edge> buildable = new ArrayList<>();
        for(int i = 0; i < roads.size(); i++)
            for(Vertex k : roads.get(i).getEdge().getAdjacentTiles())
                if(k.getSettlement() == null || k.getSettlement().getPlayer().getId() == id)
                    for(Edge e : k.getAdjacentEdges().values())
                        if(e.getRoad() == null)
                            buildable.add(e);

        return buildable;
    }

     */

    public Color getColor()
    {
        return color;
    }

    public int getId()
    {
        return id;
    }

    public List<Settlement> getSettlements()
    {
        return settlements;
    }

    public String getName()
    {
        return name;
    }

    public Map<Resource, Integer> getResources()
    {
        return resources;
    }

    public int getPrivateVictoryPoints()
    {
        return getPublicVictoryPoints() + (int) developmentCards.stream().filter(dc -> dc.getId() <= 4).count();
    }

    public int getPublicVictoryPoints()
    {
        return settlements.stream().map(s -> {
            if (s.isSettlement()){
                return 1;
            } else {
                return 2;
            }
        }).reduce(0, Integer::sum) + (BoardGame.getLongestRoad() != null && BoardGame.getLongestRoad().equals(this) ? 2 : 0) + ((BoardGame.getLargestArmy() != null && BoardGame.getLargestArmy().equals(this)) ? 2 : 0);
    }

    public int getLongestRoad() {
        return longestRoad;
    }

    public void updateLongestRoad()
    {
        curRoads = getRoadSet();
        longestRoad = 0;
        for(int i = 0; i < roads.size(); i++)
            for(Vertex k : roads.get(i).getEdge().getAdjacentVertices().values())
                findLongestRoad(k, 0);
    }

    private void findLongestRoad(Vertex cur, int len)
    {
        if(len > longestRoad)
            longestRoad = len;
        if(cur.getSettlement() != null && !settlements.contains(cur.getSettlement()))
            return;

        for(Iterator<Map.Entry<Integer, Edge>> i = cur.getAdjacentEdges().entrySet().iterator(); i.hasNext();)
        {
            Map.Entry<Integer, Edge> e = i.next();
            Road r = e.getValue().getRoad();
            if(r != null && curRoads.contains(r.toString()))
            {
                curRoads.remove(r.toString());
                findLongestRoad(e.getValue().getAdjacentVertices().get(e.getKey()), len + 1);
                curRoads.add(r.toString());
            }
        }
    }

    public List<DevelopmentCard> getDevelopmentCards(){
        return developmentCards;
    }

    public HashSet<String> getRoadSet()
    {
        HashSet<String> h = new HashSet<>();
        for(Road r : roads)
            h.add(r.toString());

        return h;
    }

    public List<Road> getRoads(){
        return roads;
    }

    public void changeCards(Resource r, int num)
    {
        Integer cur = resources.get(r);
        if(cur != null && cur + num < 0)
            System.out.println("You don't have enough cards to take away from!");
        else
        {
            if(cur == null)
                cur = 0;
            BoardGame.getResourceDeck().changeCount(r, num);
            resources.put(r, cur + num);
        }
    }

    public BufferedImage getGraphics(){
        return p.get(this.color).get(0);
    }

    public boolean canBuildRoad(){
        return resources.get(Resource.BRICK) >= 1 && resources.get(Resource.LUMBER) >= 1;
    }

    public void buildRoad(Edge e){
        e.buildRoad(this);
        changeCards(Resource.LUMBER, -1);
        changeCards(Resource.BRICK, -1);
    }

    public void buildSettlement(Vertex v) {
        v.addSettlement(this);
        changeCards(Resource.BRICK, -1);
        changeCards(Resource.LUMBER, -1);
        changeCards(Resource.WHEAT, -1);
        changeCards(Resource.WOOL, -1);
    }

    public void buildCity(Settlement s){
        s.buildCity();
        changeCards(Resource.WHEAT, -2);
        changeCards(Resource.ORE, -3);
    }

    public void buyDevelopment() {
        developmentCards.add(BoardGame.getDevelopmentCardDeck().drawCard());
        changeCards(Resource.WOOL, -1);
        changeCards(Resource.WHEAT, -1);
        changeCards(Resource.ORE, -1);
    }

    public void useDevelopment(DevelopmentCard dc){
        if (dc.getId() == 6){

        } else if (dc.getId() == 7){

        } else if (dc.getId() == 8){

        } else {

        }
        developmentCards.remove(dc);
        TurnManager.addAction(TurnManager.getCurrentPlayer().getName() + " placed on a " + dc.getType() + " card.");
    }

    public boolean canBuildSettlement() {
        return resources.get(Resource.LUMBER) >= 1 && resources.get(Resource.BRICK) >= 1 && resources.get(Resource.WHEAT) >=1 && resources.get(Resource.WOOL) >=1;
    }

    public boolean canBuildCity(){
        return resources.get(Resource.WHEAT) >= 2 && resources.get(Resource.ORE) >= 3;
    }

    public boolean canBuyDevelopment(){
        return resources.get(Resource.WOOL) >= 1 && resources.get(Resource.ORE) >= 1 && resources.get(Resource.WHEAT) >= 1;
    }

    public BufferedImage getPawn() {
        return pawn;
    }

    public int getKnights() {
        return knights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }
}
