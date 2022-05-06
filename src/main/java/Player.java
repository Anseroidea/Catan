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
    private HashMap<String, Integer> victoryPoints;
    private Color color;
    private HashSet<String> curRoads;
    private int longestRoad;
    static HashMap<Color, HashMap<String, BufferedImage>> p=new HashMap<Color, HashMap<String, BufferedImage>>();

    public Player(String n, int ID, Color c){
        resources = new HashMap<Resource, Integer>();
        developmentCards =new ArrayList<DevelopmentCard>();
        settlements = new ArrayList<Settlement>();
        roads = new ArrayList<Road>();
        cities = new ArrayList<City>();
        victoryPoints=new HashMap<String, Integer>();
        curRoads=new HashSet<String>();
        longestRoad=0;
        name=n;
        id=ID;
        color=c;

        String[] t={"Pawn", "Settlement", "Road", "City"};
        ArrayList<BufferedImage> v=new ArrayList<BufferedImage>();
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
        Iterator it = v.iterator();
        for(int a=0;a<4;a++){
            HashMap<String, BufferedImage> w=new HashMap<String, BufferedImage>();
            for(int b=0;b<4;b++){
                w.put(t[b], (BufferedImage) it.next());
            }
            if(a==0)
                p.put(Color.BLUE, w);
            else if(a==1)
                p.put(Color.RED, w);
            else if(a==2)
                p.put(Color.WHITE, w);
            else if(a==3)
                p.put(Color.YELLOW, w);
        }

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
        return buildable;
    }

    public List<Edge> getBuildableEdges(){
        List<Edge> buildable = new ArrayList<>();
        for (Vertex v : settlements.stream().map(Settlement::getVertex).collect(Collectors.toList())){
            for (Edge e : v.getAdjacentEdges().values()){
                if (!buildable.contains(e) && !(roads.stream().map(Road::getEdge).collect(Collectors.toList())).contains(e)){
                    buildable.add(e);
                }
            }
        }
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
        int sum = 0;
        for(Iterator<Integer> i = victoryPoints.values().iterator(); i.hasNext();)
        {
            sum += i.next();
        }
        return sum;
    }

    public int getPublicVictoryPoints()
    {
        Integer i = victoryPoints.get("VPCard");
        return getPrivateVictoryPoints() - (i == null ? 0 : i);
    }

    public void use(DevelopmentCard dc)
    {
        switch(dc.getId())
        {
            case 0:
                victoryPoints.put("VPCard", victoryPoints.getOrDefault("VPCard", 0) + 1);
                break;
            case 1:
            {
                //PopUp.load(ROB);
            }
            case 2:
            {
                //2 roads
                //PopUp.load(ROAD);
                //PopUp.load(ROAD);
            }
            case 3:
            {
                //PopUp.load(
            }
            case 4:
            {

            }
        }
    }

    public void getLongestRoad()
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
            resources.put(r, cur + num);
        }
    }

    public BufferedImage getGraphics(){
        return p.get(this.color).get(0);
    }
}
