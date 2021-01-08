package lab.aisd.algorithm.shortest_path;

import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.model.Vertex;
import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloydWarshallTest {
    static Graph graph;

    @BeforeAll
    static void setup() {
        graph = new Graph(6);
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            vertices.add(new Vertex(new Hospital(), true, i));
        }
        vertices.add(new Vertex(new Coordinate(69, 82), false, 5));
        graph.addEdge(vertices.get(0), vertices.get(3), 550);
        graph.addEdge(vertices.get(0), vertices.get(5), 455);
        graph.addEdge(vertices.get(0), vertices.get(4), 800);
        graph.addEdge(vertices.get(3), vertices.get(5), 338);
        graph.addEdge(vertices.get(3), vertices.get(1), 550);
        graph.addEdge(vertices.get(1), vertices.get(5), 245);
        graph.addEdge(vertices.get(1), vertices.get(2), 300);
        graph.addEdge(vertices.get(2), vertices.get(4), 600);
        graph.addEdge(vertices.get(4), vertices.get(5), 412);
    }

    @Test
    void computeShortestPaths() {
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
        FloydWarshall floydWarshall = new FloydWarshall(graph);
        floydWarshall.computeShortestPaths();
        List<Vertex> expectedPath = new ArrayList<>(
                Arrays.asList(
                        new Vertex(new Hospital(), true, 1),
                        new Vertex(new Coordinate(69, 82), false, 5),
                        new Vertex(new Hospital(), true, 4)
                )
        );
        assertEquals(expectedPath, floydWarshall.getShortestPath(1, 4));

        expectedPath = new ArrayList<>(
                Arrays.asList(
                        new Vertex(new Hospital(), true, 0),
                        new Vertex(new Coordinate(69, 82), false, 5),
                        new Vertex(new Hospital(), true, 1),
                        new Vertex(new Hospital(), true, 2)
                )
        );
        assertEquals(expectedPath, floydWarshall.getShortestPath(0, 2));
    }

    @Test
    void getPathToNearestNotVisitedHospital() {
        FloydWarshall floydWarshall = new FloydWarshall(graph);
        floydWarshall.computeShortestPaths();
        Integer[] destinations = {3, 1, 4, 2, null};
        for (int i = 0; i < 4; i++) {
            List<Vertex> path = floydWarshall.getPathToNearestNotVisitedHospital(0);
            int destinationId = path.get(path.size() - 1).getOrderedId();
            assertEquals(destinations[i], destinationId);
        }
        List<Vertex> path = floydWarshall.getPathToNearestNotVisitedHospital(0);
        assertNull(path);
    }

}
