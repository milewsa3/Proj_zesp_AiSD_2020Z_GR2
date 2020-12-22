package lab.aisd.model;

import java.util.Objects;

public class MapObject {
    protected int id;
    protected Coordinate position;

    public MapObject(int id, Coordinate position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapObject that = (MapObject) o;
        return id == that.id && position.equals(that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position);
    }
}
