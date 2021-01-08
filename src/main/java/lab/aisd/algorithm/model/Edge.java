package lab.aisd.algorithm.model;

public class Edge {

    private final Vertex from;
    private final Vertex to;
    private int distance;

    public Edge(Vertex from, Vertex to, int distance) {
        super();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
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
