package lab.aisd.algorithm.intersections;

import java.util.List;
import lab.aisd.model.*;
import lab.aisd.util.input.InputData;

public class IntersectionFinder {

    public void intersectionFinder(InputData inputData) {

        List<Path> paths = inputData.getPaths();
        List<Hospital> hosps = inputData.getHospitals();

        if (paths == null || hosps == null) {
            throw new NullPointerException("Either hospitals or paths are null");
        }

        if (hosps.size() < 4 || paths.size() < 2) {
            throw new NullPointerException("Too few hospitals or paths");
        }

        int index = 0;
        int maximumSizeOfHosps = 0;

        int[][] inLineCheck = null;

        try {
            maximumSizeOfHosps = calculateMaxSizeOfHosps(hosps.size());
            inLineCheck = new int[maximumSizeOfHosps][maximumSizeOfHosps];

        } catch (OutOfMemoryError e) {
            maximumSizeOfHosps = 25000;
            inLineCheck = new int[maximumSizeOfHosps][maximumSizeOfHosps];
        }

        preperePaths(paths);

        while (index < paths.size()) {
            if (safetyLoopStop(hosps.size(), maximumSizeOfHosps)) {
                throw new IndexOutOfBoundsException();
            }

            Path path = paths.get(index);
            int firstID = path.getFirstHospitalID() - 1;
            int secondID = path.getSecondHospitalID() - 1;

            if (firstID < 0 || secondID < 0) {
                index++;
                continue;
            }

            int[][] intersetionPointCheck = new int[4000][4000];

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

                        if (intersetionPointCheck[firstID][fourthID] == 1 || intersetionPointCheck[firstID][thirdID] == 1 || intersetionPointCheck[secondID][fourthID] == 1
                                || intersetionPointCheck[secondID][thirdID] == 1) {
                            continue;
                        }

                        if (inLineCheck[firstID][secondID] == 1 || inLineCheck[firstID][thirdID] == 1 || inLineCheck[firstID][fourthID] == 1) {
                            continue;
                        }

                        int intersectionX = (int) Math.round(intersectionCoords[0]);
                        int intersectionY = (int) Math.round(intersectionCoords[1]);

                        int intersectionID = hosps.size() + 1;

                        hosps.add(new Hospital(intersectionID, "INTERSECTION", new Coordinate(intersectionX, intersectionY), 0, 0));

                        intersetionPointCheck[firstID][intersectionID - 1] = 1;
                        intersetionPointCheck[secondID][intersectionID - 1] = 1;
                        intersetionPointCheck[thirdID][intersectionID - 1] = 1;
                        intersetionPointCheck[fourthID][intersectionID - 1] = 1;

                        inLineCheck[firstID][secondID] = 1;
                        inLineCheck[thirdID][fourthID] = 1;

                        addNewPaths(paths, hosps, firstID, secondID, intersectionID, index);
                        addNewPaths(paths, hosps, thirdID, fourthID, intersectionID, i);

                        removeOldPaths(paths, index, i);
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

        if (determminateX >= 0 && determminateX <= 1 && determminateY >= 0 && determminateY <= 1) {
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

        return distance / length;
    }

    private double calculateDistanceBetweenPoints(Coordinate point1, Coordinate point2) {
        return Math.sqrt(Math.pow(point2.getY() - point1.getY(), 2) + Math.pow(point2.getX() - point1.getX(), 2));
    }

    private void addNewPaths(List<Path> paths, List<Hospital> hosps, int firstID, int secondID, int intersectionID, int index) {
        int newPathId = paths.size() + 1;
        double distanceMultiplier = distanceMultipplier(hosps.get(firstID), hosps.get(secondID), hosps.get(intersectionID - 1));

        int distance = 0;
        distance = (int) Math.round(paths.get(index).getDistance() * distanceMultiplier);

        paths.add(new Path(newPathId, firstID + 1, intersectionID, distance));
        paths.add(new Path(newPathId + 1, secondID + 1, intersectionID, Math.abs(distance - paths.get(index).getDistance())));
    }

    private void removeOldPaths(List<Path> paths, int index1, int index2) {
        int oldId = paths.get(index1).getId();
        paths.set(index1, new Path(oldId, 0, 0, 0));

        oldId = paths.get(index2).getId();
        paths.set(index2, new Path(oldId, 0, 0, 0));
    }

    private void preperePaths(List<Path> paths) {
        paths.forEach((path) -> {
            int firstID = path.getFirstHospitalID();
            int secondID = path.getSecondHospitalID();
            if (secondID < firstID) {
                swapID(path);
            }
        });
    }

    private void swapID(Path path) {
        int tmp = path.getFirstHospitalID();
        path.setFirstHospitalID(path.getSecondHospitalID());
        path.setSecondHospitalID(tmp);

    }

    private boolean safetyLoopStop(int actualSizeOfHosps, int maximumSizeOfHosps) {
        return (actualSizeOfHosps > maximumSizeOfHosps);
    }

    private int calculateMaxSizeOfHosps(int size) {
        return size + size * (size - 1) * (size - 2) * (size - 3) / 24;
    }

    /*
            ACCESS METHODS FOR TESTS
     */
    public void testPreperePaths(List<Path> paths) {
        preperePaths(paths);
    }

    public double testCalculateDistanceBetweenPoints(Coordinate point1, Coordinate point2) {
        return calculateDistanceBetweenPoints(point1, point2);
    }

    public double testDistanceMultipplier(Hospital point1, Hospital point2, Hospital intersection) {
        return distanceMultipplier(point1, point2, intersection);
    }

    public double[] testIntersectionPoint(Coordinate point1, Coordinate point2, Coordinate point3, Coordinate point4) {
        return intersectionPoint(point1, point2, point3, point4);
    }
}
