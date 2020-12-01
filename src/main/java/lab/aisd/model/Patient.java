package lab.aisd.model;

public class Patient {
    private int id;
    private Coordinate coordinate;

    public Patient(int id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
