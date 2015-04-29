/**
 * Created by Dan on 4/20/2015.
 */
public class Edge {

    public Vertex from;
    public Vertex to;
    public float cost;

    public Edge(Vertex from, Vertex to, float cost) {
        super();
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

}