package lab.aisd.util;

import lab.aisd.model.*;
import lab.aisd.util.input.InputData;
import lab.aisd.util.input.InputFileReader;
import lab.aisd.util.input.InvalidFileFormatException;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputFileReaderTest {

    @Test
    public void readMainFile() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | 999 | 99\n"
                + "3 | Pierwszy Szpital im. Prezesa RP | 120 | 130 | 99 | 0\n"
                + "4 | Drugi Szpital im. Naczelnika RP | 10 | 140 | 70 | 1\n"
                + "5 | Trzeci Szpital im. Kr贸la RP | 140 | 10 | 996 | 0\n"
                + "\n"
                + "# Obiekty (id | nazwa | wsp. x | wsp. y)\n"
                + "1 | Pomnik Wikipedii | -1 | 50\n"
                + "2 | Pomnik Fryderyka Chopina | 110 | 55\n"
                + "3 | Pomnik Anonimowego Przechodnia | 40 | 70\n"
                + "\n"
                + "# Drogi (id | id_szpitala | id_szpitala | odleg艂o艣膰)\n"
                + "1 | 1 | 2 | 700\n"
                + "2 | 1 | 4 | 550\n"
                + "3 | 1 | 5 | 800\n"
                + "4 | 2 | 3 | 300\n"
                + "5 | 2 | 4 | 550\n"
                + "6 | 3 | 5 | 600\n"
                + "7 | 4 | 5 | 750");
        InputFileReader inputFileReader = new InputFileReader();
        InputData inputData = null;
        try {
            inputData = inputFileReader.saveMainFileData(reader);
        } catch (Exception e) {
        }

        List<Hospital> hospitals = new ArrayList<>(
                Arrays.asList(new Hospital(1, " Szpital Wojew贸dzki nr 997 ", new Coordinate(10, 10), 1000, 100),
                        new Hospital(2, " Krakowski Szpital Kliniczny ", new Coordinate(100, 120), 999, 99),
                        new Hospital(3, " Pierwszy Szpital im. Prezesa RP ", new Coordinate(120, 130), 99, 0),
                        new Hospital(4, " Drugi Szpital im. Naczelnika RP ", new Coordinate(10, 140), 70, 1),
                        new Hospital(5, " Trzeci Szpital im. Kr贸la RP ", new Coordinate(140, 10), 996, 0)
                )
        );
        assertEquals(hospitals, inputData.getHospitals());

        List<Building> objectsOnMap = new ArrayList<>(
                Arrays.asList(new Building(1, " Pomnik Wikipedii ", new Coordinate(-1, 50)),
                        new Building(2, " Pomnik Fryderyka Chopina ", new Coordinate(110, 55)),
                        new Building(3, " Pomnik Anonimowego Przechodnia ", new Coordinate(40, 70))
                )
        );
        assertEquals(objectsOnMap, inputData.getBuildings());

        List<Path> paths = new ArrayList<>(
                Arrays.asList(new Path(1, 1, 2, 700),
                        new Path(2, 1, 4, 550),
                        new Path(3, 1, 5, 800),
                        new Path(4, 2, 3, 300),
                        new Path(5, 2, 4, 550),
                        new Path(6, 3, 5, 600),
                        new Path(7, 4, 5, 750)
                )
        );
        assertEquals(paths, inputData.getPaths());
    }

    @Test
    public void readMainFileWithIncorrectId() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "abcdefghij | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | 999 | 99\n");

        InputFileReader inputFileReader = new InputFileReader();
        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 2", exception.getMessage());
    }

    @Test
    public void readMainFileWithIncorrectHeaderCount() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | 999 | 99\n"
                + "\n"
                + "# Obiekty (id | nazwa | wsp. x | wsp. y)\n"
                + "1 | Pomnik Wikipedii | -1 | 50\n"
                + "2 | Pomnik Fryderyka Chopina | 110 | 55\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Incorrect number of headers. Expected number of headers: 3", exception.getMessage());
    }

    @Test
    public void readMainFileWithIdOutOfBounds() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "10000000000000000000000000000000000000000000000000000000000000000000 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | 999 | 99\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 2", exception.getMessage());
    }

    @Test
    public void readMainFileWithHospitalIdNotUnique() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "1 | Krakowski Szpital Kliniczny | 100 | 120 | 999 | 99\n"
                + "3 | Pierwszy Szpital im. Prezesa RP | 120 | 130 | 99 | 0\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 3", exception.getMessage());
    }

    @Test
    public void readMainFileWithEmptyHospitalName() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | | 100 | 120 | 999 | 99\n"
                + "3 | Pierwszy Szpital im. Prezesa RP | 120 | 130 | 99 | 0\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 3", exception.getMessage());
    }

    @Test
    public void readMainFileWithNegativeBedsCount() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | -999 | 99\n"
                + "3 | Pierwszy Szpital im. Prezesa RP | 120 | 130 | 99 | 0\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 3", exception.getMessage());
    }

    @Test
    public void readMainFileWithNegativeEMptyBedsCount() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | 999 | -99\n"
                + "3 | Pierwszy Szpital im. Prezesa RP | 120 | 130 | 99 | 0\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 3", exception.getMessage());
    }

    @Test
    public void readMainFileWithMoreEmptyBedsThanBedsInHospital() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | 99 | 999\n"
                + "3 | Pierwszy Szpital im. Prezesa RP | 120 | 130 | 99 | 0\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 3", exception.getMessage());
    }

    @Test
    public void readMainFileWithoutPaths() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 丑縠k | Liczba wolnych 丑縠k)\n"
                + "1 | Szpital Wojew骴zki nr 997 | 10 | 10 | 1000 | 100\n"
                + "\n"
                + "# Obiekty (id | nazwa | wsp. x | wsp. y)\n"
                + "1 | Pomnik Wikipedii | -1 | 50\n"
                + "\n"
                + "# Drogi (id | id_szpitala | id_szpitala | odleg硂滄)\n");
        
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Not enough paths", exception.getMessage());
    }
    
    @Test
    public void readMainFileWithDuplicatedIDInPaths() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 丑縠k | Liczba wolnych 丑縠k)\n"
                + "1 | Szpital Wojew骴zki nr 997 | 10 | 10 | 1000 | 100\n"
                + "\n"
                + "# Obiekty (id | nazwa | wsp. x | wsp. y)\n"
                + "1 | Pomnik Wikipedii | -1 | 50\n"
                + "\n"
                + "# Drogi (id | id_szpitala | id_szpitala | odleg硂滄)\n"
                + "1 | 1 | 1 | 700\n"
                + "\n");
        
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 8", exception.getMessage());
    }

    @Test
    public void readMainFileWithObjectIdNotUnique() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | 999 | 99\n"
                + "\n"
                + "# Obiekty (id | nazwa | wsp. x | wsp. y)\n"
                + "1 | Pomnik Wikipedii | -1 | 50\n"
                + "1 | Pomnik Fryderyka Chopina | 110 | 55\n"
                + "3 | Pomnik Anonimowego Przechodnia | 40 | 70\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 7", exception.getMessage());
    }

    @Test
    public void readMainFileWithIncorrectHospitalIdsInPath() {
        Reader reader = new StringReader("# Szpitale (id | nazwa | wsp. x | wsp. y | Liczba 艂贸偶ek | Liczba wolnych 艂贸偶ek)\n"
                + "1 | Szpital Wojew贸dzki nr 997 | 10 | 10 | 1000 | 100\n"
                + "2 | Krakowski Szpital Kliniczny | 100 | 120 | 999 | 99\n"
                + "\n"
                + "# Obiekty (id | nazwa | wsp. x | wsp. y)\n"
                + "1 | Pomnik Wikipedii | -1 | 50\n"
                + "\n"
                + "# Drogi (id | id_szpitala | id_szpitala | odleg艂o艣膰)\n"
                + "1 | 100 | 2 | 700\n"
                + "2 | 1 | 4 | 550\n"
                + "3 | 1 | 5 | 800\n");
        InputFileReader inputFileReader = new InputFileReader();

        Throwable exception = assertThrows(InvalidFileFormatException.class, () -> inputFileReader.saveMainFileData(reader));
        assertEquals("Invalid file format in line 9", exception.getMessage());
    }

    @Test
    public void readPatientFile() {
        Reader reader = new StringReader("# Pacjenci (id | wsp. x | wsp.y)\n"
                + "1 | 20 | 20\n"
                + "2 | 99 | 105\n"
                + "3 | 23 | 40");
        List<Patient> patients = new ArrayList<>(
                Arrays.asList(new Patient(1, new Coordinate(20, 20)),
                        new Patient(2, new Coordinate(99, 105)),
                        new Patient(3, new Coordinate(23, 40)))
        );
        InputFileReader inputFileReader = new InputFileReader();
        List<Patient> patientsActual = null;
        try {
            patientsActual = inputFileReader.savePatientFileData(reader);
        } catch (Exception e) {
        }
        assertEquals(patients, patientsActual);
    }
}
