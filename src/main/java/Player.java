import javafx.scene.paint.Color;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

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
    static HashMap<Color, HashMap<String, Image>> p=new HashMap<Color, HashMap<String, Image>>();

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
        ArrayList<Image> v=new ArrayList<Image>();
        Scanner sc=new Scanner("blueP.png settB roadB");
        for(int a=0;a<4;a++){
            for(int b=0;b<4;b++){
                v.add();
            }
        }
        for(int a=0;a<4;a++){
            HashMap<String, Image> w=new HashMap<String, Image>();
            for(int b=0;b<4;b++){
                w.put(t[b], );
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

    /*public List<Edge> getBuildableEdges()
    {
        List<Edge> buildable = new ArrayList<>();
        for(int i = 0; i < roads.size(); i++)
            for(Vertex k : roads.get(i).getEdge().getAdjacentVertices())
                if(k.getSettlement() == null || k.getSettlement().getPlayer().getId() == id)
                    for(Edge e : k.getAdjacentEdges().values())
                        if(e.getRoad() == null)
                            buildable.add(e);

        return buildable;
    }*/

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
        curRoads = getRoads();
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

    private HashSet<String> getRoads()
    {
        HashSet<String> h = new HashSet<>();
        for(Road r : roads)
            h.add(r.toString());

        return h;
    }
}
