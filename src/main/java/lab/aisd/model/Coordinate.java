package lab.aisd.model;

import static java.lang.Math.abs;
import java.util.Objects;

public class Coordinate implements Comparable<Coordinate> {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void offsetBy(int offsetX, int offsetY) {
        x += offsetX;
        y += offsetY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Coordinate plus(Coordinate p){
        return new Coordinate (x + p.x, y + p.y);
    }

    public Coordinate minus(Coordinate p){
        return new Coordinate (x - p.x, y - p.y);
    }

    int cross(Coordinate p){
        return x * p.y - y * p.x;
    }

    int dot(Coordinate p){
        return x * p.x + y * p.y;
    }

    int cross(Coordinate a, Coordinate b){
        return (a.minus(this)).cross((b.minus(this)));
    }

    int dot(Coordinate a, Coordinate b){
        return a.minus(this).dot(b.minus(this));
    }

    int sqrLen(){
        return this.dot(this);
    }

    @Override
    public int compareTo(Coordinate p) {
        return (x < p.x || (x == p.x && y < p.y)) ? -1 : ((x == p.x && y == p.y) ? 0 : 1);
    }

    boolean pointInTriangle(Coordinate a, Coordinate b, Coordinate c){
        int s1 = abs(a.cross(c, b));
        int s2 = abs(this.cross(a, b)) + abs(this.cross(b, c)) + abs(this.cross(c, a));
        return s1 == s2;
    }

}
