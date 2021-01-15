package lab.aisd.algorithm.border;

import lab.aisd.model.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvexHullTest {

    @Test
    void testShouldReturnTrueIfPointInsideSquare(){
        ConvexHull tmp = new ConvexHull();
        Coordinate A = new Coordinate(4, 8);
        Coordinate B = new Coordinate(4, 4);
        Coordinate C = new Coordinate(8, 8);
        Coordinate D = new Coordinate(8, 4);
        Coordinate E = new Coordinate(6, 6);
        Coordinate F = new Coordinate(6, 8);
        tmp.add(A);
        tmp.add(B);
        tmp.add(C);
        tmp.add(D);
        tmp.prepare();
        assertTrue(tmp.isPointWithinTheBorder(E));
        assertTrue(tmp.isPointWithinTheBorder(F));
    }

    @Test
    void testShouldReturnFalseIfPointOutsideSquare(){
        ConvexHull tmp = new ConvexHull();
        Coordinate A = new Coordinate(4, 8);
        Coordinate B = new Coordinate(4, 4);
        Coordinate C = new Coordinate(8, 8);
        Coordinate D = new Coordinate(8, 4);
        Coordinate E = new Coordinate(9, 6);
        tmp.add(A);
        tmp.add(B);
        tmp.add(C);
        tmp.add(D);
        tmp.prepare();
        assertFalse(tmp.isPointWithinTheBorder(E));
    }

    @Test
    void testShouldReturnFalseIf0Area(){
        ConvexHull tmp = new ConvexHull();
        Coordinate A = new Coordinate(4, 8);
        Coordinate B = new Coordinate(4, 4);
        Coordinate C = new Coordinate(4, 6);
        tmp.add(C);
        tmp.add(B);
        tmp.add(A);
        tmp.prepare();
        assertFalse(tmp.isPointWithinTheBorder(C));
    }

    @Test
    void testShouldReturnFalseIfNoPoints(){
        ConvexHull tmp = new ConvexHull();
        Coordinate A = new Coordinate(4, 8);
        assertFalse(tmp.isPointWithinTheBorder(A));
    }

}