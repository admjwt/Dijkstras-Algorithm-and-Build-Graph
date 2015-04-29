/**
 * Created by Dan on 4/20/2015.
 */

import java.util.LinkedList;

public class Vertex {

    public String name; // Vertex name
    public LinkedList<Edge> adj; // Adjacent vertices
    public Vertex prev; // Previous vertex on shortest path
    public float dist; // Distance of path
    public int index; //location in min Binary Heap
    public String status;//undiscovered, discovered, finished

    public Vertex(String nm) {
        name = nm;
        adj = new LinkedList<Edge>();
        reset();
    }

    public void reset() { //initial state of all vertices
        dist = Graph.INFINITY;
        prev = null;
        status = "undiscovered";
    }

}