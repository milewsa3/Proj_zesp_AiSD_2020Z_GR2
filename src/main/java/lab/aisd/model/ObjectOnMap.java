package lab.aisd.model;

public class ObjectOnMap {
    private int id;
    private String name;
    private Coordinate coordinate;

    public ObjectOnMap(int id, String name, Coordinate coordinate) {
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

        if (!(o instanceof ObjectOnMap)) {
            return false;
        }

        ObjectOnMap objectOnMap = (ObjectOnMap) o;
        return (objectOnMap.id == this.id && objectOnMap.name.equals(this.name) && objectOnMap.coordinate.equals(this.coordinate));
    }
}
