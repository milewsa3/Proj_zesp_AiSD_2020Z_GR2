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

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Patient)) {
            return false;
        }

        Patient patient = (Patient) o;
        return (patient.id == this.id && patient.coordinate.equals(this.coordinate));
    }
}
