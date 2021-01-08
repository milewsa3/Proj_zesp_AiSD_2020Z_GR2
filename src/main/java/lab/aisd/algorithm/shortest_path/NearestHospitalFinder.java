package lab.aisd.algorithm.shortest_path;

import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.model.Node;
import lab.aisd.model.Coordinate;

import java.util.List;

public class NearestHospitalFinder {
    private Graph graph;

    public NearestHospitalFinder(Graph graph) {
        this.graph = graph;
    }

    public Node findNearestHospitalByCoordinate(Coordinate patientCoordinate) {
        double minDistance = Integer.MAX_VALUE;
        Node nearestHospital = null;
        List<Node> nodes = graph.getAllNodes();
        for (Node node : nodes) {
            double distance = calculateDistanceBetweenPoints(patientCoordinate, node.getPosition());
            if (distance < minDistance && node.isHospital()) {
                minDistance = distance;
                nearestHospital = node;
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
