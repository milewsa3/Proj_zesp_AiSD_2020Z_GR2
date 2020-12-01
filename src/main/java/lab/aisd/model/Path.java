package lab.aisd.model;

public class Path {
    private int id;
    private int firstHospitalID;
    private int secondHospitalID;
    private int distance;

    public Path(int id, int firstHospitalID, int secondHospitalID, int distance) {
        this.id = id;
        this.firstHospitalID = firstHospitalID;
        this.secondHospitalID = secondHospitalID;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstHospitalID() {
        return firstHospitalID;
    }

    public void setFirstHospitalID(int firstHospitalID) {
        this.firstHospitalID = firstHospitalID;
    }

    public int getSecondHospitalID() {
        return secondHospitalID;
    }

    public void setSecondHospitalID(int secondHospitalID) {
        this.secondHospitalID = secondHospitalID;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
