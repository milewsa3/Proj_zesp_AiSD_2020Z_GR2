package lab.aisd.util.input;

import lab.aisd.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputFileReader {
    public InputData readMainFile(String pathToFile) throws Exception {
        existsFile(pathToFile);
        return saveMainFileData(new FileReader(pathToFile));
    }

    public List<Patient> readPatientsFile(String pathToFile) throws Exception {
        existsFile(pathToFile);
        return savePatientFileData(new FileReader(pathToFile));
    }

    public InputData saveMainFileData(Reader fileReader) throws Exception {
        BufferedReader reader;
        int header = 0;
        int lineNumber = 0;
        InputData inputData = new InputData();
        try {
            reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                lineNumber++;
                if(line.equals("")){
                    line = reader.readLine();
                    continue;
                }
                if (isNextLineHeader(line)) {
                    header++;
                    line = reader.readLine();
                    continue;
                }
                switch (header) {
                    case 1 -> inputData.getHospitals().add(readHospital(line, lineNumber));
                    case 2 -> inputData.getObjectsOnMap().add(readObjectOnMap(line, lineNumber));
                    case 3 -> inputData.getPaths().add(readPath(line, lineNumber));
                }
                line = reader.readLine();
            }
            reader.close();
            if (header != 3) {
                throw new Exception("Niepoprawna liczba nagłówków. Plik powinien zawierać 3 nagłówki");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return inputData;
    }

    private Hospital readHospital(String line, int lineNumber) throws Exception {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y, bedsCount, freeBedsCount;
        String name;
        try {
            id = Integer.parseInt(scanner.next().trim());
            name = scanner.next();
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            bedsCount = Integer.parseInt(scanner.next().trim());
            freeBedsCount = Integer.parseInt(scanner.next().trim());
            return new Hospital(id, name, new Coordinate(x, y), bedsCount, freeBedsCount);
        } catch (Exception e) {
            throw new Exception("Błędny format pliku w linii " + lineNumber);
        }
    }

    private ObjectOnMap readObjectOnMap(String line, int lineNumber) throws Exception {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y;
        String name;
        try {
            id = Integer.parseInt(scanner.next().trim());
            name = scanner.next();
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            return new ObjectOnMap(id, name, new Coordinate(x, y));
        } catch (Exception e) {
            throw new Exception("Błędny format pliku w linii " + lineNumber);
        }
    }

    private Path readPath(String line, int lineNumber) throws Exception {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, firstHospitalID, secondHospitalID, distance;
        try {
            id = Integer.parseInt(scanner.next().trim());
            firstHospitalID = Integer.parseInt(scanner.next().trim());
            secondHospitalID = Integer.parseInt(scanner.next().trim());
            distance = Integer.parseInt(scanner.next().trim());
            return new Path(id, firstHospitalID, secondHospitalID, distance);
        } catch (Exception e) {
            throw new Exception("Błędny format pliku w linii " + lineNumber);
        }
    }

    public List<Patient> savePatientFileData(Reader fileReader) throws Exception {
        BufferedReader reader;
        List<Patient> patients = new ArrayList<>();
        int lineNumber = 0;
        int header = 0;
        try {
            reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                lineNumber++;
                if(line.equals("")){
                    line = reader.readLine();
                    continue;
                }
                if (isNextLineHeader(line)) {
                    header++;
                    line = reader.readLine();
                    continue;
                }
                if (header == 1) {
                    patients.add(readPatient(line, lineNumber));
                }
                line = reader.readLine();
            }
            if (header != 1) {
                throw new Exception("Niepoprawna liczba nagłówków. Plik powinien zawierać jeden nagłówek");
            }
            reader.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return patients;
    }

    private Patient readPatient(String line, int lineNumber) throws Exception {
        Scanner scanner = new Scanner(line).useDelimiter("\\|");
        int id, x, y;
        try {
            id = Integer.parseInt(scanner.next().trim());
            x = Integer.parseInt(scanner.next().trim());
            y = Integer.parseInt(scanner.next().trim());
            return new Patient(id, new Coordinate(x, y));
        } catch (Exception e) {
            throw new Exception("Błędny format pliku w linii " + lineNumber);
        }
    }

    private void existsFile(String pathToFile) throws Exception {
        File tmpDir = new File(pathToFile);
        if (!tmpDir.exists() || !tmpDir.isFile()) {
            throw new Exception("Podany plik nie istnieje.");
        }
    }

    private boolean isNextLineHeader(String line) {
        return line.trim().startsWith("#");
    }
}
