package lab.aisd.model;

public class Building {
    private int id;
    private String name;
    private Coordinate coordinate;

    public Building(int id, String name, Coordinate coordinate) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
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

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Building)) {
            return false;
        }

        Building building = (Building) o;
        return (building.id == this.id && building.name.equals(this.name) && building.coordinate.equals(this.coordinate));
    }
}
