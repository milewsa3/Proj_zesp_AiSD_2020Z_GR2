package lab.aisd.algorithm.model;

import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;

public class Node extends Hospital {
    private boolean isHospital;
    private boolean isVisited;
    private int orderedId; // because edges in graph are stored in list; has to be unique

    public Node(Hospital hospital, boolean isHospital, int orderedId) {
        super(hospital.getId(), hospital.getName(), hospital.getPosition(),
                hospital.getBedsCount(), hospital.getFreeBedsCount());
        this.isHospital = isHospital;
        this.isVisited = false;
        this.orderedId = orderedId;
    }

    public boolean isHospital() {
        return isHospital;
    }

    public void setIsHospital(boolean hospital) {
        isHospital = hospital;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean visited) {
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
