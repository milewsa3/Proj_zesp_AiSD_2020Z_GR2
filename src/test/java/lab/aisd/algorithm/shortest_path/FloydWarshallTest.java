package lab.aisd.algorithm.shortest_path;

import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.model.Node;
import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloydWarshallTest {

    @Test
    void computeShortestPaths() {
        Graph graph = new Graph(6);
        List<Node> nodes = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            nodes.add(new Node(new Hospital(), true, i));
        }
        nodes.add(new Node(new Coordinate(69, 82), false, 5));
        graph.addEdge(nodes.get(0), nodes.get(3), 550);
        graph.addEdge(nodes.get(0), nodes.get(5), 455);
        graph.addEdge(nodes.get(0), nodes.get(4), 800);
        graph.addEdge(nodes.get(3), nodes.get(5), 338);
        graph.addEdge(nodes.get(3), nodes.get(1), 550);
        graph.addEdge(nodes.get(1), nodes.get(5), 245);
        graph.addEdge(nodes.get(1), nodes.get(2), 300);
        graph.addEdge(nodes.get(2), nodes.get(4), 600);
        graph.addEdge(nodes.get(4), nodes.get(5), 412);
        FloydWarshall floydWarshall = new FloydWarshall(graph);
        floydWarshall.computeShortestPaths();

        int[][] dist = floydWarshall.getDist();
        assertEquals(550, dist[0][3]);
        assertEquals(700, dist[0][1]);
        assertEquals(1000, dist[0][2]);
        assertEquals(800, dist[0][4]);
        assertEquals(550, dist[3][1]);
        assertEquals(850, dist[3][2]);
        assertEquals(750, dist[3][4]);
        assertEquals(300, dist[1][2]);
        assertEquals(657, dist[1][4]);
        assertEquals(600, dist[2][4]);
    }

    @Test
    void getShortestPath() {
    }
}
