package lab.aisd.algorithm.intersections;

import java.util.ArrayList;
import lab.aisd.model.*;

public class intersectionFinder {

    public void intersectionFinder(ArrayList<Path> paths, ArrayList<Hospital> hosps) {

        /*
        TO DO:
        - tests, tests and even more tests
        - improve code quality and readability 
        - change some var names
        - test and online array sizes
        - make this to work from main and to be compatible with other classes
        
        */
        
        int index = 0;
        int[][] onLine = new int[100][100];
        
        preperePaths(paths);

        while (index < paths.size()) {
            Path path = paths.get(index);
            int firstID = path.getFirstHospitalID() - 1;
            int secondID = path.getSecondHospitalID() - 1;

            if (firstID < 0 || secondID < 0) {
                index++;
                continue;
            }

            int[][] testList = new int[100][100];

            for (int i = index; i < paths.size(); i++) {
                Path newPath = paths.get(i);
                int thirdID = newPath.getFirstHospitalID() - 1;
                int fourthID = newPath.getSecondHospitalID() - 1;
                if (thirdID < 0 || fourthID < 0) {
                    continue;
                }

                if (thirdID != firstID && thirdID != secondID && fourthID != firstID && fourthID != secondID) {

                    double[] intersectionCoords = intersectionPoint(hosps.get(firstID).getPosition(),
                            hosps.get(secondID).getPosition(), hosps.get(thirdID).getPosition(), hosps.get(fourthID).getPosition());

                    if (intersectionCoords != null) {

                        if (testList[firstID][fourthID] == 1 || testList[firstID][thirdID] == 1 || testList[secondID][fourthID] == 1 || testList[secondID][thirdID] == 1) {
                            continue;
                        }

                        if (onLine[firstID][secondID] == 1 || onLine[firstID][thirdID] == 1 || onLine[firstID][fourthID] == 1) {
                            continue;
                        }
                        
                        int intersectionX = (int) Math.round(intersectionCoords[0]);
                        int intersectionY = (int) Math.round(intersectionCoords[1]);

                        int intersectionID = hosps.size() + 1;

                        hosps.add(new Hospital(intersectionID, "INTER", new Coordinate(intersectionX, intersectionY), 0, 0));

                        testList[firstID][intersectionID - 1] = 1;
                        testList[secondID][intersectionID - 1] = 1;
                        testList[thirdID][intersectionID - 1] = 1;
                        testList[fourthID][intersectionID - 1] = 1;

                        onLine[firstID][secondID] = 1;
                        onLine[thirdID][fourthID] = 1;

                        int newPathId = paths.size() + 1;

                        double distanceMultiplier = distanceMultipplier(hosps.get(firstID), hosps.get(secondID), hosps.get(intersectionID - 1));
                        
                        int distance = 0;
                        distance = (int) Math.round(paths.get(index).getDistance() * distanceMultiplier);
                        
                        paths.add(new Path(newPathId, firstID + 1, intersectionID, distance));
                        paths.add(new Path(newPathId + 1, secondID + 1, intersectionID, Math.abs(distance - paths.get(index).getDistance())));

                        distanceMultiplier = distanceMultipplier(hosps.get(thirdID), hosps.get(fourthID), hosps.get(intersectionID - 1));
                        
                        distance = (int) Math.round(paths.get(i).getDistance() * distanceMultiplier);
                        
                        paths.add(new Path(newPathId + 2, thirdID + 1, intersectionID, distance));
                        paths.add(new Path(newPathId + 3, fourthID + 1, intersectionID, Math.abs(distance - paths.get(i).getDistance())));

                        int oldId = paths.get(index).getId();
                        paths.set(index, new Path(oldId, 0, 0, 0));

                        oldId = paths.get(i).getId();
                        paths.set(i, new Path(oldId, 0, 0, 0));

                        intersectionCoords = null;

                    }
                }

            }
            index++;
        }

    }

    private double[] intersectionPoint(Coordinate point1, Coordinate point2, Coordinate point3, Coordinate point4) {
        double firstSegmentX = point2.getX() - point1.getX();
        double firstSegmentY = point2.getY() - point1.getY();
        double secondSegmentX = point4.getX() - point3.getX();
        double secondSegmentY = point4.getY() - point3.getY();

        double determminateX = (-firstSegmentY * (point1.getX() - point3.getX()) + firstSegmentX * (point1.getY() - point3.getY()))
                / (-secondSegmentX * firstSegmentY + firstSegmentX * secondSegmentY);

        double determminateY = (secondSegmentX * (point1.getY() - point3.getY()) - secondSegmentY * (point1.getX() - point3.getX()))
                / (-secondSegmentX * firstSegmentY + firstSegmentX * secondSegmentY);

        if (determminateX > 0 && determminateX < 1 && determminateY > 0 && determminateY < 1) {
            double x = point1.getX() + (determminateY * firstSegmentX);
            double y = point1.getY() + (determminateY * firstSegmentY);

            return new double[]{x, y};
        }

        return null;

    }

    private double distanceMultipplier(Hospital point1, Hospital point2, Hospital intersection) {
        Coordinate point1Coordinate = point1.getPosition();
        Coordinate point2Coordinate = point2.getPosition();
        Coordinate intersectionCoordinate = intersection.getPosition();

        double distance = calculateDistanceBetweenPoints(point1Coordinate, intersectionCoordinate);
        double length = calculateDistanceBetweenPoints(point1Coordinate, point2Coordinate);
             
        return distance/length;
    }

    private double calculateDistanceBetweenPoints(Coordinate point1, Coordinate point2) {
        return Math.sqrt(Math.pow(point2.getY() - point1.getY(), 2) + Math.pow(point2.getX() - point1.getX(), 2));
    }
    
    private void preperePaths(ArrayList<Path> paths){
        paths.forEach((path) -> {
            int firstID = path.getFirstHospitalID();
            int secondID = path.getSecondHospitalID();
            if (secondID < firstID) {
                swapID(path);
            }
        });
    }
    
    private void swapID(Path path){
        int tmp = path.getFirstHospitalID();
        path.setFirstHospitalID(path.getSecondHospitalID());
        path.setSecondHospitalID(tmp);
        
    }
}
