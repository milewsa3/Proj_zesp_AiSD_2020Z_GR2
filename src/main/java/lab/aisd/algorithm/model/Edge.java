package lab.aisd.algorithm.model;

public class Edge {

    private final Node from;
    private final Node to;
    private int distance;

    public Edge(Node from, Node to, int distance) {
        super();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = Math.max(distance, 0);
    }

    public String toString() {
        return "" + from + "->" + to + " : d=" + distance;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Edge)) {
            return false;
        }

        Edge c = (Edge) o;
        return (c.from == this.from && c.to == this.to && c.distance == this.distance);
    }

}
