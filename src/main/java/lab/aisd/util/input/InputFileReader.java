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
            throw new FileNotFoundException("Podany plik nie istnieje.");
        }
        return fileReader;
    }

    public InputData saveMainFileData(Reader fileReader) throws Exception {
        header = 0;
        int lineNumber = 0;
        Map<Integer, Integer> hospitalIds = new HashMap<>();
        Map<Integer, Integer> objectIds = new HashMap<>();
        Map<Integer, Integer> pathIds = new HashMap<>();
        InputData inputData = new InputData();
        BufferedReader reader = new BufferedReader(fileReader);
        line = reader.readLine();

        while (line != null) {
            lineNumber++;

            if (canContinue(reader)) {
                continue;
            }

            switch (header) {
                case 1 -> inputData.getHospitals().add(readHospital(line, lineNumber, hospitalIds));
                case 2 -> inputData.getBuildings().add(readObjectOnMap(line, lineNumber, objectIds));
                case 3 -> inputData.getPaths().add(readPath(line, lineNumber, hospitalIds, pathIds));
            }
            line = reader.readLine();
        }

        reader.close();
        isCorrectHeaderCount(3, header);

        return inputData;
    }

    private Hospital readHospital(String line, int lineNumber, Map<Integer, Integer> hospitalIds) throws InvalidFileFormatException {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y, bedsCount, freeBedsCount;
        String name;
        try {
            id = Integer.parseInt(scanner.next().trim());
            ensureIdUniqueness(id, hospitalIds);
            name = scanner.next();
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            bedsCount = Integer.parseInt(scanner.next().trim());
            freeBedsCount = Integer.parseInt(scanner.next().trim());
            return new Hospital(id, name, new Coordinate(x, y), bedsCount, freeBedsCount);
        } catch (NumberFormatException | NoSuchElementException | InvalidFileFormatException e) {
            throw new InvalidFileFormatException("Błędny format pliku w linii " + lineNumber);
        }
    }

    private Building readObjectOnMap(String line, int lineNumber, Map<Integer, Integer> objectIds) throws InvalidFileFormatException {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y;
        String name;
        try {
            id = Integer.parseInt(scanner.next().trim());
            ensureIdUniqueness(id, objectIds);
            name = scanner.next();
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            return new Building(id, name, new Coordinate(x, y));
        } catch (NumberFormatException | NoSuchElementException | InvalidFileFormatException e) {
            throw new InvalidFileFormatException("Błędny format pliku w linii " + lineNumber);
        }
    }

    private Path readPath(String line, int lineNumber, Map<Integer, Integer> hospitalIds, Map<Integer, Integer> pathIds) throws InvalidFileFormatException {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, firstHospitalID, secondHospitalID, distance;
        try {
            id = Integer.parseInt(scanner.next().trim());
            ensureIdUniqueness(id, pathIds);
            firstHospitalID = Integer.parseInt(scanner.next().trim());
            secondHospitalID = Integer.parseInt(scanner.next().trim());
            if (!hospitalIds.containsKey(firstHospitalID) || !hospitalIds.containsKey(secondHospitalID)) {
                throw new InvalidFileFormatException("");
            }
            distance = Integer.parseInt(scanner.next().trim());
            return new Path(id, firstHospitalID, secondHospitalID, distance);
        } catch (NumberFormatException | NoSuchElementException | InvalidFileFormatException e) {
            throw new InvalidFileFormatException("Błędny format pliku w linii " + lineNumber);
        }
    }

    private void ensureIdUniqueness(Integer id, Map<Integer, Integer> map) throws InvalidFileFormatException {
        if (map.containsKey(id)) {
            throw new InvalidFileFormatException("");
        } else {
            map.put(id, id);
        }
    }

    public List<Patient> savePatientFileData(Reader fileReader) throws Exception {
        List<Patient> patients = new ArrayList<>();
        int lineNumber = 0;
        header = 0;
        Map<Integer, Integer> patientIds = new HashMap<>();
        BufferedReader reader = new BufferedReader(fileReader);
        line = reader.readLine();

        while (line != null) {
            lineNumber++;
            if (canContinue(reader)) {
                continue;
            }
            if (header == 1) {
                patients.add(readPatient(line, lineNumber, patientIds));
            }
            line = reader.readLine();
        }
        isCorrectHeaderCount(1, header);
        reader.close();
        return patients;
    }

    private Patient readPatient(String line, int lineNumber, Map<Integer, Integer> patientIds) throws InvalidFileFormatException {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y;
        try {
            id = Integer.parseInt(scanner.next().trim());
            ensureIdUniqueness(id, patientIds);
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            return new Patient(id, new Coordinate(x, y));
        } catch (NumberFormatException | NoSuchElementException | InvalidFileFormatException e) {
            throw new InvalidFileFormatException("Błędny format pliku w linii " + lineNumber);
        }
    }

    private boolean isNextLineHeader(String line) {
        return line.trim().startsWith("#");
    }

    private void isCorrectHeaderCount(int headerCountExpected, int headerCountActual) throws InvalidFileFormatException {
        if (headerCountExpected != headerCountActual) {
            throw new InvalidFileFormatException("Niepoprawna liczba nagłówków. Oczekiwana liczba nagłówków to: "
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

