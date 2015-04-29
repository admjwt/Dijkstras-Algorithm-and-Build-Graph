
Program Design:
~Build a graph from a input file
~be able to update the graph from the program(ie. add and remove and change the cost of edges)
~display the graph in a proper format
~find the shortest path to another vertices using Dijkstras algorithm
~find all reachable vertices of a node

Files:
~Contains
    -Edge.java: creates the structure of an edge
    -Vertex.java: Creates the struture of a vertex
    -MBH.java: Create a min binary heap which is used for Dijkstras Algorithm
    -Graph.java: Contains the main method to run, as well as all graph related functions such as printing,
                 dijkstras algorithm, adding and removing edges, and reachable vertices
~Built with InteliJ

How to run:
1. run the graph.java file through cmd or terminal and give it the name of the file you wish to read in as
part of the cmd arguments
2. Type in the commands the same way they are presented in the example command file on moodle
ex. addedge Belk Atkins .25, deleteedge, path Belk Duke
3. close the program by typing quit

Complexity:
The complexity of the reachabily function takes O(V) time to intalize and because it is built using BFS the
running time of that is O(V+E). Whish means that every vertex has a run time of  O(V+E) and for V vertices
that means that it has O(V(V+E)) running time.

Works/Fails:
It all should work just fine. I would have liked more time to kind of flesh out the way choices, so instead of
typing all variables into each command, instead have a menu of options to pick from and after picking one it will
prompt for the rest of the necessary parameters. That is more of just an asethtic thing though, and doesnt really
impact the way that the program should run.

NOTE:
If for some reason the program doesnt read in the file from the command line, there are a few lines commented out in
the main, which ask for the location of the file on the hard drive. If you uncomment them and give the path of the
file it will definitely work. I wrote the program using that method.
