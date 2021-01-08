package lab.aisd.algorithm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph implements Iterable<Edge> {
    private int nodesNumber;
    private ArrayList<ArrayList<Edge>> edges = new ArrayList<>();

    public Graph(int nodesNumber) {
        this.nodesNumber = nodesNumber;

        for (int i = 0; i < nodesNumber; i++) {
            edges.add(new ArrayList<>());
        }
    }

    public void addEdge(Vertex from, Vertex to, int distance) {
        int fromId = from.getOrderedId();
        int toId = to.getOrderedId();
        edges.get(fromId).add(new Edge(from, to, distance));
        edges.get(toId).add(new Edge(to, from, distance));
    }

    public Vertex getNodeByOrderedId(int orderedId) {
        if (!edges.get(orderedId).isEmpty()) {
            return edges.get(orderedId).get(0).getFrom();
        }
        return null;
    }

    public List<Vertex> getAllNodes() {
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < nodesNumber; i++) {
            Vertex vertex = getNodeByOrderedId(i);
            if (vertex != null) {
                vertices.add(vertex);
            }
        }
        return vertices;
    }

    public int getNodesNumber() {
        return nodesNumber;
    }

    public ArrayList<ArrayList<Edge>> getEdges() {
        return edges;
    }

    @Override
    public Iterator<Edge> iterator() {

        return new EdgeIterator();
    }

    class EdgeIterator implements Iterator<Edge> {

        Edge next;
        int currentBucket = 0;

        EdgeIterator() {

            next = getNextEdgeWithCapacity();
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Edge next() {
            Edge prevNext = next;
            next = getNextEdgeWithCapacity();
            return prevNext;
        }

        @Override
        public void remove() {
        }

        private Edge getNextEdgeWithCapacity() {

            ArrayList<Edge> edgeBucket = edges.get(currentBucket);
            int idx = -1;
            if (next != null) {
                idx = edgeBucket.indexOf(next);

                if (idx == edgeBucket.size()) {
                    currentBucket++;
                    edgeBucket = edges.get(currentBucket);
                    idx = -1;
                }
            }
            if (currentBucket >= nodesNumber) {
                return null;
            }
            while (true) {
                Edge e;
                for (int i = idx + 1; i < edgeBucket.size(); i++) {
                    e = edgeBucket.get(i);
                    return e;
                }
                currentBucket++;
                idx = -1;
                if (currentBucket == nodesNumber) {
                    return null;
                }
                edgeBucket = edges.get(currentBucket);
            }
        }
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Graph)) {
            return false;
        }

        Graph c = (Graph) o;
        return (c.edges.equals(this.edges) && c.nodesNumber == this.nodesNumber);
    }
}
