package lab.aisd.algorithm.shortest_path;

import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.model.Vertex;
import lab.aisd.model.Coordinate;

import java.util.List;

public class NearestHospitalFinder {
    private Graph graph;

    public NearestHospitalFinder(Graph graph) {
        this.graph = graph;
    }

    public Vertex findNearestHospitalByCoordinate(Coordinate patientCoordinate) {
        double minDistance = Integer.MAX_VALUE;
        Vertex nearestHospital = null;
        List<Vertex> vertices = graph.getAllNodes();
        for (Vertex vertex : vertices) {
            double distance = calculateDistanceBetweenPoints(patientCoordinate, vertex.getPosition());
            if (distance < minDistance && vertex.isHospital()) {
                minDistance = distance;
                nearestHospital = vertex;
            }
        }
        if(nearestHospital != null){
            nearestHospital.setIsVisited(true);
        }
        return nearestHospital;
    }

    private double calculateDistanceBetweenPoints(Coordinate point1, Coordinate point2) {
        return Math.sqrt(Math.pow(point2.getY() - point1.getY(), 2) + Math.pow(point2.getX() - point1.getX(), 2));
    }
}
