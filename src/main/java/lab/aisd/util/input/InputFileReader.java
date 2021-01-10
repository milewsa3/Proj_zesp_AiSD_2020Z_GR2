package lab.aisd.util.input;

import lab.aisd.model.*;

import java.io.*;
import java.util.*;

public class InputFileReader {
    private int header;
    private String line;

    public InputData readMainFile(String pathToFile) throws Exception {
        FileReader fileReader = getFileReader(pathToFile);
        return saveMainFileData(fileReader);
    }

    public List<Patient> readPatientsFile(String pathToFile) throws Exception {
        FileReader fileReader = getFileReader(pathToFile);
        return savePatientFileData(fileReader);
    }

    private FileReader getFileReader(String pathToFile) throws FileNotFoundException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(pathToFile);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File does not exist.");
        }
        return fileReader;
    }

    public InputData saveMainFileData(Reader fileReader) throws Exception {
        header = 0;
        int lineNumber = 0;
        InputData inputData = new InputData();
        BufferedReader reader = new BufferedReader(fileReader);
        line = reader.readLine();

        while (line != null) {
            lineNumber++;

            if (canContinue(reader)) {
                continue;
            }

            switch (header) {
                case 1 -> {
                    Integer nextHospitalId = inputData.getHospitals().size() + 1;
                    inputData.getHospitals().add(readHospital(line, lineNumber, nextHospitalId));
                }
                case 2 -> {
                    Integer nextBuildingId = inputData.getBuildings().size() + 1;
                    inputData.getBuildings().add(readObjectOnMap(line, lineNumber, nextBuildingId));
                }
                case 3 -> {
                    Integer hospitalCount = inputData.getHospitals().size();
                    Integer nextPathId = inputData.getPaths().size() + 1;
                    inputData.getPaths().add(readPath(line, lineNumber, hospitalCount, nextPathId));
                }
            }
            line = reader.readLine();
        }

        reader.close();
        isCorrectHeaderCount(3, header);

        return inputData;
    }

    private Hospital readHospital(String line, int lineNumber, Integer nextHospitalId) throws InvalidFileFormatException {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y, bedsCount, freeBedsCount;
        String name;
        try {
            id = Integer.parseInt(scanner.next().trim());
            ensureSortedId(nextHospitalId, id);
            name = scanner.next();
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            bedsCount = Integer.parseInt(scanner.next().trim());
            freeBedsCount = Integer.parseInt(scanner.next().trim());
            return new Hospital(id, name, new Coordinate(x, y), bedsCount, freeBedsCount);
        } catch (NumberFormatException | NoSuchElementException | InvalidFileFormatException e) {
            throw new InvalidFileFormatException("Invalid file format in line " + lineNumber);
        }
    }

    private Building readObjectOnMap(String line, int lineNumber, Integer nextBuildingId) throws InvalidFileFormatException {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y;
        String name;
        try {
            id = Integer.parseInt(scanner.next().trim());
            ensureSortedId(nextBuildingId, id);
            name = scanner.next();
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            return new Building(id, name, new Coordinate(x, y));
        } catch (NumberFormatException | NoSuchElementException | InvalidFileFormatException e) {
            throw new InvalidFileFormatException("Invalid file format in line " + lineNumber);
        }
    }

    private Path readPath(String line, int lineNumber, Integer hospitalsCount, Integer nextPathId) throws InvalidFileFormatException {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, firstHospitalID, secondHospitalID, distance;
        try {
            id = Integer.parseInt(scanner.next().trim());
            ensureSortedId(nextPathId, id);
            firstHospitalID = Integer.parseInt(scanner.next().trim());
            secondHospitalID = Integer.parseInt(scanner.next().trim());
            if (!isNumberInRange(1, hospitalsCount, firstHospitalID) || !isNumberInRange(1, hospitalsCount, secondHospitalID)) {
                throw new InvalidFileFormatException("");
            }
            distance = Integer.parseInt(scanner.next().trim());
            return new Path(id, firstHospitalID, secondHospitalID, distance);
        } catch (NumberFormatException | NoSuchElementException | InvalidFileFormatException e) {
            throw new InvalidFileFormatException("Invalid file format in line " + lineNumber);
        }
    }

    private void ensureSortedId(Integer idExpected, Integer idActual) throws InvalidFileFormatException {
        if (!idExpected.equals(idActual)) {
            throw new InvalidFileFormatException("");
        }
    }

    private boolean isNumberInRange(int start, int end, int number) {
        return number >= start && number <= end;
    }

    public List<Patient> savePatientFileData(Reader fileReader) throws Exception {
        List<Patient> patients = new ArrayList<>();
        int lineNumber = 0;
        header = 0;
        BufferedReader reader = new BufferedReader(fileReader);
        line = reader.readLine();

        while (line != null) {
            lineNumber++;
            if (canContinue(reader)) {
                continue;
            }
            if (header == 1) {
                Integer nextPatientId = patients.size() + 1;
                patients.add(readPatient(line, lineNumber, nextPatientId));
            }
            line = reader.readLine();
        }
        isCorrectHeaderCount(1, header);
        reader.close();
        return patients;
    }

    private Patient readPatient(String line, int lineNumber, Integer nextPatientId) throws InvalidFileFormatException {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y;
        try {
            id = Integer.parseInt(scanner.next().trim());
            ensureSortedId(nextPatientId, id);
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            return new Patient(id, new Coordinate(x, y));
        } catch (NumberFormatException | NoSuchElementException | InvalidFileFormatException e) {
            throw new InvalidFileFormatException("Invalid file format in line " + lineNumber);
        }
    }

    private boolean isNextLineHeader(String line) {
        return line.trim().startsWith("#");
    }

    private void isCorrectHeaderCount(int headerCountExpected, int headerCountActual) throws InvalidFileFormatException {
        if (headerCountExpected != headerCountActual) {
            throw new InvalidFileFormatException("Incorrect number of headers. Expected number of headers: "
                    + headerCountExpected);
        }
    }

    private boolean canContinue(BufferedReader reader) throws IOException {
        if (line.equals("")) {
            line = reader.readLine();
            return true;
        }
        if (isNextLineHeader(line)) {
            header++;
            line = reader.readLine();
            return true;
        }
        return false;
    }
}

