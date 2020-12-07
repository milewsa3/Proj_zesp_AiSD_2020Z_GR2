package lab.aisd.model;

public class Hospital {
    private int id;
    private String name;
    private Coordinate coordinate;
    private int bedsCount;
    private int freeBedsCount;

    public Hospital(int id, String name, Coordinate coordinate, int bedsCount, int freeBedsCount) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.bedsCount = bedsCount;
        this.freeBedsCount = freeBedsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getBedsCount() {
        return bedsCount;
    }

    public void setBedsCount(int bedsCount) {
        this.bedsCount = bedsCount;
    }

    public int getFreeBedsCount() {
        return freeBedsCount;
    }

    public void setFreeBedsCount(int freeBedsCount) {
        this.freeBedsCount = freeBedsCount;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Hospital)) {
            return false;
        }

        Hospital hospital = (Hospital) o;
        return (hospital.id == this.id && hospital.name.equals(this.name) && hospital.coordinate.equals(this.coordinate)
                && hospital.bedsCount == this.bedsCount && hospital.freeBedsCount == this.freeBedsCount);
    }
}
