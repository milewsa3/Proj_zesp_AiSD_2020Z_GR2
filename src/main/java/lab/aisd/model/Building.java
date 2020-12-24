package lab.aisd.model;

import java.util.Objects;

public class Building extends MapObject {
    private String name;

    public Building(int id, String name, Coordinate position) {
        super(id, position);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Building building = (Building) o;
        return name.equals(building.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
