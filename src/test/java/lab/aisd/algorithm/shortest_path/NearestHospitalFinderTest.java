package lab.aisd.algorithm.shortest_path;

import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.model.Vertex;
import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NearestHospitalFinderTest {
    static Graph graph;

    @BeforeAll
    static void setup() {
        graph = new Graph(6);
        List<Vertex> vertices = new ArrayList<>();
        int i = 0;
        vertices.add(new Vertex(new Hospital(i++, "name", new Coordinate(10,10), 0, 0), true, i-1));
        vertices.add(new Vertex(new Hospital(i++, "name", new Coordinate(100, 120), 0, 0), true, i-1));
        vertices.add(new Vertex(new Hospital(i++, "name", new Coordinate(120, 130), 0, 0), true, i-1));
        vertices.add(new Vertex(new Hospital(i++, "name", new Coordinate(10, 140), 0, 0), true, i-1));
        vertices.add(new Vertex(new Hospital(i++, "name", new Coordinate(140, 10), 0, 0), true, i-1));

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
    void findNearestHospitalByCoordinate() {
        NearestHospitalFinder nearestHospitalFinder = new NearestHospitalFinder(graph);

        Vertex nearestHospital = nearestHospitalFinder.findNearestHospitalByCoordinate(new Coordinate(20, 20));
        assertEquals(0, nearestHospital.getId());

        nearestHospital = nearestHospitalFinder.findNearestHospitalByCoordinate(new Coordinate(20, 140));
        assertEquals(3, nearestHospital.getId());

        nearestHospital = nearestHospitalFinder.findNearestHospitalByCoordinate(new Coordinate(10, 10));
        assertEquals(0, nearestHospital.getId());

        nearestHospital = nearestHospitalFinder.findNearestHospitalByCoordinate(new Coordinate(110, 125));
        assertEquals(1, nearestHospital.getId());
    }
}
