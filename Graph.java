/**
 * Created by Dan on 4/20/2015.
 */
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Main file to run
public class Graph {

    //Intalization of variables needed throughout
    private static Boolean quit = false; //used to exit the program properly
    public static final float INFINITY = Float.MAX_VALUE; //used for distance of vertices not yet reached. Distance is infinity until it has a path
    protected Map<String, Vertex> vMap = new HashMap<String, Vertex>(); //stores all vertices and edges in a map

        //Prints the graph as it currently is
    public void printGraph() {
        ArrayList<String> sort = new ArrayList<String>(vMap.keySet());
        Collections.sort(sort); //Allows for alphabetical sorting
        for (String key : sort) {
            Vertex w = vMap.get(key);
            System.out.println(w.name);

            ArrayList<String> neighbors = new ArrayList<String>(); //stores adjacent vertices so the van be sorted alphabetically
            for (Edge edge : w.adj) {
                neighbors.add(edge.to.name + " " + edge.cost);
            }
            Collections.sort(neighbors);//alphabetical sorting and then output
            for (String n : neighbors) {
                System.out.println("    " + n);
            }
        }
    }

    //Add a vertex if it doesnt exist otherwise return its name
    private Vertex getVertex(String vertexName) {
        Vertex w = vMap.get(vertexName);
        if (w == null) { //checks if vertex exists already
            w = new Vertex(vertexName);
            vMap.put(vertexName, w);//adds it in if it doesnt
        }
        return w;
    }

    //remove an edge between two vertices, keeps the opposite direction edge if it exists between the two
    public void removeEdge(String from, String to) {
        if (vMap.get(from) != null && vMap.get(to) != null) {//checks if vertices exist
            Vertex u = getVertex(from);
            for (Edge edge : u.adj) {//cycles through the adjacent vertices of the first node and finds the location in the list of the second one
                if (edge.to.name.equals(to)) {
                    u.adj.remove(edge); //removes the link ands the loop
                    break;
                }
            }
        }
    }

    //Add an edge between vertices, to add a bi-directional edge reverse order of the input vertices
    public void addEdge(String from, String to, float cost) {
        boolean check = false;
        if (vMap.get(from) != null && vMap.get(to) != null) {//checks if the nodes already exist
            Vertex v1 = getVertex(from);
            Vertex v2 = getVertex(to);
            for (Edge edg : v1.adj) {
                if (edg.to.name.equals(to)) {//the edge already exists so it updates the cost
                    edg.cost = cost;
                    check = true;
                }
            }
            if (!check) {
                Edge edg = new Edge(v1, v2, cost);
                v1.adj.add(edg);
            }
        } else {//if they dont it will create them
            Vertex v1 = getVertex(from);
            Vertex v2 = getVertex(to);
            Edge edg = new Edge(v1, v2, cost);
            v1.adj.add(edg);
        }

    }

    //Takes the input vertex and computes all paths that it can from the it to the rest of the vertices
    public void computePaths(String from) {
        // Initialization
        for (Vertex v : vMap.values()) {
            v.dist = INFINITY;
            v.prev = null;
        }
        // Initial Check
        Vertex start = vMap.get(from);
        if (start == null) {
            System.out.println("Start vertex not found!");
            return;
        }

        //intital distance
        start.dist = 0.0;

        //Creates a Priority Queue using a min Binary Heap
        MBH pq = new MBH(new ArrayList<Vertex>(vMap.values()));

        // Dijkstras Algorithm
        while (!pq.isEmpty()) {
            Vertex u = pq.extractMin(); //gets last element of priority queue
            for (Edge edge : u.adj) {
                float alt = u.dist + edge.cost;//gets the new distance from the start node to the next node in the path
                alt = (float) Math.round(alt * 100) / 100;
                if (alt < edge.to.dist) {   //if the new distance is less then the distance to the next node
                    edge.to.dist = alt;     //change its distance to the new distance
                    edge.to.prev = u;       //and set the node the leads to it
                    pq.decreaseKey(edge.to.index, alt); //Decrease the priority in the priority queue
                }
            }
        }
    }

    //Determines if a vertex is reachable based off the paths computed previously, if it is it will call the
    // printPath method and also return the distance from point A to point B
    public void ShortestPath(String dest) {
        Vertex w = vMap.get(dest);
        if (w == null)//Checks if node exists, if it is spelt wrong or improperly type it will trigger this
            System.out.println("'"+dest+"' ~vertex not found");
        else if (w.dist == INFINITY) //if the path to the node doesnt exist it has a distance of infinity and is unreachable
            System.out.println(dest + " is unreachable");
        else {
            printPath(w); //returns the shortest path computed above
            System.out.print("\n    Distance: " + w.dist);
            System.out.println();
        }
    }

