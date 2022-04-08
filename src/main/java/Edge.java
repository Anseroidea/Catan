import java.util.*;

public class Edge {
    private ArrayList<Tile> adjacentT;
    private ArrayList<Vertex> adjacentV;
    private Road r;
    private Vertex v1, v2;
    public Edge(Vertex c, Vertex d){
        adjacentT=new ArrayList<Tile>();
        adjacentV=new ArrayList<Vertex>();
        adjacentV.add(c);
        adjacentV.add(d);
        Tile[] a= new Tile[6];
        Tile[] k= new Tile[6];
        for(int b=0;b<6;b++){
            a[b]=c.adjacentTiles.get(b);
            k[b]=d.adjacentTiles.get(b);
        }
        int r=0;
        for(int b=0;b<6;b++){
            for(int j=0;j<6;j++){
               if(a[b].equals(k[j])){
                   adjacentT.add(a[b]);
               }
            }
        }
    }
    public ArrayList<Tile> getAdjacentTiles(){
        return adjacentT;
    }
    public ArrayList<Vertex> getAdjacentVertex(){
        return adjacentV;
    }
    public void buildRoad(){
        r=new Road();
    }
}
