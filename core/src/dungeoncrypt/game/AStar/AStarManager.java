package dungeoncrypt.game.AStar;

import dungeoncrypt.game.tiles.Tile;

import java.util.*;

import static dungeoncrypt.game.data.Data.ROOM_SIZE;

public class AStarManager {

    private final ArrayList<Node> nodes;
    private Node[][] graph;
    private final HashMap<String,Node> hashMapNodes;
    public static final AStarManager instance = new AStarManager();

    private AStarManager(){
        this.hashMapNodes = new HashMap<>();
        nodes = new ArrayList<>();
    }

    /**
     * Transformer le tableau de tuile en graph (ne connait pas ses voisins)
     * @param map à convertir
     */
    public void transformToGraph(Tile[][] map){
        hashMapNodes.clear();
        Node n;
        graph = new Node[ROOM_SIZE][ROOM_SIZE];
        for (int y = ROOM_SIZE-1; y >= 0 ; y--) {
            for (int x = 1; x < ROOM_SIZE; x++) {
                if(map[y][x].getCharTile() == ' ' || map[y][x].getCharTile() == 'T'){
                    n = new Node(x,y);
                    graph[y][x] = n;
                    hashMapNodes.put(n.name,n);
                }
            }
        }
        addNeighbors();
    }

    /**
     * Ajouter les voisins de tous les noeuds
     */
    private void addNeighbors(){
        Node n;
        int h = 0;
        for (int y = ROOM_SIZE-2; y > 0 ; y--) {
            for (int x = 1; x < ROOM_SIZE-1; x++) {
                if(graph[y][x] != null){
                    n = hasValidNodeNeighbor(x+1,y);
                    if(n != null){
                        graph[y][x].addBranch(1,n);
                        h++;
                    }

                    n = hasValidNodeNeighbor(x-1,y);
                    if(n != null){
                        graph[y][x].addBranch(1,n);
                        h++;
                    }

                    n = hasValidNodeNeighbor(x,y+1);
                    if(n != null){
                        graph[y][x].addBranch(1,n);
                        h++;
                    }

                    n = hasValidNodeNeighbor(x,y-1);
                    if(n != null){
                        graph[y][x].addBranch(1,n);
                        h++;
                    }

                    graph[y][x].set(h);
                    h = 0;
                }
            }
        }
    }

    /**
     * Savoir si le noeud au coordonnées x et y existe
     * @param x position x du noeud
     * @param y position y du noeud
     * @return le noeud s'il existe, null sinon
     */
    private Node hasValidNodeNeighbor(int x, int y) {
        if(graph[y][x] != null){
            return graph[y][x];
        }
        return null;
    }

    /**
     * Afficher le graph
     */
    public void printGraph(){
        for (int y = 1; y < ROOM_SIZE ; y++) {
            for (int x = 1; x < ROOM_SIZE; x++) {
                if(graph[y][x] != null){
                    if(graph[y][x].toString().length() == 2){
                        System.out.print(graph[y][x].toString()+" ");
                    }else{
                        System.out.print(graph[y][x].toString());
                    }
                }
                else{
                    System.out.print("   ");
                }
            }
            System.out.println("");
        }
    }

    /**
     * Afficher le chemin, càd tous les noeuds où il faut passer
     * @param target
     */
    public void printPath(Node target){
        Node n = target;

        if(n==null)
            return;

        List<String> names = new ArrayList<>();
        int counter = 0;

        while(counter < 50 && n.parent != null){
            names.add(n.name);
            n = n.parent;
            counter++;
        }
        names.add(n.name);
        Collections.reverse(names);

        for(String name : names){
            System.out.print(name + " ");
        }
        System.out.println("");
    }

    public List<Node> getPath(Node target){
        Node n = target;

        if(n==null)
            return null;

        nodes.clear();
        int counter = 0;

        while(counter < 50 && n.parent != null){
            nodes.add(n);
            n = n.parent;
            counter++;
        }
        nodes.add(n);
        Collections.reverse(nodes);
        return nodes;
    }

    public Node aStar(String startName, String targetName){
        Node start = hashMapNodes.get(startName);
        Node target = hashMapNodes.get(targetName);

        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.f = start.g + start.calculateHeuristic(target);
        openList.add(start);

        while(!openList.isEmpty()){
            Node n = openList.peek();
            if(n == target){
                return n;
            }

            for(Node.Edge edge : n.neighbors){
                Node m = edge.node;
                double totalWeight = n.g + edge.weight;

                if(!openList.contains(m) && !closedList.contains(m)){
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                } else {
                    if(totalWeight < m.g){
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.g + m.calculateHeuristic(target);

                        if(closedList.contains(m)){
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
        return null;
    }

}
