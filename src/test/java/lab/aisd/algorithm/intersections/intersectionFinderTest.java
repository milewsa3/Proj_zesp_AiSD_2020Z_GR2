package lab.aisd.algorithm.intersections;

import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;
import lab.aisd.model.Path;
import lab.aisd.util.input.InputData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class intersectionFinderTest {
    private final intersectionFinder finder = new intersectionFinder();

    @Test
    public void testShouldThrowNullPointerExceptionIfDataIsNull() {
        assertThrows(NullPointerException.class, () -> {
            finder.intersectionFinder(null);
        });
    }
    
    @Test
    public void testShouldThrowNullPointerExceptionIfTooLessHospsOrPaths() {
        InputData inputData = new InputData();
        
        inputData.getHospitals().add(new Hospital(1, "A", new Coordinate(20,20), 100, 100));
        inputData.getHospitals().add(new Hospital(2, "A", new Coordinate(20,20), 100, 100));
        inputData.getHospitals().add(new Hospital(3, "A", new Coordinate(20,20), 100, 100));
        
        inputData.getPaths().add(new Path(1, 1, 2, 700));
        inputData.getPaths().add(new Path(2, 1, 3, 700));
        inputData.getPaths().add(new Path(3, 1, 4, 700));

        
        assertThrows(NullPointerException.class, () -> {
            finder.intersectionFinder(inputData);
        });

    }
    
    @Test
    public void testShouldSwitchHospsIDIfFirstIsBigger(){
        InputData inputData = new InputData();
        
        inputData.getPaths().add(new Path(1, 1, 2, 700));
        inputData.getPaths().add(new Path(2, 3, 2, 700));
        inputData.getPaths().add(new Path(3, 4, 1, 700));
        
        int[] expectedFirstIDs = {1,2,1};
        int[] actualFirstIDs = {0,0,0};
        int index = 0;
        
        finder.testPreperePaths(inputData.getPaths());
        
        for(Path x : inputData.getPaths()){
            actualFirstIDs[index++] = x.getFirstHospitalID();
        }
        
        assertArrayEquals(expectedFirstIDs, actualFirstIDs);
    }
    
    @Test
    public void testShouldCalculateDistanceBetweenTwoPoints(){
        Coordinate point1 = new Coordinate(5, 5);
        Coordinate point2 = new Coordinate(10, 5);
        Coordinate point3 = new Coordinate(20, 20);
        
        double distancePoint1Point2 = finder.testCalculateDistanceBetweenPoints(point1, point2);
        double distancePoint1Point3 = finder.testCalculateDistanceBetweenPoints(point1, point3);
        double distancePoint2Point3 = finder.testCalculateDistanceBetweenPoints(point2, point3);
        
        assertEquals(5, distancePoint1Point2, 0.001);
        assertEquals(21.213, distancePoint1Point3, 0.001);
        assertEquals(18.028, distancePoint2Point3, 0.001);
    }
    
    @Test
    public void testShouldCalculateIntersectionPointOfSegmentsBetweenTwoPoints(){
        Coordinate point1 = new Coordinate(5, 5);
        Coordinate point2 = new Coordinate(20, 5);
        Coordinate point3 = new Coordinate(20, 20);
        Coordinate point4 = new Coordinate(10, 15);
        Coordinate point5 = new Coordinate(15, 20);
        Coordinate point6 = new Coordinate(5, 15);
        
        double[] parrarel = finder.testIntersectionPoint(point1, point2, point4, point6);
        double[] intersection1 = finder.testIntersectionPoint(point1, point3, point2, point5);
        double[] intersection2 = finder.testIntersectionPoint(point3, point6, point4, point5);
        double[] noIntersection = finder.testIntersectionPoint(point2, point3, point4, point5);
        
        assertArrayEquals(null, parrarel);
        assertArrayEquals(null, noIntersection);
        assertArrayEquals(new double[]{16.25,16.25}, intersection1, 0.001);
        assertArrayEquals(new double[]{12.5,17.5}, intersection2, 0.001);
    }
    
    @Test
    public void shouldReturnDistanceMulltiplierNeededToCalculateDistanceToIntersection(){        
        Hospital hosp1 = new Hospital(1, "A", new Coordinate(20,20), 100, 100);
        Hospital hosp2 = new Hospital(2, "B", new Coordinate(40,20), 100, 100);
        Hospital intersection1 = new Hospital(3, "INTERSECTION", new Coordinate(30,20), 100, 100);
        Hospital intersection2 = new Hospital(4, "INTERSECTION2", new Coordinate(23,20), 100, 100);
        
        double distanceMultiplier1 = finder.testDistanceMultipplier(hosp1, hosp2, intersection1);
        double distanceMultiplier2 = finder.testDistanceMultipplier(hosp1, hosp2, intersection2);
        
        assertEquals(0.5, distanceMultiplier1, 0.001);
        assertEquals(0.15, distanceMultiplier2, 0.001);
        
        
    }
}