package lab.aisd.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import lab.aisd.model.Hospital;

import java.net.URL;
import java.util.ResourceBundle;

public class HospitalInfoController implements Initializable {

    @FXML
    private TextArea hospitalInfoTa;

    public void setHospitalInfo(Hospital hospital) {
        String info = generateInfo(hospital);

        hospitalInfoTa.setText(info);
    }

    private String generateInfo(Hospital hospital) {
        return "ID: " + hospital.getId() + '\n' +
                "Name: " + hospital.getName() + '\n'  +
                "Coordinate x: " + hospital.getPosition().getX() + '\n' +
                "Coordinate y: " + hospital.getPosition().getY() + '\n' +
                "Number of all beds: " + hospital.getBedsCount() + '\n' +
                "Number of free beds: " + hospital.getFreeBedsCount();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
