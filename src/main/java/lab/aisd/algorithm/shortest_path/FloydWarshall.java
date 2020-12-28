package lab.aisd.algorithm.shortest_path;

import lab.aisd.algorithm.model.Edge;
import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.model.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloydWarshall {
    private Integer[][] next;

    public int[][] getDist() {
        return dist;
    }

    private int[][] dist;
    private Graph graph;

    public FloydWarshall(Graph graph) {
        this.graph = graph;
        int nodesNumber = graph.getNodesNumber();
        next = new Integer[nodesNumber][nodesNumber];
        dist = new int[nodesNumber][nodesNumber];
        for (int[] row: dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
    }

    public void computeShortestPaths(){
        int nodesNumber = graph.getNodesNumber();
        for(Edge edge: graph){
            int from = edge.getFrom().getOrderedId();
            int to = edge.getTo().getOrderedId();
            dist[from][to] = edge.getDistance();
            next[from][to] = to;
        }
        for(int i = 0; i< nodesNumber; i++){
            dist[i][i] = 0;
            next[i][i] = i;
        }
        for(int k = 0; k < nodesNumber; k++){
            for(int i = 0; i< nodesNumber; i++){
                for(int j = 0; j < nodesNumber; j++){
                    if(dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }

    }

    public List<Node> getShortestPath(int from, int to){
        if(next[from][to] == null){
            return null;
        }
        List<Node> path = new ArrayList<>();
        path.add(graph.getNodeByOrderedId(from));
        while (from != to){
            from = next[from][to];
            path.add(graph.getNodeByOrderedId(from));
        }
        return path;
    }

}
