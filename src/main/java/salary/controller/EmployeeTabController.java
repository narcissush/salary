package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import salary.model.entity.Employee;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeTabController implements Initializable {
    @FXML
    private TextField firstNameTxt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
    public void setEmployee(Employee employee) {
        firstNameTxt.setText("heloo");
    }
}
