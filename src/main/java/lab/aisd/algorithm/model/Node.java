package lab.aisd.algorithm.model;

import lab.aisd.model.Hospital;

public class Node extends Hospital {
    private boolean isHospital;
    private boolean isVisited;
    private int orderedId; // just because edges in graph are stored in list; has to be unique

    public Node(Hospital hospital, boolean isHospital, boolean isVisited, int orderedId) {
        super(hospital.getId(), hospital.getName(), hospital.getCoordinate(),
                hospital.getBedsCount(), hospital.getFreeBedsCount());
        this.isHospital = isHospital;
        this.isVisited = isVisited;
        this.orderedId = orderedId;
    }

    public Node(boolean isHospital, boolean isVisited, int orderedId) {
        super();
        this.isHospital = isHospital;
        this.isVisited = isVisited;
        this.orderedId = orderedId;
    }

    public boolean isHospital() {
        return isHospital;
    }

    public void setHospital(boolean hospital) {
        isHospital = hospital;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getOrderedId() {
        return orderedId;
    }

    public void setOrderedId(int orderedId) {
        this.orderedId = orderedId;
    }

    public String toString() {
        return "" + orderedId;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Node)) {
            return false;
        }

        Node n = (Node) o;
        return (n.orderedId == this.orderedId);
    }
}