    // prints path between the vertices
    private void printPath(Vertex end) {
        if (end.prev != null) {
            printPath(end.prev);
            System.out.print(" -> ");
        }
        System.out.print(end.name);
    }

    //Performs BFS to check which vertices are reachable
    public static void BFS(Graph g) {
        //Stores vertices found at the end of the path from the bfs for each vertex in the graph
        ArrayList<String> List = new ArrayList<String>(g.vMap.keySet());
        Collections.sort(List); //allows alphabetical sorting

        for (String vertex : List) {
            resetBFS(g); //changes vertices back to default so that bfs can run again and be accurate
            Vertex s = g.vMap.get(vertex); //gets a vertex to perform bfs on

            s.status = "discovered";
            s.dist = 0.0;
            s.prev = null;

            Queue<Vertex> q = new LinkedList<Vertex>(); //stores vertices that are reachable
            q.add(s);

            //BFS Algorithm
            while (!q.isEmpty()) {
                Vertex v = q.remove(); //gets the vertex to check
                for (Edge e : v.adj) { //finds its list of edges
                    if ( e.to.dist == Graph.INFINITY) { //set the next vertices that can be reached to discovered
                        e.to.status = "discovered";     //repeat until all paths have been found
                        e.to.dist = v.dist + 1.0;
                        e.to.prev = v;
                        q.add(e.to);
                    }
                }
                v.status = "finished";
            }
            System.out.println(vertex);
            printBFS(List, g, vertex);
        }
    }

    //Prints the vertices that are reachable
    private static void printBFS(ArrayList<String> List, Graph g, String source) {
        for (String vertex : List) {
            //goes through all vertices and prints only those that have been finished
            if (g.vMap.get(vertex).status.equals("finished") && !vertex.equals(source)) {
                System.out.println("    "+vertex);
            }
        }
    }

    //resets the changeable values of a vertex back to the defaults
    private static void resetBFS(Graph g) {
        for (Vertex v : g.vMap.values()) {
            v.status = "undiscovered";
            v.dist = Graph.INFINITY;
            v.prev = null;
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();

        //String file1;
       //Scanner scanner = new Scanner(System.in);

        //gets user input for input file name and location
       // System.out.println("What is the file name and location for the file?");
        //System.out.println("Ex. \\Desktop\\examples\\network.txt");
        //file1 = scanner.nextLine();

        try { //modified file reader provided from moodle
            //FileReader file = new FileReader(file1);
            FileReader file = new FileReader(args[0]);
            Scanner graphFile = new Scanner(file);
            String line;
            while (graphFile.hasNextLine()) {
                line = graphFile.nextLine();
                StringTokenizer st = new StringTokenizer(line);
                try {
                    if (st.countTokens() != 3) {
                        System.err.println("Skipping ill-formatted line "+ line);
                        continue;
                    }
                    String source = st.nextToken();
                    String dest = st.nextToken();
                    float cost = Float.parseFloat(st.nextToken());
                    graph.addEdge(source, dest, cost);
                    graph.addEdge(dest, source, cost);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping ill-formatted line " + line);
                }
            }
            Scanner input = new Scanner(System.in);
            String user_choice;
            while (!quit) {//shows optoins and gets user input

                user_choice = input.nextLine();
                Choice(user_choice, graph); //sends the input and the grpah to the method the determines the choice

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static void Choice(String uChoice, Graph g) {
        String[] parts =  uChoice.split(" "); //gets input and splits it

        //determines which choice was picked
        if(parts[0].equalsIgnoreCase("addedge")){
            g.addEdge(parts[1], parts[2], Float.valueOf(parts[3]));
        }
        
        if(parts[0].equalsIgnoreCase("deleteEdge")){
            g.removeEdge(parts[1], parts[2]);
        }
        
        if(parts[0].equalsIgnoreCase("print")){
            g.printGraph();
            System.out.println();
        }
        
        if(parts[0].equalsIgnoreCase("path")){
            g.computePaths(parts[1]);
            g.ShortestPath(parts[2]);
            System.out.println();
        }

        if(parts[0].equalsIgnoreCase("reachable")){
            g.BFS(g);
        }
        
        if(parts[0].equalsIgnoreCase("quit")){
            quit = true;
        }
    }
}