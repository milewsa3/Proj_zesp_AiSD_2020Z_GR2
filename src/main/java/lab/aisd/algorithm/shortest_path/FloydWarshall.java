package lab.aisd.algorithm.shortest_path;

import lab.aisd.algorithm.model.Edge;
import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.model.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FloydWarshall {
    private Integer[][] next;
    private Graph graph;
    private int[][] dist;
    private List<List<Element>> sortedDist;

    public int[][] getDist() {
        return dist;
    }

    public Graph getGraph() {
        return graph;
    }

    public FloydWarshall(Graph graph) {
        this.graph = graph;
        int nodesNumber = graph.getNodesNumber();
        next = new Integer[nodesNumber][nodesNumber];
        dist = new int[nodesNumber][nodesNumber];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        sortedDist = new ArrayList<>();
        for (int i = 0; i < nodesNumber; i++) {
            sortedDist.add(new ArrayList<>());
        }
    }

    public void computeShortestPaths() {
        int nodesNumber = graph.getNodesNumber();
        for (Edge edge : graph) {
            int from = edge.getFrom().getOrderedId();
            int to = edge.getTo().getOrderedId();
            dist[from][to] = edge.getDistance();
            next[from][to] = to;
        }
        for (int i = 0; i < nodesNumber; i++) {
            dist[i][i] = 0;
            next[i][i] = i;
        }
        for (int k = 0; k < nodesNumber; k++) {
            for (int i = 0; i < nodesNumber; i++) {
                for (int j = 0; j < nodesNumber; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }
        createSortedDistanceList();
    }

    private void createSortedDistanceList() {
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[i].length; j++) {
                sortedDist.get(i).add(new Element(j, dist[i][j]));
            }
        }
        for (List<Element> elements : sortedDist) {
            Collections.sort(elements);
        }
    }

    public List<Node> getShortestPath(int from, int to) {
        if (next[from][to] == null) {
            return null;
        }
        List<Node> path = new ArrayList<>();
        path.add(graph.getNodeByOrderedId(from));
        while (from != to) {
            from = next[from][to];
            path.add(graph.getNodeByOrderedId(from));
        }
        return path;
    }

    public List<Node> getPathToNearestNotVisitedHospital(int fromId) {
        List<Element> sortedDistFrom = sortedDist.get(fromId);
        int toId = Integer.MAX_VALUE;
        for (int i = 1; i < sortedDistFrom.size(); i++) {
            int nodeId = sortedDistFrom.get(i).index;
            Node node = graph.getNodeByOrderedId(nodeId);
            if (!node.isVisited() && node.isHospital()) {
                toId = nodeId;
                node.setIsVisited(true);
                break;
            }
        }
        if (toId != Integer.MAX_VALUE) {
            return getShortestPath(fromId, toId);
        }
        return null;
    }

    static class Element implements Comparable<Element> {

        int index, value;

        Element(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public int compareTo(Element e) {
            return this.value - e.value;
        }

        public String toString() {
            return "i: " + index + ", v:" + value;
        }
    }
}
