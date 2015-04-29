/**
 * Created by Dan on 4/20/2015.
 */
import java.util.ArrayList;

public class MBH {

    private ArrayList<Vertex> sQ;//Store the sorted Queue

    // Constructor
    public MBH(ArrayList<Vertex> ar) {
        this.sQ = ar;
        build(this.sQ, sQ.size());
    }

    // Build Min binary Heap
    private void build(ArrayList<Vertex> usQ, int n) {
        for (int i = (n / 2) - 1; i >= 0; i--){
            Heapify(usQ, i, n);
        }
    }

    private void Heapify(ArrayList<Vertex> usQ, int i, int n) {
        int l, r; //gets right and left children
        l = left(i);
        r = right(i);
        int smallest;
        if (l <= n - 1) {//checks to make sure that the left and right children are place correctly
            if (getDist(usQ.get(l)) < getDist(usQ.get(i))) {
                smallest = l;
                usQ.get(l).index = i;
                usQ.get(i).index = l;
            } else {
                usQ.get(l).index = l;
                usQ.get(i).index = i;
                smallest = i;
            }
        } else {
            usQ.get(i).index = i;
            smallest = i;
        }
        if (r <= n - 1) {
            if (getDist(usQ.get(r)) < getDist(usQ.get(smallest))) {
                usQ.get(r).index = i;
                usQ.get(i).index = r;
                smallest = r;
            } else{
                usQ.get(r).index = r;
                usQ.get(i).index = i;
            }
        }

        //checks to make sure that things are correct
        if (smallest != i) {
            Vertex temp = usQ.get(i);
            usQ.set(i, usQ.get(smallest));
            usQ.set(smallest, temp);
            Heapify(usQ, smallest, n);
        }
    }

    // Remove last element
    public Vertex extractMin(ArrayList<Vertex> sQ, int n) {
        if (n < 1) {
            return null;
        }
        Vertex min = sQ.get(0);
        sQ.set(0, sQ.get(n - 1));
        sQ.remove(n - 1);
        n = n - 1;
        if (n > 0) {
            Heapify(sQ, 0, n);
        }
        return min;
    }

    // Decrease Priority
    public void decreaseKey(int i, float key) {
        if (sQ.get(i).dist < key) {
            return;
        }
        sQ.get(i).dist = key;
        while (i > 0 && sQ.get(parent(i)).dist > sQ.get(i).dist) {
            // Change index
            sQ.get(parent(i)).index = i;
            sQ.get(i).index = parent(i);

            Vertex temp = sQ.get(i);
            sQ.set(i, sQ.get(parent(i)));
            sQ.set(parent(i), temp);
            i = parent(i);
        }
    }

    // Get the Parent of a node
    private int parent(int i){
        return (i-1)/2;
    }
    // Get the left child of the node
    private int left(int i) {
        return 2 * i + 1;
    }

    // Get the right child of the node
    private int right(int i) {
        return 2 * i + 2;
    }

    public boolean isEmpty() {
        return sQ.isEmpty();
    }

    public Vertex extractMin() {
        return extractMin(sQ, sQ.size());
    }

    private float getDist(Vertex v) {
        return v.dist;
    }
}