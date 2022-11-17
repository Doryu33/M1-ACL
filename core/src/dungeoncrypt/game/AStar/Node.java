package dungeoncrypt.game.AStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dungeoncrypt.game.data.Data.ROOM_SIZE;

public class Node implements Comparable<Node> {
    private static int idCounter = 0;
    public int id;

    // Parent in the path
    public Node parent = null;
    public List<Edge> neighbors;

    /**  A  B  C  D ...
     * 1
     * 2    X
     * 3
     * 4
     * ...
     * X est la case B2
     */
    public String name;
    public int xPos;
    public int yPos;

    // Evaluation functions
    public double f = Double.MAX_VALUE;
    public double g = Double.MAX_VALUE;
    // Hardcoded heuristic
    public double h;
    public void set(int h){
        this.h = h;
    }

    public String toString(){
        return name;
    }

    public Node(int x, int y){
        this.id = idCounter++;
        this.xPos = x;
        this.yPos = y;
        this.neighbors = new ArrayList<>();
        this.name = (char) (x+65) + String.valueOf(ROOM_SIZE-y);
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    @Override
    public int compareTo(Node n) {
        return Double.compare(this.f, n.f);
    }

    public void addBranch(int weight, Node node){
        Edge newEdge = new Edge(weight, node);
        neighbors.add(newEdge);
    }

    public double calculateHeuristic(Node target){
        return target.h;
    }

    public static class Edge {
        Edge(int weight, Node node){
            this.weight = weight;
            this.node = node;
        }

        public int weight;
        public Node node;
    }

    public static void printPath(Node target){
        Node n = target;

        if(n==null)
            return;

        List<Integer> ids = new ArrayList<>();

        while(n.parent != null){
            ids.add(n.id);
            n = n.parent;
        }
        ids.add(n.id);
        Collections.reverse(ids);

        for(int id : ids){
            System.out.print(id + " ");
        }
        System.out.println("");
    }
}
