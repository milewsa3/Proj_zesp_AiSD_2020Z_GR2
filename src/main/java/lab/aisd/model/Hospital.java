package lab.aisd.model;

import java.util.Objects;

public class Hospital extends MapObject {
    private String name;
    private int bedsCount;
    private int freeBedsCount;

    public Hospital(int id, String name, Coordinate position, int bedsCount, int freeBedsCount) {
        super(id, position);
        this.name = name;
        this.bedsCount = bedsCount;
        this.freeBedsCount = freeBedsCount;
    }

    public Hospital() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean areThereFreeBeds() {
        return  freeBedsCount > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hospital hospital = (Hospital) o;
        return bedsCount == hospital.bedsCount && name.equals(hospital.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, bedsCount);
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "name='" + name + '\'' +
                ", bedsCount=" + bedsCount +
                ", freeBedsCount=" + freeBedsCount +
                ", id=" + id +
                ", position=" + position +
                '}';
    }
}
