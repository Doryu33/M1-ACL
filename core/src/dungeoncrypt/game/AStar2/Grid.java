package dungeoncrypt.game.AStar2;

import java.util.ArrayList;
import java.util.List;

import static dungeoncrypt.game.data.Data.ROOM_SIZE;

/**
 * The grid of nodes we use to find path
 */
public class Grid {
    public Node[][] nodes;
    int gridWidth, gridHeight;
    public static final Grid instance = new Grid();

    /**
     * Create a new grid of just walkable / unwalkable tiles.
     */
    private Grid() {

    }

    public void setGrid(boolean[][] walkableTiles){
        gridWidth = ROOM_SIZE;
        gridHeight = ROOM_SIZE;
        nodes = new Node[ROOM_SIZE][ROOM_SIZE];

        for (int x = 0; x < ROOM_SIZE; x++)
            for (int y = 0; y < ROOM_SIZE; y++)
                nodes[x][y] = new Node(x, y, walkableTiles[x][y] ? 1.0f : 0.0f);

    }

    public Node[][] getGrid(){
        return nodes.clone();
    }

    public List<Node> get8Neighbours(Node node) {
        List<Node> neighbours = new ArrayList<Node>();

        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;

                int checkX = node.x + x;
                int checkY = node.y + y;

                if (checkX >= 0 && checkX < gridWidth && checkY >= 0 && checkY < gridHeight)
                    neighbours.add(nodes[checkX][checkY]);
            }

        return neighbours;
    }

    public List<Node> get4Neighbours(Node node) {
        List<Node> neighbours = new ArrayList<Node>();

        if (node.y + 1 >= 0 && node.y + 1  < gridHeight) neighbours.add(nodes[node.x][node.y + 1]); // N
        if (node.y - 1 >= 0 && node.y - 1  < gridHeight) neighbours.add(nodes[node.x][node.y - 1]); // S
        if (node.x + 1 >= 0 && node.x + 1  < gridHeight) neighbours.add(nodes[node.x + 1][node.y]); // E
        if (node.x - 1 >= 0 && node.x - 1  < gridHeight) neighbours.add(nodes[node.x - 1][node.y]); // W

        return neighbours;
    }
}
